package com.example.sharmafixture.home.ui.home.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sharmafixture.R
import com.example.sharmafixture.data.model.Product
import com.example.sharmafixture.home.MapsActivity
import com.example.sharmainteriordesign.api.ServiceBuilder
import com.squareup.picasso.Picasso

class NewReleaseAdapter(
    val listpost:ArrayList<Product>,
    val context: Context
): RecyclerView.Adapter<NewReleaseAdapter.NewReleaseviewHolder>() {
    class NewReleaseviewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val fImage: ImageView;
        val f_price: TextView;

        init {
            fImage=view.findViewById(R.id.f_image)

            f_price=view.findViewById(R.id.f_price)

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewReleaseviewHolder {
        val view= LayoutInflater.from(parent.context)
            .inflate(R.layout.for_home,parent,false)
        return NewReleaseAdapter.NewReleaseviewHolder(view)
    }
    override fun onBindViewHolder(holder: NewReleaseviewHolder, position: Int) {
        val post=listpost[position]
        holder.f_price.text=post.price
        val id=post._id
        val imagePath = ServiceBuilder.loadImagePath() +post.image
        Picasso.with(context).load(imagePath).fit().into(holder.fImage)

        holder.fImage.setOnClickListener() {
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