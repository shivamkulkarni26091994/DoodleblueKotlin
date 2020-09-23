package com.doodleblue.doodleblue.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.doodleblue.doodleblue.Adapters.DataAdapter
import com.doodleblue.doodleblue.R
import com.doodleblue.doodleblue.models.MasterData
import com.doodleblue.doodleblue.models.data
import com.doodleblue.doodleblue.retrofitcliets.API
import com.doodleblue.doodleblue.retrofitcliets.RetrofitClient
import kotlinx.android.synthetic.main.fragment_prices.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [PricesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PricesFragment : Fragment() {
    private lateinit var adapter: DataAdapter
    var data:ArrayList<data>?=null
    var masterData:MasterData?=null
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
   public var datalistinswipeView:ArrayList<data>?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    private lateinit var viewOfLayout:View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewOfLayout=  inflater!!.inflate(R.layout.fragment_prices, container, false)
        var swipeRefresh= viewOfLayout.findViewById<SwipeRefreshLayout>(R.id.swipeRefresh)
        var edtTxtSearch= viewOfLayout.findViewById<EditText>(R.id.edtTxtSearch)
        var data_recycler_view=viewOfLayout.findViewById<RecyclerView>(R.id.data_recycler_view)

        val api= RetrofitClient.retrofit.create(API::class.java)
        api.getData().enqueue(object : Callback<MasterData> {
            override fun onResponse(call: Call<MasterData>?, response: Response<MasterData>?) {
                //var toast=Toast.makeText(context,"Success",Toast.LENGTH_LONG).show()
                try {
                    masterData= response?.body()
                    data = masterData?.data as ArrayList<data>
                    adapter= DataAdapter(data as ArrayList<data>,viewOfLayout.context,this@PricesFragment)
                    data_recycler_view.apply {
                        layoutManager =
                            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        adapter = DataAdapter(data as ArrayList<data>,viewOfLayout.context,this@PricesFragment)
                        var add:Double=0.0
                            for(i in data!!){
                                add += (i.marketCapUsd.toDouble())
                        }
                        var result= String.format("%,.2f",add)
                        tv_globalCap.text= "$$result"

                    }
                }catch (ex:Exception){
                    ex.message
                }
            }
            override fun onFailure(call: Call<MasterData>?, t: Throwable?) {
                var toast=Toast.makeText(context,"Failed",Toast.LENGTH_LONG).show()

            }
        })
        // swipe to refresh view
        swipeRefresh.setOnRefreshListener {
            api.getData().enqueue(object : Callback<MasterData> {
                override fun onResponse(call: Call<MasterData>?, response: Response<MasterData>?) {
                   // var toast=Toast.makeText(context,"Success",Toast.LENGTH_LONG).show()
                    try {
                        data?.clear()
                        masterData= response?.body()
                        data = masterData?.data as ArrayList<data>
                        adapter=DataAdapter(data as ArrayList<data>,viewOfLayout.context,this@PricesFragment)
                        data_recycler_view.apply {
                            layoutManager =
                                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                            adapter = DataAdapter(data as ArrayList<data>,viewOfLayout.context,this@PricesFragment)
                        }
                        var add:Double=0.0
                        for(i in data!!){
                            add += (i.marketCapUsd.toDouble())
                        }
                        var result= String.format("%,.2f",add)
                        tv_globalCap.text= "$$result"
                        swipeRefresh.isRefreshing=false
                    }catch (ex:Exception){
                        ex.message
                        swipeRefresh.isRefreshing=false
                    }
                }
                override fun onFailure(call: Call<MasterData>?, t: Throwable?) {
                    var toast=Toast.makeText(context,"Failed",Toast.LENGTH_LONG).show()
                    swipeRefresh.isRefreshing=false
                }

            })
        }

        edtTxtSearch.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                try {
                    adapter.filter.filter(s)
                }catch (ex:Exception){
                    ex.message
                }
            }

        })
        return viewOfLayout
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PricesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            PricesFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    fun searchData(data: ArrayList<data>?) {
        try {
            adapter = DataAdapter(data as ArrayList<data>,viewOfLayout.context,this@PricesFragment)
            adapter.notifyDataSetChanged()
            data_recycler_view.layoutManager= LinearLayoutManager(viewOfLayout.context,LinearLayoutManager.VERTICAL,false)
            data_recycler_view.setAdapter(adapter)
        } catch (ex: java.lang.Exception) {
            ex.message
        }
    }
}