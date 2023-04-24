package com.codepath.flexbody.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.flexbody.FeedAdapter
import com.codepath.flexbody.PhotoActivity
import com.codepath.flexbody.Post
import com.codepath.flexbody.R
import com.parse.ParseQuery

open class FeedFragment : Fragment() {
    lateinit var adapter: FeedAdapter
    var allPosts: MutableList<Post> = mutableListOf()
    lateinit var rv: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_feed, container, false)
        activity?.title = "Feed"
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val REQUEST_CODE = 200
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            queryPosts()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = FeedAdapter(requireContext(), allPosts)
        rv = view.findViewById(R.id.postRV)
        rv.layoutManager =
            LinearLayoutManager(requireContext())
        queryPosts()

        view.findViewById<Button>(R.id.photoButton).setOnClickListener {
            val intent = Intent(requireContext(), PhotoActivity::class.java)
            requireContext().startActivity(intent)
        }
        rv.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        queryPosts()
    }

    companion object {
        const val TAG = "FeedFragment/"
    }

    fun newInstance(): FeedFragment {
        return FeedFragment()
    }

    open fun queryPosts() {
        val query: ParseQuery<Post> = ParseQuery.getQuery(Post::class.java)
        query.include(Post.KEY_USER)
        query.addDescendingOrder("createdAt")
        query.findInBackground { posts, e ->
            if (e != null) {
                Log.i(TAG, "ERROR fetching posts")
            } else {
                if (posts != null) {
                    for (post in posts) {
                        Log.i(
                            TAG, "POST: ${post.getDescription()}, " +
                                    "USER: ${post.getUser()?.username}"
                        )
                    }
                }
                allPosts.clear()
                allPosts.addAll(posts)
                adapter.notifyDataSetChanged()
            }
        }
    }

}
