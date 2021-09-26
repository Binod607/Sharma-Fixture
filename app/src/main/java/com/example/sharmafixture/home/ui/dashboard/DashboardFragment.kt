package com.example.sharmafixture.home.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sharmafixture.addproduct.AddProductActivity
import com.example.sharmafixture.data.model.Product
import com.example.sharmafixture.data.repository.ProductRepository
import com.example.sharmafixture.databinding.FragmentDashboardBinding
import com.example.sharmafixture.databinding.OwnProductBinding
import com.example.sharmafixture.home.ui.dashboard.adapter.OwnProductAdapter
import com.example.sharmafixture.home.ui.home.adapter.NewReleaseAdapter
import com.example.sharmafixture.login.LoginActivity
import com.example.sharmainteriordesign.api.ServiceBuilder
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main


class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getOwnData()
        binding.add.setOnClickListener() {
            startActivity(Intent(requireContext(), AddProductActivity::class.java))
        }
        val name = ServiceBuilder.name
        binding.textView2.text=name
        binding.logout.setOnClickListener(){
            startActivity(Intent(context,LoginActivity::class.java))
        }
    }

    fun getOwnData() {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val repository = ProductRepository()
                val response = repository.getinter(ServiceBuilder.id!!)
                if (response.success == true) {
                    val data = response.data
                    println(data)
                    withContext(Main) {
                        val adapter3 =
                            OwnProductAdapter(data as ArrayList<Product>, requireContext())
                        binding.recyclarForInterior.layoutManager = GridLayoutManager(context, 3)
                        binding.recyclarForInterior.adapter = adapter3
                    }

                }
            }

        } catch (e: Exception) {
            Toast.makeText(context, "${e.localizedMessage}", Toast.LENGTH_SHORT).show()
        }
    }
}