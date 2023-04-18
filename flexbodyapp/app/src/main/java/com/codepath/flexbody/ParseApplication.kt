package com.codepath.flexbody

import android.app.Application
import com.parse.Parse
import com.parse.ParseObject
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class ParseApplication : Application() {
    override fun onCreate() {
    super.onCreate()
    Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG)
    val builder = OkHttpClient.Builder()
    val httpLoggingInterceptor = HttpLoggingInterceptor()
//    ParseObject.registerSubclass(StreamMuscle::class.java);

    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    builder.networkInterceptors().add(httpLoggingInterceptor)
    Parse.initialize(
        Parse.Configuration.Builder(this)
            .applicationId(getString(R.string.back4app_app_id))
            .clientKey(getString(R.string.back4app_client_key))
            .server(getString(R.string.back4app_server_url))
            .build())
    val testObject = ParseObject("TestObject")
    testObject.put("foo", "bar")
    testObject.saveInBackground()

}
}