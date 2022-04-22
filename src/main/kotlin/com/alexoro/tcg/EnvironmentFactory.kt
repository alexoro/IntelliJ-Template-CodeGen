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

import com.alexoro.tcg.settings.ModuleSettingsState
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile
import java.io.File

/**
 * Utility class for building [Environment]
 */
internal object EnvironmentFactory {
    fun create(
        project: Project,
    ): Environment {
        val application = ApplicationManager.getApplication()
        val lang = Lang(langFileName = "lang")
        val icons = Icons()
        val placeholders = TemplatePlaceholders()
        val parser = TemplateParser()
        val generator = TemplateGenerator(application, placeholders)
        return Environment(
            project = project,
            vfTemplateDirs = findTemplateDirs(project),
            lang = lang,
            icons = icons,
            placeholders = placeholders,
            parser = parser,
            generator = generator,
        )
    }

    private fun findTemplateDirs(project: Project): Collection<VirtualFile> {
        val settingsState = project.getService(ModuleSettingsState::class.java)
        val projectTemplatesDirFile = File(settingsState.projectTemplatesDir)
        val externalTemplatesDirFile = File(settingsState.externalTemplatesDir)

        val vfProjectTemplatesDir = when (isValidTemplateFile(projectTemplatesDirFile)) {
            true -> LocalFileSystem.getInstance().findFileByIoFile(projectTemplatesDirFile)
            false -> null
        }
        val vfExternalTemplatesDir = when (isValidTemplateFile(externalTemplatesDirFile)) {
            true -> LocalFileSystem.getInstance().findFileByIoFile(externalTemplatesDirFile)
            false -> null
        }

        val result = mutableListOf<VirtualFile>()
        if (vfProjectTemplatesDir != null) {
            result += vfProjectTemplatesDir
        }
        if (vfExternalTemplatesDir != null) {
            result += vfExternalTemplatesDir
        }
        return result
    }

    private fun isValidTemplateFile(file: File): Boolean {
        return file.absolutePath.isNotBlank() && file.exists() && file.canRead() && file.isDirectory
    }

}
