package com.speer.test.github.user

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.speer.test.R
import com.speer.test.SpeerFragmentManager
import com.speer.test.SpeerFragmentTag
import com.speer.test.github.UserInfo
//import com.bumptech.glide.Glide
import com.speer.test.service.RestServiceInterface
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.log

/**
 * Created by Charles Raj I on 05/06/23.
 * @author Charles Raj I
 */

@HiltViewModel
class UserViewModel @Inject constructor() : ViewModel(){

    val navigationLiveData: MutableLiveData<Int> = MutableLiveData<Int>()

    fun buttonClick(view: View, info: Int){
        navigationLiveData.postValue(info)
    }

    override fun onCleared() {
        super.onCleared()
    }
}