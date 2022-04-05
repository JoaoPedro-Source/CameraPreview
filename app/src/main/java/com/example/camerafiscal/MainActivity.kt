package com.example.camerafiscal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.example.camerafiscal.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding  : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.AbrirCamera.setOnClickListener {
            cameraProviderResult.launch(android.Manifest.permission.CAMERA)
        }
    }
    private fun abrircamera(){
        val intentCamperapreview = Intent(this, CameraPreview::class.java)
        startActivity(intentCamperapreview)
    }

    private val cameraProviderResult = registerForActivityResult(ActivityResultContracts.RequestPermission()){
        if(it){
            abrircamera()
        }
        else{
            Snackbar.make(binding.root, "Não há permissão para acessar a câmera",Snackbar.LENGTH_INDEFINITE).show()
        }
    }

}