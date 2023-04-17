package com.codepath.flexbody

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup



class ExerciseFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exercise, container, false)
    }

    companion object {
        private const val TAG = "ExerciseFragment/"
        private const val EXERCISE_SEARCH_URL = "https://wger.de/api/v2/exerciseinfo/"
        private const val SEARCH_LIMIT = 20
        private const val ORDERING = "name"
        // should be EN
        private const val LANGUAGE = 2
        fun newInstance(): ExerciseFragment {
            return ExerciseFragment()
        }
    }
}