package com.a.features.notes.noteDi

import com.a.features.notes.data.repoImpl.NoteImpl
import com.a.features.notes.data.serviceImpl.NoteServiceImpl
import com.a.features.notes.domain.repository.NoteRepo
import com.a.features.notes.domain.service.NoteServices
import org.koin.dsl.module


val noteModule = module {
    single<NoteRepo> {
        NoteImpl()
    }

    single<NoteServices> {
        NoteServiceImpl(get())
    }
}