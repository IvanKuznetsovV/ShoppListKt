package com.ivankuznetsov.shopplistkt.adapter

import android.app.AlertDialog
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

class BinListAdapter(val appContext : Context,
                     val viewOwner : ViewModelStoreOwner) :
      RecyclerView.Adapter<BinListAdapter.ProductListViewHolder>() {

    private var binListArray = mutableListOf<BinListEntity>()
    private lateinit var userViewModel : UserViewModel
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        val context : Context = parent.context
        val layoutId : Int = R.layout.list_item_2
        val layoutInflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = layoutInflater.inflate(layoutId, parent, false)
        return ProductListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) { holder.bind(position) }
    override fun getItemCount() : Int = binListArray.size

    inner class ProductListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textview: TextView? = itemView.findViewById(R.id.name)
        var imageviewRec: ImageView? = itemView.findViewById(R.id.recover)
        var imageviewBin: ImageView? = itemView.findViewById(R.id.bin)

        fun bind(index : Int){
            textview?.text = binListArray[index].product_bin_name

            imageviewRec?.setOnClickListener(View.OnClickListener {
                val product = ProductListEntity(0, binListArray[index].product_bin_name)
                userViewModel = ViewModelProvider(viewOwner)[UserViewModel :: class.java]
                Log.d("MyLog", product.product_name_id.toString())
                userViewModel.addProduct(product)
                userViewModel.deleteBin(binListArray[index])
                Toast.makeText(appContext,"ВОССТАНОВЛЕНО", Toast.LENGTH_SHORT).show()
                notifyDataSetChanged()
            })
            imageviewBin?.setOnClickListener(View.OnClickListener {
                showDeleteForeverDialog(index)
            })
        }
    }

    fun setData(bin : MutableList<BinListEntity>){
        binListArray = bin
        notifyDataSetChanged()
    }

    fun showDeleteForeverDialog(index: Int){
        val builder = AlertDialog.Builder(appContext)
        builder.setTitle("Удалить безвозвратно?")
        builder.setPositiveButton("Да"){dialog, which ->
            userViewModel = ViewModelProvider(viewOwner)[UserViewModel :: class.java]
            userViewModel.deleteBin(binListArray[index])
            notifyDataSetChanged()
        }
        builder.setNegativeButton("Нет"){dialog, which -> }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}