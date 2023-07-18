package com.example.imtixon.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Users(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val body: String,
    val title: String,
    val userId: Int
)