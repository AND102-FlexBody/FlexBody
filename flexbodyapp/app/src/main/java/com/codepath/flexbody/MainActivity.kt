package com.codepath.flexbody

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.codepath.flexbody.databinding.ActivityMainBinding
import com.codepath.flexbody.fragments.*
import kotlinx.serialization.json.Json

fun createJson() = Json {
    isLenient = true
    ignoreUnknownKeys = true
    useAlternativeNames = false
}

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val fragmentManager: FragmentManager = supportFragmentManager
        val homeFragment: Fragment = HomeFragment()
        val exerciseFragment: Fragment = ExerciseFragment()
        val nutritionFragment: Fragment = NutritionFragment()
        val personalFragment: Fragment = PersonalFragment()
        val feedFragment: Fragment = FeedFragment()


        binding?.bottomNavigation?.setOnItemSelectedListener { item ->
            lateinit var fragment: Fragment
            when (item.itemId) {
                R.id.action_home -> fragment = homeFragment
                R.id.action_exercise -> fragment = exerciseFragment
                R.id.action_nutrition -> fragment = nutritionFragment
                R.id.action_personal -> fragment = personalFragment
                R.id.action_feed -> fragment = feedFragment
            }
            fragmentManager.beginTransaction().replace(R.id.rlContainer, fragment).commit()
            true
        }

        binding?.bottomNavigation?.selectedItemId = R.id.action_home
        Log.d(TAG, "MainActivity onCreate() method called")

//        if (Intent.ACTION_SEARCH == intent.action) {
//            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
//                val bundle = Bundle()
//                bundle.putString(query, "From Activity")
//// set Fragmentclass Arguments
//// set Fragmentclass Arguments
//                val fragobj = Fragmentclass()
//                fragobj.setArguments(bundle)
//
//
//            }
//        }

    }

    companion object {
        private const val TAG = "MainActivity/"
    }
}