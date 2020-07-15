package com.ccc.ccp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_country_code_picker.view.*

class CountryCodeAdapter : RecyclerView.Adapter<CountryCodeAdapter.ViewHolder>() {

    var mCountries = ArrayList<Country>()
    private var mOnItemClick: OnItemClickListener? = null
    private var mIsShowFlag = true;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_country_code_picker, parent, false)
        return ViewHolder(view, mOnItemClick)
    }

    override fun getItemCount(): Int {
        return mCountries.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mCountries[position], mIsShowFlag)
    }

    fun setOnItemClickListener(onItemClick: OnItemClickListener?) {
        mOnItemClick = onItemClick
    }

    fun updateData(countries: List<Country>) {
        mCountries.clear()
        mCountries.addAll(countries)
        notifyDataSetChanged()
    }

    fun deleteCountry(country: Country) {
        val index = mCountries.indexOfFirst { it.nameCode == country.nameCode }
        if (index == -1) return
        mCountries.removeAt(index)
        notifyItemRemoved(index)
    }

    fun setShowFlag(boolean: Boolean) {
        mIsShowFlag = boolean
    }

    class ViewHolder(
        itemView: View,
        onItemClick: OnItemClickListener?
    ) : RecyclerView.ViewHolder(itemView) {
        private var mOnItemClick = onItemClick

        fun bind(country: Country, isShowFlag: Boolean) {
            itemView.flagImageView.setImageResource(Country.getFlagMasterResID(country))
            itemView.nameTextView.text = country.name
            itemView.phoneCodeTextView.text = country.phoneCode
            if (isShowFlag) {
                itemView.flagImageView.visibility = View.VISIBLE
            } else {
                itemView.flagImageView.visibility = View.GONE
            }
            itemView.setOnClickListener {
                mOnItemClick?.onItemClick(country)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(country: Country)
    }
}