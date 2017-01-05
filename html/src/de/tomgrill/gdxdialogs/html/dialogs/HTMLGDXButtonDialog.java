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

package de.tomgrill.gdxdialogs.html.dialogs;

import com.badlogic.gdx.utils.Array;

import de.tomgrill.gdxdialogs.core.dialogs.GDXButtonDialog;
import de.tomgrill.gdxdialogs.core.listener.ButtonClickListener;

@SuppressWarnings("JniMissingFunction")
public class HTMLGDXButtonDialog implements GDXButtonDialog {

    String title, message = "";
    boolean isBuild = false;
    ButtonClickListener listener;

    Array<String> labels = new Array<String>();

    @Override
    public GDXButtonDialog setCancelable(boolean cancelable) {
        return this;
    }

    @Override
    public GDXButtonDialog show() {
        if (isBuild == false) {
            throw new RuntimeException(GDXButtonDialog.class.getSimpleName() + " has not been build. Use build() before show().");
        }
        showJSButtonDialog(toString());
        return this;
    }

    @Override
    public GDXButtonDialog dismiss() {
        if (isBuild == false) {
            throw new RuntimeException(GDXButtonDialog.class.getSimpleName() + " has not been build. Use build() before dismiss().");
        }
        dismissJSButtonDialog(toString());
        return this;
    }

    @Override
    public GDXButtonDialog setClickListener(ButtonClickListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public GDXButtonDialog addButton(CharSequence label) {
        if (labels.size >= 3) {
            throw new RuntimeException("You can only have up to three buttons added.");
        }
        labels.add((String) label);
        return this;
    }

    @Override
    public GDXButtonDialog build() {
        switch (labels.size) {
            case 1:
                createJSButtonDialog(title, message, toString(), listener, labels.get(0), null, null);
                break;
            case 2:
                createJSButtonDialog(title, message, toString(), listener, labels.get(0), labels.get(1), null);
                break;
            case 3:
                createJSButtonDialog(title, message, toString(), listener, labels.get(0), labels.get(1), labels.get(2));
                break;
            default:
                throw new RuntimeException("Invalid button number:" + labels.size);
        }

        isBuild = true;
        return this;
    }

    @Override
    public GDXButtonDialog setMessage(CharSequence message) {
        this.message = (String) message;
        return this;
    }

    @Override
    public GDXButtonDialog setTitle(CharSequence title) {
        this.title = (String) title;
        return this;
    }

    protected native void createJSButtonDialog(String title, String message, String id, ButtonClickListener listener, String label1, String label2, String label3)/*-{
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

        if (label1){
            var button1 = $doc.createElement('input');
            button1.id = id + "-button1";
            button1.type = "button"
            button1.style = "font-size: 1em; margin-top: 10px;";
            button1.value = label1;
            var button1Action = function() {
                listener.@de.tomgrill.gdxdialogs.core.listener.ButtonClickListener::click(I)(0);
                $doc.getElementById(id + "-background").style = "display:none;";
                $doc.getElementById(id).style = "display:none;";
            }
            if($wnd.addEventListener){ // Mozilla, Netscape, Firefox
                button1.addEventListener('click', button1Action, false);
            } else { // IE
                button1.attachEvent('onclick', button1Action);
            }
            void($doc.getElementById(id).appendChild(button1));
        }

        var br1 = $doc.createElement('br');
        br1.id = id + "-br1";
        void($doc.getElementById(id + "-message").appendChild(br1));

        if (label2){
            var button2 = $doc.createElement('input');
            button2.id = id + "-button2";
            button2.type = "button"
            button2.style = "font-size: 1em; margin-top: 10px;";
            button2.value = label2;
            var button2Action = function() {
                listener.@de.tomgrill.gdxdialogs.core.listener.ButtonClickListener::click(I)(1);
                $doc.getElementById(id + "-background").style = "display:none;";
                $doc.getElementById(id).style = "display:none;";
            }
            if($wnd.addEventListener){ // Mozilla, Netscape, Firefox
                button2.addEventListener('click', button2Action, false);
            } else { // IE
                button2.attachEvent('onclick', button2Action);
            }
            void($doc.getElementById(id).appendChild(button2));
        }

        if (label3){
            var button3 = $doc.createElement('input');
            button3.id = id + "-button3";
            button3.type = "button"
            button3.style = "font-size: 1em; margin-top: 10px;";
            button3.value = label3;
            var button3Action = function() {
                listener.@de.tomgrill.gdxdialogs.core.listener.ButtonClickListener::click(I)(2);
                $doc.getElementById(id + "-background").style = "display:none;";
                $doc.getElementById(id).style = "display:none;";
            }
            if($wnd.addEventListener){ // Mozilla, Netscape, Firefox
                button3.addEventListener('click', button3Action, false);
            } else { // IE
                button3.attachEvent('onclick', button3Action);
            }
            void($doc.getElementById(id).appendChild(button3));
        }
    }-*/;

    protected native void showJSButtonDialog(String id)/*-{
        $doc.getElementById(id + "-background").style = "z-index: 1000000; position: absolute; top: 0px; left: 0px; bottom: 0px; right: 0px; background-color: rgb(0, 0, 0); opacity: 0.5;";
        $doc.getElementById(id).style = "z-index: 1000001; position: fixed; top: 5em; left: 3em; right: 3em; background-color: white; border: 0.5em solid rgb(233,233,233); padding: 1em; border-radius: 0.3em; word-wrap: break-word; opacity: 0.9;"
    }-*/;

    protected native void dismissJSButtonDialog(String id)/*-{
        $doc.getElementById(id + "-background").style = "display:none;";
        $doc.getElementById(id).style = "display:none;";
        // var n = $doc.getElementById(id);
        // n.parentNode.removeChild(n);
    }-*/;

}
