package com.citizen.registration.database.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Atik Faysal(Android Developer)
 * @Email mdatikfaysal@gmail.com
 * @Created 1/3/2022 at 3:10 PM
 */
@Entity(tableName = "tbl_suggestion")
data class SuggestionEntity(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id : Long ,

    @ColumnInfo(name = "suggestion") val suggestion : String,

    @ColumnInfo(name = "type") val type : Int
)