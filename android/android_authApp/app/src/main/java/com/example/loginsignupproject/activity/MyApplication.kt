package com.example.loginsignupproject.activity

import android.app.Application
import com.example.loginsignupproject.data.AccountManager

class MyApplication : Application() {
    val accountManager: AccountManager by lazy {
        AccountManager()
    }
}