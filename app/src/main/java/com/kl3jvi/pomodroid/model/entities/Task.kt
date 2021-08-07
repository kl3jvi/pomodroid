package com.kl3jvi.pomodroid.model.entities


import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "task_table")
data class Task(
    @ColumnInfo val title: String,
    @ColumnInfo(name = "time_stamp") val timeStamp: String,
    @ColumnInfo val category: String,
    @ColumnInfo val content: String,
    @ColumnInfo(name = "primary_task") var primaryTask: Boolean = false,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
) : Parcelable