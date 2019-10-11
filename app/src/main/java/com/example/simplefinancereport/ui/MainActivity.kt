package com.example.simplefinancereport.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simplefinancereport.MyApplication
import com.example.simplefinancereport.R
import com.example.simplefinancereport.model.Report
import com.example.simplefinancereport.ui.adapter.ReportAdapter
import io.objectbox.Box
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var box: Box<Report>
    lateinit var context: Context
    private val reports = ArrayList<Report>()
    private var reportAdapter: ReportAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        context = this@MainActivity
        box = (application as MyApplication).getBoxStore().boxFor(Report::class.java)

        initRecyclerview()
    }

    private fun initRecyclerview(){
        reportAdapter = ReportAdapter(context)
        reportAdapter?.setReports(reports)

        rv_report.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = reportAdapter
        }

        reports.addAll(box.query().build().find())
        reportAdapter?.notifyItemRangeChanged(0, reports.size)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item?.itemId == R.id.item_create){
            val intent = Intent(context, CreateActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}
