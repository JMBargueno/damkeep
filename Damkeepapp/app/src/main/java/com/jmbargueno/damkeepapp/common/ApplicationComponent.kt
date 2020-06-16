package com.jmbargueno.damkeepapp.common

import com.jmbargueno.damkeepapp.*
import com.jmbargueno.damkeepapp.client.NetworkModule
import com.jmbargueno.damkeepapp.common.SharedPreferenceModule
import com.jmbargueno.damkeepapp.ui.NoteFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, SharedPreferenceModule::class])
interface ApplicationComponent {
    fun inject(login: LoginActivity)
    fun inject(signUp: SignUpActivity)
    fun inject(main: MainActivity)
    fun inject(detail: NoteDetailActivity)
    fun inject(addNote: AddNoteActivity)
    fun inject(noteListFragment: NoteFragment)
}