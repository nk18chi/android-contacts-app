import com.derrick.park.assignment3_contacts.network.ContactsListProperties
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory

import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://randomuser.me/api/"

private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BASE_URL)
        .build()

interface ContactsApiService {
    @GET("?nat=ca")
    fun getProperties(@Query("results") num: Int): Deferred<ContactsListProperties>
}

object ContactsApi {
    val retrofitService: ContactsApiService by lazy {
        retrofit.create(ContactsApiService::class.java)
    }
}