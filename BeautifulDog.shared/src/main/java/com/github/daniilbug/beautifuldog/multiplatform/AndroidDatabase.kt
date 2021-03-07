package com.github.daniilbug.beautifuldog.multiplatform

import com.github.daniilbug.beautifuldog.Database
import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import org.koin.core.module.Module
import org.koin.dsl.module

actual class DriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(Database.Schema, context, "dogphoto.db")
    }
}

actual fun makeModuleForDriver(): Module = module {
    single { DriverFactory(get()) }
}