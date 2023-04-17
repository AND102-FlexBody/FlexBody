package com.codepath.flexbody

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.codepath.flexbody.databinding.ActivityMainBinding

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
    }

    companion object {
        private const val TAG = "MainActivity/"
    }
}