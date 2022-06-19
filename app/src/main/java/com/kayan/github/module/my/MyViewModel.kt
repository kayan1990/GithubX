package com.kayan.github.module.my

import android.app.Application
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.kayan.github.kotlin.R
import com.kayan.github.common.net.ResultCallBack
import com.kayan.github.model.AppGlobalModel
import com.kayan.github.module.base.BaseUserInfoViewModel
import com.kayan.github.repository.LoginRepository
import com.kayan.github.repository.UserRepository
import javax.inject.Inject

class MyViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val userRepository: UserRepository,
    private val globalAppModel: AppGlobalModel,
    private val application: Application
) : BaseUserInfoViewModel(loginRepository, userRepository, application) {

    val notifyColor = MutableLiveData<Int>()

    override fun loadDataByRefresh() {
        super.loadDataByRefresh()
        userRepository.getNotify(null, null, 1, object : ResultCallBack<ArrayList<Any>> {
            override fun onSuccess(result: ArrayList<Any>?) {
                notifyColor.value = if (result == null || result.size == 0) {
                    ContextCompat.getColor(application, R.color.subTextColor)
                } else {
                    ContextCompat.getColor(application, R.color.actionBlue)
                }
            }

            override fun onFailure() {
            }
        })

    }

    override fun getUserModel() = globalAppModel.userObservable


}