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

import com.alexoro.tcg.Template
import com.intellij.ui.components.JBList
import java.awt.BorderLayout
import javax.swing.DefaultListModel
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.ListSelectionModel

/**
 * Component with all available templates
 */
internal class TemplateListComponent : JPanel(BorderLayout()) {

    private val listComponent = JBList<String>()
    private var listModel = DefaultListModel<String>()
    private var templateList = emptyList<Template>()
    private var onTemplateSelectionListener: ((Template) -> Unit)? = null

    init {
        with(listComponent) {
            model = listModel
            selectionMode = ListSelectionModel.SINGLE_SELECTION
            addListSelectionListener { event ->
                val index = (event.source as? JBList<*>)?.selectedIndex ?: -1
                val template = templateList.getOrNull(index)
                if (template != null) {
                    onTemplateSelectionListener?.invoke(template)
                }
            }
        }
        this.add(JScrollPane(listComponent))
    }

    fun setTemplateList(templateList: List<Template>) {
        this.templateList = templateList
        this.listModel = templateList.toListModel()
        listComponent.model = listModel
        if (templateList.isNotEmpty()) {
            listComponent.selectedIndex = 0
        }
    }

    fun getTemplateSelection(): Template? {
        return templateList.getOrNull(listComponent.selectedIndex)
    }

    fun setTemplateSelectionListener(listener: ((Template) -> Unit)?) {
        onTemplateSelectionListener = listener
    }

    private fun List<Template>.toListModel(): DefaultListModel<String> {
        val result = DefaultListModel<String>()
        this.forEach { template ->
            result.addElement(template.title)
        }
        return result
    }

}
