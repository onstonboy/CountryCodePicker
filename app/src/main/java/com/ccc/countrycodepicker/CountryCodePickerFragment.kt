package com.ccc.countrycodepicker

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_country_code_picker.view.*
import java.util.*

class CountryCodePickerFragment : DialogFragment(), CountryCodeAdapter.OnItemClickListener {

    private lateinit var mView: View
    private lateinit var mAdapter: CountryCodeAdapter

    private var mOnCountryPicked: OnCountryPickedListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.fragment_country_code_picker, container, false)

        initRecyclerView()
        initData()
        return mView
    }

    override fun onDestroy() {
        mAdapter.setOnItemClickListener(null)
        super.onDestroy()
    }

    override fun onItemClick(country: Country) {
        mOnCountryPicked?.onCountryPicked(country)
        dismiss()
    }

    fun setOnCountryPickedListener(onCountryPicked: OnCountryPickedListener?) {
        mOnCountryPicked = onCountryPicked
    }

    private fun initData() {
        val country = arguments?.getParcelable<Country>("Country") ?: return
        mAdapter.setCountryPicked(country)
    }

    private fun initRecyclerView() {
        val context = context ?: return
        mAdapter = CountryCodeAdapter()
        val manager = LinearLayoutManager(context)
        mView.recyclerView.layoutManager = manager
        mView.recyclerView.adapter = mAdapter
        mAdapter.updateData(Country.loadCountryDataFromXML(context, Locale.getDefault().language))
        mAdapter.setOnItemClickListener(this)
    }

    companion object {
        var TAG: String = this::class.java.simpleName

        fun getInstance(country: Country): CountryCodePickerFragment {
            val fragment = CountryCodePickerFragment()
            val bundle = Bundle()
            bundle.putParcelable("Country", country)
            fragment.arguments = bundle
            return fragment
        }
    }
}
