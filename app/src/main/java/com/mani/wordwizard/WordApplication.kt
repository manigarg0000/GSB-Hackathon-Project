package com.mani.wordwizard

import android.app.Application

class WordApplication : Application() {

    val database by lazy {
        WordDatabase.getDatabase(this)
    }

    val repository by lazy{
        WordRepository(database.wordDao())
    }


}