/*******************************************************************************
 * Copyright 2015 See AUTHORS file.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package de.tomgrill.gdxdialogs.html.dialogs;

import com.badlogic.gdx.utils.ObjectMap;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import de.tomgrill.gdxdialogs.core.dialogs.GDXButtonDialog;
import de.tomgrill.gdxdialogs.core.dialogs.GDXTextPrompt;
import de.tomgrill.gdxdialogs.core.listener.TextPromptListener;

@SuppressWarnings("JniMissingFunction")
public class HTMLGDXTextPrompt implements GDXTextPrompt {

    public static Map<String, HTMLGDXTextPrompt> dialogs = new LinkedHashMap<String, HTMLGDXTextPrompt>();

    String title, message, value, cancel, confirm = "";
    TextPromptListener listener;
    boolean isBuild = false;

    @Override
    public GDXTextPrompt setTitle(CharSequence title) {
        this.title = (String) title;
        return this;
    }

    @Override
    public GDXTextPrompt show() {
        if (isBuild == false) {
            throw new RuntimeException(GDXButtonDialog.class.getSimpleName() + " has not been build. Use build() before show().");
        }
        showJSTextPrompt(toString());
        return this;
    }

    @Override
    public GDXTextPrompt dismiss() {
        if (isBuild == false) {
            throw new RuntimeException(GDXButtonDialog.class.getSimpleName() + " has not been build. Use build() before dismiss().");
        }
        dismissJSTextPrompt(toString());
        return this;
    }

    @Override
    public GDXTextPrompt build() {
        createJSTextPrompt(title, message, toString(), value, cancel, confirm, listener); //this.toString() is the dialog id.
        isBuild = true;
        return this;
    }

    @Override
    public GDXTextPrompt setMessage(CharSequence message) {
        this.message = (String) message;
        return this;
    }

    @Override
    public GDXTextPrompt setValue(CharSequence message) {
        this.value = (String) message;
        return this;
    }

    @Override
    public GDXTextPrompt setCancelButtonLabel(CharSequence label) {
        this.cancel = (String) label;
        return this;
    }

    @Override
    public GDXTextPrompt setConfirmButtonLabel(CharSequence label) {
        this.confirm = (String) label;
        return this;
    }

    @Override
    public GDXTextPrompt setTextPromptListener(TextPromptListener listener) {
        this.listener = listener;
        return this;
    }

    public static void cancel(String id) {
        Iterator iterator = dialogs.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            if (id.equals(entry.getKey())) {
                HTMLGDXTextPrompt prompt = (HTMLGDXTextPrompt) (entry.getValue());
                if (prompt.listener != null) {
                    prompt.listener.cancel();
                }

            }
        }
    }

    public static void confirm(String id, String text) {
        Iterator iterator = dialogs.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            if (id.equals(entry.getKey())) {
                HTMLGDXTextPrompt prompt = (HTMLGDXTextPrompt) (entry.getValue());
                if (prompt.listener != null) {
                    prompt.listener.confirm(text);
                }

            }
        }
    }

    protected native void createJSTextPrompt(String title, String message, String id, String value, String cancel, String confirm, TextPromptListener listener)/*-{
        var background = $doc.createElement('div');
        background.id = id + "-background";
        background.style = "display:none;";
        void($doc.getElementsByTagName('body')[0].appendChild(background));

        var dialog = $doc.createElement('div');
        dialog.id = id;
        dialog.style = "display:none;";
        void($doc.getElementsByTagName('body')[0].appendChild(dialog));

        var titleNode = $doc.createElement('div');
        titleNode.id = id + "-title";
        titleNode.style = "font-size: 2em;";
        titleNode.innerHTML = title;
        void($doc.getElementById(id).appendChild(titleNode));

        var messageNode = $doc.createElement('p');
        messageNode.id = id + "-message";
        messageNode.innerHTML = message;
        void($doc.getElementById(id).appendChild(messageNode));

        var br = $doc.createElement('br');
        br.id = id + "-br";
        void($doc.getElementById(id + "-message").appendChild(br));

        var input = $doc.createElement('input');
        input.id = id + "-input";
        input.type = "text";
        if (value) {
            input.value = value;
        } else {
            input.value = "";
        }
        void($doc.getElementById(id + "-message").appendChild(input));

        var cancelNode = $doc.createElement('input');
        cancelNode.id = id + "-cancel";
        cancelNode.type = "button"
        cancelNode.style = "font-size: 1em; margin-top: 10px;";
        cancelNode.value = cancel;
        var cancelAction = function() {
            listener.@de.tomgrill.gdxdialogs.core.listener.TextPromptListener::cancel()();
            $doc.getElementById(id + "-background").style = "display:none;";
            $doc.getElementById(id).style = "display:none;";
        }
        // cancelNode.onClick = cancelAction;
        if($wnd.addEventListener){ // Mozilla, Netscape, Firefox
            cancelNode.addEventListener('click', cancelAction, false);
        } else { // IE
            cancelNode.attachEvent('onclick', cancelAction);
        }
        void($doc.getElementById(id).appendChild(cancelNode));

        var confirmNode = $doc.createElement('input');
        confirmNode.id = id + "-confirm";
        confirmNode.style = "font-size: 1em; margin-top: 10px;";
        confirmNode.value = confirm;
        var confirmAction = function() {
            listener.@de.tomgrill.gdxdialogs.core.listener.TextPromptListener::confirm(Ljava/lang/String;)($doc.getElementById(id + "-input").value);
            $doc.getElementById(id + "-background").style = "display:none;";
            $doc.getElementById(id).style = "display:none;";
        }
        if($wnd.addEventListener){ // Mozilla, Netscape, Firefox
            confirmNode.addEventListener('click', confirmAction, false);
        } else { // IE
            confirmNode.attachEvent('onclick', confirmAction);
        }
        void($doc.getElementById(id).appendChild(confirmNode));
    }-*/;

    protected native void showJSTextPrompt(String id)/*-{
        $doc.getElementById(id + "-background").style = "z-index: 1000000; position: absolute; top: 0px; left: 0px; bottom: 0px; right: 0px; background-color: rgb(0, 0, 0); opacity: 0.5;";
        $doc.getElementById(id).style = "z-index: 1000001; position: fixed; top: 5em; left: 3em; right: 3em; background-color: white; border: 0.5em solid rgb(233,233,233); padding: 1em; border-radius: 0.3em; word-wrap: break-word; opacity: 0.9;"
    }-*/;

    protected native void dismissJSTextPrompt(String id)/*-{
        $doc.getElementById(id + "-background").style = "display:none;";
        $doc.getElementById(id).style = "display:none;";
        // var n = $doc.getElementById(id);
        // n.parentNode.removeChild(n);
    }-*/;
}
