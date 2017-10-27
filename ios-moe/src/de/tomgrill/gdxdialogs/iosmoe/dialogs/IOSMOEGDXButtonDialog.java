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
import com.badlogic.gdx.utils.Array;

import org.moe.natj.general.ann.NInt;
import de.tomgrill.gdxdialogs.core.GDXDialogsVars;
import de.tomgrill.gdxdialogs.core.dialogs.GDXButtonDialog;
import de.tomgrill.gdxdialogs.core.listener.ButtonClickListener;
import apple.uikit.UIAlertView;
import apple.uikit.protocol.UIAlertViewDelegate;

public class IOSMOEGDXButtonDialog implements GDXButtonDialog {
	private final static String TAG = IOSMOEGDXButtonDialog.class.getSimpleName();

	private UIAlertView alertView;

	private String title = "title";
	private String message = "mesage";

	private ButtonClickListener listener;

	private Array<CharSequence> labels = new Array<CharSequence>();

	public IOSMOEGDXButtonDialog () {
	}

	@Override
	public GDXButtonDialog setCancelable(boolean cancelable) {
		return this;
	}

	@Override
	public GDXButtonDialog show() {
		if (alertView == null) {
			throw new RuntimeException(GDXButtonDialog.class.getSimpleName() + " has not been built. Use build() " +
					"before show().");
		}
		Gdx.app.debug(GDXDialogsVars.LOG_TAG, IOSMOEGDXButtonDialog.class.getSimpleName() + " now shown.");
		Gdx.app.postRunnable(new Runnable() {
			@Override
			public void run() {
				alertView.show();
			}
		});
		return this;
	}

	@Override
	public GDXButtonDialog dismiss() {
		if (alertView == null) {
			throw new RuntimeException(GDXButtonDialog.class.getSimpleName() + " has not been build. Use build() " +
					"before dismiss().");
		}
		Gdx.app.debug(GDXDialogsVars.LOG_TAG, IOSMOEGDXButtonDialog.class.getSimpleName() + " dismissed.");
		alertView.dismissWithClickedButtonIndexAnimated(-1, false);
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

			// swap first and second if we have 2 buttons
			if (labels.size == 2) {
				if (buttonIndex == 0) {
					buttonNr = 1;
				} else {
					buttonNr = 0;
				}
			}

			final int resultNr = buttonNr;
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {
					listener.click(resultNr);
				}
			});
		}
	}

	@Override
	public GDXButtonDialog build() {
		Gdx.app.log(TAG, "build 1");
		UIAlertViewDelegate delegate = new UIAlertViewDelegate() {
			@Override
			public void alertViewDidDismissWithButtonIndex (UIAlertView alertView, @NInt long buttonIndex) {
				performClickOnButton(buttonIndex);
			}
		};

		// for whatver reason this crashes :/
//		alertView = UIAlertView.alloc().initWithTitleMessageDelegateCancelButtonTitleOtherButtonTitles(
//			title, message, delegate, firstButton, null);
//		);
		alertView = UIAlertView.alloc().init();
		alertView.setTitle(title);
		alertView.setMessage(message);
		// if we have 2 labels, first and second are flipped
		if (labels.size == 2) {
			alertView.addButtonWithTitle((String)labels.get(1));
			alertView.addButtonWithTitle((String)labels.get(0));
		} else {
			for (CharSequence label : labels) {
				alertView.addButtonWithTitle((String)label);
			}
		}
		alertView.setDelegate(delegate);
		alertView.setCancelButtonIndex(2);
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
