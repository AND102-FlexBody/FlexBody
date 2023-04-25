package com.codepath.flexbody

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.codepath.flexbody.Nutrition as Nutrition

class NutritionDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gsonBuilder = GsonBuilder()
        val gson: Gson = gsonBuilder.create()
        setContentView(R.layout.activity_nutrition_detail)

        val item: Nutrition= gson.fromJson(intent.getStringExtra("item"),Nutrition::class.java)
        val nutritionName: TextView =findViewById(R.id.nutritionDetailName)
        val nutritionImage: ImageView=findViewById(R.id.nutritionDetailImage)
        nutritionName.text= item.data?.name

        Glide.with(nutritionImage)
            .load(item.data?.image)
            .placeholder(R.drawable.placeholder_small)
            .centerInside()
            .into(nutritionImage)






//        val nutritionArray= object : TypeToken<MutableList<Nutrition>>() {}.type
//        val gsonBuilder = GsonBuilder()
//        val gson: Gson = gsonBuilder.create()
//        val new_nutritionItems: List<Nutrition> = gson.fromJson( resultList.toString(), nutritionArray)
//        for (item in new_nutritionItems){
//            Log.d("item insert",item.toString())
//            Log.d("arraysize",nutritionlist.size.toString())
//            nutritionlist.add(item)
//        }
//        adapter.notifyItemRangeInserted (nutritionlist.size-new_nutritionItems.size,new_nutritionItems.size)









    }
}