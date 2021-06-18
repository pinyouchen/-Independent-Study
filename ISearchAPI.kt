package com.example.louis.API

import io.reactivex.Observable
import com.example.louis.Model.Person
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import java.util.*

interface ISearchAPI {
    @get:GET("person")
    val personList:Observable<List<Person>>

    @POST("search")
    @FormUrlEncoded
    fun searchPerson(@Field("search") searchQuery:String) : Observable<List<Person>>
}