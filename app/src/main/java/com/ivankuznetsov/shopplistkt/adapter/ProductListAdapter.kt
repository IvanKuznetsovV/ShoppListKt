package com.ivankuznetsov.shopplistkt.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.ivankuznetsov.shopplistkt.R
import com.ivankuznetsov.shopplistkt.room.UserViewModel
import com.ivankuznetsov.shopplistkt.room.entity.BinListEntity
import com.ivankuznetsov.shopplistkt.room.entity.ProductListEntity

class ProductListAdapter(val appContext : Context,
                         val viewOwner : ViewModelStoreOwner) :
      RecyclerView.Adapter<ProductListAdapter.ListViewHolder>(){

    private var shopListArray = mutableListOf<ProductListEntity>()
    private lateinit var userViewModel : UserViewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val context : Context = parent.context
        val layoutId : Int = R.layout.list_item
        val layoutInflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = layoutInflater.inflate(layoutId, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) { holder.bind(position) }
    override fun getItemCount() : Int = shopListArray.size

       inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var textview: TextView? = itemView.findViewById(R.id.name)
            var imageview: ImageView? = itemView.findViewById(R.id.bin)

            fun bind(index : Int){
                textview?.text = shopListArray[index].product_name

                textview?.setOnClickListener(View.OnClickListener {})
                imageview?.setOnClickListener(View.OnClickListener {
                    userViewModel = ViewModelProvider(viewOwner)[UserViewModel :: class.java]
                    var bin = BinListEntity(0,shopListArray[index].product_name )
                    userViewModel.addBin(bin)
                    userViewModel.deleteProduct(shopListArray[index])
                    shopListArray.removeAt(index)
                    notifyDataSetChanged()
                    Toast.makeText(appContext, "В КОРЗИНЕ", Toast.LENGTH_SHORT).show()
                })
            }
       }
    fun setData(product : MutableList<ProductListEntity>){
        this.shopListArray = product
        notifyDataSetChanged()
    }

}