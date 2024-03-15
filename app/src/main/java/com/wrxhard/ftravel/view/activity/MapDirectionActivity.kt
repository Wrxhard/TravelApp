package com.wrxhard.ftravel.view.activity

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Dash
import com.google.android.gms.maps.model.Dot
import com.google.android.gms.maps.model.Gap
import com.google.android.gms.maps.model.JointType
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PatternItem
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.wrxhard.ftravel.R
import com.wrxhard.ftravel.constant.AppConstant
import com.wrxhard.ftravel.databinding.ActivityMapDirectionBinding
import com.wrxhard.ftravel.databinding.DirectionStepBottomSheetBinding
import com.wrxhard.ftravel.model.google.DirectionLegModel
import com.wrxhard.ftravel.model.google.DirectionResponseModel
import com.wrxhard.ftravel.model.google.DirectionRouteModel
import com.wrxhard.ftravel.model.google.Location
import com.wrxhard.ftravel.premission.AppPermissions
import com.wrxhard.ftravel.util.Event
import com.wrxhard.ftravel.view.adapter.DirectionStepAdapter
import com.wrxhard.ftravel.view.common.LoadingDialog
import com.wrxhard.ftravel.view_model.activity.MapDirectionActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Arrays


@AndroidEntryPoint
class MapDirectionActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityMapDirectionBinding
    private var mGoogleMap: GoogleMap? = null
    private var appPermissions: AppPermissions? = null
    private var isLocationPermissionOk = false
    private var isTrafficEnable = false
    private var bottomSheetBehavior: BottomSheetBehavior<RelativeLayout>? = null
    private var bottomSheetLayoutBinding: DirectionStepBottomSheetBinding? = null
//    private var retrofitAPI: RetrofitAPI? = null
    private var loadingDialog: LoadingDialog? = null
    private var currentLocation: Location? = null
    private var endLat: Double? = null
    private var endLng: Double? = null
    private var placeId: String? = null
    private val currentMode = 0
    private var adapter: DirectionStepAdapter? = null
    private val mapDirectionViewModel: MapDirectionActivityViewModel by viewModels()
    private val origin: Location = Location(10.740962,106.6704584)
    private val destination: Location = Location(10.733267, 106.699663)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapDirectionBinding.inflate(layoutInflater)

        endLat = intent.getDoubleExtra("lat", 0.0)
        endLng = intent.getDoubleExtra("lng", 0.0)
        placeId = intent.getStringExtra("placeId")

//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            // Enable the Up button
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        } else {
//            // Log an error or handle the case where the ActionBar is null
//            Toast.makeText(this, "Action bar null", Toast.LENGTH_SHORT).show();
//        }
        appPermissions = AppPermissions()
        loadingDialog = LoadingDialog(this)
        //
//        retrofitAPI = RetrofitClient.getRetrofitClient().create(RetrofitAPI::class.java)
        bottomSheetLayoutBinding = binding.bottomSheet
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayoutBinding!!.root)
        bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
        adapter = DirectionStepAdapter()
        bottomSheetLayoutBinding!!.stepRecyclerView.setLayoutManager(LinearLayoutManager(this))
        bottomSheetLayoutBinding!!.stepRecyclerView.setAdapter(adapter)
        val mapFragment = (supportFragmentManager
            .findFragmentById(R.id.directionMap) as SupportMapFragment?)!!
        mapFragment.getMapAsync(this)
        binding.enableTraffic.setOnClickListener { view ->
            if (isTrafficEnable) {
                if (mGoogleMap != null) {
                    mGoogleMap!!.isTrafficEnabled = false
                    isTrafficEnable = false
                }
            } else {
                if (mGoogleMap != null) {
                    mGoogleMap!!.isTrafficEnabled = true
                    isTrafficEnable = true
                }
            }
        }

        mapDirectionViewModel.getDirection(this.origin, destination, "driving", getResources().getString(R.string.API_KEY))
//        binding.travelMode.setOnCheckedChangeListener(ChipGroup.OnCheckedChangeListener { group, checkedId ->
//            if (checkedId != -1) {
//                when (checkedId) {
//                    R.id.btnChipDriving -> getDirection("driving")
//                    R.id.btnChipWalking -> getDirection("walking")
//                    R.id.btnChipBike -> getDirection("bicycling")
//                    R.id.btnChipTrain -> getDirection("transit")
//                }
//            }
//        })

        watchStateMapDirection()
        setContentView(binding.root)
    }
    private fun watchStateMapDirection(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                mapDirectionViewModel.directionEvent.collectLatest {
                        resp ->
                    when(resp){
                        is Event.Loading -> {
                            //TODO: Implement loading behavior
                        }
                        is Event.Success -> {
                           val directionModel = resp.result;
                            if(directionModel === null ) {
                                Toast.makeText(this@MapDirectionActivity, "Direction modelnull", Toast.LENGTH_SHORT).show()
                            }else {
                                renderMap(directionModel);
                            }
                        }
                        is Event.Failure -> {
                            Toast.makeText(this@MapDirectionActivity, resp.error, Toast.LENGTH_SHORT).show()
                        }
                        else -> Unit
                    }
                }
            }
        }
    }

    private fun renderMap(directionResponseModel: DirectionResponseModel) {
        val routeModel: DirectionRouteModel =
            directionResponseModel.getDirectionRouteModels().get(0)

//                                Objects.requireNonNull(getSupportActionBar()).setTitle(routeModel.getSummary());
        val legModel: DirectionLegModel = routeModel.getLegs().get(0)
        binding.txtStartLocation.setText(legModel.getStartAddress())
        binding.txtEndLocation.setText(legModel.getEndAddress())
        bottomSheetLayoutBinding?.txtSheetTime?.setText(
            legModel.getDuration().getText()
        )
        bottomSheetLayoutBinding?.txtSheetDistance?.setText(
            legModel.getDistance().getText()
        )
        mGoogleMap!!.addMarker(
            MarkerOptions()
                .position(
                    LatLng(
                        legModel.getEndLocation().getLat(),
                        legModel.getEndLocation().getLng()
                    )
                )
                .title("End Location")
        )
        mGoogleMap!!.addMarker(
            MarkerOptions()
                .position(
                    LatLng(
                        legModel.getStartLocation().getLat(),
                        legModel.getStartLocation().getLng()
                    )
                )
                .title("Start Location")
        )
        adapter!!.setDirectionStepModels(legModel.getSteps())
        val stepList: MutableList<LatLng> = ArrayList()
        val options = PolylineOptions()
            .width(25f)
            .color(Color.BLUE)
            .geodesic(true)
            .clickable(true)
            .visible(true)
        val pattern: List<PatternItem>
        val mode = "walking"
        if (mode == "walking") {
            pattern = Arrays.asList(
                Dot(), Gap(10f)
            )
            options.jointType(JointType.ROUND)
        } else {
            pattern = Arrays.asList<PatternItem>(
                Dash(30f)
            )
        }
        options.pattern(pattern)
        for (stepModel in legModel.getSteps()) {
            val decodedLatLng = decode(stepModel.getPolyline().getPoints())
            for (latLng in decodedLatLng) {
                stepList.add(LatLng(latLng.lat, latLng.lng))
            }
        }
        options.addAll(stepList)
        val polyline = mGoogleMap!!.addPolyline(options)
        val startLocation = LatLng(
            legModel.getStartLocation().getLat(),
            legModel.getStartLocation().getLng()
        )
        val endLocation = LatLng(
            legModel.getStartLocation().getLat(),
            legModel.getStartLocation().getLng()
        )
        mGoogleMap!!.animateCamera(
            CameraUpdateFactory.newLatLngBounds(
                LatLngBounds(startLocation, endLocation),
                17
            )
        )
    }

    private fun clearUI() {
        mGoogleMap!!.clear()
        binding.txtStartLocation.setText("")
        binding.txtEndLocation.setText("")
        getSupportActionBar()?.setTitle("");
        bottomSheetLayoutBinding?.txtSheetDistance?.setText("")
        bottomSheetLayoutBinding?.txtSheetTime?.setText("")
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap
        if (appPermissions?.isLocationOk(this)!!) {
            isLocationPermissionOk = true
            setupGoogleMap()
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                AlertDialog.Builder(this)
                    .setTitle("Location Permission")
                    .setMessage("Near Me required location permission to show you near by places")
                    .setPositiveButton(
                        "Ok"
                    ) { dialog, which -> appPermissions!!.requestLocationPermission(this@MapDirectionActivity) }
                    .create().show()
            } else {
                appPermissions!!.requestLocationPermission(this@MapDirectionActivity)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == AppConstant.LOCATION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                isLocationPermissionOk = true
                setupGoogleMap()
            } else {
                isLocationPermissionOk = false
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupGoogleMap() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        mGoogleMap!!.isMyLocationEnabled = true
        mGoogleMap!!.uiSettings.isTiltGesturesEnabled = true
        mGoogleMap!!.uiSettings.isMyLocationButtonEnabled = false
        mGoogleMap!!.uiSettings.isCompassEnabled = false
        getCurrentLocation()
    }

    private fun getCurrentLocation() {
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
//                currentLocation = location
//                getDirection("driving")
            } else {
                Toast.makeText(this@MapDirectionActivity, "Location Not Found", Toast.LENGTH_SHORT)
                    .show()
            }
        }.addOnFailureListener { e -> //adn new
            Toast.makeText(this@MapDirectionActivity, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (bottomSheetBehavior!!.state == BottomSheetBehavior.STATE_EXPANDED) bottomSheetBehavior!!.setState(
            BottomSheetBehavior.STATE_COLLAPSED
        ) else super.onBackPressed()
    }

    private fun decode(points: String): List<com.google.maps.model.LatLng> {
        val len = points.length
        val path: MutableList<com.google.maps.model.LatLng> = ArrayList(len / 2)
        var index = 0
        var lat = 0
        var lng = 0
        while (index < len) {
            var result = 1
            var shift = 0
            var b: Int
            do {
                b = points[index++].code - 63 - 1
                result += b shl shift
                shift += 5
            } while (b >= 0x1f)
            lat += if (result and 1 != 0) (result shr 1).inv() else result shr 1
            result = 1
            shift = 0
            do {
                b = points[index++].code - 63 - 1
                result += b shl shift
                shift += 5
            } while (b >= 0x1f)
            lng += if (result and 1 != 0) (result shr 1).inv() else result shr 1
            path.add(com.google.maps.model.LatLng(lat * 1e-5, lng * 1e-5))
        }
        return path
    }
}