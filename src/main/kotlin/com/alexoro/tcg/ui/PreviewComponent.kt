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

package com.alexoro.tcg.ui

import com.intellij.ui.EditorTextField
import com.intellij.ui.components.JBScrollPane
import java.awt.BorderLayout
import javax.swing.JPanel

/**
 * Component for display source code of selected file in template
 */
internal class PreviewComponent : JPanel(BorderLayout()) {

    private val component = EditorTextField().apply {
        isViewer = true
    }

    var content: String
        get() = component.text
        set(value) { component.text = value }

    init {
        add(JBScrollPane(component))
    }

    override fun isRequestFocusEnabled(): Boolean {
        return false
    }

}
