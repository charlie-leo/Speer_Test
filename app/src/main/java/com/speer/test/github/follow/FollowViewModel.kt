package com.speer.test.github.follow

import android.view.View
import android.widget.Button
import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.speer.test.github.UserInfo
import com.speer.test.service.RestServiceInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URLDecoder
import javax.inject.Inject

/**
 * Created by Charles Raj I on 05/06/23.
 * @author Charles Raj I
 */

@HiltViewModel
class FollowViewModel @Inject constructor(private val retrofitService: RestServiceInterface): ViewModel() {

    lateinit var job: Job
    var userInfo: UserInfo = UserInfo()
    var type = MutableLiveData<Int>(0)
    var buttonAction = MutableLiveData<Int>(0)
    var responseData: MutableLiveData<List<UserInfo>> = MutableLiveData()

    init {
//        loadData()
    }

    fun followClick(view: View, ty: Int){
        type.value = ty
        loadData()
    }

    fun loadData(){
        var url = userInfo.followersUrl
        if (type.value == 3){
            url = userInfo.followersUrl?.replace("followers","following")
        }

        job = viewModelScope.launch(){
            retrofitService.getFollowers(URLDecoder.decode(url!!)).enqueue(object : Callback<List<UserInfo>>{
                override fun onResponse(
                    call: Call<List<UserInfo>>,
                    response: Response<List<UserInfo>>
                ) {
                    response.body()?.let {
                        responseData.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<List<UserInfo>>, t: Throwable) {
                    responseData.postValue(null)
                }

            })
        }
    }

    fun btnClick(view: View, value: Int){
        buttonAction.postValue(value)
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}