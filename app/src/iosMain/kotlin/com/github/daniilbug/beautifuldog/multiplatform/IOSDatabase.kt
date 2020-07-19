package com.github.daniilbug.beautifuldog.multiplatform

import com.github.daniilbug.beautifuldog.Database
import org.koin.core.module.Module
import org.koin.dsl.module

actual class DriverFactory {
    fun createDriver(): SqlDriver {
        return NativeSqliteDriver(Database.Schema, "dogphoto.db")
    }
}

actual fun makeModuleForDriver(): Module {
    return module {
        single { DriverFactory() }
    }
}
