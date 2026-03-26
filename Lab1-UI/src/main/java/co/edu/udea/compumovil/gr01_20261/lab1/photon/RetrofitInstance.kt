package co.edu.udea.compumovil.gr01_20261.lab1.photon

object RetrofitInstance {

    val api: PhotonApi by lazy {
        retrofit2.Retrofit.Builder()
            .baseUrl("https://photon.komoot.io/")
            .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
            .build()
            .create(PhotonApi::class.java)
    }
}