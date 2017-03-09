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

public interface GDXButtonDialog {

	/**
	 * Only on Android: When set true, user can click outside of the dialog and
	 * the dialog will be closed. Has no effect on other operating systems.
	 * 
	 * @param cancelable this value will be set to the corresponding android property if applicable.
	 * @return  The same instance that the method was called from.
	 */
	GDXButtonDialog setCancelable(boolean cancelable);

	/**
	 * Shows the dialog. show() can only be called after build() has been called
	 * else there might be strange behavior. You need to add at least one button
	 * with addButton() before calling build(). Runs asynchronously on a different thread.
	 * 
	 * @return The same instance that the method was called from.
	 */
	GDXButtonDialog show();

	/**
	 * Dismisses the dialog. You can show the dialog again.
	 * 
	 * @return The same instance that the method was called from.
	 */
	GDXButtonDialog dismiss();

	/**
	 * Sets the {@link ButtonClickListener}
	 * 
	 * @param listener listener to be called when the event is triggered
	 * @return The same instance that the method was called from.
	 */
	GDXButtonDialog setClickListener(ButtonClickListener listener);

	/**
	 * Add new button to the dialog. You need to add at least one button.
	 * 
	 * @param label Text of the added new button.
	 * @return The same instance that the method was called from.
	 */
	GDXButtonDialog addButton(CharSequence label);

	/**
	 * This builds the button and prepares for usage. You need to add at least
	 * one button with addButton() before calling build().
	 * 
	 * @return The same instance that the method was called from.
	 */
	GDXButtonDialog build();

	/**
	 * Sets the message.
	 * 
	 * @param message The text to be displayed at the body of the dialog.
	 * @return The same instance that the method was called from.
	 */
	GDXButtonDialog setMessage(CharSequence message);

	/**
	 * Sets the title
	 * 
	 * @param title String value to set the title
	 * @return The same instance that the method was called from.
	 */
	GDXButtonDialog setTitle(CharSequence title);
}
