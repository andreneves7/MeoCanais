package com.example.meocanais

import com.google.gson.annotations.SerializedName

class ProgramaList(
    @SerializedName("value")
                   val programaList : List<Programas> ) {
}