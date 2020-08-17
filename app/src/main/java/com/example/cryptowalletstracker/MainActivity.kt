/*
 * Copyright (c) 2020 by Jason McKinney.
 */

package com.example.cryptowalletstracker

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptowalletstracker.ui.CustomAdapter
import com.example.cryptowalletstracker.ui.WalletViewModel
import com.idescout.sql.SqlScoutServer
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private var adapter: CustomAdapter? = null
    private lateinit var viewModel: WalletViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        SqlScoutServer.create(this, packageName)

        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager

        adapter = CustomAdapter(this)
        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this)[WalletViewModel::class.java]

        viewModel.wallets.observe(this, Observer {
            adapter?.wallets = it
        })
        viewModel.initViewModel()

        fab.setOnClickListener {
            viewModel.addWallet("doge", "DPdHJchjuYNxvEi2vhv2XLtKRzNKADq3zc")
        }

        swiperefresh.setOnRefreshListener {
            swiperefresh.isRefreshing = true
            viewModel.refreshWallets()
            swiperefresh.isRefreshing = false
        }
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        destroyDbInstance()
//    }
//
//    private fun destroyDbInstance() {
//        WalletDatabase.destroyInstance()
//    }

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
                refreshWallets()
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun refreshWallets(): Boolean {
        viewModel.refreshWallets()
        return true
    }


}
