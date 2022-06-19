package com.kayan.github.module.my

import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import com.kayan.github.kotlin.R
import com.kayan.github.kotlin.databinding.LayoutUserHeaderBinding
import com.kayan.github.module.base.BaseUserInfoFragment
import com.kayan.github.module.notify.NotifyActivity

/**
 * 我的
 * Created by kayan
 * Date: 2022-06-18
 */
class MyFragment : BaseUserInfoFragment<MyViewModel>() {

    override fun getViewModelClass(): Class<MyViewModel> = MyViewModel::class.java

    override fun getLoginName(): String? = null

    override fun bindHeader(binding: LayoutUserHeaderBinding) {
        binding.userHeaderNotify.visibility = View.VISIBLE
        binding.userHeaderNotify.setOnClickListener {
            startActivityForResult(Intent(context, NotifyActivity::class.java), 1000)
        }

        binding.loginBtn.setOnClickListener {
            navigationPopUpTo(binding.root, null, R.id.action_myFragment_to_loginOAuthFragment2, false, false)
        }

        binding.loginOffBtn.setOnClickListener {
//            loginRepository.logout(view!!.context)
        }

        getViewModel().notifyColor.observe(this, Observer { result ->
            result?.apply {
                binding.userHeaderNotify.setTextColor(this)
            }
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        showRefresh()
    }
}