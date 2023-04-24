package com.codepath.flexbody

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.codepath.flexbody.fragments.FeedFragment
import com.parse.ParseFile
import com.parse.ParseUser
import java.io.File

class PhotoActivity : AppCompatActivity() {
    val photoFileName = "photo.jpg"
    var photoFile: File? = null
    val APP_TAG = "MyCustomApp"
    val CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034
    lateinit var ivPreview: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)

        ivPreview = findViewById<ImageView>(R.id.photo_to_upload)
        findViewById<Button>(R.id.save_button).setOnClickListener {
            val description = findViewById<EditText>(R.id.photo_comment_et).text.toString()
            val user = ParseUser.getCurrentUser()
            if (photoFile != null) {
                submitPost(description, user, photoFile!!)
            } else {
                Toast.makeText(this, "A photo is required.", Toast.LENGTH_SHORT).show()

            }
        }

        findViewById<Button>(R.id.take_picture_button).setOnClickListener {
            onLaunchCamera()
        }

    }

    fun onLaunchCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        photoFile = getPhotoFileUri(photoFileName)

        if (photoFile != null) {
            val fileProvider: Uri =
                FileProvider.getUriForFile(
                    this,
                    "com.codepath.fileprovider",
                    photoFile!!
                )
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)

            if (intent.resolveActivity(this.packageManager) != null) {
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE)
            }
        }
    }

    private fun getPhotoFileUri(fileName: String): File {
        val mediaStorageDir =
            File(this.getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG)

        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d(APP_TAG, "failed to create directory")
        }
        return File(mediaStorageDir.path + File.separator + fileName)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            val takenImage = BitmapFactory.decodeFile(photoFile!!.absolutePath)
            Log.i(FeedFragment.TAG, takenImage.toString())
            ivPreview.setImageBitmap(takenImage)
        } else {
            Toast.makeText(this, "Error taking picture.", Toast.LENGTH_SHORT).show()
        }

    }


    private fun submitPost(description: String, user: ParseUser, file: File) {
        val post = Post()
        post.setDescription(description)
        post.setUser(user)
        post.setImage(ParseFile(file))
        post.saveInBackground { e ->
            if (e != null) {
                Log.e(FeedFragment.TAG, "Error while submitting post.")
                e.printStackTrace()
                Toast.makeText(this, "Error while submitting post.", Toast.LENGTH_SHORT).show()

            } else {
                findViewById<EditText>(R.id.photo_comment_et).text.clear()
                findViewById<ImageView>(R.id.photo_to_upload).setImageResource(0)
                val resultIntent = Intent()
                resultIntent.putExtra("key", "value")
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }
    }
}