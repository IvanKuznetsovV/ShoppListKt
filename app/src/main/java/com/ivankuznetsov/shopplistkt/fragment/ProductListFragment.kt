package com.ivankuznetsov.shopplistkt.fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ivankuznetsov.shopplistkt.R
import com.ivankuznetsov.shopplistkt.adapter.ProductListAdapter
import com.ivankuznetsov.shopplistkt.contract.VoiceRecordContract
import com.ivankuznetsov.shopplistkt.databinding.FragmentProductListBinding
import com.ivankuznetsov.shopplistkt.room.UserViewModel
import com.ivankuznetsov.shopplistkt.room.database.AppDataBase
import com.ivankuznetsov.shopplistkt.room.entity.ProductListEntity

class ProductListFragment : Fragment() {

    private lateinit var binding : FragmentProductListBinding
    private lateinit var productText : String
    private lateinit var userViewModel : UserViewModel
    private var db : AppDataBase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {


        userViewModel = ViewModelProvider(this)[UserViewModel :: class.java]

        binding = FragmentProductListBinding.inflate(inflater)
        activity?.let{db = AppDataBase.getAppDataBase(it)}
        val adapter = activity?.let{ProductListAdapter(it, this)}
        val recyclerView = binding.productRecyclerview
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        userViewModel.readAllProducts?.observe(viewLifecycleOwner, Observer{
                product -> adapter?.setData(product)
        })
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.micButton.setOnClickListener(View.OnClickListener { getVoice.launch(null) })
        binding.plusButton.setOnClickListener(View.OnClickListener { showAddDialog() })

    }
    private fun showAddDialog(){
        val builder = AlertDialog.Builder(context)
        val cl = LayoutInflater.from(context).inflate(R.layout.add_shop_dialog, null)
        builder.setView(cl)

        builder.setPositiveButton("Добавить"){dialog, which ->
            val ad : AlertDialog = dialog as AlertDialog
            val edittext = ad.findViewById<EditText>(R.id.editText)
            productText = edittext.text.toString()
            insertDataToDatabase(productText)
        }

        builder.setNegativeButton("Отмена"){dialog, which ->

        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun insertDataToDatabase(text : String){
        val product = ProductListEntity(0, text)
        userViewModel.addProduct(product)

    }

    var getVoice = registerForActivityResult(VoiceRecordContract()){result ->
        if(result != null){
            insertDataToDatabase(result.capitalize())
        }
    }
}