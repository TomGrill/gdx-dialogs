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

import de.tomgrill.gdxdialogs.core.dialogs.GDXButtonDialog;
import de.tomgrill.gdxdialogs.core.listener.ButtonClickListener;

public class IOSGDXButtonDialog implements GDXButtonDialog {

	private UIAlertView alertView;

	private String title = "";
	private String message = "";

	private ButtonClickListener listener;

	private Array<CharSequence> labels = new Array<CharSequence>();

	public IOSGDXButtonDialog() {
	}

	@Override
	public GDXButtonDialog setCancelable(boolean cancelable) {
		return this;
	}

	@Override
	public GDXButtonDialog show() {
		if (alertView == null) {
			throw new RuntimeException(GDXButtonDialog.class.getSimpleName() + " has not been build. Use build() before show().");
		}
		alertView.show();
		return this;
	}

	@Override
	public GDXButtonDialog dismiss() {
		if (alertView == null) {
			throw new RuntimeException(GDXButtonDialog.class.getSimpleName() + " has not been build. Use build() before show().");
		}
		alertView.dismiss(-1, false);
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

	private void performClickOnButton(long buttonIndex) {
		if (listener != null) {

			int buttonNr = (int) buttonIndex;

			if (labels.size == 2) {
				if (buttonIndex == 0) {
					buttonNr = 1;
				} else {
					buttonNr = 0;
				}
			}

			listener.click(buttonNr);
		}
	}

	@Override
	public GDXButtonDialog build() {

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

		if (labels.size == 2) {
			firstButton = (String) labels.get(1);
			otherButtons[0] = (String) labels.get(0);
		}

		if (labels.size == 3) {
			for (int i = 1; i < labels.size; i++) {
				otherButtons[i - 1] = (String) labels.get(i);
			}
		}

		alertView = new UIAlertView(title, message, delegate, firstButton, otherButtons);

		// alertView.setCancelButtonIndex(2);

		return this;
	}

	@Override
	public GDXButtonDialog setMessage(CharSequence message) {
		this.message = (String) message;
		return this;
	}

	@Override
	public GDXButtonDialog setTitle(CharSequence title) {
		this.title = (String) title;
		return this;
	}

}
