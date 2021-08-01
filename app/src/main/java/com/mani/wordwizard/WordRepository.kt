package com.mani.wordwizard

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class WordRepository(private val wordDao: WordDao) {

    val allWords : Flow<List<Word>> = wordDao.getWords()

    @Suppress("RedundantSuppressModifier")
    @WorkerThread
    suspend fun insert(word: Word){
        wordDao.insert(word)
    }

    @Suppress("RedundantSuppressModifier")
    @WorkerThread
    suspend fun delete(word: Word){
        wordDao.delete(word)
    }
    
}