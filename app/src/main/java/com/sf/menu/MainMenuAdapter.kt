package com.sf.menu

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.sf.opengldemo1.R
import java.util.*

class MainMenuAdapter(val context: Context, val data: ArrayList<MenuBean>?) : RecyclerView.Adapter<MainMenuAdapter.MenuHolder?>() {
    var menuClick: View.OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuHolder {
        return MenuHolder(LayoutInflater.from(context).inflate(R.layout.main_button, parent, false))
    }

    override fun onBindViewHolder(holder: MenuHolder, position: Int) {
        setPosition(holder, position)
        holder?.mBtn?.setOnClickListener {
            menuClick?.onClick(it)
        }
    }

    fun setPosition(holder: MenuHolder, position: Int) {
        val bean: MenuBean? = data?.get(position)
        holder?.mBtn?.text = bean?.name
        holder?.mBtn?.tag = position
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    inner class MenuHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mBtn: Button = itemView.findViewById(R.id.main_function_btn)


    }
}