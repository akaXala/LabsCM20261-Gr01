package co.edu.udea.compumovil.gr01_20261.lab1.photon

import retrofit2.http.GET
import retrofit2.http.Query

interface PhotonApi {

    @GET("api")
    suspend fun searchCities(
        @Query("q") query: String,
        @Query("limit") limit: Int = 5,
        @Query("layer") layer: String = "city"
    ): PhotonResponse
}