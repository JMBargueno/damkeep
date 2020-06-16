package com.jmbargueno.damkeepapp.repository

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.jmbargueno.damkeepapp.client.request.CreateNoteRequest
import com.jmbargueno.damkeepapp.client.request.LoginRequest
import com.jmbargueno.damkeepapp.client.response.LoginResponse
import com.jmbargueno.damkeepapp.common.Constants
import com.jmbargueno.damkeepapp.common.MyApp
import com.jmbargueno.damkeepapp.common.SharedPreferenceModule
import com.jmbargueno.damkeepapp.model.AppUser
import com.jmbargueno.damkeepapp.model.Note
import com.jmbargueno.damkeepapp.service.DamKeepService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class DamKeepAppRepository @Inject constructor(var damKeepService: DamKeepService) {
    var appUser: MutableLiveData<AppUser> = MutableLiveData()
    var newAppUser: MutableLiveData<AppUser> = MutableLiveData()
    var notes: MutableLiveData<List<Note>> = MutableLiveData()

    var note: MutableLiveData<Note> = MutableLiveData()


    fun login(request: LoginRequest): MutableLiveData<AppUser> {
        val call: Call<LoginResponse>? = damKeepService.login(request)
        call?.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    appUser.value = response.body()?.user!!
                    SharedPreferenceModule().setStringValue(
                        Constants.SHARED_PREFERENCES_TOKEN,
                        response.body()!!.token
                    )


                } else {
                    appUser.postValue(null)
                    SharedPreferenceModule().removeStringValue(Constants.SHARED_PREFERENCES_TOKEN)
                    Toast.makeText(
                        MyApp.instance,
                        "El usuario o contraseña no es correcto",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(MyApp.instance, t.message, Toast.LENGTH_SHORT).show()

            }
        })
        return appUser
    }

    fun register(request: LoginRequest): MutableLiveData<AppUser> {
        val call: Call<AppUser>? = damKeepService.signup(request)
        call?.enqueue(object : Callback<AppUser> {
            override fun onResponse(call: Call<AppUser>, response: Response<AppUser>) {
                if (response.isSuccessful)
                    newAppUser.value = response.body()
            }

            override fun onFailure(call: Call<AppUser>, t: Throwable) {
                Toast.makeText(MyApp.instance, t.message, Toast.LENGTH_SHORT).show()
            }
        })
        return newAppUser
    }

    fun getAllNotes(): MutableLiveData<List<Note>> {
        val call: Call<List<Note>>? = damKeepService.getNotesByUser()
        Log.d("NOTE", "ALL NOTE REPOSITORY")
        call?.enqueue(object : Callback<List<Note>> {

            override fun onResponse(call: Call<List<Note>>, response: Response<List<Note>>) {
                if (response.isSuccessful) {
                    notes.value = response.body()
                    Log.d("NOTE", "ALL NOTE REPOSITORY SUCCESSFUL")
                }

            }

            override fun onFailure(call: Call<List<Note>>, t: Throwable) {
                Toast.makeText(MyApp.instance, t.message, Toast.LENGTH_SHORT).show()
                Log.d("NOTE", "ALL NOTE REPOSITORY NOT SUCCESSFUL")
            }
        })
        return notes
    }

    fun getNote(id: String): MutableLiveData<Note> {
        val call: Call<Note>? = damKeepService.getNote(id)

        call?.enqueue(object : Callback<Note> {
            override fun onResponse(call: Call<Note>, response: Response<Note>) {
                if (response.isSuccessful) note.value = response.body()
            }

            override fun onFailure(call: Call<Note>, t: Throwable) {
                Toast.makeText(MyApp.instance, t.message, Toast.LENGTH_SHORT).show()
            }
        })
        return note
    }

    fun removeNote(id: String) {
        val call: Call<Void>? = damKeepService.deleteNote(id)
        call?.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful)
                    Toast.makeText(
                        MyApp.instance,
                        "Se ha eliminado correctamente la nota",
                        Toast.LENGTH_SHORT
                    ).show()
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(MyApp.instance, t.message, Toast.LENGTH_SHORT).show()
            }
        })

    }

    fun modifyNote(id: String, request: CreateNoteRequest): MutableLiveData<Note> {
        val call: Call<Note>? = damKeepService.editNote(id, request)

        call?.enqueue(object : Callback<Note> {
            override fun onResponse(call: Call<Note>, response: Response<Note>) {
                if (response.isSuccessful) note.value = response.body()
            }

            override fun onFailure(call: Call<Note>, t: Throwable) {
                Toast.makeText(MyApp.instance, t.message, Toast.LENGTH_SHORT).show()
            }
        })
        return note
    }

    fun newNote(request: CreateNoteRequest): MutableLiveData<Note> {
        val call: Call<Note>? = damKeepService.createNote(request)

        call?.enqueue(object : Callback<Note> {
            override fun onResponse(call: Call<Note>, response: Response<Note>) {
                if (response.isSuccessful) note.value = response.body()
            }

            override fun onFailure(call: Call<Note>, t: Throwable) {
                Toast.makeText(MyApp.instance, t.message, Toast.LENGTH_SHORT).show()
            }
        })
        return note
    }
}