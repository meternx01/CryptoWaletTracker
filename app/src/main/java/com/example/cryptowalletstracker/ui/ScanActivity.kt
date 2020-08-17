package com.example.cryptowalletstracker.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.SparseArray
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.isNotEmpty
import com.example.cryptowalletstracker.R
import com.google.android.gms.vision.barcode.Barcode
import info.androidhive.barcode.BarcodeReader

class ScanActivity : AppCompatActivity(), BarcodeReader.BarcodeReaderListener {

    lateinit var barcodeReader: BarcodeReader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

        barcodeReader =
            supportFragmentManager.findFragmentById(R.id.barcode_scanner) as BarcodeReader

    }

    override fun onBitmapScanned(sparseArray: SparseArray<Barcode>?) {
        if (sparseArray != null && sparseArray.isNotEmpty()) {
            val replyIntent = Intent()
            replyIntent.putExtra(SCANNED_BARCODE, sparseArray[0].rawValue)
            setResult(Activity.RESULT_OK, replyIntent)
            finish()
        }
    }

    override fun onScannedMultiple(barcodes: MutableList<Barcode>?) {
        TODO("Not yet implemented")
    }

    override fun onCameraPermissionDenied() {
        TODO("Not yet implemented")
    }

    override fun onScanned(barcode: Barcode?) {
        TODO("Not yet implemented")
    }

    override fun onScanError(errorMessage: String?) {
        TODO("Not yet implemented")
    }

    companion object {
        const val SCANNED_BARCODE = "com.example.cryptowalletstracker.ui.BARCODE"
    }
}