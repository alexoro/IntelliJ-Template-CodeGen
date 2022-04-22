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

import com.alexoro.tcg.TemplateFile
import com.intellij.openapi.ui.DialogWrapper
import java.awt.Dimension
import javax.swing.Action
import javax.swing.JComponent

/**
 * Dialog with preview of selected file from template
 */
internal class TemplatePreviewDialog(
    file: TemplateFile,
) : DialogWrapper(false) {

    private companion object {
        const val WIDTH = 900
        const val HEIGHT = 500
    }

    private val previewComponent = PreviewComponent()

    init {
        title = file.name
        isOKActionEnabled = false
        with(previewComponent) {
            preferredSize = Dimension(WIDTH, HEIGHT)
            content = file.content
        }
        init()
    }

    override fun createCenterPanel(): JComponent {
        return previewComponent
    }

    override fun createActions(): Array<Action> {
        return emptyArray()
    }

}
