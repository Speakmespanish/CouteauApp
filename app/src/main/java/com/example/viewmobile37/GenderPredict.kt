package com.example.viewmobile37

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class GenderPredict : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var buttonPredict: Button
    private lateinit var textViewResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_predictgender)

        editTextName = findViewById(R.id.editTextName)
        buttonPredict = findViewById(R.id.buttonPredict)
        textViewResult = findViewById(R.id.textViewResult)

        buttonPredict.setOnClickListener {
            val name = editTextName.text.toString()
            if (name.isNotEmpty()) {
                predictGender(name)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun predictGender(name: String) {
        lifecycleScope.launch {
            try {
                val prediction = withContext(Dispatchers.IO) {
                    RetrofitClient.instance.getGenderPrediction(name)
                }
                if (prediction.gender == "male") {
                    textViewResult.text = "Genero: Hombre"
                    textViewResult.text = "Género: ${prediction.gender}"
                }
                else {
                    textViewResult.text = "Genero: Mujer"
                    textViewResult.text = "Género: ${prediction.gender}"
                }

            } catch (e: Exception) {
                Log.e("GenderPredict", "Error al obtener la predicción", e)
                textViewResult.text = "Error: ${e.message}"
            }
        }
    }
}

interface GenderAPI {
    @GET("/")
    suspend fun getGenderPrediction(@Query("name") name: String): GenderResponse
}

data class GenderResponse(
    val name: String,
    val gender: String?,
    val probability: Double?,
    val count: Int
)

object RetrofitClient {
    private const val BASE_URL = "https://api.genderize.io/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(GenderAPI::class.java)

    val instance: GenderAPI by lazy { retrofit }
}