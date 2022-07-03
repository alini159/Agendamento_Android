package com.example.agendamento.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CardInformation(
    @SerializedName("title") var title: String? = null,
    @SerializedName("details") var details: String? =null
): Serializable
