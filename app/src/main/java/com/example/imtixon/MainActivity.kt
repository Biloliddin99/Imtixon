package com.example.imtixon

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import com.example.imtixon.adapters.RvAdapter
import com.example.imtixon.databinding.ActivityMainBinding
import com.example.imtixon.db.MyRoomDatabase
import com.example.imtixon.models.Users
import com.example.imtixon.retrofit.NetworkHelper
import com.example.projectend.retrofit.ApiClient
import com.example.projectend.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var apiService: ApiService
    private lateinit var rvAdapter: RvAdapter
    private lateinit var list: ArrayList<Users>
    private lateinit var networkHelper: NetworkHelper
    private lateinit var myRoomDatabase: MyRoomDatabase

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        networkHelper = NetworkHelper(this)
        myRoomDatabase = MyRoomDatabase.newInstance(this)
        list = ArrayList()

        if (networkHelper.isNetworkConnected()){
            byNetwork()
            Toast.makeText(this, "Internet is available", Toast.LENGTH_SHORT).show()
        }else{
            rvAdapter = RvAdapter(myRoomDatabase.usersDao().getAllUsers() as ArrayList<Users>)
            binding.myRv.adapter = rvAdapter
            rvAdapter.notifyDataSetChanged()
/*
            binding.searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(p0: String?): Boolean {

                    return true
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    val searchQuery = p0?:""
                    val list = myRoomDatabase.usersDao().searchQuery(searchQuery)
                    rvAdapter.list.clear()
                    rvAdapter.list.addAll(list)
                    rvAdapter.notifyDataSetChanged()
                    return true
                }
            })

 */
        }

        binding.searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                val searchQuery = p0?:""
                if (networkHelper.isNetworkConnected()) {
                    // If online, search through fetched data from API
                    val filteredList = list.filter { user -> user.title.contains(searchQuery, ignoreCase = true) }
                    rvAdapter.list.clear()
                    rvAdapter.list.addAll(filteredList)
                } else {
                    // If offline, search through saved data in local database
                    val list = myRoomDatabase.usersDao().searchQuery(searchQuery)
                    rvAdapter.list.clear()
                    rvAdapter.list.addAll(list)
                }
                rvAdapter.notifyDataSetChanged()
                return true
            }
        })

    }

    private fun byNetwork(){
        apiService = ApiClient.getRetrofitService(this)
        apiService.getData().enqueue(object : Callback<List<Users>> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<List<Users>>, response: Response<List<Users>>) {
                if (response.isSuccessful && response.body() !=null){
                    val getData = response.body()
                    list.addAll(getData!!)
                    rvAdapter = RvAdapter(list)
                    binding.myRv.adapter = rvAdapter
                    if (myRoomDatabase.usersDao().getAllUsers() == null){
                    myRoomDatabase.usersDao().addUsers(list)
                    }
                    rvAdapter.notifyDataSetChanged()

                }
            }

            override fun onFailure(call: Call<List<Users>>, t: Throwable) {
                Log.d("@getData", "onFailure: ${t.message}")
            }
        })

        // Add search functionality to search view
    }

//    override fun onStop() {
//        super.onStop()
//        myRoomDatabase.usersDao().addUsers(list)
//    }
}