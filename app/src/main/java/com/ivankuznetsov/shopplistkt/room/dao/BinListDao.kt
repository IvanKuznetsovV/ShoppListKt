package com.ivankuznetsov.shopplistkt.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ivankuznetsov.shopplistkt.room.entity.BinListEntity
@Dao
interface BinListDao {
    @Insert
    suspend fun insertBin(vararg obj: BinListEntity)

    @Update
    suspend fun updateBin(vararg obj: BinListEntity)

    @Delete
    suspend fun deleteBin(vararg obj: BinListEntity)

    @Query("SELECT * FROM BinListEntity")
    fun getAllBin() : LiveData<MutableList<BinListEntity>>

    @Query("DELETE FROM BinListEntity")
    fun deleteAllBin()

}