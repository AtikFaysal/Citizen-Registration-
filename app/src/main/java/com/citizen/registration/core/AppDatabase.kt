package com.citizen.registration.core

import androidx.room.Database
import androidx.room.RoomDatabase
import com.citizen.registration.database.room.dao.SuggestionDao
import com.citizen.registration.database.room.entity.SuggestionEntity

/**
 * @author Atik Faysal(Android Developer)
 * @Email mdatikfaysal@gmail.com
 * @Created 1/3/2022 at 3:11 PM
 */
@Database(entities = [
    SuggestionEntity::class
], version = 3)
abstract class AppDatabase : RoomDatabase()
{
    abstract fun suggestionDao(): SuggestionDao
}