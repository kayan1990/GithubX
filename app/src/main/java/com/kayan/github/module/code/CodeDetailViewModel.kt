package com.kayan.github.module.code

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kayan.github.common.net.ResultCallBack
import com.kayan.github.repository.ReposRepository
import javax.inject.Inject

/**
 * Created by kayan
 * Date: 2022-06-18
 */
class CodeDetailViewModel @Inject constructor(private val reposRepository: ReposRepository) : ViewModel() {

    val htmlData = MutableLiveData<String>()


    init {
        htmlData.value = ""
    }

    fun getCodeHtml(userName: String, reposName: String, path: String) {
        reposRepository.getRepoFilesDetail(userName, reposName, path, object : ResultCallBack<String> {
            override fun onSuccess(result: String?) {
                htmlData.value = result!!
            }

            override fun onFailure() {
                htmlData.value = "<h1>该文件不支持预览</h1>"
            }
        })
    }
}