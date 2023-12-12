package com.example.jobguardian.ui.main.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jobguardian.R
import com.example.jobguardian.ui.main.ui.detail.DetailActivity

class ListCompanyAdapter(private val listCompany: ArrayList<Company>) :
    RecyclerView.Adapter<ListCompanyAdapter.ListViewHolder>() {

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgLogo: ImageView = itemView.findViewById(R.id.iv_companyPhoto)
        val tvName: TextView = itemView.findViewById(R.id.tv_company)
        val tvSalary: TextView = itemView.findViewById(R.id.tv_salary)
        val tvLocation: TextView = itemView.findViewById(R.id.tv_location)
        val tvPosition: TextView = itemView.findViewById(R.id.tv_position)
        val tvDescription: TextView = itemView.findViewById(R.id.tv_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.list_company, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listCompany.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name,salary, position, location, description, logo) = listCompany[position]
        holder.imgLogo.setImageResource(logo)
        holder.tvPosition.text = position
        holder.tvSalary.text = salary
        holder.tvLocation.text = location
        holder.tvName.text = name
        holder.tvDescription.text = description
        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra("key_company", listCompany[holder.adapterPosition])
            holder.itemView.context.startActivity(intentDetail)
        }
    }
}
