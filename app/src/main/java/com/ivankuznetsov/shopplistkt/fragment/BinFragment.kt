package com.ivankuznetsov.shopplistkt.fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ivankuznetsov.shopplistkt.adapter.BinListAdapter
import com.ivankuznetsov.shopplistkt.databinding.FragmentBinBinding
import com.ivankuznetsov.shopplistkt.room.UserViewModel


class BinFragment : Fragment() {
    private lateinit var binding : FragmentBinBinding
    private lateinit var userViewModel : UserViewModel
    private var adapter : BinListAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,): View {
        binding = FragmentBinBinding.inflate(inflater)
        userViewModel = ViewModelProvider(this)[UserViewModel :: class.java]
        adapter = activity?.let{ BinListAdapter(it, this) }
        val recyclerView = binding.binRecyclerview
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        userViewModel.readAllBin?.observe(viewLifecycleOwner, Observer{
                bin -> adapter?.setData(bin)
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.binButton.setOnClickListener(View.OnClickListener {
            showDeleteForeverDialog()
        })

        binding.recButton.setOnClickListener(View.OnClickListener {
            showRecoverAllDialog()
        })
        super.onViewCreated(view, savedInstanceState)
    }
    private fun showDeleteForeverDialog(){
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Удалить все безвозвратно?")
        builder.setPositiveButton("Да"){dialog, which ->
            userViewModel.deleteAllBin()
            adapter?.notifyDataSetChanged()
        }
        builder.setNegativeButton("Нет"){dialog, which -> }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
    private fun showRecoverAllDialog(){
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Восттановить всё?")
        builder.setPositiveButton("Да"){dialog, which ->
            userViewModel.copyBinToProduct()
            adapter?.notifyDataSetChanged()
        }
        builder.setNegativeButton("Нет"){dialog, which -> }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}