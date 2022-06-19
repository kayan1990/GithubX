package com.kayan.github.module.repos.file

import android.app.Application
import com.kayan.github.model.ui.ReposUIModel
import com.kayan.github.module.base.BaseViewModel
import com.kayan.github.repository.ReposRepository
import javax.inject.Inject

/**
 * Created by kayan
 * Date: 2018-10-26
 */

class ReposFileViewModel @Inject constructor(private val reposRepository: ReposRepository, application: Application) : BaseViewModel(application) {

    val reposUIModel = ReposUIModel()

    var path: String = ""

    var userName: String = ""

    var reposName: String = ""


    override fun loadDataByRefresh() {
        reposRepository.getFiles(userName, reposName, path, this)
    }

    override fun loadDataByLoadMore() {

    }


}