// Copyright 2000-2022 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package org.jetbrains.kotlin.idea.stubindex

import com.intellij.openapi.project.Project
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.stubs.StringStubIndexExtension
import com.intellij.psi.stubs.StubIndex
import com.intellij.psi.stubs.StubIndexKey
import org.jetbrains.kotlin.psi.KtNamedFunction

object KotlinProbablyNothingFunctionShortNameIndex : StringStubIndexExtension<KtNamedFunction>() {
    private val KEY: StubIndexKey<String, KtNamedFunction> =
        StubIndexKey.createIndexKey("org.jetbrains.kotlin.idea.stubindex.KotlinProbablyNothingFunctionShortNameIndex")

    override fun getKey(): StubIndexKey<String, KtNamedFunction> = KEY

    override fun get(s: String, project: Project, scope: GlobalSearchScope): Collection<KtNamedFunction> {
        return StubIndex.getElements(KEY, s, project, scope, KtNamedFunction::class.java)
    }
}