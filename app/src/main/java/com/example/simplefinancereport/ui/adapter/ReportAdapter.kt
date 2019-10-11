package com.example.simplefinancereport.ui.adapter

import android.content.Context
import android.content.DialogInterface
import android.content.Intent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.simplefinancereport.MyApplication
import com.example.simplefinancereport.R
import com.example.simplefinancereport.extension.formatDate
import com.example.simplefinancereport.model.Report
import com.example.simplefinancereport.model.Report_
import com.example.simplefinancereport.ui.DetailActivity
import com.example.simplefinancereport.ui.EditActivity
import com.example.simplefinancereport.ui.helper.InterfaceHelper
import io.objectbox.Box
import kotlinx.android.synthetic.main.item_list_report.view.*
import kotlinx.android.synthetic.main.item_list_report.view.tv_name

class ReportAdapter(private val context: Context): RecyclerView.Adapter<ReportAdapter.ViewHolder>(){

    var box: Box<Report> = (context.applicationContext as MyApplication).getBoxStore().boxFor(Report::class.java)
    private var reports: MutableList<Report>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_report, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return reports?.size?:0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val report = reports?.get(position)
        val selectedQuery = box.query().equal(Report_.id, report?.id?:0).build()
        holder.itemView.tv_name.text = "${report?.name}"
        holder.itemView.tv_quantity.text = "${report?.quantity}"
        holder.itemView.tv_date.text = report?.date?.formatDate(report.date.time)

        holder.itemView.btn_edit.setOnClickListener {
            val intent = Intent(context, EditActivity::class.java)
            intent.putExtra("report_id", report?.id)
            context.startActivity(intent)
        }

        holder.itemView.btn_delete.setOnClickListener {
            InterfaceHelper.getInstance().showConfirmationDialog(context, context.getString(R.string.alert_dialog_on_delete_item),
                DialogInterface.OnClickListener { _, _ ->
                    selectedQuery.remove()
                    reports?.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(0, itemCount)
                })
        }

        holder.itemView.layout_report.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("report_id", report?.id)
            context.startActivity(intent)
        }
    }

    fun setReports(reports: MutableList<Report>) {
        this.reports = reports
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}