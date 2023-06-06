package com.speer.test.github.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.speer.test.MainActivity
import com.speer.test.R
import com.speer.test.SpeerFragmentManager
import com.speer.test.SpeerFragmentTag
import com.speer.test.databinding.SearchFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Created by Charles Raj I on 05/06/23.
 * @author Charles Raj I
 */
@AndroidEntryPoint
class SearchFragment : Fragment() {

    @Inject
    lateinit var fragmentManager: SpeerFragmentManager

    lateinit var searchFragmentBinding: SearchFragmentBinding

    val searchViewModel: SearchViewModel by lazy {
        ViewModelProvider(this)[SearchViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        searchFragmentBinding = DataBindingUtil
            .inflate(
                LayoutInflater.from(context),
                R.layout.search_fragment, container, false
            )
        searchFragmentBinding.searchViewModel = searchViewModel

        searchViewModel.userInfoLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                searchFragmentBinding.searchEditTextLayout.isErrorEnabled = false
                fragmentManager.updateFragmentWithBundle(
                    SpeerFragmentTag.USER_PROFILE_SCREEN,
                    activity as MainActivity,
                    true,
                    null,
                    Bundle().apply {
                        putParcelable("userData", it)
                    },
                    "User Info"
                )
            }
            Log.d("TAG", "onCreateView: " + it)
        })

        searchViewModel.errorLiveData.observe(viewLifecycleOwner, Observer {
            it?.let{
                searchFragmentBinding.searchEditTextLayout.isErrorEnabled = true
                searchFragmentBinding.searchEditTextLayout.error = it
                searchFragmentBinding.searchEditTextLayout.boxStrokeErrorColor =
                    context?.let { it1 -> ContextCompat.getColorStateList(it1,R.color.red) }
            }
        })

        return searchFragmentBinding.root
    }

    override fun onResume() {
        super.onResume()

    }


    override fun onStop() {
        super.onStop()
        searchViewModel.userInfoLiveData.postValue(null)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}