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

public interface GDXProgressDialog {
	/**
	 * Sets the message.
	 * 
	 * @param message The text to be displayed at the body of the dialog.
	 * @return The same instance that the method was called from.
	 */
	GDXProgressDialog setMessage(CharSequence message);

	/**
	 * Sets the title
	 * 
	 * @param title String value to set the title
	 * @return The same instance that the method was called from.
	 */
	GDXProgressDialog setTitle(CharSequence title);

	/**
	 * Shows the dialog. show() can only be called after build() has been called
	 * else there might be strange behavior. Runs asynchronously on a different thread.
	 *
	 * @return The same instance that the method was called from.
	 */
	GDXProgressDialog show();

	/**
	 * Dismisses the dialog. You can show the dialog again.
	 * 
	 * @return The same instance that the method was called from.
	 */
	GDXProgressDialog dismiss();

	/**
	 * This builds the button and prepares for usage.
	 * 
	 * @return The same instance that the method was called from.
	 */
	GDXProgressDialog build();

}
