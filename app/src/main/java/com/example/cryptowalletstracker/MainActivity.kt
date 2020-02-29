package com.example.cryptowalletstracker

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cryptowalletstracker.db.WalletDatabase
import com.example.cryptowalletstracker.ui.WalletViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: WalletViewModel
    private var walletDatabase: WalletDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        createDbInstance()
        viewModel = ViewModelProvider(this)[WalletViewModel::class.java]

        viewModel.wallets.observe(this, Observer {
            if (it.isNotEmpty()) {
                balanceText.text = it[0].amount
            }
        })
        fab.setOnClickListener {
            if (walletDatabase != null) {
                val database = walletDatabase
                viewModel.addWallet(database)
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyDbInstance()
    }

    private fun destroyDbInstance() {
        WalletDatabase.destroyInstance()
    }

    private fun createDbInstance() {
        walletDatabase = WalletDatabase.getInstance(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }


}
