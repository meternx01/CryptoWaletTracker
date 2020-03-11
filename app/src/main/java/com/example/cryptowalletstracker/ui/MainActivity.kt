/*
 * Copyright (c) 2020 by Jason McKinney.
 */

package com.example.cryptowalletstracker.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptowalletstracker.R
import com.example.cryptowalletstracker.db.WalletDatabase
import com.example.cryptowalletstracker.db.entities.Wallet
import com.example.cryptowalletstracker.viewmodel.WalletViewModel
import com.example.cryptowalletstracker.viewmodel.factory.WalletViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    var adapter: CustomAdapter? = null
    lateinit var viewModel: WalletViewModel
    private var walletDatabase: WalletDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        actionBar

        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager

        adapter = CustomAdapter(this)
        recyclerView.adapter = adapter

        createDbInstance()
        viewModel = ViewModelProvider(
            this,
            WalletViewModelFactory(application)
        ).get(WalletViewModel::class.java)

        viewModel.state.observe(this, Observer { appState ->
            when (appState) {
                is WalletViewModel.AppState.LOADING -> displayLoading()
                is WalletViewModel.AppState.SUCCESS -> displayArray(appState.walletArray)
                is WalletViewModel.AppState.ERROR -> displayMessage(appState.message)
                else -> displayMessage("Oh Noes! There's a Problem.. Try again!")
            }
        })

        viewModel.initViewModel(walletDatabase)
        fab.setOnClickListener {
            if (walletDatabase != null) {
                swiperefresh.isRefreshing = true
                val database = walletDatabase
                viewModel.addWallet(database)
            }
        }

        swiperefresh.setOnRefreshListener {
            swiperefresh.isRefreshing = true
            viewModel.refreshWallets(walletDatabase)
        }
    }

    private fun displayArray(walletArray: List<Wallet>) {
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
        containerMessage.visibility = View.GONE
        adapter?.wallets = walletArray
        swiperefresh.isRefreshing = false
    }

    private fun displayMessage(message: String) {
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.GONE
        containerMessage.visibility = View.VISIBLE
        messageText.text = message
    }

    private fun displayLoading() {
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        containerMessage.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyDbInstance()
    }

    private fun destroyDbInstance() {
        WalletDatabase.destroyInstance()
    }

    private fun createDbInstance() {
        walletDatabase = WalletDatabase.getInstance(application)
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
            R.id.action_refresh -> {
                swiperefresh.isRefreshing = true
                refreshWallets()
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun refreshWallets(): Boolean {
        viewModel.refreshWallets(walletDatabase)
        return true
    }


}
