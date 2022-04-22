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

import com.alexoro.tcg.Icons
import com.alexoro.tcg.Template
import com.alexoro.tcg.TemplateDirectory
import com.alexoro.tcg.TemplateFile
import com.intellij.ui.components.JBLabel
import com.intellij.ui.treeStructure.Tree
import java.awt.BorderLayout
import java.awt.Component
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTree
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeModel
import javax.swing.tree.TreeCellRenderer
import javax.swing.tree.TreeNode

/**
 * Component with preview of template hierarchy and files in it
 */
internal class TemplateTreeComponent(
    private val icons: Icons,
) : JPanel(BorderLayout()) {

    private val treeComponent = Tree()
    private var templatePreviewListener: ((TemplateFile) -> Unit)? = null

    init {
        with(treeComponent) {
            cellRenderer = TreeCellRendererImpl(icons)
            addMouseListener(DoubleClickListener(this, ::onFileDoubleClick))
        }
        this.add(JScrollPane(treeComponent))
    }

    fun setTemplate(template: Template) {
        treeComponent.model = DefaultTreeModel(toTreeNode(template))
        expandAllRows()
    }

    fun setTemplatePreviewListener(listener: ((TemplateFile) -> Unit)?) {
        templatePreviewListener = listener
    }

    private fun expandAllRows() {
        var i = 0
        var j = treeComponent.rowCount
        while (i < j) {
            treeComponent.expandRow(i)
            i += 1
            j = treeComponent.rowCount
        }
    }

    private fun onFileDoubleClick(file: TemplateFile) {
        templatePreviewListener?.invoke(file)
    }

    // -----------------------------------------------------------------------------------------------------------------

    private class TreeCellRendererImpl(
        private val icons: Icons,
    ) : TreeCellRenderer {

        private val label = JBLabel()

        override fun getTreeCellRendererComponent(
            tree: JTree,
            value: Any?,
            selected: Boolean,
            expanded: Boolean,
            leaf: Boolean,
            row: Int,
            hasFocus: Boolean
        ): Component {
            val descriptionObj = (value as? DefaultMutableTreeNode)?.userObject
            label.text = when (descriptionObj) {
                is TemplateDirectory -> descriptionObj.name
                is TemplateFile -> descriptionObj.name
                else -> ""
            }
            label.icon = when (descriptionObj) {
                is TemplateDirectory -> icons.directory
                is TemplateFile -> icons.forFile(descriptionObj.name)
                else -> icons.fileAny
            }
            return label
        }
    }

    private class DoubleClickListener(
        private val treeComponent: Tree,
        private val onFileDoubleClick: (TemplateFile) -> Unit,
    ) : MouseAdapter() {
        override fun mousePressed(e: MouseEvent) {
            val node = treeComponent.lastSelectedPathComponent as? DefaultMutableTreeNode
            val templateFile = node?.userObject as? TemplateFile
            if (templateFile != null && e.clickCount == 2) {
                onFileDoubleClick.invoke(templateFile)
            }
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    private fun toTreeNode(template: Template): TreeNode {
        return toTreeNode(template.rootDirectory)
    }

    private fun toTreeNode(templateDirectory: TemplateDirectory): DefaultMutableTreeNode {
        val node = DefaultMutableTreeNode(templateDirectory)

        templateDirectory.dirs
            .map { toTreeNode(it) }
            .forEach { node.add(it) }
        templateDirectory.files
            .map { toTreeNode(it) }
            .forEach { node.add(it) }

        return node
    }

    private fun toTreeNode(templateFile: TemplateFile): DefaultMutableTreeNode {
        return DefaultMutableTreeNode(templateFile)
    }

}
