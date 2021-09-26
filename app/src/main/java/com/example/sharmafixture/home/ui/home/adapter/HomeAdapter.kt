package com.example.sharmafixture.home.ui.home.adapter

import android.content.Context
import android.content.Intent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sharmafixture.R
import com.example.sharmafixture.data.model.Product
import com.example.sharmafixture.home.MapsActivity

import com.example.sharmainteriordesign.api.ServiceBuilder
import com.squareup.picasso.Picasso


class HomeAdapter(
    val listpost: ArrayList<Product>,
    val context: Context
) : RecyclerView.Adapter<HomeAdapter.HomwviewHolder>() {
    class HomwviewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val fImage: ImageView;
        val f_price: TextView;

        init {
            fImage = view.findViewById(R.id.f_image)

            f_price = view.findViewById(R.id.f_price)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomwviewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.for_home, parent, false)
        return HomeAdapter.HomwviewHolder(view)
    }

    override fun onBindViewHolder(holder: HomwviewHolder, position: Int) {
        val post = listpost[position]
        holder.f_price.text = post.price
        val id = post._id
        val imagePath = ServiceBuilder.loadImagePath() + post.image
        Picasso.with(context).load(imagePath).fit().into(holder.fImage)
        holder.fImage.setOnClickListener() {
            context.startActivity(Intent(context, MapsActivity::class.java))
        }
    }

    override fun getItemCount(): Int {
        return listpost.size
    }
}