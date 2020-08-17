package com.example.cryptowalletstracker.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.cryptowalletstracker.R
import com.example.cryptowalletstracker.util.DataSources.Companion.COINS
import kotlinx.android.synthetic.main.dialog_add.view.*

open class AddCoinDialogFragment : DialogFragment() {

    private lateinit var listener: AddWalletDialogListener

    interface AddWalletDialogListener {
        fun onFinishedAddWallet(coin: String, address: String)
    }

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        try{
//            listener = context as Activity
//        }
//    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.dialog_add, null)
            val spinnerAdapter = context?.let { context -> SpinnerAdapter(context, COINS) }
            val spin = view.spinner
            spin.adapter = spinnerAdapter
            view.et_coin.setText("XoYKZffZWPcWgQSv9Fz82Xc6SLzoBrW5fw")

            builder.setView(view)
                .setPositiveButton("Add") { dialog, which ->
                    val coin = COINS[spin.selectedItemPosition].symbol
                    val addr = view.et_coin.text.toString()
                    addButtonClicked(coin, addr)
                }
                .setNegativeButton(
                    "Cancel"
                ) { dialog, _ -> dialog.cancel() }
            builder.create()

        } ?: throw IllegalStateException("Activity cannot be Null")
    }

    private fun addButtonClicked(coin: String, addr: String) {
        listener = activity as AddWalletDialogListener
        listener.onFinishedAddWallet(coin, addr)

    }
}