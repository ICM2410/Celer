package com.app.ridexpasajero

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetOntrip : BottomSheetDialogFragment() {

    private lateinit var originTextView: TextView
    private lateinit var destinationTextView: TextView
    private lateinit var descriptionTextView: TextView

    // Propiedades para almacenar los datos del origen y destino
    private var originName: String? = null
    private var destinationName: String? = null
    private var originLatLng: LatLng? = null
    private var destinationLatLng: LatLng? = null

    companion object {
        fun newInstance(
            origin: String,
            destination: String,
            originLat: Double,
            originLng: Double,
            destinationLat: Double,
            destinationLng: Double,
            distanceTime: String
        ): BottomSheetOntrip {
            val fragment = BottomSheetOntrip()
            val args = Bundle().apply {
                putString("origin", origin)
                putString("destination", destination)
                putDouble("originLat", originLat)
                putDouble("originLng", originLng)
                putDouble("destinationLat", destinationLat)
                putDouble("destinationLng", destinationLng)
                putString("distanceTime", distanceTime)
            }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_onride, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.setOnClickListener {
            dismiss()
        }

        val btnPay: Button = view.findViewById(R.id.btnConfirmRequest)
        originTextView = view.findViewById(R.id.textOrigin)
        destinationTextView = view.findViewById(R.id.textDestiny)
        descriptionTextView = view.findViewById(R.id.textTimeDistance)

        // Obtener los argumentos pasados al BottomSheet
        val args = arguments
        originName = args?.getString("origin")
        destinationName = args?.getString("destination")
        val description = args?.getString("distanceTime")
        val originLat = args?.getDouble("originLat")
        val originLng = args?.getDouble("originLng")
        val destinationLat = args?.getDouble("destinationLat")
        val destinationLng = args?.getDouble("destinationLng")

        originTextView.text = originName
        destinationTextView.text = destinationName
        descriptionTextView.text = description

        // Almacenar los valores de latitud y longitud en LatLng
        originLatLng = LatLng(originLat ?: 0.0, originLng ?: 0.0)
        destinationLatLng = LatLng(destinationLat ?: 0.0, destinationLng ?: 0.0)

        btnPay.setOnClickListener {
            dismiss()
            goToSearchDriver()
        }
    }

    private fun goToSearchDriver() {
        if (originLatLng != null && destinationLatLng != null) {
            val intent = Intent(activity, ThanksConfirmrideActivity::class.java)
            intent.putExtra("origin", originName)
            intent.putExtra("destination", destinationName)
            intent.putExtra("origin_lat", originLatLng?.latitude)
            intent.putExtra("origin_lng", originLatLng?.longitude)
            intent.putExtra("destination_lat", destinationLatLng?.latitude)
            intent.putExtra("destination_lng", destinationLatLng?.longitude)
            startActivity(intent)
        } else {
            Toast.makeText(requireContext(), "Debes Seleccionar el origen y el destino", Toast.LENGTH_SHORT).show()
        }
    }
}
