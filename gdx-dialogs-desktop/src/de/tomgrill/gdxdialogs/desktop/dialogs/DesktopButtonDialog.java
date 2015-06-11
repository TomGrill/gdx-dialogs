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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;

import de.tomgrill.gdxdialogs.core.dialogs.ButtonDialog;
import de.tomgrill.gdxdialogs.core.listener.ButtonClickListener;

public class DesktopButtonDialog implements ButtonDialog {

	private final String TAG = "gdx-dialogs";

	private CharSequence title;
	private CharSequence message;

	private ButtonClickListener listener;

	private Array<CharSequence> labels = new Array<CharSequence>();

	boolean isBuild = false;

	@Override
	public ButtonDialog setCancelable(boolean cancelable) {
		Gdx.app.log(TAG, "INFO: Desktop Dialogs cannot be set cancelable");
		return this;
	}

	@Override
	public ButtonDialog show() {

		if (isBuild == false) {
			throw new RuntimeException("ButtonDialog has not been build. Use build() before show().");
		}

		Object[] options = new Object[labels.size];

		for (int i = 0; i < labels.size; i++) {
			options[i] = labels.get(i);
		}

		int optionType = JOptionPane.YES_OPTION;

		if (labels.size != 1) {
			optionType = JOptionPane.YES_NO_CANCEL_OPTION;
		}

		int n = JOptionPane.showOptionDialog(null, (String) message, (String) title, optionType, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		if (listener != null) {
			listener.click(n);
		}
		return this;
	}

	@Override
	public ButtonDialog dismiss() {
		Gdx.app.log(TAG, "INFO: Desktop Dialogs cannot be dismissed");
		return this;
	}

	@Override
	public ButtonDialog setClickListener(ButtonClickListener listener) {
		this.listener = listener;
		return this;
	}

	@Override
	public ButtonDialog addButton(CharSequence label) {
		if (labels.size >= 3) {
			throw new RuntimeException("You can only have up to three buttons added.");
		}
		labels.add(label);
		return this;
	}

	@Override
	public ButtonDialog build() {
		isBuild = true;
		return this;
	}

	@Override
	public ButtonDialog setMessage(CharSequence message) {
		this.message = message;
		return this;
	}

	@Override
	public ButtonDialog setTitle(CharSequence title) {
		this.title = title;
		return this;
	}

}
