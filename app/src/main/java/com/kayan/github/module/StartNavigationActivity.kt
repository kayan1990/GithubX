package com.kayan.github.module

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kayan.github.kotlin.R
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnPermissionDenied
import permissions.dispatcher.RuntimePermissions
import javax.inject.Inject


/**
 * 通过 navigation 实现的首页
 * Created by kayan
 * Date: 2018-11-02
 */
@RuntimePermissions
class StartNavigationActivity : AppCompatActivity(), HasSupportFragmentInjector {

    // 当 Fragment 调用 AndroidSupportInjection.inject(this)时
    // 从Activity获取一个DispatchingAndroidInjector<Fragment>，并将Fragment传递给inject(Fragment)
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main_new)

        getStorageWithPermissionCheck()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_container) as NavHostFragment
        navController = navHostFragment.navController

        // Setup the bottom navigation view with navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigationView.setupWithNavController(navController)

    }


    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun getStorage() {

    }

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun showDeniedForGetStorage() {
        Toast.makeText(this, R.string.permission_storage_denied, Toast.LENGTH_SHORT).show()
        finish()

    }

    //实现 HasSupportFragmentInjector 的接口，表示有Fragment需要注入
    override fun supportFragmentInjector() = dispatchingAndroidInjector


    @SuppressLint("NeedOnRequestPermissionsResult")
    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }
}