package com.codepath.flexbody.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import okhttp3.Headers


class NutritionFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    fun request(offset:Int=0,term: String? =null){
        val client = AsyncHttpClient()
        val params = RequestParams()
        params["format"] = "json"
        params["limit"] = "500"
        params["offset"] = offset.toString()
        if(term!=null){
            params["term"] =term
        }
        client["https://wger.de/api/v2/ingredientinfo", params, object :
            JsonHttpResponseHandler()  {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                // called when response HTTP status is "200 OK"
                if(json.jsonObject!=null){
                    Log.d("test",json.jsonObject["results"].toString())
                    val nutritionArray= object : TypeToken<List<Nutrition>>() {}.type
                    val gsonBuilder = GsonBuilder()
                    val gson: Gson = gsonBuilder.create()
                    val nutritionList: List<Nutrition> = gson.fromJson(json.jsonObject["results"].toString(), nutritionArray)


                    val rvNutrition = view?.findViewById<View>(R.id.nutritionresults) as RecyclerView
                    val adapter =NutritionAdapter (nutritionList)
                rvNutrition .adapter = adapter
//                     Set layout manager to position the items
                    rvNutrition.layoutManager = LinearLayoutManager(context)



                }


            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                t: Throwable?
            ) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.d("fail",errorResponse)


            }
        }]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        request(0)
        val searchView: SearchView = view.findViewById(R.id.nutritionsearch)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                request(0,query)
                return false


            }






    }) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.title = "Nutrition Search"

        return inflater.inflate(R.layout.fragment_nutrition, container, false)
    }

    companion object {
        fun newInstance(): NutritionFragment {
            return NutritionFragment()
        }
    }
}