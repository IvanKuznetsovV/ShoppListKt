package com.ivankuznetsov.shopplistkt.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ivankuznetsov.shopplistkt.room.database.AppDataBase
import com.ivankuznetsov.shopplistkt.room.entity.BinListEntity
import com.ivankuznetsov.shopplistkt.room.entity.ProductListEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    val readAllProducts : LiveData<MutableList<ProductListEntity>>?
    val readAllBin : LiveData<MutableList<BinListEntity>>?
    private val repository : UserRepository

        init{
            val productListDao = AppDataBase.getAppDataBase(application)?.productListDao()
            val binListDao = AppDataBase.getAppDataBase(application)?.binListDao()
            repository = UserRepository(productListDao, binListDao)
            readAllProducts = repository.readAllProducts
            readAllBin = repository.readAllBin
        }

    fun addProduct(product : ProductListEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addProduct(product)
        }
    }

    fun deleteProduct(product : ProductListEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteProduct(product)
        }
    }
    fun addBin(bin : BinListEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addBin(bin)
        }
    }
    fun deleteBin(bin : BinListEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteBin(bin)
        }
    }
    fun deleteAllBin(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAllBin()
        }
    }
    fun copyBinToProduct(){
        viewModelScope.launch(Dispatchers.IO){
            repository.copyBinToProduct()
        }
    }

}