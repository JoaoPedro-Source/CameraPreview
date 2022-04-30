package com.example.camerafiscal

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.example.camerafiscal.databinding.ActivityCameraPreviewBinding
import com.example.camerafiscal.databinding.ActivityGaleriaBinding
import com.google.common.util.concurrent.ListenableFuture
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class CameraPreview : AppCompatActivity() {
    private lateinit var binding  : ActivityCameraPreviewBinding
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private lateinit var cameraSelector: CameraSelector
    private var ImageCapture: ImageCapture?  = null
    private lateinit var imgCaptureExecutor: ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
        imgCaptureExecutor = Executors.newSingleThreadExecutor()
        startCamera()
        binding.tirarfoto.setOnClickListener{
            takePicture()
        }

    }


    private fun startCamera(){
        cameraProviderFuture.addListener({

            ImageCapture = androidx.camera.core.ImageCapture.Builder().build()
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(binding.cameraPreview.surfaceProvider)
            }
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, ImageCapture)
            }catch (e: Exception){
                Log.e("CameraPreview","A Câmera não está funcionando")
            }
        }, ContextCompat.getMainExecutor(this))
    }
    private fun takePicture(){
        ImageCapture?.let {
            val fileName = "Picture_JPEG_${System.currentTimeMillis()}"
            val file = File(externalMediaDirs[0], fileName)
            val outputFileOptions = androidx.camera.core.ImageCapture.OutputFileOptions.Builder(file).build()
            it.takePicture(
                outputFileOptions,
                imgCaptureExecutor,
                object : ImageCapture.OnImageSavedCallback{
                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        Log.i("CameraPreview", "A imagem foi salva no diretório: ${file.toURI()}")
                    }

                    override fun onError(exception: ImageCaptureException) {
                        Toast.makeText(binding.root.context, "Erro ao salvar a foto", Toast.LENGTH_LONG)
                        Log.e("CameraPreview", "Excessão ao gravar arquivo da foto: $exception")
                    }
                }
            )
        }
    }
    private fun blinkPreview(){
        binding.root.postDelayed({
            binding.root.foreground = ColorDrawable(Color.WHITE)
            binding.root.postDelayed({
                binding.root.foreground = null
            }, 58)
        }, 100)
    }
    private fun abrirGaleria(){
        val Galeria = Intent(this, ActivityGaleriaBinding::class.java)
        startActivity(ActivityGaleriaBinding)
    }
}