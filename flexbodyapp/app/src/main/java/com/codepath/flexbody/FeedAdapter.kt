package com.codepath.flexbody

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat

class FeedAdapter(val context: Context, val posts: List<Post>) :
    RecyclerView.Adapter<FeedAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: FeedAdapter.ViewHolder, position: Int) {
        val post = posts.get(position)
        holder.bind(post)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvUserName: TextView
        val ivImage: ImageView
        val tvDescription: TextView

        init {
            tvUserName = itemView.findViewById(R.id.tvUserName)
            ivImage = itemView.findViewById(R.id.ivImage)
            tvDescription = itemView.findViewById(R.id.tvDescription)
        }

        fun bind(post: Post) {
            tvDescription.text = post.getDescription()
            val format = SimpleDateFormat("MMMM d, yyyy")
            val dateString =
                format.format(post.createdAt) // Returns a string in the format 'yyyy-MM-dd'
            tvUserName.text = "By ${post.getUser()?.username} on ${dateString}"

            Glide.with(itemView.context).load(post.getImage()?.url).into(ivImage)

        }

    }
}

