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

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

/**
 * Utility-class for holding all data required for plugin correct work.
 */
internal data class Environment(
    val project: Project,
    val vfTemplateDirs: Collection<VirtualFile>,
    val lang: Lang,
    val icons: Icons,
    val placeholders: TemplatePlaceholders,
    val parser: TemplateParser,
    val generator: TemplateGenerator,
)
