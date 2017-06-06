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

package de.tomgrill.gdxdialogs.core.dialogs;

import com.badlogic.gdx.Gdx;

import de.tomgrill.gdxdialogs.core.GDXDialogsVars;
import de.tomgrill.gdxdialogs.core.listener.TextPromptListener;

public class FallbackGDXTextPrompt implements GDXTextPrompt {

	@Override
	public GDXTextPrompt show() {
		Gdx.app.debug(GDXDialogsVars.LOG_TAG, FallbackGDXTextPrompt.class.getSimpleName() + " now shown " +
				"ignored. (Fallback with empty methods)");
		return this;
	}

	@Override
	public GDXTextPrompt build() {
		return this;
	}

	@Override
	public GDXTextPrompt setTitle(CharSequence title) {
		return this;
	}

	@Override
	public GDXTextPrompt setMaxLength(int maxLength) {
		return this;
	}

	@Override
	public GDXTextPrompt setMessage(CharSequence message) {
		return this;
	}

	@Override
	public GDXTextPrompt setCancelButtonLabel(CharSequence label) {
		return this;
	}

	@Override
	public GDXTextPrompt setConfirmButtonLabel(CharSequence label) {
		return this;
	}

	@Override
	public GDXTextPrompt setTextPromptListener(TextPromptListener listener) {
		return this;
	}

	@Override
	public GDXTextPrompt setValue(CharSequence inputTip) {
		return this;
	}

	@Override
	public GDXTextPrompt dismiss() {
		Gdx.app.debug(GDXDialogsVars.LOG_TAG, FallbackGDXTextPrompt.class.getSimpleName() + " dismiss" +
				" ignored. (Fallback with empty methods)");
		return this;
	}

}
