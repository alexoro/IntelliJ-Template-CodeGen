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

package com.alexoro.tcg

import java.util.*

/**
 * Holder of all texts in plugin
 */
@Suppress("unused")
class Lang(langFileName: String) {

    private val bundle = ResourceBundle.getBundle(langFileName)

    val windowGeneratorTitle: String = bundle.getString("windowGeneratorTitle")
    val windowGeneratorTargetRoot: String = bundle.getString("windowGeneratorTargetRoot")
    val columnPlaceholderKey: String = bundle.getString("columnPlaceholderKey")
    val columnPlaceholderValue: String = bundle.getString("columnPlaceholderValue")

    val settingsTitle: String = bundle.getString("settingsTitle")
    val settingsInfo: String = bundle.getString("settingsInfo")
    val settingsProject: String = bundle.getString("settingsProject")
    val settingsExternal: String = bundle.getString("settingsExternal")
    val settingsClear: String = bundle.getString("settingsClear")
    val settingsDocsText: String = bundle.getString("settingDocsText")
    val settingsDocsLink: String = bundle.getString("settingDocsLink")

}
