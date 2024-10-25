package com.example.viewmobile37

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn_genderpredict: Button = findViewById(R.id.btn_genderpredict)
        val btn_agepredict: Button = findViewById(R.id.btn_agepredict)
        val btn_universitiescountry: Button = findViewById(R.id.btn_universitiescountry)
        val btn_currentweather: Button = findViewById(R.id.btn_currentweather)
        val btn_about: Button = findViewById(R.id.btn_about)



        btn_genderpredict.setOnClickListener {
            startActivity(Intent(this, GenderPredict::class.java))
        }
        btn_agepredict.setOnClickListener {
            startActivity(Intent(this, AgePredict::class.java))
        }
        btn_universitiescountry.setOnClickListener {
            startActivity(Intent(this, UniversitiesOneCountry::class.java))
        }
        btn_currentweather.setOnClickListener {
            startActivity(Intent(this, CurrentWeather::class.java))
        }
        btn_about.setOnClickListener {
            startActivity(Intent(this, About::class.java))
        }
    }
}
