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
 * Helper class for finding and replacing placeholders.
 * By design, placeholders are wrapped into double '#'.
 * I.e. ##I_AM_A_PLACEHOLDER##
 */
internal class TemplatePlaceholders {

    private companion object {
        val REGEX = "##.*?##".toRegex(RegexOption.MULTILINE)
    }

    fun findAll(template: Template): Set<String> {
        val result = mutableSetOf<String>()
        findFromDirectory(template.rootDirectory, result)
        return result
    }

    fun replaceAll(
        value: String,
        replacements: Map<String, String>,
    ): String {
        if (replacements.isEmpty()) {
            return value
        }

        var result = value
        replacements.forEach { (key, value) ->
            result = result.replace(key, value)
        }
        return result
    }

    private fun findFromDirectory(
        directory: TemplateDirectory,
        out: MutableSet<String>,
    ) {
        findFromString(directory.name, out)
        directory.dirs.forEach {
            findFromDirectory(it, out)
        }
        directory.files.forEach {
            findFromFile(it, out)
        }
    }

    private fun findFromFile(
        file: TemplateFile,
        out: MutableSet<String>,
    ) {
        findFromString(file.name, out)
        findFromString(file.content, out)
    }

    private fun findFromString(
        value: String,
        out: MutableSet<String>,
    ) {
        REGEX.findAll(value)
            .filter { it.value.isNotBlank() }
            .mapTo(out) { it.value }
    }

}
