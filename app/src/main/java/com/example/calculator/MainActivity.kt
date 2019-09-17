package com.example.calculator

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var selectedPercent = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupPercentPicker()
        btn_calculate.setOnClickListener {
            if (isAllFilledCorrect()) {
                calculateCredit()
            } else {
                Toast.makeText(this, getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupPercentPicker() {
        percent_picker.minValue = 10
        percent_picker.maxValue = 100
        selectedPercent = percent_picker.value.toDouble()
        percent_picker.setOnValueChangedListener { _, _, newVal ->
            selectedPercent = newVal.toDouble()
        }
    }

    private fun calculateCredit() {
        val months = et_input_num_month.text.toString().toDouble()
        val sum = et_input_sum.text.toString().toDouble()
        val firstPay = et_input_first_pay.text.toString().toDouble()
        val rest = sum - firstPay
        val result = (rest + (rest * (selectedPercent / 100))) / months
        showResultDialog(result.toString())
    }

    private fun showResultDialog(result: String): AlertDialog? {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.month_pay))
        builder.setMessage(result)
            .setCancelable(false)
            .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                dialog.dismiss()
            }
        val dialog = builder.create()
        dialog.show()

        return dialog
    }

    private fun isAllFilledCorrect(): Boolean {
        return et_input_num_month.text.toString().isNotEmpty() &&
                et_input_first_pay.text.toString().isNotEmpty() &&
                et_input_sum.text.toString().isNotEmpty() &&
                et_input_first_pay.text.toString().toDouble() <= et_input_sum.text.toString().toDouble()
    }


}


