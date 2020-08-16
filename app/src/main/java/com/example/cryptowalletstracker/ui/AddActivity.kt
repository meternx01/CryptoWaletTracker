package com.example.cryptowalletstracker.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cryptowalletstracker.R
import kotlinx.android.synthetic.main.activity_add.*


class AddActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)


        qr_button.setOnClickListener {
            val intent = Intent(this, ScanActivity::class.java)
            startActivityForResult(intent, SCAN_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SCAN_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    et_qr_coin.setText(data.getStringExtra(ScanActivity.SCANNED_BARCODE))
                }
            }
        }

    }

    companion object {
        private const val SCAN_REQUEST: Int = 101
    }

}
