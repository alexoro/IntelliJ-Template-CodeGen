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

package com.alexoro.tcg.settings

import com.alexoro.tcg.Environment
import com.alexoro.tcg.EnvironmentFactory
import com.alexoro.tcg.ui.ModuleSettingsComponent
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.Project
import javax.swing.JComponent

/**
 * Provides controller functionality for project settings inside 'Tools' menu.
 */
class ModuleSettingsConfigurable(
    private val project: Project,
) : Configurable {

    private var env: Environment? = null
    private var settingsComponent: ModuleSettingsComponent? = null

    override fun createComponent(): JComponent {
        val env = EnvironmentFactory.create(project)
        settingsComponent = ModuleSettingsComponent(env.lang)
        return requireNotNull(settingsComponent)
    }

    override fun getDisplayName(): String {
        return env?.lang?.settingsTitle ?: ""
    }

    override fun isModified(): Boolean {
        val settingsState = getSettingsState()
        val settingsComponent = requireNotNull(settingsComponent)
        return settingsState.projectTemplatesDir != settingsComponent.projectTemplatesDir ||
                settingsState.externalTemplatesDir != settingsComponent.externalTemplatesDir
    }

    override fun apply() {
        val settingsComponent = requireNotNull(settingsComponent)
        getSettingsState().apply {
            projectTemplatesDir = settingsComponent.projectTemplatesDir
            externalTemplatesDir = settingsComponent.externalTemplatesDir
        }
    }

    override fun reset() {
        val settingsState = getSettingsState()
        val settingsComponent = requireNotNull(settingsComponent)
        settingsComponent.projectTemplatesDir = settingsState.projectTemplatesDir
        settingsComponent.externalTemplatesDir = settingsState.externalTemplatesDir
    }

    override fun disposeUIResources() {
        settingsComponent = null
    }

    private fun getSettingsState(): ModuleSettingsState {
        return project.getService(ModuleSettingsState::class.java)
    }

}
