package com.kayan.github.module.person

import android.app.Application
import android.view.View
import com.kayan.github.common.net.ResultCallBack
import com.kayan.github.model.bean.User
import com.kayan.github.model.conversion.UserConversion
import com.kayan.github.model.ui.UserUIModel
import com.kayan.github.module.base.BaseUserInfoViewModel
import com.kayan.github.repository.LoginRepository
import com.kayan.github.repository.UserRepository
import javax.inject.Inject

/**
 * 用户详情页面 VM
 */
class PersonViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val userRepository: UserRepository,
    private val application: Application
) : BaseUserInfoViewModel(loginRepository, userRepository, application) {

    val userObservable = UserUIModel()

    private var isFocus = false

    override fun loadDataByRefresh() {
        userRepository.getPersonInfo(object : ResultCallBack<User> {

            override fun onCacheSuccess(result: User?) {
                result?.apply {
                    UserConversion.cloneDataFromUser(application, this, userObservable)
                    if (userObservable.type == "Organization") {
                        return
                    }
                    checkFocus(this.login)
                }
            }

            override fun onSuccess(result: User?) {
                result?.apply {
                    UserConversion.cloneDataFromUser(application, this, userObservable)
                }
            }

            override fun onFailure() {

            }
        }, this, login)
    }

    override fun getUserModel() = userObservable

    private fun checkFocus(login: String?) {
        userRepository.checkFocus(login, object : ResultCallBack<Boolean> {
            override fun onSuccess(result: Boolean?) {
                result?.apply {
                    isFocus = result
                    foucsIcon.set(getFocusIcon())
                }
            }
        })
    }

    override fun onFocusClick(v: View?) {
        userRepository.doFocus(v!!.context, userObservable.login, isFocus, object : ResultCallBack<Boolean> {
            override fun onSuccess(result: Boolean?) {
                isFocus = isFocus.not()
                foucsIcon.set(getFocusIcon())
            }
        })
    }

    private fun getFocusIcon(): String {
        return if (isFocus) {
            "GSY-FOCUS"
        } else {
            "GSY-UN_FOCUS"
        }
    }
}