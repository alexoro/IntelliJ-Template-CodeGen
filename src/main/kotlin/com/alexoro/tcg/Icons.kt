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

import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

/**
 * Holder of all icons for plugin
 * https://jetbrains.design/intellij/resources/icons_list/
 */
@Suppress("MoveVariableDeclarationIntoWhen", "MemberVisibilityCanBePrivate")
class Icons {

    val directory = IconLoader.findIcon("AllIcons.Nodes.Folder")

    val fileAny = IconLoader.findIcon("AllIcons.FileTypes.Any_type")
    val fileCss = IconLoader.findIcon("AllIcons.FileTypes.Css")
    val fileHtAccess = IconLoader.findIcon("AllIcons.FileTypes.Htaccess")
    val fileHtml = IconLoader.findIcon("AllIcons.FileTypes.Html")
    val fileJava = IconLoader.findIcon("AllIcons.FileTypes.Java")
    val fileJavaScript = IconLoader.findIcon("AllIcons.FileTypes.JavaScript")
    val fileJson = IconLoader.findIcon("AllIcons.FileTypes.Json")
    val fileJsp = IconLoader.findIcon("AllIcons.FileTypes.Jsp")
    val fileJspx = IconLoader.findIcon("AllIcons.FileTypes.Jspx")
    val fileManifest = IconLoader.findIcon("AllIcons.FileTypes.Manifest")
    val filePlist = IconLoader.findIcon("AllIcons.FileTypes.PlistFile")
    val fileProperties = IconLoader.findIcon("AllIcons.FileTypes.Properties")
    val fileText = IconLoader.findIcon("AllIcons.FileTypes.Text")
    val fileXhtml = IconLoader.findIcon("AllIcons.FileTypes.Xhtml")
    val fileXml = IconLoader.findIcon("AllIcons.FileTypes.Xml")
    val fileYaml = IconLoader.findIcon("AllIcons.FileTypes.Yaml")

    fun forFile(fileName: String): Icon? {
        val extension = fileName.substringAfterLast('.').lowercase()
        return when (extension) {
            "css" -> fileCss
            "htaccess" -> fileHtAccess
            "html" -> fileHtml
            "java" -> fileJava
            "kt" -> fileJava
            "js" -> fileJavaScript
            "json" -> fileJson
            "jsp" -> fileJsp
            "jspx" -> fileJspx
            "manifest" -> fileManifest
            "plist" -> filePlist
            "properties" -> fileProperties
            "txt" -> fileText
            "xhtml" -> fileXhtml
            "xml" -> fileXml
            "yml" -> fileYaml
            else -> fileAny
        }
    }

}
