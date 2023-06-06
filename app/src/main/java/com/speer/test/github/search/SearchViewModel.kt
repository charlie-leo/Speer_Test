package com.speer.test.github.search

import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.speer.test.SpeerFragmentManager
import com.speer.test.SpeerFragmentTag
import com.speer.test.github.UserInfo
import com.speer.test.service.RestServiceInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by Charles Raj I on 05/06/23.
 * @author Charles Raj I
 */

@HiltViewModel
class SearchViewModel @Inject constructor(private val retrofitService: RestServiceInterface) :
    ViewModel() {

    var job: Job? = null
    val userInfoLiveData: MutableLiveData<UserInfo> = MutableLiveData()
    val errorLiveData: MutableLiveData<String> = MutableLiveData()


    fun searchBtn(view: View, editText: EditText) {
        try {
            job = viewModelScope.launch {
                retrofitService.getUserDetailByUserName(editText.text.toString())
                    .enqueue(object : Callback<UserInfo> {
                        override fun onResponse(
                            call: Call<UserInfo>,
                            response: Response<UserInfo>
                        ) {
                            if (response.code() == 200) {
                                response.body()?.let {
                                    userInfoLiveData.postValue(response.body())
                                    errorLiveData.postValue(null)
                                }
                            }else{
                                errorLiveData.postValue("${response.code()} : ${response.message()}")
                            }
                        }
                        override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                            userInfoLiveData.postValue(null)
                            errorLiveData.postValue(" ${t.message}")
                        }
                    })
            }
        } catch (e: Exception) {
            Log.d("TAG", "searchBtn: " + e.message)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}