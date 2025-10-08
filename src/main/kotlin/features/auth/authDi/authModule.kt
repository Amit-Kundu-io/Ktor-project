package com.a.features.auth.authDi

import com.a.NotesApp.features.auth.repository.AuthRepo
import com.a.NotesApp.features.auth.repositoryImpl.AuthRepoImpl
import com.a.NotesApp.features.auth.service.AuthService
import com.a.features.auth.data.serviceImpl.AuthServiceImpl
import org.koin.dsl.module
import org.koin.dsl.single

val authModule = module {
    single<AuthRepo> {
        AuthRepoImpl()
    }

    single<AuthService> {
        AuthServiceImpl(get())
    }
}