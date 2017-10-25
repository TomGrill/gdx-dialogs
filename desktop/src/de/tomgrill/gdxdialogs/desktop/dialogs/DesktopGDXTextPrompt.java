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

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class DesktopGDXTextPrompt implements GDXTextPrompt {

	private CharSequence title = "";
	private CharSequence message = "";
	private CharSequence value = "";

	private CharSequence cancelButtonLabel = "Cancel", confirmButtonLabel = "OK";

	private TextPromptListener listener;

	private InputType inputType = InputType.PLAIN_TEXT;

	public DesktopGDXTextPrompt() {
	}

	@Override
	public GDXTextPrompt show() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				Gdx.app.debug(GDXDialogsVars.LOG_TAG,
						DesktopGDXTextPrompt.class.getSimpleName() + " now shown");

				JPanel panel = new JPanel();
				panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
				JLabel label = new JLabel(message.toString());

				JTextField textField;
				switch (inputType) {
					case PASSWORD:
						textField = new JPasswordField();
						textField.setText(String.valueOf(value));
						break;

					case PLAIN_TEXT:
					default:
						textField = new JTextField();
						textField.setText(String.valueOf(value));
				}

				panel.add(label);
				panel.add(textField);

				String[] options = new String[] {(String) confirmButtonLabel, (String) cancelButtonLabel};
				int response = JOptionPane.showOptionDialog(
						null,
						panel,
						(String) title,
						JOptionPane.NO_OPTION,
						JOptionPane.QUESTION_MESSAGE,
						null,
						options,
						options[1]
				);

				if (listener != null) {
					if (response == 1) {
						listener.cancel();
					} else {
						switch (inputType) {
							case PASSWORD:
								listener.confirm(new String(((JPasswordField)textField).getPassword()));
								break;

							case PLAIN_TEXT:
							default:
								listener.confirm(textField.getText());
						}
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
	public GDXTextPrompt setMaxLength(int maxLength) {
		if (maxLength < 1) {
			throw new RuntimeException("Char limit must be >= 1");
		}
		return this;
	}

	@Override
	public GDXTextPrompt setMessage(CharSequence message) {
		this.message = message;
		return this;
	}

	@Override
	public GDXTextPrompt setCancelButtonLabel(CharSequence label) {
		cancelButtonLabel = label;
		return this;
	}

	@Override
	public GDXTextPrompt setConfirmButtonLabel(CharSequence label) {
		confirmButtonLabel = label;
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
	public GDXTextPrompt setInputType(InputType inputType) {
		this.inputType = inputType;
		return this;
	}

	@Override
	public GDXTextPrompt setValue(CharSequence inputTip) {
		this.value = inputTip;
		return this;
	}

	@Override
	public GDXTextPrompt dismiss() {
		Gdx.app.debug(GDXDialogsVars.LOG_TAG, DesktopGDXTextPrompt.class.getSimpleName() + " dismiss " +
				"ignored. (Desktop TextPrompt cannot be dismissed)");
		return this;
	}

}
