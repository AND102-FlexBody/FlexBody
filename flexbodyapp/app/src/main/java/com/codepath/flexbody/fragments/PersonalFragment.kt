package com.codepath.flexbody.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.flexbody.R

class PersonalFragment : Fragment() {
    private lateinit var myPlanExercisesRV: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_personal, container, false)
        activity?.title = "My Plan"

        // My Plan Exercise Recycler view
        myPlanExercisesRV = view.findViewById<View>(R.id.myPlanExercisesRV) as RecyclerView
        val context = view.context
        myPlanExercisesRV.layoutManager = LinearLayoutManager(context)


        // TODO: if we add nutrition my plan recyclerview, it goes here
        return view
    }

    companion object {
        fun newInstance(): PersonalFragment {
            return PersonalFragment()
        }
    }
}