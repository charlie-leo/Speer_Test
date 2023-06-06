package com.speer.test.github.follow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.speer.test.R
import com.speer.test.databinding.FollowFragmentBinding
import com.speer.test.github.UserInfo
import com.speer.test.github.search.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Created by Charles Raj I on 05/06/23.
 * @author Charles Raj I
 */
@AndroidEntryPoint
class FollowFragment: Fragment() {

    val followViewModel: FollowViewModel by lazy {
        ViewModelProvider(this)[FollowViewModel::class.java]
    }

    lateinit var followFragmentBinding: FollowFragmentBinding
    var userInfo: UserInfo? = UserInfo()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        followFragmentBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.follow_fragment,
            container,
            false
        )

        arguments?.also {
            userInfo = it.getParcelable("userData") as UserInfo?
            userInfo?.let {
                followViewModel.userInfo = it
                followFragmentBinding.userData = it
            }
            followViewModel.type.postValue(it.getInt("type"))
            followViewModel.loadData()
        }
        followFragmentBinding.followViewModel = followViewModel
        followFragmentBinding.followSwipeLayout.setOnRefreshListener {
            followViewModel.loadData()
        }

        followViewModel.responseData.observe(viewLifecycleOwner, loadRecyclerView())
        followViewModel.type.observe(viewLifecycleOwner, Observer {

            if (it == 2){
                context?.let { it1 ->
                    ContextCompat.getColor(
                        it1,R.color.primaryColor)
                }?.let { it2 -> followFragmentBinding.button2.setBackgroundColor(it2) }

                context?.let { it1 ->
                    ContextCompat.getColor(
                        it1,R.color.black_2)
                }?.let { it2 -> followFragmentBinding.button.setBackgroundColor(it2) }
            }else{
                context?.let { it1 ->
                    ContextCompat.getColor(
                        it1,R.color.black_2)
                }?.let { it2 -> followFragmentBinding.button2.setBackgroundColor(it2) }

                context?.let { it1 ->
                    ContextCompat.getColor(
                        it1,R.color.primaryColor)
                }?.let { it2 -> followFragmentBinding.button.setBackgroundColor(it2) }
            }
        })
        followViewModel.buttonAction.observe(viewLifecycleOwner, Observer {
            when(it){
                1 -> activity?.onBackPressed()
            }
        })

        return followFragmentBinding.root
    }

    private fun loadRecyclerView() = Observer<List<UserInfo>> {response ->

        followFragmentBinding.followSwipeLayout.isRefreshing = false
        val followAdapter = FollowAdapter(response)
        followFragmentBinding.followRecyclerView.layoutManager = LinearLayoutManager(context)
        followFragmentBinding.followRecyclerView.adapter = followAdapter
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
        followViewModel.buttonAction.postValue(0)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}