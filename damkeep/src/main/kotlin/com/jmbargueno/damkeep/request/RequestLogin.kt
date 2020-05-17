package com.jmbargueno.damkeep.request

import javax.validation.constraints.NotBlank

data class RequestLogin (
        @NotBlank val username: String,
        @NotBlank val password: String
)