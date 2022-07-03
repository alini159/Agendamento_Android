package com.example.agendamento.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.ArrayList

data class User(
    @SerializedName("name") var name: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("nextConsult") var nextConsult: String? = null,
    @SerializedName("cpf") var cpf: String? = null,
    @SerializedName("cardInformation") var cardInformation : String? = null
    ) : Serializable{
        fun toNextConsult(consultData: String){
            this.nextConsult = consultData
        }
        fun toCardInformation(card: String){
            this.cardInformation = card
        }
    }