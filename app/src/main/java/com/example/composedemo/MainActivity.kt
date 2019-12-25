package com.example.composedemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewModel = ViewModelProviders.of(this).get(FetchViewModel::class.java)
        val dialog = ProgressbarFragment.newInstance(getString(R.string.loading))
        send_btn.setOnClickListener {
            viewModel.fetchData()
        }
        viewModel.loading.observe(this, Observer {
            if(it){
                supportFragmentManager.run {
                    dialog.show(this, "")
                }
            } else{
                dialog.dismiss()
            }
        })
        viewModel.data.observe(this, Observer {
            info.text = it
        })
    }
}
