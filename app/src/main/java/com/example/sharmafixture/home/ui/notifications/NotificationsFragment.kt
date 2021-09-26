package com.example.sharmafixture.home.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sharmafixture.data.model.Product
import com.example.sharmafixture.databinding.FragmentNotificationsBinding
import com.example.sharmafixture.home.ui.notifications.adapter.AddToCartAdpter

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var arrayList = ArrayList<Product>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arrayList.add(
            Product(
                "",
                "200 X 400",
                "500",
                "Tinkunee",
                "976543210",
                "",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS0qubfJuWSLPFWe1WRxZnKAb1W75ypWlxWmi89KbkMOEJUUNK2j-3J7wmEkZR4_OZoeeI&usqp=CAU"
            )
        )
        arrayList.add(
            Product(
                "",
                "500 X 400",
                "600",
                "Baneshower",
                "9876543211",
                "",
                "https://images.pexels.com/photos/1866149/pexels-photo-1866149.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"
            )
        )
        arrayList.add(
            Product(
                "",
                "600 X 600",
                "5S00",
                "Balaju",
                "9876543211",
                "",
                "https://hips.hearstapps.com/vader-prod.s3.amazonaws.com/1592920567-mid-century-double-pop-up-coffee-table-walnut-white-marble-2-c.jpg"         )
        )
        var adapter = AddToCartAdpter(arrayList, requireContext())
        val NewLayoutManager = LinearLayoutManager(context)
        NewLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.addcarts.layoutManager = NewLayoutManager
        binding.addcarts.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}