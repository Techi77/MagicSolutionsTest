package com.test.di

import com.home.HomeViewModel
import com.mvi.main_mvi.MainViewModel
import com.permissions.PermissionsViewModel
import com.social_list.SocialListViewModel
import com.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val featureModule = module {
    singleOf(::MainViewModel)
    viewModelOf(::SplashViewModel)
    viewModelOf(::PermissionsViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::SocialListViewModel)
}