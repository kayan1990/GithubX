package com.kayan.github.module.login

import android.content.Context
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kayan.github.kotlin.R
import com.kayan.github.AppConfig
import com.kayan.github.common.utils.GSYPreference
import com.kayan.github.repository.LoginRepository
import org.jetbrains.anko.toast
import javax.inject.Inject

/**
 * 登录模块的VM
 * Created by kayan
 * Date: 2018-09-29
 */
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository) : ViewModel() {

    private var usernameStorage: String by GSYPreference(AppConfig.USER_NAME, "")

    private var passwordStorage: String by GSYPreference(AppConfig.PASSWORD, "")

    /**
     * 用户名
     */
    val username = ObservableField<String>()

    /**
     * 密码
     */
    val password = ObservableField<String>()

    /**
     * 登录结果
     */
    val loginResult = MutableLiveData<Boolean>()

    init {
        //读取本地存储的用户名和密码
        username.set(usernameStorage)
        password.set(passwordStorage)
    }

    /**
     * 登录执行
     */
    fun login(context: Context) {
        loginRepository.login(context, username.get()!!.trim(), password.get()!!.trim(), loginResult)
    }

    /**
     * 登录执行
     */
    fun oauth(context: Context, code: String) {
        loginRepository.oauth(context, code, loginResult)
    }

    /**
     * 通过DataBinding在XML绑定的点击方法
     */
    fun onSubmitClick(view: View) {
        val username = this.username.get()
        val password = this.password.get()

        view.context.toast(R.string.LoginTip)

        //navigationPopUpTo(view, null, R.id.action_nav_wel_to_login, false)
        /*username?.apply {
            if (this.isEmpty()) {
                view.context.toast(R.string.LoginNameTip)
                return
            }
        }

        password?.apply {
            if (this.isEmpty()) {
                view.context.toast(R.string.LoginPWTip)
                return
            }
        }

        login(view.context)*/
    }
}