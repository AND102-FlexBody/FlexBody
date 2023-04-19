package com.codepath.flexbody.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.codepath.flexbody.R

class NutritionFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.title = "Nutrition Search"

        return inflater.inflate(R.layout.fragment_nutrition, container, false)
    }

    companion object {
        fun newInstance(): NutritionFragment {
            return NutritionFragment()
        }
    }
}