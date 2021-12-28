package com.ivankuznetsov.shopplistkt.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BinListEntity(
                        @PrimaryKey(autoGenerate = true)
                        val product_bin_id : Int = 0,
                        val product_bin_name : String,
                        )
