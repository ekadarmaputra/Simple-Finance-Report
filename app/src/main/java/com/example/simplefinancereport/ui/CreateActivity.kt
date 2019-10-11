package com.example.simplefinancereport.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.simplefinancereport.MyApplication
import com.example.simplefinancereport.R
import com.example.simplefinancereport.extension.convertForCalendar
import com.example.simplefinancereport.model.Report
import com.example.simplefinancereport.ui.helper.InterfaceHelper
import io.objectbox.Box
import kotlinx.android.synthetic.main.activity_create.*

class CreateActivity: AppCompatActivity(), View.OnClickListener {

    lateinit var box: Box<Report>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        box = (application as MyApplication).getBoxStore().boxFor(Report::class.java)

        btn_save.setOnClickListener(this)

    }

    override fun onClick(v: View?) {

        if (v?.id == R.id.btn_save){
            val name = et_name.text.toString().trim { it <= ' ' }
            val quantity = et_quantity.text.toString().trim { it <= ' ' }
            val date = et_date.text.toString().trim { it <= ' ' }
            val calendarDate = date.convertForCalendar()
            when {
                name.isEmpty() -> InterfaceHelper.getInstance().showAlertDialog(context = this@CreateActivity, message = getString(R.string.error_empty_name))
                quantity.isEmpty() -> InterfaceHelper.getInstance().showAlertDialog(context = this@CreateActivity, message = getString(R.string.error_empty_quantity))
                date.isEmpty() -> InterfaceHelper.getInstance().showAlertDialog(context = this@CreateActivity, message = getString(R.string.error_empty_date))
                else -> {
                    box.put(Report(0,
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