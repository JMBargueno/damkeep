package com.jmbargueno.damkeepapp.model

import java.util.*

data class Note(
    val id: UUID,
    val title: String,
    val content: String,
    val createdDate: String,
    val editedOn: String,
    val author: AppUser
)