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
import com.intellij.ui.table.JBTable
import java.awt.BorderLayout
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.table.AbstractTableModel

/**
 * Component for view and edit template placeholders
 */
internal class PlaceholderListComponent(
    private val lang: Lang,
) : JPanel(BorderLayout()) {

    private val tableComponent = JBTable()
    private var tableModel: TableModel = TableModel()
    private var placeholders: Collection<String> = emptyList()

    init {
        tableComponent.model = tableModel
        this.add(JScrollPane(tableComponent))
    }

    fun getPlaceholderValues(): Map<String, String> {
        return tableModel.getPlaceholderValues()
    }

    fun setPlaceholders(placeholders: Collection<String>) {
        this.placeholders = placeholders
        this.tableModel = TableModel(lang.columnPlaceholderKey, lang.columnPlaceholderValue, placeholders)
        this.tableComponent.model = tableModel
    }

    private class TableModel(
        columnPlaceholderTitle: String = "",
        columnValueTitle: String = "",
        placeholders: Collection<String> = emptyList(),
    ) : AbstractTableModel() {

        private companion object {
            const val COL_PLACEHOLDER = 0
            const val COL_VALUE = 1
        }

        private class Item(
            var placeholder: String,
            var value: String,
        )

        private val headers = listOf(columnPlaceholderTitle, columnValueTitle)
        private val items = placeholders.map {
            Item(placeholder = it, value = "")
        }

        override fun getColumnCount(): Int {
            return headers.size
        }

        override fun getColumnName(column: Int): String {
            return headers[column]
        }

        override fun getRowCount(): Int {
            return items.size
        }

        override fun isCellEditable(rowIndex: Int, columnIndex: Int): Boolean {
            return columnIndex == COL_VALUE
        }

        override fun getValueAt(rowIndex: Int, columnIndex: Int): Any {
            val item = items[rowIndex]
            return when (columnIndex) {
                COL_PLACEHOLDER -> item.placeholder
                COL_VALUE -> item.value
                else -> throw IllegalArgumentException("Illegal columnIndex: $columnIndex")
            }
        }

        override fun setValueAt(aValue: Any?, rowIndex: Int, columnIndex: Int) {
            val value = aValue as? String
            if (value != null) {
                items[rowIndex].value = value
            }
        }

        fun getPlaceholderValues(): Map<String, String> {
            return items.associate { it.placeholder to it.value }
        }
    }

}
