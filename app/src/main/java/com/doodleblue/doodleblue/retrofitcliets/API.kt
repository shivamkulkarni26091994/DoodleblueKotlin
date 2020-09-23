package com.doodleblue.doodleblue.retrofitcliets

import com.doodleblue.doodleblue.models.MasterData
import retrofit2.Call
import retrofit2.http.GET


interface API {
    @GET("v2/assets")
    fun getData() : Call<MasterData>
}