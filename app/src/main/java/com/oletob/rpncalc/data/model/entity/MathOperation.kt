package com.oletob.rpncalc.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "math_operation")
data class MathOperation(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val statement: String,
    val result: String
)