package com.codepath.flexbody

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.GsonBuilder


class NutritionAdapter(private val context: Context?, private val mNutrition: List<Nutrition>) : RecyclerView.Adapter<NutritionAdapter.ViewHolder>()
{
    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    inner class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        val nameTextView = itemView.findViewById<TextView>(R.id.nutritionItem)
        val imageView = itemView.findViewById<ImageView>(R.id.nutritionImage)

        init {
            // Attach a click listener to the entire row view
            itemView.setOnClickListener(this)
        }

        // Handles the row being being clicked
        override fun onClick(v: View?) {
            val position = this.adapterPosition
            if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                val item = mNutrition[position]
                val intent = Intent(context, NutritionDetail::class.java)
                // put "extras" into the bundle for access in the second activity
                val gsonBuilder = GsonBuilder()
                val gson: Gson = gsonBuilder.create()

                intent.putExtra("item",gson.toJson(item))
                context?.startActivity(intent)

            }
        }


    }

    // ... constructor and member variables
    // Usually involves inflating a layout from XML and returning the holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):NutritionAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val nutrictionView = inflater.inflate(R.layout.nutrition_layout, parent, false)
        // Return a new holder instance
        return ViewHolder(nutrictionView)
    }

    // Involves populating data into the item through holder
    override fun onBindViewHolder(viewHolder: NutritionAdapter.ViewHolder, position: Int) {
        // Get the data model based on position
        val nutrition: Nutrition = mNutrition.get(position)
        // Set item views based on your views and data model
        val nameTextView = viewHolder.nameTextView
        val imageView=viewHolder.imageView
        nameTextView .setText(nutrition.data?.name)
        Glide.with(imageView)
            .load(nutrition.data?.image)
            .placeholder(R.drawable.placeholder_small)
            .centerInside()
            .into(imageView)


    }


    // Returns the total count of items in the list
    override fun getItemCount(): Int {
        return mNutrition.size
    }


}