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

package de.tomgrill.gdxdialogs.iosmoe.dialogs;


import com.badlogic.gdx.Gdx;

import de.tomgrill.gdxdialogs.core.GDXDialogsVars;
import de.tomgrill.gdxdialogs.core.dialogs.GDXProgressDialog;
import ios.uikit.UIActivityIndicatorView;
import ios.uikit.UIAlertView;

public class IOSMOEGDXProgressDialog implements GDXProgressDialog {

	private UIAlertView alertView;
	private UIActivityIndicatorView indicator;

	private String title = "";
	private String message = "";

	public IOSMOEGDXProgressDialog () {
	}

	@Override
	public GDXProgressDialog setMessage(CharSequence message) {
		this.message = (String) message;
		return this;
	}

	@Override
	public GDXProgressDialog setTitle(CharSequence title) {
		this.title = (String) title;
		return this;
	}

	@Override
	public GDXProgressDialog show() {
		if (alertView == null) {
			throw new RuntimeException(GDXProgressDialog.class.getSimpleName() + " has not been build. Use build() before show().");
		}
		Gdx.app.debug(GDXDialogsVars.LOG_TAG, IOSMOEGDXProgressDialog.class.getSimpleName() + " now shown.");
		alertView.show();
		return this;
	}

	@Override
	public GDXProgressDialog dismiss() {
		if (alertView == null) {
			throw new RuntimeException(GDXProgressDialog.class.getSimpleName() + " has not been build. Use build() before dismiss().");
		}
		Gdx.app.debug(GDXDialogsVars.LOG_TAG, IOSMOEGDXProgressDialog.class.getSimpleName() + " dismissed.");
		alertView.dismissWithClickedButtonIndexAnimated(0, false);
		return this;
	}

	@Override
	public GDXProgressDialog build() {
		if (alertView == null) {

			alertView = UIAlertView.alloc().init();

			alertView.setTitle(title);
			alertView.setMessage(message);

			// CGSize screenSize =
			// UIScreen.getMainScreen().getBounds().getSize();

			// indicator = new
			// UIActivityIndicatorView(UIActivityIndicatorViewStyle.White);
			// indicator.setFrame(new CGRect(0.0, 0.0, 40.0, 40.0));
			//
			// indicator.setCenter(new CGPoint(screenSize.getWidth() / 2f - 20f,
			// screenSize.getWidth() / 2f - 50));
			// indicator.startAnimating();
			// alertView.addSubview(indicator);
			// indicator.release();

		}

		return this;
	}

}
