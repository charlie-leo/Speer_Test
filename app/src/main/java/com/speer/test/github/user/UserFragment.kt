package com.speer.test.github.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.speer.test.MainActivity
import com.speer.test.R
import com.speer.test.SpeerFragmentManager
import com.speer.test.SpeerFragmentTag
import com.speer.test.databinding.UsersFragmentBinding
import com.speer.test.github.UserInfo
import com.speer.test.github.search.SearchViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Created by Charles Raj I on 05/06/23.
 * @author Charles Raj I
 */

@AndroidEntryPoint
class UserFragment : Fragment() {

    @Inject
    lateinit var fragmentManager: SpeerFragmentManager
    var userInfo: UserInfo? = UserInfo()
    val userViewModel: UserViewModel by lazy {
        ViewModelProvider(this)[UserViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val userFragmentBinding: UsersFragmentBinding = DataBindingUtil
            .inflate(
                LayoutInflater.from(context),
                R.layout.users_fragment, container, false
            )

        userFragmentBinding.shimmerFrame.visibility = View.VISIBLE
        userFragmentBinding.shimmerFrame.startShimmerAnimation()

        userFragmentBinding.userViewModel = userViewModel

        arguments?.also {
            userInfo = it.getParcelable("userData") as UserInfo?
            userFragmentBinding.userData = userInfo
            Picasso.get()
                .load(userInfo?.avatarUrl)
                .into(userFragmentBinding.userImage)
        }

        userViewModel.navigationLiveData.observe(viewLifecycleOwner, navigationObserver())

        userFragmentBinding.shimmerFrame.visibility = View.GONE
        userFragmentBinding.shimmerFrame.stopShimmerAnimation()

        return userFragmentBinding.root
    }

    fun navigationObserver() = Observer<Int> {
        when (it){
            1 -> onBackPress()
            2,3 -> followNavigation(it)
        }
    }

    private fun followNavigation(value: Int) = fragmentManager.updateFragmentWithBundle(
        SpeerFragmentTag.FOLLOWERS_SCREEN,
        activity as MainActivity,
        true,
        null,
        Bundle().apply {
            putInt("type",value)
            putParcelable("userData", userInfo)
        },
        "Followers"
    )

    private fun onBackPress(){
        activity?.onBackPressed()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
        userViewModel.navigationLiveData.postValue(0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}