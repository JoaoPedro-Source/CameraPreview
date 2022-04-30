package com.example.galeria

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        img1.setOnClickListener{
            imageview.setImageResource()
        }
        img2.setOnClickListener{
            imageview.setImageResource()
        }
        img3.setOnClickListener{
            imageview.setImageResource()
        }
        img4.setOnClickListener{
            imageview.setImageResource()
        }
        sendImage.setOnClickListener{

        }
        fun delete{

        }
    }
}