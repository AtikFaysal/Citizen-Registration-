package com.citizen.registration.di

import android.content.Context
import androidx.room.Room
import com.citizen.registration.core.AppDatabase
import com.citizen.registration.database.room.dao.SuggestionDao
import com.citizen.registration.utils.constants.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author Atik Faysal(Android Developer)
 * @Email mdatikfaysal@gmail.com
 * @Created 1/3/2022 at 3:23 PM
 */
@Module
@InstallIn(SingletonComponent::class)
class RoomModule {
    @Singleton
    @Provides
    fun provideBlogDb(@ApplicationContext context: Context): AppDatabase {
        return Room
            .databaseBuilder(
                context,
                AppDatabase::class.java,Constants.APP_DB)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideSuggestionDAO(db: AppDatabase): SuggestionDao = db.suggestionDao()
}