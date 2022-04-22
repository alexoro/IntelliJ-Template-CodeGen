/*
 * Copyright 2022 Alexoro (uas.sorokin@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alexoro.tcg.settings

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

/**
 * Settings for project. May be changed in 'Preferences' -> 'Tools'
 */
@State(
    name = "com.alexoro.tcg.ModuleSettingsState",
    storages = [Storage("Template-CodeGen.xml")],
)
class ModuleSettingsState : PersistentStateComponent<ModuleSettingsState> {

    var projectTemplatesDir = ""
    var externalTemplatesDir = ""

    override fun getState(): ModuleSettingsState {
        return this
    }

    override fun loadState(state: ModuleSettingsState) {
        XmlSerializerUtil.copyBean(state, this)
    }

}
