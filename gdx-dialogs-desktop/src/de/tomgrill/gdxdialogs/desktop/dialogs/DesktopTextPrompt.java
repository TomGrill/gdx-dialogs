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

import javax.swing.JOptionPane;

import de.tomgrill.gdxdialogs.core.dialogs.TextPrompt;
import de.tomgrill.gdxdialogs.core.listener.TextPromptListener;

public class DesktopTextPrompt implements TextPrompt {

	private CharSequence title = "";
	private CharSequence message = "";
	private CharSequence cancelLabel = "";
	private CharSequence confirmLabel = "";

	private TextPromptListener listener;

	@Override
	public TextPrompt show() {

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {

				// System.out.println("INFO message: " + infoMessage);

				String response = JOptionPane.showInputDialog(null, (String) message, (String) title, JOptionPane.QUESTION_MESSAGE);

				if (listener != null) {
					if (response == null || (response != null && ("".equals(response)))) {
						listener.cancel();
					} else {
						listener.confirm(response);
					}
				}
			}
		});

		t.start();
		return this;
	}

	@Override
	public TextPrompt setTitle(CharSequence title) {
		this.title = title;
		return this;
	}

	@Override
	public TextPrompt setMessage(CharSequence message) {
		this.message = message;
		return this;
	}

	@Override
	public TextPrompt setCancelButtonLabel(CharSequence label) {
		this.cancelLabel = label;
		return this;
	}

	@Override
	public TextPrompt setConfirmButtonLabel(CharSequence label) {
		this.confirmLabel = label;
		return this;
	}

	@Override
	public TextPrompt build() {
		return this;
	}

	@Override
	public TextPrompt setTextPromptListener(TextPromptListener listener) {
		this.listener = listener;
		return this;
	}

	@Override
	public TextPrompt setValue(CharSequence message) {
		return this;
	}

	@Override
	public TextPrompt dismiss() {
		return this;
	}

}
