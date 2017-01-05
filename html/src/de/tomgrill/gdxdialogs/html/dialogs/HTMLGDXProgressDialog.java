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

import de.tomgrill.gdxdialogs.core.dialogs.GDXButtonDialog;
import de.tomgrill.gdxdialogs.core.dialogs.GDXProgressDialog;

@SuppressWarnings("JniMissingFunction")
public class HTMLGDXProgressDialog implements GDXProgressDialog {

    String title, message = "";
    boolean isBuild = false;

    @Override
    public GDXProgressDialog setMessage(CharSequence message) {
        this.message = (String) message;
        return this;
    }

    @Override
    public GDXProgressDialog setTitle(CharSequence title) {
        this.title = (String) title;
        return this;
    }

    @Override
    public GDXProgressDialog show() {
        if (isBuild == false) {
            throw new RuntimeException(GDXButtonDialog.class.getSimpleName() + " has not been build. Use build() before show().");
        }
        showJSProgressDialog(toString());
        return this;
    }

    @Override
    public GDXProgressDialog dismiss() {
        if (isBuild == false) {
            throw new RuntimeException(GDXButtonDialog.class.getSimpleName() + " has not been build. Use build() before show().");
        }
        dismissJSProgressDialog(toString());
        return this;
    }

    @Override
    public GDXProgressDialog build() {
        createJSProgressDialog(title, message, toString());
        isBuild = true;
        return this;
    }

    protected native void createJSProgressDialog(String title, String message, String id)/*-{
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
    }-*/;

    protected native void showJSProgressDialog(String id)/*-{
        $doc.getElementById(id + "-background").style = "z-index: 1000000; position: absolute; top: 0px; left: 0px; bottom: 0px; right: 0px; background-color: rgb(0, 0, 0); opacity: 0.5;";
        $doc.getElementById(id).style = "z-index: 1000001; position: fixed; top: 5em; left: 3em; right: 3em; background-color: white; border: 0.5em solid rgb(233,233,233); padding: 1em; border-radius: 0.3em; word-wrap: break-word; opacity: 0.9;"
    }-*/;

    protected native void dismissJSProgressDialog(String id)/*-{
        $doc.getElementById(id + "-background").style = "display:none;";
        $doc.getElementById(id).style = "display:none;";
        // var n = $doc.getElementById(id);
        // n.parentNode.removeChild(n);
    }-*/;
}
