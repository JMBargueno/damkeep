package com.jmbargueno.damkeep.model

import com.fasterxml.jackson.annotation.JsonBackReference
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
@EntityListeners(AuditingEntityListener::class)
data class Note(
        var title: String,
        var content: String,
        @CreatedDate var creationDate: LocalDateTime? = null,
        @LastModifiedDate var lastModified: LocalDateTime? = null,
        @JsonBackReference @ManyToOne var lastModifier: AppUser? = null,
        @JsonBackReference @ManyToOne var author: AppUser? = null,
        @Id @GeneratedValue val id: UUID? = null
)