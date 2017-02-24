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

import de.tomgrill.gdxdialogs.core.listener.TextPromptListener;

public interface GDXTextPrompt {
	/**
	 * Sets the title
	 *
	 * @param title String value to set the title
	 * @return The same instance that the method was called from.
	 */
	GDXTextPrompt setTitle(CharSequence title);

	/**
	 * Shows the dialog. show() can only be called after build() has been called
	 * else there might be strange behavior. Runs asynchronously on a different thread.
	 *
	 * @return The same instance that the method was called from.
	 */
	GDXTextPrompt show();

	/**
	 * Dismisses the dialog. You can show the dialog again.
	 *
	 * @return The same instance that the method was called from.
	 */
	GDXTextPrompt dismiss();

	/**
	 * This builds the button and prepares for usage.
	 *
	 * @return The same instance that the method was called from.
	 */
	GDXTextPrompt build();

	/**
	 * Sets the message.
	 *
	 * @param message The text to be displayed at the body of the dialog.
	 * @return The same instance that the method was called from.
	 */
	GDXTextPrompt setMessage(CharSequence message);

	/**
	 * Sets the default value for the input field.
	 *
	 * @param inputTip Placeholder for the text input field.
	 * @return The same instance that the method was called from.
	 */
	GDXTextPrompt setValue(CharSequence inputTip);

	/**
	 * Sets the label for the cancel button on the dialog.
	 *
	 * @param label Text of the cancel button
	 * @return The same instance that the method was called from.
	 */
	GDXTextPrompt setCancelButtonLabel(CharSequence label);

	/**
	 * Sets the label for the confirm button on the dialog.
	 *
	 * @param label Text of the confirm button
	 * @return The same instance that the method was called from.
	 */
	GDXTextPrompt setConfirmButtonLabel(CharSequence label);

	/**
	 * Sets the {@link TextPromptListener}
	 *
	 * @param listener listener to be called when the event is triggered
	 * @return The same instance that the method was called from.
	 */
	GDXTextPrompt setTextPromptListener(TextPromptListener listener);
}
