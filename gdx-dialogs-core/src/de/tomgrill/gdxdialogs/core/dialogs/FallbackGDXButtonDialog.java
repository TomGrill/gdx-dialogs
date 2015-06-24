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

package de.tomgrill.gdxdialogs.core.dialogs;

import de.tomgrill.gdxdialogs.core.listener.ButtonClickListener;

public class FallbackGDXButtonDialog implements GDXButtonDialog {

	@Override
	public GDXButtonDialog setCancelable(boolean cancelable) {
		return this;
	}

	@Override
	public GDXButtonDialog show() {
		return this;
	}

	@Override
	public GDXButtonDialog dismiss() {
		return this;
	}

	@Override
	public GDXButtonDialog setClickListener(ButtonClickListener listener) {
		return this;
	}

	@Override
	public GDXButtonDialog addButton(CharSequence label) {
		return this;
	}

	@Override
	public GDXButtonDialog build() {
		return this;
	}

	@Override
	public GDXButtonDialog setMessage(CharSequence message) {
		return this;
	}

	@Override
	public GDXButtonDialog setTitle(CharSequence title) {
		return this;
	}

}
