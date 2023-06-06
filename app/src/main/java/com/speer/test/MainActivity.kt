package com.speer.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.speer.test.databinding.ActivityMainBinding
import com.speer.test.github.search.SearchFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var fragmentManager: SpeerFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityMainBinding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        fragmentManager.updateFragmentWithBundle(
            SpeerFragmentTag.SEARCH_SCREEN,
            this,
            true,
            null,
            null,
            "User Info"
        )

    }
}