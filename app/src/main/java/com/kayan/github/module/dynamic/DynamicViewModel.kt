package com.kayan.github.module.dynamic

import android.app.Application
import com.kayan.github.module.base.BaseViewModel
import com.kayan.github.repository.UserRepository
import javax.inject.Inject

/**
 * Created by kayan
 * Date: 2018-10-19
 */

class DynamicViewModel @Inject constructor(private val userRepository: UserRepository, application: Application) : BaseViewModel(application) {

    override fun refresh() {
        if (isLoading()) {
            return
        }
        super.refresh()
    }

    override fun loadDataByRefresh() {
        loadData()
    }

    override fun loadDataByLoadMore() {
        loadData()
    }

    private fun loadData() {
        clearWhenRefresh()
        userRepository.getReceivedEvent(this, page)
    }

}