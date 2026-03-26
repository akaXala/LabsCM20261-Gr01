package co.edu.udea.compumovil.gr01_20261.lab1.photon

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PhotonFetch : ViewModel() {

    val results = MutableLiveData<List<Feature>>()

    fun search(query: String, country: String? = null) {
        viewModelScope.launch {
            try {
                val fullQuery = if (!country.isNullOrBlank()) "$query, $country" else query
                val response = RetrofitInstance.api.searchCities(fullQuery)
                results.value = response.features
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}