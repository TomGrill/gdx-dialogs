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

import org.robovm.apple.uikit.UIAlertView;
import org.robovm.apple.uikit.UIAlertViewDelegateAdapter;

import com.badlogic.gdx.utils.Array;

import de.tomgrill.gdxdialogs.core.dialogs.ButtonDialog;
import de.tomgrill.gdxdialogs.core.listener.ButtonClickListener;

public class IOSButtonDialog implements ButtonDialog {

	private UIAlertView alertView;

	private String title;
	private String message;

	private ButtonClickListener listener;

	private Array<CharSequence> labels = new Array<CharSequence>();

	@Override
	public ButtonDialog setCancelable(boolean cancelable) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public ButtonDialog show() {
		if (alertView == null) {
			throw new RuntimeException("ButtonDialog has not been build. Use build() before show().");
		}
		alertView.show();
		return this;
	}

	@Override
	public ButtonDialog dismiss() {
		if (alertView != null) {
			alertView.dismiss(-1, false);
		}
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

	private void performClickOnButton(long buttonIndex) {
		if (listener != null) {
			listener.click((int) buttonIndex);
		}
	}

	@Override
	public ButtonDialog build() {
		if (alertView == null) {

			UIAlertViewDelegateAdapter delegate = new UIAlertViewDelegateAdapter() {

				@Override
				public void didDismiss(UIAlertView alertView, long buttonIndex) {
					performClickOnButton(buttonIndex);
				}

				@Override
				public void clicked(UIAlertView alertView, long buttonIndex) {

				}

				@Override
				public void cancel(UIAlertView alertView) {

				}

				@Override
				public void willPresent(UIAlertView alertView) {

				}

				@Override
				public void didPresent(UIAlertView alertView) {

				}

				@Override
				public void willDismiss(UIAlertView alertView, long buttonIndex) {

				}

				@Override
				public boolean shouldEnableFirstOtherButton(UIAlertView alertView) {
					return false;
				}

			};

			String[] otherButtons = new String[labels.size - 1];

			String firstButton = (String) labels.get(0);

			for (int i = 1; i < labels.size; i++) {
				otherButtons[i] = (String) labels.get(i);
			}

			alertView = new UIAlertView(title, message, delegate, firstButton, otherButtons);

			// alertView.setCancelButtonIndex(2);
		}
		return this;
	}

	@Override
	public ButtonDialog setMessage(CharSequence message) {
		this.message = (String) message;
		return this;
	}

	@Override
	public ButtonDialog setTitle(CharSequence title) {
		this.title = (String) title;
		return this;
	}

}
