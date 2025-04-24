package br.com.rodrigoamora.transitorio.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import br.com.rodrigoamora.transitorio.R
import br.com.rodrigoamora.transitorio.databinding.FragmentOnibusBinding
import br.com.rodrigoamora.transitorio.extension.hide
import br.com.rodrigoamora.transitorio.extension.show
import br.com.rodrigoamora.transitorio.model.Onibus
import br.com.rodrigoamora.transitorio.ui.activity.MainActivity
import br.com.rodrigoamora.transitorio.ui.viewmodel.OnibusViewModel
import br.com.rodrigoamora.transitorio.util.GPSUtil
import br.com.rodrigoamora.transitorio.util.NetworkUtil
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import java.util.Timer
import kotlin.concurrent.schedule

class OnibusFragment: Fragment(), LocationListener, OnMapReadyCallback {

    private var _binding: FragmentOnibusBinding? = null
    private val binding get() = _binding!!

    private lateinit var googleMap: GoogleMap
    private lateinit var progressBar: ProgressBar
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var location: Location

    private var locationManager: LocationManager? = null

    private val viewModel: OnibusViewModel by inject()

    private lateinit var myMarker: MarkerOptions

    private val mainActivity: MainActivity by lazy {
        activity as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this._binding = FragmentOnibusBinding.inflate(inflater, container, false)
        val root: View = this.binding.root

        this.progressBar = this.binding.progressBar

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        this.initView()
        this.getLocation(this.mainActivity)
        this.progressBar.show()
        this.buscarOnibus()
    }

    override fun onDestroy() {
        super.onDestroy()
        this.googleMap.clear()
    }

    private fun initView() {
        this.mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        this.mapFragment!!.getMapAsync(this)
        this.mapFragment!!.setMenuVisibility(false)
        this.mapFragment!!.setHasOptionsMenu(false)
    }

    override fun onLocationChanged(location: Location) {}

    @SuppressLint("MissingPermission")
    override fun onMapReady(map: GoogleMap) {
        val myLocation = context?.let { getLocation(it) }
        val latLng = if (myLocation != null) {
            LatLng(myLocation?.latitude!!.toDouble(), myLocation?.longitude!!.toDouble())
        } else {
            LatLng(0.0, 0.0)
        }

        if (map != null) {
            this.googleMap = map
            val mapStyleOptions = MapStyleOptions.loadRawResourceStyle(
                this.mainActivity,
                R.raw.map_style)
            this.googleMap.setMapStyle(mapStyleOptions)
        }

        if (GPSUtil.gpsIsEnable(mainActivity)) {
            val update = CameraUpdateFactory.newLatLngZoom(latLng, 15f)
            map?.moveCamera(update)
            this.myMarker = MarkerOptions()
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                            .title(getString(R.string.you_are_here))
                            .position(latLng)

            map?.addMarker(this.myMarker)

            map?.isMyLocationEnabled = false
            map?.mapType = GoogleMap.MAP_TYPE_NORMAL

            this.centerMap(update)
        }
    }

    private fun agendarProximaBusca() {
        this.limparMapa()
        Timer().schedule(10000) {
            buscarOnibus()
        }
    }

    private fun limparMapa() {
        this.googleMap.clear()
    }

    private fun buscarOnibus() {
        if (NetworkUtil.checkConnection(this.mainActivity)) {
            lifecycleScope.launch {
                withContext(Dispatchers.Main) {
                    viewModel.buscarOnibus()
                        .observe(mainActivity,
                            Observer { resource ->
                                limparMapa()
                                resource.result?.let { listaOnibus ->
                                    val onibusProximos = verificarOnibusProximos(listaOnibus)
                                    populateMap(onibusProximos)
                                }
                                agendarProximaBusca()
                            }
                        )
                }
            }
        } else {
            this.mainActivity.showToast(getString(R.string.error_no_internet))
        }
    }

    private fun createLocationWhenGetLastKnownLocationReturnsNull(): Location {
        this.location = Location(LocationManager.NETWORK_PROVIDER)
        this.location.latitude = -22.9673061
        this.location.longitude = -43.181529
        return this.location
    }

    @SuppressLint("MissingPermission")
    private fun centerMap(update: CameraUpdate) {
        this.googleMap.moveCamera(update)
        this.googleMap.isMyLocationEnabled = false
        this.googleMap.isBuildingsEnabled = false
        this.googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
    }

    @SuppressLint("MissingPermission")
    fun getLocation(context: Context): Location? {
        if (GPSUtil.gpsIsEnable(context)) {
            this.locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            this.locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0F, this)

            this.location = if (this.locationManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER) != null) {
                this.locationManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER)!!
            } else {
                this.locationManager?.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)!!
            }

            return this.location
        }

        this.mainActivity.showToast(getString(R.string.error_gps_disabled))

        return this.createLocationWhenGetLastKnownLocationReturnsNull()
    }

    fun populateMap(listaOnibus: List<Onibus>) {
        this.progressBar.hide()
        this.googleMap.addMarker(this.myMarker)

        if (listaOnibus.isNotEmpty()) {
            for (onibus in listaOnibus) {
                val localizacaoOnibus = this.instanciarNovaLatLng(onibus)

                this.googleMap.addMarker(
                    MarkerOptions()
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                        .title(onibus.ordem+" - "+onibus.linha)
                        .position(localizacaoOnibus)
                )

//                    val infoWindowCustom: OnibusInfoWindowCustom = OnibusInfoWindowCustom(this.mainActivity, onibus)
//                    this.googleMap.setInfoWindowAdapter(infoWindowCustom)


            }

            val latLng = this.instanciarNovaLatLng(listaOnibus.get(0))
            val update = CameraUpdateFactory.newLatLngZoom(latLng, 15f)
            this.centerMap(update)
            this.mapFragment.getMapAsync(this)

            this.agendarProximaBusca()
        }
    }

    private fun verificarOnibusProximos(listaOnibus: List<Onibus>): List<Onibus> {
        val onibusProximos = mutableListOf<Onibus>()
        for (onibus in listaOnibus) {
            val localizacaoOnibus = this.instanciarNovaLocalizacao(onibus)
            val distance = localizacaoOnibus.distanceTo(location) / 1000

            if (distance <= 2000) {
                onibusProximos.add(onibus)
            }
        }
        return onibusProximos.toList()
    }

    private fun instanciarNovaLatLng(onibus: Onibus): LatLng {
        val latitude = onibus.latitude.replace(",", ".")
        val longitude = onibus.longitude.replace(",", ".")

        return LatLng(latitude.toDouble(), longitude.toDouble())
    }

    private fun instanciarNovaLocalizacao(onibus: Onibus): Location {
        val latitude = onibus.latitude.replace(",", ".")
        val longitude = onibus.longitude.replace(",", ".")

        val localizacaoOnibus = Location(onibus.ordem)
        localizacaoOnibus.latitude = latitude.toDouble()
        localizacaoOnibus.longitude = longitude.toDouble()

        return localizacaoOnibus
    }
}
