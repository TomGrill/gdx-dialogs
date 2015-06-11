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

public interface ButtonDialog {

	/**
	 * Only on Android: When set true, user can click outside of the dialog and
	 * the dialog will be closed. Has no effect on other operating systems.
	 * 
	 * @param cancelable
	 * @return
	 */
	public ButtonDialog setCancelable(boolean cancelable);

	/**
	 * Shows the dialog. show() can only be called after build() has been called
	 * else there might be strange behavior. You need to add at least one button
	 * with addButton() before calling build().
	 * 
	 * @return
	 */
	public ButtonDialog show();

	/**
	 * Dismisses the dialog. You can show the dialog again.
	 * 
	 * @return
	 */
	public ButtonDialog dismiss();

	/**
	 * Sets the {@link ButtonClickListener}
	 * 
	 * @param listener
	 * @return
	 */
	public ButtonDialog setClickListener(ButtonClickListener listener);

	/**
	 * Add new button to the dialog. You need to add at least one button.
	 * 
	 * @param label
	 * @return
	 */
	public ButtonDialog addButton(CharSequence label);

	/**
	 * This builds the button and prepares for usage. You need to add at least
	 * one button with addButton() before calling build().
	 * 
	 * @return
	 */
	public ButtonDialog build();

	/**
	 * Sets the message.
	 * 
	 * @param message
	 * @return
	 */
	public ButtonDialog setMessage(CharSequence message);

	/**
	 * Sets the title
	 * 
	 * @param title
	 * @return
	 */
	public ButtonDialog setTitle(CharSequence title);
}
