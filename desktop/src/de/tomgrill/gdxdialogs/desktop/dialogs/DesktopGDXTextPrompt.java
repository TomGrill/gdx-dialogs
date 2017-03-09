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

package de.tomgrill.gdxdialogs.desktop.dialogs;

import com.badlogic.gdx.Gdx;
import de.tomgrill.gdxdialogs.core.GDXDialogsVars;
import de.tomgrill.gdxdialogs.core.dialogs.GDXTextPrompt;
import de.tomgrill.gdxdialogs.core.listener.TextPromptListener;

import javax.swing.JOptionPane;

public class DesktopGDXTextPrompt implements GDXTextPrompt {

	private CharSequence title = "";
	private CharSequence message = "";

	private TextPromptListener listener;

	public DesktopGDXTextPrompt() {
	}

	@Override
	public GDXTextPrompt show() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				Gdx.app.debug(GDXDialogsVars.LOG_TAG,
						DesktopGDXTextPrompt.class.getSimpleName() + " now shown");
				String response = JOptionPane.showInputDialog(null, message, (String) title,
						JOptionPane.QUESTION_MESSAGE);

				if (listener != null) {
					//if the first is true the second is implicitly always true
					//e.g bool j, f;   ((j or (not j and f)) == (j or f)) == true
					//Regardless of j and f values.
					if (response == null || "".equals(response)) {
						listener.cancel();
					} else {
						listener.confirm(response);
					}
				}
			}
		}).start();

		return this;
	}

	@Override
	public GDXTextPrompt setTitle(CharSequence title) {
		this.title = title;
		return this;
	}

	@Override
	public GDXTextPrompt setMessage(CharSequence message) {
		this.message = message;
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
	public GDXTextPrompt build() {
		return this;
	}

	@Override
	public GDXTextPrompt setTextPromptListener(TextPromptListener listener) {
		this.listener = listener;
		return this;
	}

	@Override
	public GDXTextPrompt setValue(CharSequence inputTip) {
		return this;
	}

	@Override
	public GDXTextPrompt dismiss() {
		Gdx.app.debug(GDXDialogsVars.LOG_TAG, DesktopGDXTextPrompt.class.getSimpleName() + " dismiss " +
				"ignored. (Desktop TextPrompt cannot be dismissed)");
		return this;
	}

}
