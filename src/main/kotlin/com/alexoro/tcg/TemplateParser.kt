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

import com.intellij.openapi.vfs.VirtualFile

/**
 * Utility class for parsing template from specified dirs
 */
internal class TemplateParser {

    fun parse(vfTemplateDirs: Collection<VirtualFile>): List<Template> {
        val templates = mutableListOf<Template>()
        collectAllTemplatesFromDirs(vfTemplateDirs).forEach {
            templates += createTemplateFromDirectory(it)
        }
        return templates
    }

    private fun collectAllTemplatesFromDirs(
        vfTemplateDirs: Collection<VirtualFile>,
    ): Collection<VirtualFile> {
        val result = mutableListOf<VirtualFile>()
        vfTemplateDirs.forEach {
            collectAllTemplatesFromDir(it, result)
        }
        return result
    }

    private fun collectAllTemplatesFromDir(
        vfTemplateDir: VirtualFile,
        out: MutableCollection<VirtualFile>,
    ) {
        vfTemplateDir.children.forEach {
            if (it.isDirectory) {
                out.add(it)
            }
        }
    }

    private fun createTemplateFromDirectory(
        vfTemplateDir: VirtualFile,
    ): Template {
        return Template(
            title = vfTemplateDir.name,
            rootDirectory = createTemplateDirectory(vfTemplateDir)
        )
    }

    private fun createTemplateDirectory(vfDir: VirtualFile): TemplateDirectory {
        val vfChildDirs = mutableListOf<VirtualFile>()
        val vfChildFiles = mutableListOf<VirtualFile>()
        vfDir.children.forEach {
            if (it.isDirectory) {
                vfChildDirs.add(it)
            } else {
                vfChildFiles.add(it)
            }
        }

        return TemplateDirectory(
            name = vfDir.name,
            dirs = vfChildDirs.map(::createTemplateDirectory),
            files = vfChildFiles.map(::createTemplateFile),
        )
    }

    private fun createTemplateFile(vfFile: VirtualFile): TemplateFile {
        return TemplateFile(
            name = vfFile.name,
            content = String(vfFile.contentsToByteArray()),
        )
    }

}
