/*******************************************************************************
 * Copyright 2015 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package de.tomgrill.gdxdialogs.core;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;

public class GDXDialogsSystem {

	private Class<?> gdxClazz = null;
	private Object gdxAppObject = null;

	private GDXDialogs gdxDialogs;

	private static GDXDialogsSystem instance;

	private GDXDialogsSystem() {

	}

	public static GDXDialogs install() {
		instance = new GDXDialogsSystem();
		instance.installSystem();
		return GDXDialogsSystem.getDialogManager();
	}

	private void installSystem() {
		installGdxReflections();

		installAndroidGDXDialogs();
		installIOSGDXDialogs();
		installDesktopGDXDialogs();
		installHTMLGDXDialogs();

		if (gdxDialogs == null) {
			gdxDialogs = new FallbackGDXDialogs();
		}
	}

	private void installGdxReflections() {

		try {
			gdxClazz = ClassReflection.forName("com.badlogic.gdx.Gdx");
			gdxAppObject = ClassReflection.getField(gdxClazz, "app").get(null);

		} catch (ReflectionException e) {
			e.printStackTrace();
			throw new RuntimeException("No libGDX environment. \n");
		}

	}

	private void installDesktopGDXDialogs() {
		if (Gdx.app.getType() != ApplicationType.Desktop) {
			showDebugSkipInstall(ApplicationType.Desktop.name());
			return;
		}
		try {

			final Class<?> dialogManagerClazz = ClassReflection.forName("de.tomgrill.gdxdialogs.desktop.DesktopGDXDialogs");

			Object dialogManager = ClassReflection.getConstructor(dialogManagerClazz).newInstance();

			this.gdxDialogs = (GDXDialogs) dialogManager;

			showDebugInstallSuccessful(ApplicationType.Desktop.name());

		} catch (ReflectionException e) {
			showErrorInstall(ApplicationType.Desktop.name(), "desktop");
			e.printStackTrace();
		}

	}

	private void installHTMLGDXDialogs() {
		if (Gdx.app.getType() != ApplicationType.WebGL) {
			showDebugSkipInstall(ApplicationType.WebGL.name());
			return;
		}

		try {

			final Class<?> dialogManagerClazz = ClassReflection.forName("de.tomgrill.gdxdialogs.html.HTMLGDXDialogs");
			Object dialogManager = ClassReflection.getConstructor(dialogManagerClazz).newInstance();

			this.gdxDialogs = (GDXDialogs) dialogManager;
			showDebugInstallSuccessful(ApplicationType.WebGL.name());

		} catch (ReflectionException e) {
			showErrorInstall(ApplicationType.WebGL.name(), "html");
			e.printStackTrace();
		}

	}

	private void installIOSGDXDialogs() {

		if (Gdx.app.getType() != ApplicationType.iOS) {
			showDebugSkipInstall(ApplicationType.iOS.name());
			return;
		}
		// there doesn't seem to be an easy way to tell robovm and moe apart
		try {

			final Class<?> dialogManagerClazz = ClassReflection.forName("de.tomgrill.gdxdialogs.ios.IOSGDXDialogs");

			Object dialogManager = ClassReflection.getConstructor(dialogManagerClazz).newInstance();

			this.gdxDialogs = (GDXDialogs) dialogManager;
			showDebugInstallSuccessful(ApplicationType.iOS.name()+"-robovm");

		} catch (ReflectionException e) {
			try {
				final Class<?> dialogManagerClazz = ClassReflection.forName("de.tomgrill.gdxdialogs.iosmoe.IOSMOEGDXDialogs");

				Object dialogManager = ClassReflection.getConstructor(dialogManagerClazz).newInstance();

				this.gdxDialogs = (GDXDialogs) dialogManager;
				showDebugInstallSuccessful(ApplicationType.iOS.name() + "-moe");
			} catch (ReflectionException ex) {
				showErrorInstall(ApplicationType.iOS.name(), "ios");
				e.printStackTrace();

				showErrorInstall(ApplicationType.iOS.name(), "ios-moe");
				ex.printStackTrace();
			}
		}

	}

	private void installAndroidGDXDialogs() {

		if (Gdx.app.getType() != ApplicationType.Android) {
			showDebugSkipInstall(ApplicationType.Android.name());
			return;
		}

		if (Gdx.app.getType() == ApplicationType.Android) {
			try {

				Class<?> activityClazz = ClassReflection.forName("android.app.Activity");

				Class<?> dialogManagerClazz = ClassReflection.forName("de.tomgrill.gdxdialogs.android.AndroidGDXDialogs");

				Object activity = null;

				if (ClassReflection.isAssignableFrom(activityClazz, gdxAppObject.getClass())) {

					activity = gdxAppObject;
				} else {

					Class<?> supportFragmentClass = findClass("android.support.v4.app.Fragment");
					// {
					if (supportFragmentClass != null && ClassReflection.isAssignableFrom(supportFragmentClass, gdxAppObject.getClass())) {

						activity = ClassReflection.getMethod(supportFragmentClass, "getActivity").invoke(gdxAppObject);
					} else {
						Class<?> fragmentClass = findClass("android.app.Fragment");
						if (fragmentClass != null && ClassReflection.isAssignableFrom(fragmentClass, gdxAppObject.getClass())) {
							activity = ClassReflection.getMethod(fragmentClass, "getActivity").invoke(gdxAppObject);
						}
					}

				}

				if (activity == null) {
					throw new RuntimeException("Can't find your gdx activity to instantiate gdx-dialogs. " + "Looks like you have implemented AndroidApplication without using "
							+ "Activity or Fragment classes or Activity is not available at the moment");
				}
				Object dialogManager = ClassReflection.getConstructor(dialogManagerClazz, activityClazz).newInstance(activity);

				this.gdxDialogs = (GDXDialogs) dialogManager;
				showDebugInstallSuccessful(ApplicationType.Android.name());

			} catch (Exception e) {
				showErrorInstall(ApplicationType.Android.name(), "android");
				e.printStackTrace();
			}
		}
	}

	public static GDXDialogs getDialogManager() {
		return instance.gdxDialogs;
	}

	private static Class<?> findClass(String name) {
		try {
			return ClassReflection.forName(name);
		} catch (Exception e) {
			return null;
		}
	}

	private void showDebugSkipInstall(String os) {
		Gdx.app.debug(GDXDialogsVars.LOG_TAG, "Skip installing " + GDXDialogsVars.LOG_TAG + " for " + os + ". Not running " + os + ". \n");
	}

	private void showErrorInstall(String os, String artifact) {
		Gdx.app.error(GDXDialogsVars.LOG_TAG, "Error installing " + GDXDialogsVars.LOG_TAG + " for " + os + "\n");
		Gdx.app.error(GDXDialogsVars.LOG_TAG, "Did you add compile >> \"de.tomgrill.gdxdialogs:gdx-dialogs-" + artifact + ":" + GDXDialogsVars.VERSION
				+ "\" << to your gradle dependencies?\n");
	}

	private void showDebugInstallSuccessful(String os) {
		Gdx.app.debug(GDXDialogsVars.LOG_TAG, GDXDialogsVars.LOG_TAG + " for " + os + " installed successfully.");
	}
}
