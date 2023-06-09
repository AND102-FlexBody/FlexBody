package com.codepath.flexbody

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside


class MyPlanExerciseDetail : AppCompatActivity() {
    private lateinit var exerciseImageView: ImageView
    private lateinit var exerciseName: TextView
    private lateinit var exerciseDescription: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.exercise_plan_detail)
        // Get the intent extra value
        val subName = intent.getStringExtra("name")
        val subDesc = intent.getStringExtra("desc")
        val subImage = intent.extras?.get("image")

        this.title = subName
        exerciseImageView = findViewById(R.id.myExerciseIV)
        exerciseName = findViewById(R.id.myExerciseNameTV)
        exerciseDescription = findViewById(R.id.myExerciseDescriptionTV)

        exerciseName.text = subName
        exerciseDescription.text = subDesc

        Glide.with(this)
            .load(subImage)
            .transform(CenterInside()).into(exerciseImageView)
    }

    companion object {
        private const val TAG = "MyPlanExerciseDetail/"
    }
}