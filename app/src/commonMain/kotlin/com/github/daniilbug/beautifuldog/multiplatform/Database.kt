package com.github.daniilbug.beautifuldog.multiplatform

import com.github.daniilbug.beautifuldog.Database
import com.squareup.sqldelight.db.SqlDriver
import org.koin.core.module.Module

expect class DriverFactory {
    fun createDriver(): SqlDriver
}

expect fun makeModuleForDriver(): Module

fun createDatabase(driverFactory: DriverFactory): Database {
    val driver = driverFactory.createDriver()
    return Database(driver)
}