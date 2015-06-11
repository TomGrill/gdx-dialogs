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
import de.tomgrill.gdxdialogs.core.dialogs.ProgressDialog;

public class AndroidProgressDialog implements ProgressDialog {

	private Activity activity;

	private android.app.ProgressDialog progressDialog;

	private CharSequence message = "";
	private CharSequence title = "";

	private boolean isBuild = false;

	public AndroidProgressDialog(Activity activity) {
		this.activity = activity;
	}

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
		if (progressDialog == null || isBuild == false) {
			throw new RuntimeException("ProgressDialog has not been build. Use build() before show().");
		}

		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				progressDialog.show();
			}
		});

		return this;
	}

	@Override
	public ProgressDialog dismiss() {

		if (progressDialog == null || isBuild == false) {
			throw new RuntimeException("ProgressDialog has not been build. Use build() before dismiss().");
		}

		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				progressDialog.dismiss();
			}
		});

		return this;
	}

	@Override
	public ProgressDialog build() {
		if (progressDialog == null) {

			activity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					progressDialog = new android.app.ProgressDialog(AndroidProgressDialog.this.activity);

					progressDialog.setMessage(message);
					progressDialog.setTitle(title);
					progressDialog.setCancelable(false);

					isBuild = true;
				}

			});

			while (!isBuild) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
				}
			}
		}
		return this;
	}

}
