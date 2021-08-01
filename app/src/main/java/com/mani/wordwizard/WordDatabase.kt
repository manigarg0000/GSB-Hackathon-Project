package com.mani.wordwizard

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Database(entities = [Word::class], version = 1, exportSchema = false )
abstract class WordDatabase : RoomDatabase(){

    abstract fun wordDao():WordDao

    private class WordDatabaseCallback(private val scope : CoroutineScope) : RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            INSTANCE?.let{
                    database->
                scope.launch {
                    populateDatabase(database.wordDao())
                }
            }
        }

        suspend fun populateDatabase(wordDao: WordDao){
            wordDao.insert(Word("Gumption"))
            wordDao.insert(Word("Canoodle"))
            wordDao.insert(Word("Flummox"))
            wordDao.insert(Word("Sagacious"))
            wordDao.insert(Word("Numinous"))
            wordDao.insert(Word("Crapulous"))
        }
    }









    companion object{
        @Volatile
        private var INSTANCE : WordDatabase? = null
        fun getDatabase(context: Context, scope: CoroutineScope) : WordDatabase {
            return INSTANCE ?: synchronized(this){
                var instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordDatabase::class.java,
                    "word_database"
                )
                    .addCallback(WordDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}