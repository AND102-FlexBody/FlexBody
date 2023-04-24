package com.codepath.flexbody.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.flexbody.Exercise
import com.codepath.flexbody.MyPlanExerciseAdapter
import com.codepath.flexbody.R
import com.parse.ParseQuery


class PersonalFragment : Fragment() {
    private lateinit var myPlanExercisesRV: RecyclerView
    lateinit var myPlanExerciseAdapter: MyPlanExerciseAdapter
    private var allExercises: MutableList<Exercise> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_personal, container, false)
        activity?.title = "My Plan"

        // My Plan Exercise Recycler view
        myPlanExercisesRV = view.findViewById<View>(R.id.myPlanExercisesRV) as RecyclerView
        val context = view.context
        myPlanExerciseAdapter = MyPlanExerciseAdapter(requireContext(), allExercises)
        myPlanExercisesRV.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)


        // TODO: if we add nutrition my plan recyclerview, it goes here
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        queryExercises()
        myPlanExercisesRV.adapter = myPlanExerciseAdapter
    }

    companion object {
        fun newInstance(): PersonalFragment {
            return PersonalFragment()
        }

        private const val TAG = "PersonalFragment/"
    }

    // query for all user exercises in server
    private fun queryExercises() {
        val query: ParseQuery<Exercise> = ParseQuery.getQuery(Exercise::class.java)
        query.include(Exercise.KEY_USER)
        query.addDescendingOrder("createdAt")
        query.findInBackground { exercises, e ->
            if (e != null) {
                Log.i(TAG, "ERROR fetching exercises.")
            } else {

                if (exercises != null) {
                    for (ex in exercises) {
                        Log.i(
                            TAG, "EX: ${ex.getDescription()}, USER: ${ex.getUser()?.username}"
                        )
                    }
                }
                allExercises.clear()
                allExercises.addAll(exercises)
                myPlanExerciseAdapter.notifyDataSetChanged()
            }
        }
    }
}