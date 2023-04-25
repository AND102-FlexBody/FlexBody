package com.codepath.flexbody.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.codepath.flexbody.ProfileActivity
import com.codepath.flexbody.R
import com.parse.ParseUser

class HomeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.title = "Home"

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        view.findViewById<Button>(R.id.logOutButton).setOnClickListener {
            ParseUser.logOut()
            activity?.finish()
        }

        view.findViewById<Button>(R.id.goToProfileBtn).setOnClickListener {
            val intent = Intent(requireContext(), ProfileActivity::class.java)
            requireContext().startActivity(intent)
        }
    }

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }
}