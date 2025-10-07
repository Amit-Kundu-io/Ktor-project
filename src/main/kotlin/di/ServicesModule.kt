package com.a.di

import com.a.data.services.UserServicesImpl
import com.a.domain.services.UserServices
import org.koin.dsl.module

val servicesModule  = module{
    single<UserServices> {
        UserServicesImpl(get())
    }
}