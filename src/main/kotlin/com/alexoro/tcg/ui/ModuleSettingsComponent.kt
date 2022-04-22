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

import com.alexoro.tcg.Lang
import com.intellij.ide.BrowserUtil
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import java.awt.BorderLayout
import java.awt.Component
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JButton
import javax.swing.JFileChooser
import javax.swing.JPanel

/**
 * Component for display settings of plugin.
 * It is shown in 'Preferences' -> 'Tools' section.
 */
internal class ModuleSettingsComponent(
    private val lang: Lang,
): JPanel(BorderLayout()) {

    private val infoComponent: JBLabel
    private val projectLabelComponent: JBLabel
    private val projectInputComponent: JBTextField
    private val projectClearComponent: JButton
    private val externalLabelComponent: JBLabel
    private val externalInputComponent: JBTextField
    private val externalClearComponent: JButton
    private val docsComponent: JButton
    private val fileChooser: JFileChooser

    var projectTemplatesDir: String
        get() = projectInputComponent.text
        set(value) { projectInputComponent.text = value }
    var externalTemplatesDir: String
        get() = externalInputComponent.text
        set(value) { externalInputComponent.text = value }

    init {
        infoComponent = createInfoComponent()
        projectLabelComponent = createProjectLabelComponent()
        projectInputComponent = createProjectInputComponent(onClick = ::onProjectInputClick)
        projectClearComponent = createProjectClearComponent(onClick = ::onProjectClearClick)
        externalLabelComponent = createExternalLabelComponent()
        externalInputComponent = createExternalInputComponent(onClick = ::onExternalInputClick)
        externalClearComponent = createExternalClearComponent(onClick = ::onExternalClearClick)
        docsComponent = createDocsComponent()
        fileChooser = createFileChooser()

        val panel = FormBuilder.createFormBuilder()
            .addComponent(infoComponent)
            .addLabeledComponent(
                projectLabelComponent,
                createInputWithClearComponent(projectInputComponent, projectClearComponent),
                1,
                false)
            .addLabeledComponent(
                externalLabelComponent,
                createInputWithClearComponent(externalInputComponent, externalClearComponent),
                1,
                false)
            .addComponent(docsComponent)
            .addComponentFillVertically(JPanel(), 0)
            .panel
        this.add(panel)
    }

    private fun createInfoComponent(): JBLabel {
        return JBLabel().apply {
            text = lang.settingsInfo
        }
    }

    private fun createProjectLabelComponent(): JBLabel {
        return JBLabel().apply {
            text = lang.settingsProject
        }
    }

    private fun createProjectInputComponent(
        onClick: () -> Unit,
    ): JBTextField {
        return JBTextField().apply {
            isEditable = false
            addMouseListener(object : MouseAdapter() {
                override fun mouseClicked(e: MouseEvent?) {
                    onClick.invoke()
                }
            })
        }
    }

    private fun createProjectClearComponent(
        onClick: () -> Unit,
    ): JButton {
        return JButton().apply {
            text = lang.settingsClear
            addMouseListener(object : MouseAdapter() {
                override fun mouseClicked(e: MouseEvent?) {
                    onClick.invoke()
                }
            })
        }
    }

    private fun createExternalLabelComponent(): JBLabel {
        return JBLabel().apply {
            text = lang.settingsExternal
        }
    }

    private fun createExternalInputComponent(
        onClick: () -> Unit,
    ): JBTextField {
        return JBTextField().apply {
            isEditable = false
            addMouseListener(object : MouseAdapter() {
                override fun mouseClicked(e: MouseEvent?) {
                    onClick.invoke()
                }
            })
        }
    }

    private fun createExternalClearComponent(
        onClick: () -> Unit,
    ): JButton {
        return JButton().apply {
            text = lang.settingsClear
            addMouseListener(object : MouseAdapter() {
                override fun mouseClicked(e: MouseEvent?) {
                    onClick.invoke()
                }
            })
        }
    }

    private fun createInputWithClearComponent(
        inputComponent: Component,
        clearComponent: Component,
    ): JPanel {
        return JPanel(GridBagLayout()).apply {
            val inputConstraints = GridBagConstraints().apply {
                fill = GridBagConstraints.HORIZONTAL
                weightx = 0.8
            }
            val buttonConstraints = GridBagConstraints().apply {
                fill = GridBagConstraints.HORIZONTAL
                weightx = 0.2
            }
            add(inputComponent, inputConstraints)
            add(clearComponent, buttonConstraints)
        }
    }

    private fun createDocsComponent(): JButton {
        return JButton().apply {
            text = lang.settingsDocsText
            addMouseListener(object : MouseAdapter() {
                override fun mouseClicked(e: MouseEvent?) {
                    BrowserUtil.browse(lang.settingsDocsLink)
                }
            })
        }
    }

    private fun createFileChooser(): JFileChooser {
        return JFileChooser().apply {
            fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
            isAcceptAllFileFilterUsed = false
        }
    }

    private fun onProjectInputClick() {
        val option = fileChooser.showOpenDialog(this)
        if (option == JFileChooser.APPROVE_OPTION) {
            projectInputComponent.text = fileChooser.selectedFile?.absolutePath ?: ""
        }
    }

    private fun onProjectClearClick() {
        projectInputComponent.text = ""
    }

    private fun onExternalInputClick() {
        val option = fileChooser.showOpenDialog(this)
        if (option == JFileChooser.APPROVE_OPTION) {
            externalInputComponent.text = fileChooser.selectedFile?.absolutePath ?: ""
        }
    }

    private fun onExternalClearClick() {
        externalInputComponent.text = ""
    }

}
