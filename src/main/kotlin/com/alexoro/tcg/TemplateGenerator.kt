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

import com.intellij.openapi.application.Application
import com.intellij.openapi.vfs.VirtualFile

/**
 * Class for generating real files in project from Template and placeholders
 */
internal class TemplateGenerator(
    private val application: Application,
    private val placeholders: TemplatePlaceholders,
) {

    private val requestor = Any()

    /**
     * Generate files from template
     *
     */
    fun generate(
        vfTargetDir: VirtualFile,
        template: Template,
        replacements: Map<String, String>,
    ) {
        val writeAction = Runnable {
            createDirectoryContents(vfTargetDir, template.rootDirectory, replacements)
        }
        if (application.isDispatchThread) {
            application.runWriteAction(writeAction)
        } else {
            application.invokeLater { application.runWriteAction(writeAction) }
        }
    }

    private fun createDirectoryContents(
        vfParent: VirtualFile,
        directory: TemplateDirectory,
        replacements: Map<String, String>,
    ) {
        directory.dirs.forEach { dir ->
            createDirectory(vfParent, dir, replacements)
        }
        directory.files.forEach { file ->
            createFile(vfParent, file, replacements)
        }
    }

    private fun createDirectory(
        vfParent: VirtualFile,
        directory: TemplateDirectory,
        replacements: Map<String, String>,
    ) {
        val dirName = placeholders.replaceAll(directory.name, replacements)
        val existingDir = vfParent.findChild(dirName)
        if (existingDir == null) {
            val childDir = vfParent.createChildDirectory(requestor, dirName)
            createDirectoryContents(childDir, directory, replacements)
        } else {
            createDirectoryContents(existingDir, directory, replacements)
        }
    }

    private fun createFile(
        vfParent: VirtualFile,
        file: TemplateFile,
        replacements: Map<String, String>,
    ) {
        val fileName = placeholders.replaceAll(file.name, replacements)
        val fileContent = placeholders.replaceAll(file.content, replacements)
        if (vfParent.findChild(fileName) == null) {
            val childFile = vfParent.createChildData(requestor, fileName)
            childFile.setBinaryContent(fileContent.toByteArray())
        }
    }

}
