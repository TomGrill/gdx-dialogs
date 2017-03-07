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
import com.badlogic.gdx.utils.Array;
import de.tomgrill.gdxdialogs.core.GDXDialogsVars;
import de.tomgrill.gdxdialogs.core.dialogs.GDXButtonDialog;
import de.tomgrill.gdxdialogs.core.listener.ButtonClickListener;

import javax.swing.JOptionPane;

public class DesktopGDXButtonDialog implements GDXButtonDialog {

	private CharSequence title = "";
	private CharSequence message = "";

	private ButtonClickListener listener;

	private Array<CharSequence> labels = new Array<CharSequence>();

	private boolean isBuild = false;

	public DesktopGDXButtonDialog() {
	}

	@Override
	public GDXButtonDialog setCancelable(boolean cancelable) {
		Gdx.app.debug(GDXDialogsVars.LOG_TAG, "INFO: Desktop Dialogs cannot be set cancelled");
		return this;
	}

	@Override
	public GDXButtonDialog show() {

		if (!isBuild) {
			throw new RuntimeException(GDXButtonDialog.class.getSimpleName() +
					" has not been build. Use build() before show().");
		}

		new Thread(new Runnable() {

			@Override
			public void run() {
				Gdx.app.debug(
						GDXDialogsVars.LOG_TAG, DesktopGDXButtonDialog.class.getSimpleName() + " now shown.");
				Object[] options = new Object[labels.size];

				for (int i = 0; i < labels.size; i++) {
					options[i] = labels.get(i);
				}

				int optionType = JOptionPane.YES_OPTION;

				if (labels.size != 1) {
					optionType = JOptionPane.YES_NO_CANCEL_OPTION;
				}

				int n = JOptionPane.showOptionDialog(null, message, (String) title, optionType,
						JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
				if (listener != null) {
					listener.click(n);
				}
			}
		}).start();

		return this;
	}

	@Override
	public GDXButtonDialog dismiss() {
		Gdx.app.debug(GDXDialogsVars.LOG_TAG, DesktopGDXButtonDialog.class.getSimpleName() + " dismiss " +
				"ignored. (Desktop ButtonDialogs cannot be dismissed)");
		return this;
	}

	@Override
	public GDXButtonDialog setClickListener(ButtonClickListener listener) {
		this.listener = listener;
		return this;
	}

	@Override
	public GDXButtonDialog addButton(CharSequence label) {
		if (labels.size >= 3) {
			throw new RuntimeException("You can only have up to three buttons added.");
		}
		labels.add(label);
		return this;
	}

	@Override
	public GDXButtonDialog build() {
		if (labels.size == 0) {
			throw new RuntimeException("You to add at least one button with addButton(..);");
		}
		isBuild = true;
		return this;
	}

	@Override
	public GDXButtonDialog setMessage(CharSequence message) {
		this.message = message;
		return this;
	}

	@Override
	public GDXButtonDialog setTitle(CharSequence title) {
		this.title = title;
		return this;
	}

}
