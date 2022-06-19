package com.kayan.github.module.repos

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kayan.github.common.net.ResultCallBack
import com.kayan.github.repository.ReposRepository
import javax.inject.Inject

/**
 * Created by kayan
 * Date: 2022-06-18
 */
class ReposDetailViewModel @Inject constructor(private val reposRepository: ReposRepository, private val application: Application) : ViewModel() {

    val starredStatus = MutableLiveData<Boolean>()
    val watchedStatus = MutableLiveData<Boolean>()

    fun getReposStatus(userName: String, reposName: String) {
        reposRepository.getReposStatus(userName, reposName, object : ResultCallBack<HashMap<String, Boolean>> {
            override fun onSuccess(result: HashMap<String, Boolean>?) {
                result?.apply {
                    starredStatus.value = this[ReposRepository.STAR_KEY]
                    watchedStatus.value = this[ReposRepository.WATCH_KEY]
                }
            }

            override fun onFailure() {

            }
        })
    }

    fun changeStarStatus(context: Context, userName: String, reposName: String) {
        reposRepository.changeStarStatus(context, userName, reposName, starredStatus)
    }

    fun changeWatchStatus(context: Context, userName: String, reposName: String) {
        reposRepository.changeWatchStatus(context, userName, reposName, watchedStatus)
    }

    fun forkRepository(context: Context, userName: String, reposName: String) {
        reposRepository.forkRepository(context, userName, reposName)
    }
}