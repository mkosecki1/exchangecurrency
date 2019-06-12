package com.example.exchangecurrency.ui.view.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exchangecurrency.R
import com.example.exchangecurrency.ui.view.adapters.GoldRecyclerViewAdapter
import com.example.exchangecurrency.view_model.GoldViewModel
import com.example.exchangecurrency.view_model.GoldViewModelFactory
import kotlinx.android.synthetic.main.gold_fragment.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.kodein
import org.kodein.di.generic.instance
import java.text.SimpleDateFormat
import java.util.*

class GoldFragment : Fragment(), KodeinAware {
    private lateinit var goldViewModel: GoldViewModel
    private val goldViewModelFactory: GoldViewModelFactory by instance()
    override val kodein: Kodein by kodein()
    lateinit var goldRecyclerViewAdapter: GoldRecyclerViewAdapter
    private var textViewFromDate: TextView? = null
    private var textViewToDate: TextView? = null
    private var cal = Calendar.getInstance()
    private var dateFrom = false
    private var dateTo = false


    companion object {
        fun newInstance(page: Int, title: String): GoldFragment {
            val goldFragment = GoldFragment()
            val args = Bundle()
            args.putInt("someInt", page)
            args.putString("someTitle", title)
            goldFragment.arguments = args
            return goldFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.gold_fragment, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        instantiateGoldViewModel()
        runGoldRecyclerViewAdapter()
        getGoldListSuccess()
        getGoldListByDateSuccess()
        getGoldListError()
        getGoldListByDateError()
        getGoldListExceptions()
        getGoldListByDateExceptions()
        fetchGoldPrice()

        button_from_id.setOnClickListener {
            showDatePickerDialogFrom()
        }

        button_to_id.setOnClickListener {
            showDatePickerDialogTo()
        }

        gold_price_to_date_id.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                dateTo = true
                showGoldAdapterRecycler()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //not use
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //not use
            }
        })

        gold_price_from_date_id.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                dateFrom = true
                showGoldAdapterRecycler()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //not use
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //not use
            }
        })
    }

    private fun runGoldRecyclerViewAdapter() {
        goldRecyclerViewAdapter = GoldRecyclerViewAdapter()
        gold_recycler_view_id.layoutManager = LinearLayoutManager(context)
        gold_recycler_view_id.adapter = goldRecyclerViewAdapter

    }

    private fun fetchGoldPrice() {
        goldViewModel.fetchGoldPrice()
    }

    private fun getGoldListExceptions() {
        goldViewModel.getGoldPriceException.observe(
            this,
            Observer {
                Log.i("Gold_Exception", it?.message.toString())
            }
        )
    }

    private fun getGoldListByDateExceptions() {
        goldViewModel.getGoldPriceByDateException.observe(
            this,
            Observer {
                Log.i("Gold_Exception_By_Date", it?.message.toString())
            }
        )
    }

    private fun getGoldListError() {
        goldViewModel.getGoldPriceError.observe(
            this,
            Observer {
                Log.i("Gold_Exception", it)
            }
        )
    }

    private fun getGoldListByDateError() {
        goldViewModel.getGoldPriceByDateError.observe(
            this,
            Observer {
                Log.i("Gold_Exception_By_Date", it)
            }
        )
    }

    private fun getGoldListSuccess() {
        goldViewModel.getGoldPriceSuccess.observe(
            this,
            Observer { it ->
                text_view_gold_price_id.text =
                    "Cena złota: ${it.first().cena} zł w dniu ${it.first().data}"
            }
        )
    }

    private fun getGoldListByDateSuccess() {
        goldViewModel.getGoldPriceByDateSuccess.observe(
            this,
            Observer { it ->
                goldRecyclerViewAdapter.updateItemList(it)
            }
        )
    }

    private fun instantiateGoldViewModel() {
        goldViewModel =
            ViewModelProviders.of(this, goldViewModelFactory).get(GoldViewModel::class.java)
    }

    private fun showDatePickerDialogFrom(): String {
        datePicker(this.gold_price_from_date_id)
        textViewFromDate = this.gold_price_from_date_id
        return textViewFromDate.toString()
    }

    private fun showDatePickerDialogTo(): String {
        datePicker(this.gold_price_to_date_id)
        textViewToDate = this.gold_price_to_date_id
        return textViewToDate.toString()
    }

    fun showGoldAdapterRecycler() {
        if (dateFrom && dateTo) {
            goldViewModel.fetchGoldPriceByDate(
                textViewFromDate?.text.toString(),
                textViewToDate?.text.toString()
            )
        }
    }

    private fun datePicker(dateTextView: TextView) {
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val myFormat = "yyyy-MM-dd"
                val sdf = SimpleDateFormat(myFormat, Locale.GERMANY)
                dateTextView.text = sdf.format(cal.time)
            }

        DatePickerDialog(
            context!!,
            dateSetListener,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }
}