package com.example.sharmafixture.home.ui.notifications.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sharmafixture.R
import com.example.sharmafixture.data.model.Product
import com.example.sharmafixture.home.MapsActivity
import com.example.sharmafixture.home.ui.dashboard.adapter.OwnProductAdapter
import com.example.sharmainteriordesign.api.ServiceBuilder
import com.squareup.picasso.Picasso

class AddToCartAdpter(
    val listpost: ArrayList<Product>,
    val context: Context
) : RecyclerView.Adapter<AddToCartAdpter.AddToCartViewHolder>() {
    class AddToCartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageForHomeView: ImageView;
        val name: AppCompatTextView;
        val price: AppCompatTextView;
        val address: AppCompatTextView;
        val list_item: LinearLayout;

        init {
            imageForHomeView = view.findViewById(R.id.imageForHomeView)
            name = view.findViewById(R.id.name)
            price = view.findViewById(R.id.price)
            address = view.findViewById(R.id.address)
            list_item = view.findViewById(R.id.list_item)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddToCartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.bookmark, parent, false)
        return AddToCartViewHolder(view)
    }

    override fun onBindViewHolder(holder: AddToCartViewHolder, position: Int) {
        val post = listpost[position]
        val id = post._id
        val imagePath = post.image
        holder.price.text = "$ ${post.price}"
        holder.address.text = post.location
        holder.name.text = post.phNo
        Picasso.with(context).load(imagePath).fit().into(holder.imageForHomeView)
        holder.list_item.setOnClickListener() {
            context.startActivity(Intent(context, MapsActivity::class.java))
        }
//        if (!post.image.equals("noimg")) {
//            Glide.with(context)
//                    .load(imagePath)
//                    .into(holder.fImage)
//        }
//        holder.addFav.setOnClickListener() {
//            val builder = AlertDialog.Builder(context);
//            builder.setMessage("Do you want add this product to Fav.")
//            builder.setIcon(android.R.drawable.ic_dialog_alert);
//            builder.setPositiveButton("Yes") { dialogInterface, which ->
//                CoroutineScope(Dispatchers.IO).launch {
//                    val repository = AddFavrepository()
//                    val response = repository.AddFav(AddFav(userId = ServiceBuilder.id!!, productId = post._id))
//                    if (response.success == true) {
//                        withContext(Dispatchers.Main) {
//                            val snack = Snackbar.make(it, "${response.msg}", Snackbar.LENGTH_SHORT)
//                            snack.setAction("Ok") {
//                                snack.dismiss()
//                            }
//                            snack.show()
//                        }
//                    } else {
//                        withContext(Dispatchers.Main) {
//                            Toast.makeText(context, "error occur", Toast.LENGTH_SHORT).show()
//                        }
//                    }
//
//                }
//            }
//            builder.setNegativeButton("No") { dialogInterface, which ->
//            }
//            val alertDialog: AlertDialog = builder.create()
//            alertDialog.setCancelable(false)
//            alertDialog.show()
//            holder.addFav.setBackgroundColor(Color.RED)
//        }
    }

    override fun getItemCount(): Int {
        return listpost.size
    }
}