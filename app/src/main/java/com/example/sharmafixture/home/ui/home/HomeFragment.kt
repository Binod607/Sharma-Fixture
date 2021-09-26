package com.example.sharmafixture.home.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sharmafixture.data.model.Product
import com.example.sharmafixture.data.repository.ProductRepository
import com.example.sharmafixture.databinding.FragmentHomeBinding
import com.example.sharmafixture.home.ui.home.adapter.HomeAdapter
import com.example.sharmafixture.home.ui.home.adapter.NewReleaseAdapter
import com.example.sharmafixture.home.ui.home.adapter.TopRattedAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getalldata()
    }
    fun getalldata(){
        try{
            CoroutineScope(Dispatchers.IO).launch {
                val repository=ProductRepository()
                val response=repository.getallProduct()
                if(response.success==true){
                    val data=response.data
                    val adapter=HomeAdapter(data as ArrayList<Product>,requireContext())
                    val NewLayoutManager = LinearLayoutManager(context)
                    NewLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
                    binding.topSoldProduct.layoutManager = NewLayoutManager
                    binding.topSoldProduct.adapter=adapter


                    val adapter2= TopRattedAdapter(data as ArrayList<Product>,requireContext())
                    val NewLayoutManager2 = LinearLayoutManager(context)
                    NewLayoutManager2.orientation = LinearLayoutManager.HORIZONTAL
                    binding.topRatedProduct.layoutManager=NewLayoutManager2
                    binding.topRatedProduct.adapter=adapter2

                    val adapter3= NewReleaseAdapter(data as ArrayList<Product>,requireContext())
                    val NewLayoutManager3 = LinearLayoutManager(context)
                    NewLayoutManager3.orientation = LinearLayoutManager.HORIZONTAL
                    binding.newPublisedRcy.layoutManager=NewLayoutManager3
                    binding.newPublisedRcy.adapter=adapter3

                }
            }

        }catch (e:Exception){
            Toast.makeText(context, "${e.localizedMessage}", Toast.LENGTH_SHORT).show()
        }
    }
}