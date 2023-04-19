package com.codepath.flexbody.fragments

import android.os.Bundle
import android.view.*
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.flexbody.R


class ExerciseFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.exercise_search_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_exercise, container, false)
        activity?.title = getString(R.string.action_bar_search)
        val progressBar =
            view.findViewById<View>(R.id.exerciseSearchProgressBar) as ContentLoadingProgressBar
        val exerciseSearchRV = view.findViewById<View>(R.id.exerciseSearchList) as RecyclerView
        val context = view.context
        exerciseSearchRV.layoutManager = LinearLayoutManager(context)
        updateAdapter(progressBar, exerciseSearchRV)
        return view
    }

    private fun updateAdapter(progressBar: ContentLoadingProgressBar,
                              recyclerView: RecyclerView){
        progressBar.show()
        val client = AsyncHttpClient()
        val params = RequestParams()
        val searchTerm = "..."

    }

    companion object {
        private const val TAG = "ExerciseFragment/"
        private const val EXERCISE_SEARCH_URL = "https://wger.de/api/v2/exercise/"
        private const val SEARCH_LIMIT = 20
        private const val ORDERING = "name"

        // should be EN
        private const val LANGUAGE = 2
        fun newInstance(): ExerciseFragment {
            return ExerciseFragment()
        }
    }
}