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


class AgePredict : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var buttonPredict: Button
    private lateinit var textViewResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_agepredict)

        editTextName = findViewById(R.id.editTextName)
        buttonPredict = findViewById(R.id.buttonPredict)
        textViewResult = findViewById(R.id.textViewResult)

        buttonPredict.setOnClickListener {
            val name = editTextName.text.toString()
            if (name.isNotEmpty()) {
                predictAge(name)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun predictAge(name: String) {
        lifecycleScope.launch {
            try {
                val prediction = withContext(Dispatchers.IO) {
                    RetrofitClientToAge.instance.getAgePrediction(name)
                }

                textViewResult.text = "Género: ${prediction.age}"

            } catch (e: Exception) {
                Log.e("GenderPredict", "Error al obtener la predicción", e)
                textViewResult.text = "Error: ${e.message}"
            }
        }
    }
}

interface AgeAPI {
    @GET("/")
    suspend fun getAgePrediction(@Query("name") name: String): AgeResponse
}

data class AgeResponse(
    val count: Int?,
    val name: String?,
    val age: Int?,
)

object RetrofitClientToAge {
    private const val BASE_URL = "https://api.agify.io/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(AgeAPI::class.java)

    val instance: AgeAPI by lazy { retrofit }
}