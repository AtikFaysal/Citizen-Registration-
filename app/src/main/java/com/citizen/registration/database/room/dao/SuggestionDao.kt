package com.citizen.registration.database.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.citizen.registration.database.room.entity.SuggestionEntity

/**
 * @author Atik Faysal(Android Developer)
 * @Email mdatikfaysal@gmail.com
 * @Created 1/3/2022 at 3:10 PM
 */
@Dao
interface  SuggestionDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun suggestionEntry(suggestion : SuggestionEntity) : Long

    @Query("SELECT * FROM tbl_suggestion ORDER BY id DESC LIMIT 10")
    suspend fun getSuggestion() : List<SuggestionEntity>
}