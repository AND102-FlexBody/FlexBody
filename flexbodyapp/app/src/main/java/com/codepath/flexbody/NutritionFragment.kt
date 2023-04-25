package com.codepath.flexbody

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
    lateinit var nutritionlist:MutableList<Nutrition>
    var search:String?=null
    lateinit var adapter:NutritionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun request(offset:Int=0,term: String? =null) {
        val client = AsyncHttpClient()
        val params = RequestParams()
        params["format"] = "json"
        params["limit"] = "100"
        params["offset"] = offset.toString()
        var url = "https://wger.de/api/v2/ingredientinfo"

        if (term != null) {
            if (term != this.search) {
                Log.d("changed", "array changed")

                this.search = term
                var size = nutritionlist.size
                for (i in 0 until size) {
                    nutritionlist.removeAt(0)
                }
                adapter.notifyItemRangeRemoved(0, size)

            }
            params["term"] = term
            url = "https://wger.de/api/v2/ingredient/search"
            client[url, params, object :
                JsonHttpResponseHandler()  {
                override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                    // called when response HTTP status is "200 OK"
                    this
                    if(json.jsonObject!=null){
                        var resultList=json.jsonObject["suggestions"]
                        Log.d("list",resultList.toString())


                        val nutritionArray= object : TypeToken<MutableList<Nutrition>>() {}.type
                        val gsonBuilder = GsonBuilder()
                        val gson: Gson = gsonBuilder.create()
                        val new_nutritionItems: List<Nutrition> = gson.fromJson( resultList.toString(), nutritionArray)
                        for (item in new_nutritionItems){
                            if(item.data?.image!=null){
                                item.data?.image ="https://wger.de/${item.data?.image}"
                            }

                        }

                        for (item in new_nutritionItems){
                            Log.d("item insert",item.toString())
                            Log.d("arraysize",nutritionlist.size.toString())
                            nutritionlist.add(item)
                        }
                        adapter.notifyItemRangeInserted (nutritionlist.size-new_nutritionItems.size,new_nutritionItems.size)







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
                    val empty:List<Nutrition> =emptyList()



                }
            }]



        }

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rvNutrition = view?.findViewById<View>(R.id.nutritionresults) as RecyclerView
        this.nutritionlist= arrayListOf()
        adapter =NutritionAdapter (this.context,this.nutritionlist)
        rvNutrition .adapter = adapter
//                     Set layout manager to position the items
        rvNutrition.layoutManager = LinearLayoutManager(context)

        request(0,"apple")
        val searchView: SearchView = view.findViewById(R.id.nutritionsearch)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                request(0,query)
                searchView.clearFocus();
                return true


            }






    }) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nutrition, container, false)
    }

    companion object {
        fun newInstance(): NutritionFragment {
            return NutritionFragment()
        }
    }
}