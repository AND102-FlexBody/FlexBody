package com.codepath.flexbody

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NutritionAdapter (private val mNutrition: List<Nutrition>) : RecyclerView.Adapter<NutritionAdapter.ViewHolder>()
{
    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    inner class ViewHolder(itemView:    View) : RecyclerView.ViewHolder(itemView) {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        val nameTextView = itemView.findViewById<TextView>(R.id.nutritionItem)
//        val messageButton = itemView.findViewById<Button>(R.id.message_button)
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
        nameTextView .setText(nutrition.name)

    }

    // Returns the total count of items in the list
    override fun getItemCount(): Int {
        return mNutrition.size
    }
}