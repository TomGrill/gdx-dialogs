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
import com.badlogic.gdx.utils.reflect.Method;
import com.badlogic.gdx.utils.reflect.ReflectionException;

public class DialogSystem {

	private static final String TAG = "gdx-dialogs";

	private Class<?> gdxClazz = null;
	private Object gdxAppObject = null;

	private Class<?> gdxLifecycleListenerClazz = null;
	private Method gdxAppAddLifecycleListenerMethod = null;

	private DialogManager dialogManager;

	public DialogSystem() {

		loadGdxReflections();

		tryLoadAndroidDialogs();
		// tryLoadIOSDialogs();
		// tryLoadDesktopDialogs();
		// tryLoadHTMLDialogs();

		if (dialogManager == null) {
			dialogManager = new NullDialogManager();
		}
	}

	private void loadGdxReflections() {

		try {
			gdxClazz = ClassReflection.forName("com.badlogic.gdx.Gdx");
			gdxAppObject = ClassReflection.getField(gdxClazz, "app").get(null);
			gdxLifecycleListenerClazz = ClassReflection.forName("com.badlogic.gdx.LifecycleListener");
			gdxAppAddLifecycleListenerMethod = ClassReflection.getMethod(gdxAppObject.getClass(), "addLifecycleListener", gdxLifecycleListenerClazz);

		} catch (ReflectionException e) {
			e.printStackTrace();
			throw new RuntimeException("No libGDX environment. \n");
		}

	}

	private void tryLoadDesktopDialogs() {
		if (Gdx.app.getType() != ApplicationType.Desktop) {
			Gdx.app.debug(TAG, "Skip loading gdx-dialogs for Desktop. Not running Desktop. \n");
			return;
		}
		try {

			final Class<?> dialogManagerClazz = ClassReflection.forName("de.tomgrill.gdxdialogs.desktop.DesktopDialogManager");

			Object dialogManager = ClassReflection.getConstructor(dialogManagerClazz).newInstance();

			this.dialogManager = (DialogManager) dialogManager;

			Gdx.app.debug(TAG, "gdx-dialogs for Desktop loaded successfully.");

		} catch (ReflectionException e) {
			Gdx.app.debug(TAG, "Error creating gdx-dialogs for Desktop (are the gdx-dialogs **.jar files installed?). \n");
			e.printStackTrace();
		}

	}

	private void tryLoadHTMLDialogs() {
		if (Gdx.app.getType() != ApplicationType.WebGL) {
			Gdx.app.debug(TAG, "Skip loading gdx-dialogs for HTML. Not running HTML. \n");
			return;
		}

		try {

			final Class<?> dialogManagerClazz = ClassReflection.forName("de.tomgrill.gdxdialogs.html.HTMLDialogManager");
			Object dialogManager = ClassReflection.getConstructor(dialogManagerClazz).newInstance();

			this.dialogManager = (DialogManager) dialogManager;

			Gdx.app.debug(TAG, "gdx-dialogs for HTML loaded successfully.");

		} catch (ReflectionException e) {
			Gdx.app.debug(TAG, "Error creating gdx-dialogs for HTML (are the gdx-dialogs **.jar files installed?). \n");
			e.printStackTrace();
		}

	}

	private void tryLoadIOSDialogs() {

		if (Gdx.app.getType() != ApplicationType.iOS) {
			Gdx.app.debug(TAG, "Skip loading gdx-dialogs for iOS. Not running iOS. \n");
			return;
		}
		try {
			Class<?> iosApplicationClazz = ClassReflection.forName("com.badlogic.gdx.backends.iosrobovm.IOSApplication");

			final Class<?> dialogManagerClazz = ClassReflection.forName("de.tomgrill.gdxdialogs.ios.IOSDialogManager");

			Object application = null;

			if (ClassReflection.isAssignableFrom(iosApplicationClazz, gdxAppObject.getClass())) {

				application = gdxAppObject;
			} else {
				System.out.println("SHITS");
			}

			Object dialogManager = ClassReflection.getConstructor(dialogManagerClazz, iosApplicationClazz).newInstance(application);

			this.dialogManager = (DialogManager) dialogManager;

			Gdx.app.debug(TAG, "gdx-dialogs for iOS loaded successfully.");

		} catch (ReflectionException e) {
			Gdx.app.debug(TAG, "Error creating gdx-dialogs for iOS (are the gdx-dialogs **.jar files installed?). \n");
			e.printStackTrace();
		}

	}

	private void tryLoadAndroidDialogs() {

		if (Gdx.app.getType() != ApplicationType.Android) {
			Gdx.app.debug(TAG, "Skip loading gdx-dialogs for Android. Not running Android. \n");
			return;
		}

		if (Gdx.app.getType() == ApplicationType.Android) {
			try {

				Class<?> gdxAndroidEventListenerClazz = ClassReflection.forName("com.badlogic.gdx.backends.android.AndroidEventListener");

				Class<?> activityClazz = ClassReflection.forName("android.app.Activity");

				Class<?> dialogManagerClazz = ClassReflection.forName("de.tomgrill.gdxdialogs.android.AndroidDialogManager");

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

				Method gdxAppAddAndroidEventListenerMethod = ClassReflection.getMethod(gdxAppObject.getClass(), "addAndroidEventListener", gdxAndroidEventListenerClazz);
				gdxAppAddAndroidEventListenerMethod.invoke(gdxAppObject, dialogManager);

				gdxAppAddLifecycleListenerMethod.invoke(gdxAppObject, dialogManager);

				this.dialogManager = (DialogManager) dialogManager;

				Gdx.app.debug(TAG, "gdx-dialogs for Android loaded successfully.");

			} catch (Exception e) {
				Gdx.app.debug(TAG, "Error creating gdx-dialogs for Android (are the gdx-dialogs **.jar files installed?). \n");
				e.printStackTrace();
			}
		}
	}

	public DialogManager getPlayServices() {
		return dialogManager;
	}

	/** @return null if class is not available in runtime */
	private static Class<?> findClass(String name) {
		try {
			return ClassReflection.forName(name);
		} catch (Exception e) {
			return null;
		}
	}

}
