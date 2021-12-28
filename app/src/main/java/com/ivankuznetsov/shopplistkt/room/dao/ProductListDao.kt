package com.ivankuznetsov.shopplistkt.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ivankuznetsov.shopplistkt.room.entity.ProductListEntity

@Dao
interface ProductListDao {
    @Insert
    suspend fun insertProduct(vararg obj: ProductListEntity)

    @Update
    suspend fun updateProduct(vararg obj: ProductListEntity)

    @Delete
    suspend fun deleteProduct(vararg obj: ProductListEntity)

    @Query("SELECT * FROM ProductListEntity")
    fun getAllProducts() : LiveData<MutableList<ProductListEntity>>

    @Query("INSERT INTO ProductListEntity SELECT * FROM BinListEntity")
    fun copyBinToProducts()
}