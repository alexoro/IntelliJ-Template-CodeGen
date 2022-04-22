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

/**
 * Info about template directory
 * @property name Name of directory, may contain placeholders
 * @property dirs Subdirectories in directory
 * @property files Files in directory
 */
internal data class TemplateDirectory(
    val name: String,
    val dirs: Collection<TemplateDirectory>,
    val files: Collection<TemplateFile>,
)
