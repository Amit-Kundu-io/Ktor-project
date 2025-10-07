package com.a.di

import com.a.data.repoImpl.UserRepoImpl
import com.a.domain.repo.UserRepo
import org.koin.dsl.module

val repoModule = module {
    single <UserRepo>{
        UserRepoImpl()
    }
}