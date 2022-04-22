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

package com.alexoro.tcg.actions

import com.alexoro.tcg.EnvironmentFactory
import com.alexoro.tcg.ui.TemplateGeneratorDialog
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.vfs.VirtualFile

/**
 * Action to be invoked when user clicks 'New' -> 'Create from template'.
 * We initialize Environment here and passby all login to [TemplateGeneratorDialog]
 */
@Suppress("FoldInitializerAndIfToElvis")
class OnCreateAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project
        if (project == null) {
            return
        }

        val vfTargetDir = findTargetDir(e.getData(PlatformDataKeys.VIRTUAL_FILE))
        if (vfTargetDir != null) {
            TemplateGeneratorDialog(
                env = EnvironmentFactory.create(project),
                vfTargetDir = vfTargetDir,
            ).show()
        }
    }

    private fun findTargetDir(providedDir: VirtualFile?): VirtualFile? {
        if (providedDir == null) {
            return null
        }

        var resultDir = providedDir
        if (!resultDir.isDirectory) {
            resultDir = if (providedDir.parent.isDirectory) providedDir.parent else null
        }
        return resultDir
    }

}
