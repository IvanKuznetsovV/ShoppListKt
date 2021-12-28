package com.ivankuznetsov.shopplistkt.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductListEntity(
                            @PrimaryKey(autoGenerate = true)
                            val product_name_id : Int = 0,
                            val product_name : String
                            )
