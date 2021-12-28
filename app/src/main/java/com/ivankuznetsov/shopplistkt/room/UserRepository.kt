package com.ivankuznetsov.shopplistkt.room

import androidx.lifecycle.LiveData
import com.ivankuznetsov.shopplistkt.room.dao.BinListDao
import com.ivankuznetsov.shopplistkt.room.dao.ProductListDao
import com.ivankuznetsov.shopplistkt.room.entity.BinListEntity
import com.ivankuznetsov.shopplistkt.room.entity.ProductListEntity

class UserRepository(private val productListDao : ProductListDao?,
                     private val binListDao : BinListDao?) {

    val readAllProducts : LiveData<MutableList<ProductListEntity>>? = productListDao?.getAllProducts()
    val readAllBin : LiveData<MutableList<BinListEntity>>? = binListDao?.getAllBin()

        suspend fun addProduct(product : ProductListEntity){
            productListDao?.insertProduct(product)
        }
        suspend fun addBin(product : BinListEntity){
            binListDao?.insertBin(product)
        }
        suspend fun deleteProduct(product : ProductListEntity){
            productListDao?.deleteProduct(product)
        }
        suspend fun  deleteBin(bin : BinListEntity){
            binListDao?.deleteBin(bin)
        }
        suspend fun deleteAllBin(){
            binListDao?.deleteAllBin()
        }
        suspend fun copyBinToProduct(){
            productListDao?.copyBinToProducts()
            deleteAllBin()
        }

}