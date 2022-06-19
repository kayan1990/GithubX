package com.kayan.github.di.annotation

import javax.inject.Qualifier



/**
 * Created by kayan
 * Date: 2018-11-02
 */

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class FragmentQualifier (val value: String = "")
