package com.mani.wordwizard

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface WordDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word:Word)

    @Delete
    suspend fun delete(word: Word)

    @Query("SELECT * FROM WORD_TABLE ORDER BY word ASC")
    fun getWords() : Flow<List<Word>>

}