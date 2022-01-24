package com.example.myapplication.data

import android.app.Application

class NodeApplication : Application() {
    val database: Db by lazy { Db.getDatabase(this) }
}