package com.wrxhard.ftravel.view.activity

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.Window
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import com.wrxhard.ftravel.R
import com.wrxhard.ftravel.databinding.ActivityMainBinding
import com.wrxhard.ftravel.ml.MobileV2
import com.wrxhard.ftravel.model.base_model.list_item.Category
import com.wrxhard.ftravel.util.LayoutHelper
import com.wrxhard.ftravel.view.adapter.CategoryAdapter
import com.wrxhard.ftravel.view.adapter.HomeVPAdapter
import com.wrxhard.ftravel.view.adapter.LocationAdapter
import com.wrxhard.ftravel.view_model.activity.AuthActivityViewModel
import com.wrxhard.ftravel.view_model.activity.MainDetailViewModel
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.model.Model
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainDetailViewModel: MainDetailViewModel by viewModels()
    private var uri: Uri? = null
    private var image: Bitmap? = null
    private val camLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            image = data?.extras?.get("data") as? Bitmap
            val dimension = Math.min(image!!.width, image!!.height)
            image = ThumbnailUtils.extractThumbnail(image, dimension, dimension)
            image = Bitmap.createScaledBitmap(image!!, 299, 299, false)
            classifyImage(imageSize = 299,image)
        }
    }
    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()){
        if (it != null){
            uri = it
            try {
                image = MediaStore.Images.Media.getBitmap(contentResolver, uri)
            }catch (e: Exception){
                e.printStackTrace()
            }
            image = Bitmap.createScaledBitmap(image!!, 299, 299, false)
            classifyImage(imageSize = 299,image)
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        //bindingview
        binding = ActivityMainBinding.inflate(layoutInflater)
        //hideSystemBar
        LayoutHelper.hideSystem(this)

        val categories = listOf(
            Category(R.drawable.ic_lightbulb, "Suggest"),
            Category(R.drawable.ic_location, "Place"),
            Category(R.drawable.ic_calendar, "Schedule"),
        )
        setupCategoryList(categories)

        val userLocations = listOf(
            "Ho Chi Minh",
        )
        setUpDropdown(userLocations)

        setupTabLayout()
        binding.locationBtn.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            camLauncher.launch(intent)
        }

        binding.searchIconCard.setOnClickListener {
            if (checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                pickImage.launch("image/*")
            }
            else {
                requestPermissions(arrayOf(android.Manifest.permission.CAMERA), 101)
            }
        }

        LayoutHelper.blurView(this,binding.root,binding.blurView,10f)

        setContentView(binding.root)

    }

    private fun setUpDropdown(userLocations: List<String>){
        binding.LocationDropdown.adapter = LocationAdapter(this, userLocations){
            Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupCategoryList(categories: List<Category>)
    {
        binding.categoryList.adapter = CategoryAdapter(categories){
                category->
            Toast.makeText(this,category.name,Toast.LENGTH_SHORT).show()
        }
        binding.categoryList.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
    }

    private fun classifyImage(imageSize: Int,image: Bitmap?) {
        val model = MobileV2.newInstance(this@MainActivity)

        // Creates inputs for reference.
        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, imageSize, imageSize, 3),DataType.FLOAT32)
        val byteBuffer = ByteBuffer.allocate(imageSize * imageSize * 3 * 4)
        byteBuffer.order(ByteOrder.nativeOrder())
        inputFeature0.loadBuffer(byteBuffer)

        val intValues = IntArray(imageSize * imageSize)
        image!!.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)
        var pixel = 0
        //iterate over each pixel and extract R, G, and B values. Add those values individually to the byte buffer.
        for (i in 0 until imageSize) {
            for (j in 0 until imageSize) {
                val `val` = intValues[pixel++] // RGB
                byteBuffer.putFloat((`val` shr 16 and 0xFF) * (1f / 299))
                byteBuffer.putFloat((`val` shr 8 and 0xFF) * (1f / 299))
                byteBuffer.putFloat((`val` and 0xFF) * (1f / 299))
            }
        }

        inputFeature0.loadBuffer(byteBuffer)

        // Runs model inference and gets result.
        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer

        val confidences = outputFeature0.floatArray
        // find the index of the class with the biggest confidence.
        var maxPos = 0
        var maxConfidence = 0f
        for (i in confidences.indices) {
            if (confidences[i] > maxConfidence) {
                maxConfidence = confidences[i]
                maxPos = i
            }
        }
        val classes = arrayOf(
            "Bao_Tang_Chung_Tich_Chien_Tranh",
            "Bao_Tang_Lich_Su",
            "Bao_Tang_My_Thuat",
            "Bao_Tang_Thanh_Pho",
            "Ben_Nha_Rong",
            "Bitexco",
            "Bui_Vien_Tay",
            "Buu_Dien_TPHCM",
            "Cau_Mong",
            "Cho_Ben_Thanh",
            "Cho_Binh_Tay",
            "Chua_Ba_Thien_Hau",
            "Chua_Buu_Long",
            "Chua_Ngoc_Hoang",
            "Chua_Phap_Hoa",
            "Chua_Vinh_Nghiem",
            "Cot_Co_Thu_Ngu",
            "Dinh_Doc_Lap",
            "Ho_Con_Rua",
            "Landmark_81",
            "Nha_Hat_Thanh_Pho",
            "Nha_Tho_Duc_Ba",
            "Nha_Tho_Giao_Xu_Tan_Dinh",
            "Thao_Cam_Vien",
            "UBND_TPHCM"
        )
        if (maxConfidence<0.35)
        {
            Toast.makeText(this, "Can't recognize", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(this, "Predicted: " + classes[maxPos] + " $maxConfidence%", Toast.LENGTH_SHORT).show()
        }

        // Releases model resources if no longer used.
        model.close()

    }

    private fun setupTabLayout(){
        binding.viewPager.adapter = HomeVPAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Location"
                1 -> tab.text = "Food"
            }
        }.attach()
    }
}