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

import com.alexoro.tcg.Environment
import com.alexoro.tcg.Template
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.vfs.VirtualFile
import java.awt.Dimension
import java.awt.FlowLayout
import javax.swing.BorderFactory
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel

/**
 * Dialog where user selects required template, change placeholders and generate files from template
 */
internal class TemplateGeneratorDialog(
    private val env: Environment,
    private val vfTargetDir: VirtualFile,
) : DialogWrapper(true) {

    private companion object {
        const val TEMPLATES_LIST_WIDTH = 180
        const val TEMPLATES_TREE_WIDTH = 280
        const val PLACEHOLDERS_WIDTH = 500
        const val HEIGHT = 320
    }

    private val rootComponent: JComponent
    private val templateListComponent: TemplateListComponent
    private val templateTreeComponent: TemplateTreeComponent
    private val placeholderListComponent: PlaceholderListComponent

    init {
        title = env.lang.windowGeneratorTitle

        templateListComponent = createTemplateListComponent()
        templateTreeComponent = createTemplateTreeComponent()
        placeholderListComponent = createPlaceholderListComponent()

        rootComponent = JPanel(FlowLayout(FlowLayout.LEFT)).apply {
            add(templateListComponent)
            add(templateTreeComponent)
            add(placeholderListComponent)
        }

        init()
    }

    override fun createNorthPanel(): JComponent {
        return JLabel().apply {
            border = BorderFactory.createEmptyBorder(0, 8, 0 , 0)
            text = env.lang.windowGeneratorTargetRoot.format(vfTargetDir.canonicalPath ?: "")
        }
    }

    override fun createCenterPanel(): JComponent {
        setupUi()
        return rootComponent
    }

    override fun doOKAction() {
        onCreateSelectedTemplate()
        super.doOKAction()
    }

    private fun createTemplateListComponent(): TemplateListComponent {
        return TemplateListComponent().apply {
            border = BorderFactory.createEtchedBorder()
            preferredSize = Dimension(TEMPLATES_LIST_WIDTH, HEIGHT)
        }
    }

    private fun createTemplateTreeComponent(): TemplateTreeComponent {
        return TemplateTreeComponent(
            icons = env.icons,
        ).apply {
            border = BorderFactory.createEtchedBorder()
            preferredSize = Dimension(TEMPLATES_TREE_WIDTH, HEIGHT)
        }
    }

    private fun createPlaceholderListComponent(): PlaceholderListComponent {
        return PlaceholderListComponent(
            lang = env.lang,
        ).apply {
            border = BorderFactory.createEtchedBorder()
            preferredSize = Dimension(PLACEHOLDERS_WIDTH, HEIGHT)
        }
    }

    private fun setupUi() {
        val templates = env.parser.parse(env.vfTemplateDirs).sortedBy { it.title }

        isOKActionEnabled = templates.isNotEmpty()

        templateListComponent.setTemplateList(templates)
        templateListComponent.setTemplateSelectionListener {
            onTemplateSelected(it)
        }

        templateTreeComponent.setTemplatePreviewListener { file ->
            TemplatePreviewDialog(file).show()
        }

        val selectedTemplate = templateListComponent.getTemplateSelection()
        if (selectedTemplate != null) {
            onTemplateSelected(selectedTemplate)
        }
    }

    private fun onTemplateSelected(template: Template) {
        templateTreeComponent.setTemplate(template)
        val placeholders = env.placeholders.findAll(template).toList()
        placeholderListComponent.setPlaceholders(placeholders)
    }

    private fun onCreateSelectedTemplate() {
        val template = templateListComponent.getTemplateSelection()
        val placeholderValues = placeholderListComponent.getPlaceholderValues()
        if (template != null) {
            env.generator.generate(
                vfTargetDir = vfTargetDir,
                template = template,
                replacements = placeholderValues,
            )
        }
    }

}
