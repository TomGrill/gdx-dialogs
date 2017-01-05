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

package de.tomgrill.gdxdialogs.html;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.reflect.ClassReflection;

import de.tomgrill.gdxdialogs.core.GDXDialogs;
import de.tomgrill.gdxdialogs.core.GDXDialogsVars;
import de.tomgrill.gdxdialogs.core.dialogs.*;
import de.tomgrill.gdxdialogs.html.dialogs.*;

public class HTMLGDXDialogs extends GDXDialogs {

    public HTMLGDXDialogs() {
        registerDialog(GDXButtonDialog.class.getName(), HTMLGDXButtonDialog.class.getName());
        registerDialog(GDXProgressDialog.class.getName(), HTMLGDXProgressDialog.class.getName());
        registerDialog(GDXTextPrompt.class.getName(), HTMLGDXTextPrompt.class.getName());
    }

}
