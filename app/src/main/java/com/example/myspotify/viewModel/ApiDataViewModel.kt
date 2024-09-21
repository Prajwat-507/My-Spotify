package com.example.myspotify.viewModel

import androidx.lifecycle.ViewModel
import com.example.myspotify.Constants
import com.example.myspotify.Utils
import com.example.myspotify.adapter.AdapterMusic
import com.example.myspotify.models.Data
import com.example.myspotify.models.MyData
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiDataViewModel: ViewModel() {
    val _successCallMusic = MutableStateFlow(false)
    val successCallMusic = _successCallMusic

    val _successCallEpisode = MutableStateFlow(false)
    val successCallEpisode = _successCallEpisode

    val _successCallPodcast = MutableStateFlow(false)
    val successCallPodcast = _successCallPodcast

//    val _successCallMusic = MutableStateFlow(false)
//    val successCallMusic = _successCallMusic

    var musicdataList = ArrayList<List<Data>>()
    var podcastdataList = ArrayList<List<Data>>()
    var episodesdataList = ArrayList<List<Data>>()


     fun getMusicApiData(search: String): ArrayList<List<Data>> {

        val retroFitInstance = Utils.makeApiCall("https://deezerdevs-deezer.p.rapidapi.com/")

         val retroData = retroFitInstance.getData(search)

         retroData.enqueue(object : Callback<MyData?> {
             override fun onResponse(p0: Call<MyData?>, p1: Response<MyData?>) {
                 if(p1.body()!=null) {

                     musicdataList.add(p1.body()!!.data)

                     _successCallMusic.value = true
                 }

                 Utils.Log("TAG : On response", "Response successful" + p1.message())
             }

             override fun onFailure(p0: Call<MyData?>, p1: Throwable) {
                 Utils.Log("TAG : On response", "Response unsuccessful" + p1.message)
             }
         })

         return musicdataList
    }

    fun getEpisodeApiData(search: String): ArrayList<List<Data>> {

        val retroFitInstance = Utils.makeApiCall("https://deezerdevs-deezer.p.rapidapi.com/")

        val retroData = retroFitInstance.getData(search)

        retroData.enqueue(object : Callback<MyData?> {
            override fun onResponse(p0: Call<MyData?>, p1: Response<MyData?>) {
                if(p1.body()!=null) {

                    episodesdataList.add(p1.body()!!.data)

                    successCallEpisode.value = true
                }

                Utils.Log("TAG : On response", "Response successful" + p1.message())
            }

            override fun onFailure(p0: Call<MyData?>, p1: Throwable) {
                Utils.Log("TAG : On response", "Response unsuccessful" + p1.message)
            }
        })
        return episodesdataList
    }


    fun getPodcastApiData(search: String): ArrayList<List<Data>> {

        val retroFitInstance = Utils.makeApiCall("https://deezerdevs-deezer.p.rapidapi.com/")

        val retroData = retroFitInstance.getData(search)

        retroData.enqueue(object : Callback<MyData?> {
            override fun onResponse(p0: Call<MyData?>, p1: Response<MyData?>) {
                if(p1.body()!=null) {

                    podcastdataList.add(p1.body()!!.data)

                    successCallPodcast.value = true
                }

                Utils.Log("TAG : On response", "Response successful" + p1.message())
            }

            override fun onFailure(p0: Call<MyData?>, p1: Throwable) {
                Utils.Log("TAG : On response", "Response unsuccessful" + p1.message)
            }
        })
        return podcastdataList
    }
}