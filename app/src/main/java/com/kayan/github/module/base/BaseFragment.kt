package com.kayan.github.module.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.kayan.github.di.Injectable
import com.kayan.github.ui.holder.base.DataBindingComponent

/**
 * 基类Fragment
 * Created by kayan
 * Date: 2022-06-18
 */
abstract class BaseFragment<T : ViewDataBinding> : Fragment(), Injectable {

    /**
     * 根据Fragment动态清理和获取binding对象
     */
    var binding by autoCleared<T>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                getLayoutId(),
                container,
                false,
                DataBindingComponent())
        onCreateView(binding?.root)
        return binding?.root
    }

    open fun actionOpenByBrowser() {

    }

    open fun actionCopy() {

    }

    open fun actionShare() {

    }

    abstract fun onCreateView(mainView: View?)

    abstract fun getLayoutId(): Int

    /**
     * Navigation 的页面跳转
     */
    fun navigationPopUpTo(view: View, args: Bundle?, actionId: Int, finishStack: Boolean, inclusive: Boolean) {
        val controller = Navigation.findNavController(view)
        controller.navigate(actionId,
                args, NavOptions.Builder().setPopUpTo(controller.graph.id, inclusive).build())
        if (finishStack) {
            activity?.finish()
        }
    }

    fun navigationBack(view: View) {
        val controller = Navigation.findNavController(view)
        controller.popBackStack()
    }

    fun exitFull() {
        activity?.window?.setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
    }

}