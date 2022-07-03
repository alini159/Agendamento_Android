package com.example.agendamento.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserEvent(
    @SerializedName("details") var details: String? = null,
    @SerializedName("patient") var patient: String? = null,
    @SerializedName("name") var title: String? = null,
    @SerializedName("start") var start: String? = null
) : Serializable