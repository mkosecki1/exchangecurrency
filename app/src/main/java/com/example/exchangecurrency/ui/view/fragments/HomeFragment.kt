package com.example.exchangecurrency.ui.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exchangecurrency.models.AllCurrency
import com.example.exchangecurrency.ui.view.adapters.HomeRecyclerViewAdapter
import com.example.exchangecurrency.view_model.HomeViewModel
import com.example.exchangecurrency.view_model.HomeViewModelFactory
import kotlinx.android.synthetic.main.fragment_home.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import org.kodein.di.generic.instance

class HomeFragment : Fragment(), KodeinAware {
    lateinit var homeRecyclerViewAdapter: HomeRecyclerViewAdapter
    override val kodein: Kodein by kodein()
    private val homeViewModelFactory: HomeViewModelFactory by instance()
    lateinit var homeViewModel: HomeViewModel

    companion object {
        fun newInstance(page: Int, title: String): HomeFragment {
            val homeFragment = HomeFragment()
            val args = Bundle()
            args.putInt("someInt", page)
            args.putString("someTitle", title)
            homeFragment.arguments = args
            return homeFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            com.example.exchangecurrency.R.layout.fragment_home,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        instantiateHomeViewModel()
        runHomeRecyclerViewAdapter()
        getHomeListSuccess()
        getHomeListError()
        getHomeListException()
    }

    private fun getHomeListError() {
        homeViewModel.getAllCurrencyError.observe(
            this,
            Observer {
                Log.i("Home_Error", it)
            }
        )
    }

    private fun getHomeListException() {
        homeViewModel.getAllCurrencyException.observe(
            this,
            Observer {
                Log.i("Home_Exception", it?.message.toString())
            }
        )
    }

    private fun getHomeListSuccess() {
        homeViewModel.getAllCurrencySuccess.observe(
            this,
            Observer { it ->
                getDataFromApi(it.first().rates)
            }
        )
    }

    private fun getDataFromApi(allCurrencyList: List<AllCurrency.Rate>) {
        homeRecyclerViewAdapter.updateItemList(allCurrencyList)
    }

    private fun instantiateHomeViewModel() {
        homeViewModel =
            ViewModelProviders.of(this, homeViewModelFactory).get(HomeViewModel::class.java)
    }

    private fun runHomeRecyclerViewAdapter() {
        homeRecyclerViewAdapter = HomeRecyclerViewAdapter()
        home_recycler_view_id.layoutManager = LinearLayoutManager(context)
        home_recycler_view_id.adapter = homeRecyclerViewAdapter
    }
}