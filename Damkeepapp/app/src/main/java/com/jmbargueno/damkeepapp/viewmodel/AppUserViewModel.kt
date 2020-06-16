package com.jmbargueno.damkeepapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jmbargueno.damkeepapp.client.request.LoginRequest
import com.jmbargueno.damkeepapp.common.Constants
import com.jmbargueno.damkeepapp.common.SharedPreferenceModule
import com.jmbargueno.damkeepapp.model.AppUser
import com.jmbargueno.damkeepapp.repository.DamKeepAppRepository
import javax.inject.Inject

class AppUserViewModel @Inject constructor(damKeepRepository: DamKeepAppRepository) : ViewModel() {
    val repository = damKeepRepository

    fun doLogin(request: LoginRequest): MutableLiveData<AppUser> {
        return repository.login(request)
    }

    fun signup(request: LoginRequest): MutableLiveData<AppUser> {
        return repository.register(request)
    }

    fun logOut(): Boolean {
        SharedPreferenceModule().removeStringValue(Constants.SHARED_PREFERENCES_TOKEN)
        return true
    }
}