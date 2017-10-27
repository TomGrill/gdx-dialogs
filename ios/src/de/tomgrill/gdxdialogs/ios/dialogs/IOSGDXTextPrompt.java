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

import com.badlogic.gdx.Gdx;
import de.tomgrill.gdxdialogs.core.GDXDialogsVars;
import de.tomgrill.gdxdialogs.core.dialogs.GDXTextPrompt;
import de.tomgrill.gdxdialogs.core.listener.TextPromptListener;
import org.robovm.apple.foundation.NSRange;
import org.robovm.apple.uikit.*;
import org.robovm.rt.bro.annotation.ByVal;

public class IOSGDXTextPrompt implements GDXTextPrompt {

    private String message = "";
    private String title = "";
    private String cancelLabel = "";
    private String confirmLabel = "";

    private TextPromptListener listener;

    private UIAlertView alertView;

    private int maxLength = 16;

    private UIAlertViewStyle inputType = UIAlertViewStyle.PlainTextInput;

    public IOSGDXTextPrompt() {
    }

    @Override
    public GDXTextPrompt show() {

        if (alertView == null) {
            throw new RuntimeException(GDXTextPrompt.class.getSimpleName() + " has not been build. Use build() before show().");
        }
        Gdx.app.debug(GDXDialogsVars.LOG_TAG, IOSGDXTextPrompt.class.getSimpleName() + " now shown.");
        alertView.show();
        return this;
    }

    @Override
    public GDXTextPrompt build() {

        if (alertView != null) {
            alertView.dispose();
        }

        UIAlertViewDelegateAdapter delegate = new UIAlertViewDelegateAdapter() {

            @Override
            public void didDismiss(UIAlertView alertView, long buttonIndex) {
                if (listener != null) {
                    if (buttonIndex == 0) {
                        Gdx.app.postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                listener.cancel();
                            }
                        });
                    }

                    if (buttonIndex == 1) {
                        UITextField textField = alertView.getTextField(0);
                        final String result = textField.getText();
                        Gdx.app.postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                listener.confirm(result);
                            }
                        });
                    }
                }
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

        String[] otherButtons = new String[1];
        otherButtons[0] = confirmLabel;

        alertView = new UIAlertView(title, message, delegate, cancelLabel, otherButtons);

        alertView.setAlertViewStyle(inputType);

        UITextField uiTextField = alertView.getTextField(0);
//		final UITextFieldDelegate ud = uiTextField.getDelegate();

        uiTextField.setDelegate(new UITextFieldDelegateAdapter() {
            @Override
            public boolean shouldChangeCharacters(UITextField textField, @ByVal NSRange nsRange, String additionalText) {

                if (textField.getText().length() + additionalText.length() > maxLength) {
                    String oldText = textField.getText();
                    String newText = oldText + additionalText;
                    textField.setText(newText.substring(0, maxLength));
                    return false;
                }
                return true;
            }
        });


        return this;
    }

    @Override
    public GDXTextPrompt setTitle(CharSequence title) {
        this.title = (String) title;
        return this;
    }

    @Override
    public GDXTextPrompt setMaxLength(int maxLength) {
        if (maxLength < 1) {
            throw new RuntimeException("Char limit must be >= 1");
        }
        this.maxLength = maxLength;
        return this;
    }

    @Override
    public GDXTextPrompt setMessage(CharSequence message) {
        this.message = (String) message;
        return this;
    }

    @Override
    public GDXTextPrompt setValue(CharSequence inputTip) {
        return this;
    }

    @Override
    public GDXTextPrompt setCancelButtonLabel(CharSequence label) {
        this.cancelLabel = (String) label;
        return this;
    }

    @Override
    public GDXTextPrompt setConfirmButtonLabel(CharSequence label) {
        this.confirmLabel = (String) label;
        return this;
    }

    @Override
    public GDXTextPrompt setTextPromptListener(TextPromptListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public GDXTextPrompt setInputType(InputType inputType) {
        switch (inputType) {
            case PLAIN_TEXT:
                this.inputType = UIAlertViewStyle.PlainTextInput;
                break;

            case PASSWORD:
                this.inputType = UIAlertViewStyle.SecureTextInput;
                break;
        }
        return this;
    }

    @Override
    public GDXTextPrompt dismiss() {
        if (alertView == null) {
            throw new RuntimeException(GDXTextPrompt.class.getSimpleName() + " has not been build. Use build() before dismiss().");
        }
        Gdx.app.debug(GDXDialogsVars.LOG_TAG, IOSGDXTextPrompt.class.getSimpleName() + " dismissed.");
        alertView.dismiss(0, false);
        return this;
    }

}
