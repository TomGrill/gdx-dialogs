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

import com.badlogic.gdx.utils.Array;

import de.tomgrill.gdxdialogs.core.dialogs.ButtonDialog;
import de.tomgrill.gdxdialogs.core.listener.ButtonClickListener;

public class AndroidButtonDialog implements ButtonDialog {

	private Activity activity;

	private AlertDialog.Builder builder;

	private AlertDialog dialog;

	private boolean cancelable;

	private CharSequence message;
	private CharSequence title;

	private ButtonClickListener listener;

	private boolean isBuild = false;

	private Array<CharSequence> labels = new Array<CharSequence>();

	public AndroidButtonDialog(Activity activity) {
		this.activity = activity;
	}

	@Override
	public ButtonDialog setCancelable(boolean cancelable) {
		this.cancelable = cancelable;
		return this;
	}

	@Override
	public ButtonDialog show() {

		if (dialog == null || isBuild == false) {
			throw new RuntimeException("ButtonDialog has not been build. Use build() before show().");
		}

		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				dialog.show();
			}
		});
		return this;
	}

	@Override
	public ButtonDialog dismiss() {
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				dialog.dismiss();
			}
		});
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
		builder = new AlertDialog.Builder(activity);
		builder.setCancelable(cancelable);
		builder.setMessage(message);
		builder.setTitle(title);

		for (int i = 0; i < labels.size; i++) {

			if (i == 0) {

				builder.setPositiveButton(labels.get(i), new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface di, int which) {
						if (AndroidButtonDialog.this.listener != null) {
							AndroidButtonDialog.this.listener.click(0);
						}

					}
				});
			}

			if (i == 1) {
				builder.setNegativeButton(labels.get(i), new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (AndroidButtonDialog.this.listener != null) {
							AndroidButtonDialog.this.listener.click(1);
						}
					}

				});
			}

			if (i == 2) {
				builder.setNeutralButton(labels.get(i), new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (AndroidButtonDialog.this.listener != null) {
							AndroidButtonDialog.this.listener.click(2);
						}
					}

				});
			}
		}

		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				dialog = builder.create();
				isBuild = true;
			}
		});

		// Wait till button is build
		while (!isBuild) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
		}

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
