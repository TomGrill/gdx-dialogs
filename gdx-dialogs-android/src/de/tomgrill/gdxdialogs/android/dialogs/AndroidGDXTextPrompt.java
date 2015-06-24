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

package de.tomgrill.gdxdialogs.android.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.badlogic.gdx.Gdx;

import de.tomgrill.gdxdialogs.core.GDXDialogsVars;
import de.tomgrill.gdxdialogs.core.dialogs.GDXTextPrompt;
import de.tomgrill.gdxdialogs.core.listener.TextPromptListener;

public class AndroidGDXTextPrompt implements GDXTextPrompt {

	private Activity activity;

	private TextView titleView;
	private TextView messageView;

	private CharSequence message = "";
	private CharSequence title = "";
	private CharSequence cancelLabel = "";
	private CharSequence confirmLabel = "";

	private TextPromptListener listener;

	private CharSequence inputValue = "";

	private EditText userInput;

	private AlertDialog alertDialog;

	private boolean isBuild = false;

	public AndroidGDXTextPrompt(Activity activity) {
		this.activity = activity;
	}

	@Override
	public GDXTextPrompt show() {
		if (alertDialog == null || isBuild == false) {
			throw new RuntimeException(GDXTextPrompt.class.getSimpleName() + " has not been build. Use build() before show().");
		}

		if (alertDialog != null && userInput != null) {
			userInput.setText(inputValue);
		}

		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				alertDialog.show();
			}
		});

		return this;
	}

	@Override
	public GDXTextPrompt build() {
		activity.runOnUiThread(new Runnable() {
			//
			@Override
			public void run() {
				//
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
				LayoutInflater li = LayoutInflater.from(activity);
				//

				View promptsView = li.inflate(getResourceId("gdxdialogs_inputtext", "layout"), null);

				alertDialogBuilder.setView(promptsView);

				userInput = (EditText) promptsView.findViewById(getResourceId("gdxDialogsEditTextInput", "id"));

				titleView = (TextView) promptsView.findViewById(getResourceId("gdxDialogsEnterTitle", "id"));
				messageView = (TextView) promptsView.findViewById(getResourceId("gdxDialogsEnterMessage", "id"));

				titleView.setText(title);
				messageView.setText(message);

				alertDialogBuilder.setCancelable(false).setPositiveButton(confirmLabel, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

						if (listener != null) {
							listener.confirm(userInput.getText().toString());
						}

					}
				}).setNegativeButton(cancelLabel, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
						if (listener != null) {
							listener.cancel();
						}
					}
				});
				// create alert dialog alertDialog =
				alertDialog = alertDialogBuilder.create();
				isBuild = true;
			}
		});

		// Wait till TextPrompt is built.
		while (!isBuild) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
		}

		return this;
	}

	public int getResourceId(String pVariableName, String pVariableType) {
		try {
			return activity.getResources().getIdentifier(pVariableName, pVariableType, activity.getPackageName());
		} catch (Exception e) {
			Gdx.app.error(GDXDialogsVars.LOG_TAG, "Cannot find resouce with name: " + pVariableName + " Did you copy the layouts to /res/layouts and /res/layouts_v14 ?");
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public GDXTextPrompt setTitle(CharSequence title) {
		this.title = title;
		return this;
	}

	@Override
	public GDXTextPrompt setMessage(CharSequence message) {
		this.message = message;
		return this;
	}

	@Override
	public GDXTextPrompt setValue(CharSequence value) {
		this.inputValue = value;
		return this;
	}

	@Override
	public GDXTextPrompt setCancelButtonLabel(CharSequence label) {
		this.cancelLabel = label;
		return this;
	}

	@Override
	public GDXTextPrompt setConfirmButtonLabel(CharSequence label) {
		this.confirmLabel = label;
		return this;
	}

	@Override
	public GDXTextPrompt setTextPromptListener(TextPromptListener listener) {
		this.listener = listener;
		return this;
	}

	@Override
	public GDXTextPrompt dismiss() {

		if (alertDialog == null || isBuild == false) {
			throw new RuntimeException(GDXTextPrompt.class.getSimpleName() + " has not been build. Use build() before dismiss().");
		}

		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				alertDialog.dismiss();
			}
		});

		return this;
	}

}
