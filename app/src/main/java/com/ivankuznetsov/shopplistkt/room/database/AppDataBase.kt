package com.ivankuznetsov.shopplistkt.room.database


import android.content.Context
import android.util.Log
import androidx.room.*
import com.ivankuznetsov.shopplistkt.room.dao.BinListDao
import com.ivankuznetsov.shopplistkt.room.dao.ProductListDao
import com.ivankuznetsov.shopplistkt.room.entity.BinListEntity
import com.ivankuznetsov.shopplistkt.room.entity.ProductListEntity

@Database(entities = [ProductListEntity :: class, BinListEntity :: class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun binListDao() : BinListDao
    abstract fun productListDao() : ProductListDao

    companion object{
        @Volatile
        var INSTANCE : AppDataBase? = null

            fun getAppDataBase(context: Context) : AppDataBase?{
                if(INSTANCE == null) {
                    synchronized(AppDataBase::class.java) {
                        Log.d("MyLog", "DB_CREATE")
                        INSTANCE = Room
                            .databaseBuilder(context.applicationContext,
                                             AppDataBase::class.java,
                                       "App_database.db")
                            .build()
                    }
                }
                Log.d("MyLog", "DB_RETURN")
                return INSTANCE
            }
        fun destroyDataBase(){
            INSTANCE = null
        }
    }
}