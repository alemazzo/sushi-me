package io.github.alemazzo.sushime.ui.screens.restaurants

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.maps.android.SphericalUtil
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import io.github.alemazzo.sushime.BuildConfig
import io.github.alemazzo.sushime.config.BottomBars
import io.github.alemazzo.sushime.config.Routes
import io.github.alemazzo.sushime.navigation.routing.Route
import io.github.alemazzo.sushime.navigation.routing.RoutePreview
import io.github.alemazzo.sushime.navigation.screen.Screen
import io.github.alemazzo.sushime.ui.screens.restaurants.components.RestaurantsScreenContent
import io.github.alemazzo.sushime.utils.DefaultTopAppBar
import io.github.alemazzo.sushime.utils.Run
import io.github.alemazzo.sushime.utils.getViewModel
import io.github.alemazzo.sushime.utils.withIOContext
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import androidx.compose.material3.FloatingActionButton as FAB
import com.google.android.libraries.places.api.model.Place as GooglePlace

@ExperimentalMaterial3Api
object RestaurantsScreen : Screen() {

    var isMapVisible by mutableStateOf(false)
    var isQRScannerVisible by mutableStateOf(false)

    @Composable
    override fun FloatingActionButton() {
        FAB(
            onClick = { isQRScannerVisible = true },
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = contentColorFor(MaterialTheme.colorScheme.secondary)
        ) {
            Icon(
                imageVector = Icons.Filled.QrCodeScanner,
                contentDescription = "Scan"
            )
        }
    }

    @Composable
    override fun TopBar() {
        DefaultTopAppBar(title = "Restaurants") {
            IconButton(onClick = { isMapVisible = !isMapVisible },
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = contentColorFor(MaterialTheme.colorScheme.primary)
                )) {
                Icon(
                    imageVector = if (isMapVisible) Icons.Filled.List else Icons.Filled.Navigation,
                    contentDescription = "Maps"
                )
            }
        }
    }

    @Composable
    override fun BottomBar(navigator: NavHostController, currentRoute: Route) {
        BottomBars.NavigateBottomBar.Get(currentRoute, navigator)
    }

    @Composable
    override fun Content(
        navigator: NavHostController,
        paddingValues: PaddingValues,
        arguments: Bundle?,
    ) {
        when {
            isMapVisible -> ShowMap(arguments, paddingValues)
            else -> RestaurantsScreenContent(
                navigator,
                paddingValues,
                getViewModel(),
                isQRScannerVisible
            ) { isQRScannerVisible = it }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun CheckLocationPermissionState(onGranted: () -> Unit) {
    // Location permission state
    val fineLocationPermissionState = rememberPermissionState(
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )
    val coarseLocationPermissionState = rememberPermissionState(
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    when {
        fineLocationPermissionState.status is PermissionStatus.Granted || coarseLocationPermissionState.status is PermissionStatus.Granted -> {
            onGranted()
        }
        else -> {
            Run {
                fineLocationPermissionState.launchPermissionRequest()
                coarseLocationPermissionState.launchPermissionRequest()
            }
        }
    }
}

class LocationManager(private val context: Context) {

    private var fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    fun onLocationChange(onReady: (Location) -> Unit) {

        fusedLocationClient.lastLocation.addOnSuccessListener {
            Log.d("LOCATION", "SUCCESS $it")
        }.addOnFailureListener {
            Log.d("LOCATION", "FAIL $it")
        }

        fusedLocationClient.requestLocationUpdates(
            com.google.android.gms.location.LocationRequest.create(),
            object : LocationCallback() {
                override fun onLocationResult(loc: LocationResult) {
                    Log.d("LOCATION", "New Location Result: $loc")
                    loc.lastLocation?.let(onReady)
                }
            },
            Looper.getMainLooper()
        )
    }
}

data class Place(val name: String, val location: LatLng) {
    companion object {
        fun FromJsonObject(jsonObject: JSONObject): Place {
            val name = jsonObject.getString("name")
            val locationObject = jsonObject.getJSONObject("geometry").getJSONObject("location")
            val location =
                LatLng(locationObject.getDouble("lat"), locationObject.getDouble("lng"))
            return Place(name, location)
        }
    }
}

class SelectPlaceViewModel(private val _application: Application) : AndroidViewModel(_application) {

    private val placesClient by lazy { Places.createClient(_application) }
    private val autoCompleteSessionToken = AutocompleteSessionToken.newInstance()
    private var initialized = false

    private val DEFAULT_PLACE_FIELDS = listOf(
        GooglePlace.Field.ID,
        GooglePlace.Field.NAME,
        GooglePlace.Field.ADDRESS,
        // GooglePlace.Field.ADDRESS_COMPONENTS, // not supported in placesClient.findCurrentPlace
        GooglePlace.Field.TYPES,
        GooglePlace.Field.LAT_LNG,
        GooglePlace.Field.VIEWPORT,
        GooglePlace.Field.ICON_URL
    )

    private fun buildRectangleBounds(from: LatLng, distance: Double): RectangularBounds {
        val southWest = SphericalUtil.computeOffset(from, distance, 225.0)
        val northEast = SphericalUtil.computeOffset(from, distance, 45.0)
        return RectangularBounds.newInstance(southWest, northEast)
    }

    private fun fetchPlaceDetails(placeId: String, onReady: (GooglePlace) -> Unit) {
        val placeFields = listOf(GooglePlace.Field.LAT_LNG, GooglePlace.Field.NAME)
        val fetchPlaceRequest = FetchPlaceRequest.builder(placeId, placeFields).build()
        placesClient.fetchPlace(fetchPlaceRequest).addOnSuccessListener {
            onReady(it.place)
        }
    }


    @SuppressWarnings("MissingPermission")
    suspend fun findCurrentPlaces(
        location: Location,
        onPlaces: (List<Place>) -> Unit,
    ) {
        val apiKey = BuildConfig.MAPS_API_KEY
        withIOContext {
            val request = Request.Builder()
                .url("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=${location.latitude},${location.longitude}&keyword=sushi&radius=1500&type=restaurant&key=$apiKey")
                .build()

            OkHttpClient().newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("API", "FAIL")
                }

                override fun onResponse(call: Call, response: Response) {
                    val jsonObject = response.body?.string()?.let { JSONObject(it) }!!
                    val res = jsonObject.getJSONArray("results")
                    val result = mutableListOf<Place>()
                    var i = 0
                    while (!res.isNull(i)) {
                        result.add(Place.FromJsonObject(res.getJSONObject(i)))
                        i++
                    }
                    onPlaces(result)
                }
            })
        }
    }
}


@Composable
fun ShowMap(bundle: Bundle?, paddingValues: PaddingValues) {
    val context = LocalContext.current
    val locationManager = LocationManager(context)
    var location: Location? by remember {
        mutableStateOf(null)
    }
    var places: List<Place> by remember {
        mutableStateOf(listOf())
    }


    CheckLocationPermissionState {
        Log.d("LOCATION", "Start getting location")
        locationManager.onLocationChange {
            Log.d("LOCATION", location.toString())
            location = it
        }
    }

    val selectPlacesViewModel: SelectPlaceViewModel = getViewModel()

    location?.let {
        Run {
            selectPlacesViewModel.findCurrentPlaces(location!!) { _places ->
                places = _places
                _places.forEach {
                    Log.d("PLACES", "Place = $it")
                }
            }
        }
    }

    location?.let {
        val myPosition = LatLng(it.latitude, it.longitude)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(myPosition, 13f)
        }
        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = MarkerState(position = myPosition),
                title = "My Position",
                snippet = "Marker in My Position"
            )
            places.forEach { place ->
                Marker(
                    state = MarkerState(position = place.location),
                    title = place.name,
                    icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)
                )
            }
        }
    }
}

@ExperimentalMaterial3Api
@Preview
@Composable
fun RestaurantsScreenPreview() {
    RoutePreview(route = Routes.RestaurantsRoute)
}
