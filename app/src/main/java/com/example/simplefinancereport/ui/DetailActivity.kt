package com.example.simplefinancereport.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.simplefinancereport.MyApplication
import com.example.simplefinancereport.R
import com.example.simplefinancereport.extension.formatDate
import com.example.simplefinancereport.model.Report
import com.example.simplefinancereport.model.Report_
import io.objectbox.Box
import io.objectbox.query.Query
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity: AppCompatActivity() {

    lateinit var box: Box<Report>
    private var reportId = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        box = (application as MyApplication).getBoxStore().boxFor(Report::class.java)

        handleIntent(intent)

    }

    private fun handleIntent(intent: Intent){
        reportId = intent.getLongExtra("report_id", 0)
        val selectedQuery: Query<Report> = box.query().equal(Report_.id, reportId).build()
        val report = selectedQuery.findFirst()

        tv_name.text = "Nama Barang: ${report?.name}"
        tv_quantity.text = "Quantity: ${report?.quantity.toString()}"
        tv_date.text = "Tanggal Masuk Barang: ${report?.date?.formatDate(report.date.time)}"
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }
}