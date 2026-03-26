package co.edu.udea.compumovil.gr01_20261.lab1.photon

data class PhotonResponse(
    val features: List<Feature>
)

data class Feature(
    val properties: Properties,
    val geometry: Geometry
)

data class Properties(
    val name: String,
    val country: String?
)

data class Geometry(
    val coordinates: List<Double>
)
