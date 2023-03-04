package com.example.kotlin_cat_restful_updated

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.example.kotlin_cat_restful_updated.databinding.ActivityMainBinding
import com.squareup.picasso.Picasso
import org.json.JSONArray
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AndroidNetworking.initialize(getApplicationContext());
    }
    fun btnCatClciked(view: View){
        AndroidNetworking.get("http://thecatapi.com/api/images/get?format=json&size=med&results_per_page=1")
            .build()
            .getAsJSONArray(object:JSONArrayRequestListener{
                override fun onResponse(response: JSONArray) {
                    var id:String; var url:String=""; var source:String
                    // Text will show success if Response is success
                    for (i in 0 .. response.length()-1) {
                        val jsonobject: JSONObject = response.getJSONObject(i)
                        id = jsonobject.getString("id")
                        url = jsonobject.getString("url")
                        source = jsonobject.getString("source_url")
                        Log.d("Iyad","The JSON data is"+ id+" "+url+" "+source)
                    }
                    //get the image using Picasso library and place it into the imageView (catImage)
                    Picasso.get()
                        .load(url)
                        .into(binding.imageView)
                    //processJson(response.toString())
                }
                override fun onError(anError: ANError) {
                    // Text will show failure if Response is failure
                    Log.d("Iyad","response failed")
                }
            })
    }
}