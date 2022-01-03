package com.citizen.registration.repository

import com.citizen.registration.database.room.dao.SuggestionDao
import com.citizen.registration.database.room.entity.SuggestionEntity
import javax.inject.Inject

/**
 * @author Atik Faysal(Android Developer)
 * @Email mdatikfaysal@gmail.com
 * @Created 1/3/2022 at 3:31 PM
 */
class SuggestionRepository @Inject constructor(private val dao : SuggestionDao)
{
    suspend fun suggestionEntry(suggestion : SuggestionEntity) = dao.suggestionEntry(suggestion)

    suspend fun getSuggestion() = dao.getSuggestion()
}