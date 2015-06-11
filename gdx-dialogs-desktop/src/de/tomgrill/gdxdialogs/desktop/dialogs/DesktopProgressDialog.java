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

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import de.tomgrill.gdxdialogs.core.dialogs.ProgressDialog;

public class DesktopProgressDialog implements ProgressDialog {

	private final String TAG = "gdx-dialogs";

	private JOptionPane optionPane;

	private JDialog dialog;

	private CharSequence title;
	private CharSequence message;

	@Override
	public ProgressDialog setMessage(CharSequence message) {
		this.message = message;
		return this;
	}

	@Override
	public ProgressDialog setTitle(CharSequence title) {
		this.title = title;
		return this;
	}

	@Override
	public ProgressDialog show() {
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				dialog.setVisible(true);
			}

		});
		t.start();
		return this;
	}

	@Override
	public ProgressDialog dismiss() {
		dialog.dispose();
		optionPane.setVisible(false);
		return this;
	}

	@Override
	public ProgressDialog build() {

		optionPane = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[] {}, null);
		dialog = new JDialog();

		dialog.setTitle((String) title);
		dialog.setModal(true);

		dialog.setContentPane(optionPane);
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.pack();

		return this;
	}

}
