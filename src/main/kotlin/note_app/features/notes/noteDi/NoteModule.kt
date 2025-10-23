package com.a.note_app.features.notes.noteDi


import com.a.note_app.features.notes.data.repoImpl.NoteImpl
import com.a.note_app.features.notes.data.serviceImpl.NoteServiceImpl
import com.a.note_app.features.notes.domain.repository.NoteRepo
import com.a.note_app.features.notes.domain.service.NoteServices
import org.koin.dsl.module


val noteModule = module {
    single<NoteRepo> {
        NoteImpl()
    }

    single<NoteServices> {
        NoteServiceImpl(get())
    }
}