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

import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.reflect.ClassReflection;

public abstract class GDXDialogs {

	protected ArrayMap<String, String> registeredDialogs = new ArrayMap<String, String>();

	public <T> T newDialog(Class<T> cls) {
		String className = cls.getName();
		if (registeredDialogs.containsKey(className)) {

			try {
				final Class<T> dialogClazz = ClassReflection.forName(registeredDialogs.get(className));

				Object dialogObject = ClassReflection.getConstructor(dialogClazz).newInstance();
				return dialogClazz.cast(dialogObject);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		throw new RuntimeException(cls.getName() + "is not registered.");
	}

	public void registerDialog(String interfaceName, String clazzName) {
		registeredDialogs.put(interfaceName, clazzName);
	}
}