package com.example.sharmafixture.addproduct

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.PopupMenu
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.sharmafixture.R
import com.example.sharmafixture.data.model.Product
import com.example.sharmafixture.data.repository.ProductRepository
import com.example.sharmafixture.databinding.ActivityAddProductBinding
import com.example.sharmafixture.databinding.ActivitySignupBinding
import com.example.sharmainteriordesign.api.ServiceBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class AddProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddProductBinding
    private val REQUEST_GALLERY_CODE=0;
    private val REQUEST_CAMERA_CODE=1;
    private var imageUrl:String?=null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_add_product)
        binding.lifecycleOwner=this
        binding.submit.setOnClickListener(){
            val product= Product(area = binding.area.text.toString(),price =binding.price.text.toString(),location = binding.location.text.toString(),phNo = binding.phNo.text.toString(),userId = ServiceBuilder.id!!)
            CoroutineScope(Dispatchers.IO).launch {
                val repository= ProductRepository()
                val response=repository.insertProduct(product)
                if(response.success==true){
                    val id=response.id
                    if(imageUrl != null){
                        uploadImage(id!!)
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@AddProductActivity, "Student Added Successfully", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                else{
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@AddProductActivity, "${response.msg}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        binding.profilepic.setOnClickListener(){
            popup()
        }

    }
    private fun uploadImage(studentId: String) {
        if (imageUrl != null) {
            val file = File(imageUrl!!)
            val reqFile =
                RequestBody.create(MediaType.parse("image/${file.extension}"), file)
            val body =
                MultipartBody.Part.createFormData("file", file.name, reqFile)
            CoroutineScope(Dispatchers.IO).launch {
                val studentRepository = ProductRepository()
                val response = studentRepository.uploadImage(studentId, body)
                if (response.success == true) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@AddProductActivity, "Uploaded", Toast.LENGTH_SHORT)
                            .show()
                        clear()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@AddProductActivity, "image no uploaded", Toast.LENGTH_SHORT).show()
                    }
                }
//                }
//                catch (ex: Exception) {
//                    withContext(Main) {
//                        Toast.makeText(this@AddProductActivity, "$body", Toast.LENGTH_SHORT).show()
//                        Log.d("Error uploading image ", ex.toString())
//                        Toast.makeText(
//                                this@AddProductActivity,
//                                ex.localizedMessage,
//                                Toast.LENGTH_SHORT
//                        ).show()
//                    }
                // }
            }
        }
    }
    private fun clear(){
        binding.area.setText("")
        binding.price.setText("")
        binding.location.setText("")
        binding.phNo.setText("")
    }
    private fun openGallery(){
        val intent= Intent(Intent.ACTION_PICK)
        intent.type="image/*"
        startActivityForResult(intent,REQUEST_GALLERY_CODE)
    }
    private fun openCamera(){
        val cameraIntent= Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent,REQUEST_CAMERA_CODE)
    }
    private fun bitmapToFile(
        bitmap: Bitmap,
        fileNameToSave: String
    ): File? {
        var file: File? = null
        return try {
            file = File(
                getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    .toString() + File.separator + fileNameToSave
            )
            file.createNewFile()
            //Convert bitmap to byte array
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos) // YOU can also save it in JPEG
            val bitMapData = bos.toByteArray()
            //write the bytes in file
            val fos = FileOutputStream(file)
            fos.write(bitMapData)
            fos.flush()
            fos.close()
            file
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            file // it will return null
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== Activity.RESULT_OK){
            if(requestCode == REQUEST_GALLERY_CODE && data != null) {
                val selectedImage = data.data
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                val contentResolver = contentResolver
                val cursor =
                    contentResolver.query(selectedImage!!, filePathColumn, null, null, null)
                cursor!!.moveToFirst()
                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                imageUrl = cursor.getString(columnIndex)
                binding.profilepic.setImageBitmap(BitmapFactory.decodeFile(imageUrl))
                cursor.close()
            }
            else if (requestCode == REQUEST_CAMERA_CODE && data != null) {
                val imageBitmap = data.extras?.get("data") as Bitmap
                val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                val file = bitmapToFile(imageBitmap, "$timeStamp.jpg")
                imageUrl = file!!.absolutePath
                binding.profilepic.setImageBitmap(BitmapFactory.decodeFile(imageUrl))
            }
        }
    }
    private fun popup(){
        val popupMenu= PopupMenu(this,binding.profilepic)
        popupMenu.menuInflater.inflate(R.menu.pmenu,popupMenu.menu)
        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.gallery -> {
                    openGallery()
                    true
                }
                R.id.camera -> {
                    openCamera()
                    true
                }
                else -> false
            }
        })
        popupMenu.show()
    }
}