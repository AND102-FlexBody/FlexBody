package com.codepath.flexbody.fragments

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.flexbody.FeedAdapter
import com.codepath.flexbody.Post
import com.codepath.flexbody.R
import com.parse.ParseFile
import com.parse.ParseQuery
import com.parse.ParseUser
import java.io.File

open class FeedFragment : Fragment() {
    lateinit var adapter: FeedAdapter
    var allPosts: MutableList<Post> = mutableListOf()
    lateinit var rv: RecyclerView
    val photoFileName = "photo.jpg"
    var photoFile: File? = null
    val APP_TAG = "MyCustomApp"
    val CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_feed, container, false)
        activity?.title = "Feed"
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // this is where we set up the views
        adapter = FeedAdapter(requireContext(), allPosts)
        rv = view.findViewById(R.id.postRV)
        rv.adapter = adapter
        var cameraResultLauncher: ActivityResultLauncher<Intent>? = null

        rv.layoutManager =
            LinearLayoutManager(requireContext())
        view.findViewById<Button>(R.id.photoButton).setOnClickListener {
            onLaunchCamera()

            val user = ParseUser.getCurrentUser()
            if (photoFile != null) {
                submitPost("test", user, photoFile!!)
            } else {
                // TODO: print error or toast
            }
        }
        queryPosts()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == AppCompatActivity.RESULT_OK) {
            // by this point we have the camera photo on disk
            val takenImage = BitmapFactory.decodeFile(photoFile!!.absolutePath)
            // RESIZE BITMAP, see section below
            // Load the taken image into a preview
            Log.i(TAG, takenImage.toString())
        } else { // Result was a failure
            Toast.makeText(requireContext(), "Error taking picture", Toast.LENGTH_SHORT).show()
        }

    }

    companion object {
        const val TAG = "Feedfragment"
    }

    fun newInstance(): FeedFragment {
        return FeedFragment()
    }

    // query for all posts in server
    open fun queryPosts() {
        val query: ParseQuery<Post> = ParseQuery.getQuery(Post::class.java)
        // give us the user associated with post
        query.include(Post.KEY_USER)
        query.addDescendingOrder("createdAt")
        // find all posts in parse
        query.findInBackground { posts, e ->
            if (e != null) {
                Log.i(TAG, "ERROR fetching posts")
            } else {
                if (posts != null) {
                    for (post in posts) {
                        Log.i(
                            TAG, "POST: ${post.getDescription()}, USER: ${post.getUser()?.username}"
                        )
                    }
                }
                allPosts.clear()
                allPosts.addAll(posts)
                adapter.notifyDataSetChanged()
            }
        }
    }

    fun onLaunchCamera() {
        // create Intent to take a picture and return control to the calling application
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // Create a File reference for future access
        photoFile = getPhotoFileUri(photoFileName)

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        if (photoFile != null) {
            val fileProvider: Uri =
                FileProvider.getUriForFile(
                    requireContext(),
                    "com.codepath.fileprovider",
                    photoFile!!
                )
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)

            // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
            // So as long as the result is not null, it's safe to use the intent.

            // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
            // So as long as the result is not null, it's safe to use the intent.
            if (intent.resolveActivity(requireContext().packageManager) != null) {
                // Start the image capture intent to take photo
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE)
            }
        }
    }

    // Returns the File for a photo stored on disk given the fileName
    fun getPhotoFileUri(fileName: String): File {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        val mediaStorageDir =
            File(requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG)

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d(APP_TAG, "failed to create directory")
        }

        // Return the file target for the photo based on filename
        return File(mediaStorageDir.path + File.separator + fileName)
    }

    private fun submitPost(description: String, user: ParseUser, file: File) {
        // Create Post object
        val post = Post()
        post.setDescription(description)
        post.setUser(user)
        post.setImage(ParseFile(file))
        post.saveInBackground { e ->
            if (e != null) {
                Log.e(TAG, "error while saving post")
                e.printStackTrace()
                // TODO: show toast
            } else {
                Log.i(TAG, "Successfully saved post")
                // TODO: reset editextfield to empty
                // TODO: reset the image view to empty
            }
        }
    }
}
