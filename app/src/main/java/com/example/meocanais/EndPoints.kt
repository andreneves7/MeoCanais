package com.example.meocanais


import retrofit2.Call
import retrofit2.http.*


interface EndPoints {

//    @GET("/checkToken")
//    fun checkToken(
//        @Header("Cookie" ) token: String
//    ): Call<ResponseBody>


    @GET("/catalog/v9/Channels?")
    fun getChannels(@Query("format" ) format: String,
                    @Query("UserAgent" ) UserAgent: String,
                    @Query("filter" ) filter: String,
                    @Query("orderby" ) orderby: String,
                    @Query("inlinecount" ) inlinecout: String,
                    @Query("skip" ) skip: Int): Call<CanaisList>

    @GET("/Program/v9/Programs/NowAndNextLiveChannelPrograms?")
    fun getProg(@Query("\$filter" ) filter: String, @Query("UserAgent" ) UserAgent: String,
                @Query("orderby" ) orderby: String
             ): Call<ProgramaList>


//    @GET("/api/item/{id}")
//    fun getItem(@Path("id") id: String): Call<List<Canais>>



}