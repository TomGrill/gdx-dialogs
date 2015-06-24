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

package de.tomgrill.gdxdialogs.android;

import android.app.Activity;

import com.badlogic.gdx.utils.reflect.ClassReflection;

import de.tomgrill.gdxdialogs.android.dialogs.AndroidGDXButtonDialog;
import de.tomgrill.gdxdialogs.android.dialogs.AndroidGDXProgressDialog;
import de.tomgrill.gdxdialogs.android.dialogs.AndroidGDXTextPrompt;
import de.tomgrill.gdxdialogs.core.GDXDialogs;
import de.tomgrill.gdxdialogs.core.dialogs.GDXButtonDialog;
import de.tomgrill.gdxdialogs.core.dialogs.GDXProgressDialog;
import de.tomgrill.gdxdialogs.core.dialogs.GDXTextPrompt;

public class AndroidGDXDialogs extends GDXDialogs {

	private Activity activity;

	public AndroidGDXDialogs(Activity activity) {
		this.activity = activity;

		registerDialog(GDXButtonDialog.class.getName(), AndroidGDXButtonDialog.class.getName());
		registerDialog(GDXProgressDialog.class.getName(), AndroidGDXProgressDialog.class.getName());
		registerDialog(GDXTextPrompt.class.getName(), AndroidGDXTextPrompt.class.getName());

	}

	@Override
	public <T> T newDialog(Class<T> cls) {
		String className = cls.getName();
		if (registeredDialogs.containsKey(className)) {

			try {
				final Class<T> dialogClazz = ClassReflection.forName(registeredDialogs.get(className));

				Object dialogObject = ClassReflection.getConstructor(dialogClazz, Activity.class).newInstance(activity);

				return dialogClazz.cast(dialogObject);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		throw new RuntimeException(cls.getName() + "is not registered.");
	}

}
