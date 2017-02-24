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
import de.tomgrill.gdxdialogs.core.dialogs.GDXProgressDialog;

import javax.swing.*;

public class DesktopGDXProgressDialog implements GDXProgressDialog {

	private JOptionPane optionPane;

	private JDialog dialog;

	private CharSequence title = "";
	private CharSequence message = "";

	public DesktopGDXProgressDialog() {
	}

	@Override
	public GDXProgressDialog setMessage(CharSequence message) {
		this.message = message;
		return this;
	}

	@Override
	public GDXProgressDialog setTitle(CharSequence title) {
		this.title = title;
		return this;
	}

	/**
	 * Shows the dialog. show() can only be called after build() has been called
	 * else there might be strange behavior. The boolean hangs the current thread if true.
	 *
	 *
	 * @param hang if true hangs the thread witch it were called from
	 * @return The same instance that the method was called from.
	 */
	public GDXProgressDialog show(boolean hang) {
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				Gdx.app.debug(GDXDialogsVars.LOG_TAG, DesktopGDXProgressDialog.class.getSimpleName() +
						" now shown.");
				dialog.setVisible(true);
			}

		});
		if (hang) t.run();
		else t.start();
		return this;
	}

	@Override
	public GDXProgressDialog show() {
		return show(false);
	}


	@Override
	public GDXProgressDialog dismiss() {
		dialog.dispose();
		optionPane.setVisible(false);
		Gdx.app.debug(GDXDialogsVars.LOG_TAG, DesktopGDXProgressDialog.class.getSimpleName() + " dismissed.");
		return this;
	}

	@Override
	public GDXProgressDialog build() {

		optionPane = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null,
				new Object[] {}, null);
		dialog = new JDialog();

		dialog.setTitle((String) title);
		dialog.setModal(true);

		dialog.setContentPane(optionPane);
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.pack();

		return this;
	}

}
