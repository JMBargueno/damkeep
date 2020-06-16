package com.jmbargueno.damkeepapp.service

import com.jmbargueno.damkeepapp.client.request.CreateNoteRequest
import com.jmbargueno.damkeepapp.client.request.LoginRequest
import com.jmbargueno.damkeepapp.client.response.LoginResponse
import com.jmbargueno.damkeepapp.model.AppUser
import com.jmbargueno.damkeepapp.model.Note
import retrofit2.Call
import retrofit2.http.*

interface DamKeepService {
    @POST("/signup")
    fun signup(@Body request: LoginRequest): Call<AppUser>

    @POST("/auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("/notes/")
    fun createNote(@Body request: CreateNoteRequest): Call<Note>

    @DELETE("/notes/{id}")
    fun deleteNote(@Path("id") id: String): Call<Void>

    @PUT("/notes/{id}")
    fun editNote(@Path("id") id: String, @Body request: CreateNoteRequest): Call<Note>

    @GET("/notes/{id}")
    fun getNote(@Path("id") id: String): Call<Note>

    @GET("/notes/")
    fun getNotesByUser(): Call<List<Note>>
}