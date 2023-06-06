package com.speer.test

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.speer.test.github.follow.FollowFragment
import com.speer.test.github.search.SearchFragment
import com.speer.test.github.user.UserFragment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Singleton

/**
 * Created by Charles Raj I on 05/06/23.
 * @author Charles Raj I
 */

@Module
@InstallIn(SingletonComponent::class)
class SpeerFragmentManager {

    @Provides
    @Singleton
    fun getInstance(): SpeerFragmentManager = SpeerFragmentManager()



    private fun getFragmentManager(activity: MainActivity): FragmentManager {
        return activity.supportFragmentManager
    }

    private fun getFragment(afterCareFragmentTag: SpeerFragmentTag): Fragment{
        val fragment: Fragment;
        when(afterCareFragmentTag){
            SpeerFragmentTag.SEARCH_SCREEN -> fragment = SearchFragment()
            SpeerFragmentTag.USER_PROFILE_SCREEN -> fragment = UserFragment()
            SpeerFragmentTag.FOLLOWERS_SCREEN -> fragment = FollowFragment()
            else -> {
                fragment = SearchFragment()
            }
        }

        return fragment
    }

    private fun setUpFragmentManager(fragmentManager: FragmentManager): FragmentTransaction {
        return fragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.pull_in_right, R.anim.push_out_left, R.anim.pull_in_left, R.anim.push_out_right)
    }


    fun updateFragmentWithBundle(
        speerFragmentTag: SpeerFragmentTag,
        activity: MainActivity,
        needBackStack: Boolean,
        container: View?,
        bundle: Bundle?,
        title: String?
    ){
        val afterCareFragment = getFragment(speerFragmentTag)
        afterCareFragment.let { fragment: Fragment ->
            container.let { view ->
                afterCareFragment.arguments = bundle
                replaceFragment(fragment, activity,speerFragmentTag, needBackStack,
                    view, bundle, title
                )
            }

        }
    }

    private fun replaceFragment(
        fragment: Fragment,
        activity: MainActivity,
        fragmentTag: SpeerFragmentTag,
        needBackStack: Boolean,
        container: View?,
        bundle: Bundle?,
        title: String?
    ){
        CoroutineScope(Dispatchers.Main).launch {
            val fragmentManager = getFragmentManager(activity = activity)
            val fragmentTransaction = setUpFragmentManager(fragmentManager)
            container?.let {its->
                fragmentTransaction.replace(its.id,fragment,fragmentTag.toString())
            }.run {
                fragmentTransaction.replace(R.id.mainRootLayout,fragment,fragmentTag.toString())
            }
            if (needBackStack){
                fragmentTransaction.addToBackStack(fragmentTag.toString())
            }
            fragmentManagerExecution(fragmentTransaction,fragmentManager)
        }
    }

    private fun fragmentManagerExecution(fragmentTransaction: FragmentTransaction, fragmentManager: FragmentManager){
        try{
            fragmentTransaction.commitAllowingStateLoss()
            fragmentManager.executePendingTransactions()
        }catch (e:Exception){
            Log.d("TAG", e.toString())
        }
    }

}