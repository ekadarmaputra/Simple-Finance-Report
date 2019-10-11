package com.example.simplefinancereport.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.simplefinancereport.MyApplication
import com.example.simplefinancereport.R
import com.example.simplefinancereport.extension.convertForCalendar
import com.example.simplefinancereport.extension.formatDate
import com.example.simplefinancereport.model.Report
import com.example.simplefinancereport.model.Report_
import com.example.simplefinancereport.ui.helper.InterfaceHelper
import io.objectbox.Box
import io.objectbox.query.Query
import kotlinx.android.synthetic.main.activity_edit.btn_save
import kotlinx.android.synthetic.main.activity_edit.et_date
import kotlinx.android.synthetic.main.activity_edit.et_name
import kotlinx.android.synthetic.main.activity_edit.et_quantity

class EditActivity: AppCompatActivity(), View.OnClickListener {

    lateinit var box: Box<Report>
    private var reportId = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        box = (application as MyApplication).getBoxStore().boxFor(Report::class.java)

        handleIntent(intent)
        btn_save.setOnClickListener(this)
    }

    private fun handleIntent(intent: Intent){
        reportId = intent.getLongExtra("report_id", 0)
        val selectedQuery: Query<Report> = box.query().equal(Report_.id, reportId).build()
        val report = selectedQuery.findFirst()

        et_name.setText(report?.name)
        et_quantity.setText(report?.quantity.toString())
        et_date.setText(report?.date?.formatDate(report.date.time))
    }

    override fun onClick(v: View?) {

        if (v?.id == R.id.btn_save) {
            val name = et_name.text.toString().trim { it <= ' ' }
            val quantity = et_quantity.text.toString().trim { it <= ' ' }
            val date = et_date.text.toString().trim { it <= ' ' }
            val calendarDate = date.convertForCalendar()
            when {
                name.isEmpty() -> InterfaceHelper.getInstance().showAlertDialog(context = this@EditActivity, message = getString(R.string.error_empty_name))
                quantity.isEmpty() -> InterfaceHelper.getInstance().showAlertDialog(context = this@EditActivity, message = getString(R.string.error_empty_quantity))
                date.isEmpty() -> InterfaceHelper.getInstance().showAlertDialog(context = this@EditActivity, message = getString(R.string.error_empty_date))
                else -> {
                    box.put(Report(reportId,
                        et_name.text.toString(),
                        et_quantity.text.toString().toLong(),
                        calendarDate
                    ))

                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }
}