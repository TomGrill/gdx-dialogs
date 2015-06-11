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

package de.tomgrill.gdxdialogs.ios.dialogs;

import org.robovm.apple.coregraphics.CGPoint;
import org.robovm.apple.coregraphics.CGRect;
import org.robovm.apple.coregraphics.CGSize;
import org.robovm.apple.uikit.UIActivityIndicatorView;
import org.robovm.apple.uikit.UIActivityIndicatorViewStyle;
import org.robovm.apple.uikit.UIAlertView;
import org.robovm.apple.uikit.UIScreen;

import de.tomgrill.gdxdialogs.core.dialogs.ProgressDialog;

public class IOSProgressDialog implements ProgressDialog {

	private UIAlertView alertView;
	private UIActivityIndicatorView indicator;

	private String title;
	private String message;

	@Override
	public ProgressDialog setMessage(CharSequence message) {
		this.message = (String) message;
		return this;
	}

	@Override
	public ProgressDialog setTitle(CharSequence title) {
		this.title = (String) title;
		return this;
	}

	@Override
	public ProgressDialog show() {
		if (alertView == null) {
			throw new RuntimeException("ProgressDialog has not been build. Use build() before show().");
		}
		alertView.show();
		return this;
	}

	@Override
	public ProgressDialog dismiss() {
		if (alertView == null) {
			throw new RuntimeException("TextPrompt has not been build. Use build() before dismiss().");
		}
		alertView.dismiss(0, false);
		return this;
	}

	@Override
	public ProgressDialog build() {
		if (alertView == null) {

			alertView = new UIAlertView();

			
			
			alertView.setTitle(title);
			alertView.setMessage(message);
			
			CGSize screenSize = UIScreen.getMainScreen().getBounds().getSize();
			
			
			indicator = new UIActivityIndicatorView(UIActivityIndicatorViewStyle.White);
			indicator.setFrame(new CGRect(0.0, 0.0, 40.0, 40.0));
			//indicator.setCenter(indicator.getCenter());
			
			indicator.setCenter(new CGPoint(screenSize.getWidth() / 2f - 20f, screenSize.getWidth() / 2f - 50));
			indicator.startAnimating();
			alertView.addSubview(indicator);
			indicator.release();

		}
		
		return this;
	}

}
