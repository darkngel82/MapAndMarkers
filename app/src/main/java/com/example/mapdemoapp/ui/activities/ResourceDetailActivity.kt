package com.example.mapdemoapp.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.mapdemoapp.R
import com.example.mapdemoapp.domain.MapResource
import com.example.mapdemoapp.utils.paintData
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_resource.*


class ResourceDetailActivity : AppCompatActivity() {

    private var mapResource: MapResource? = null

    companion object {
        const val PARAM_DATA = "RESOURCE_DATA_IN_JSON"
        // called from Splash
        fun newInstance(context: Context, resourceData: String): Intent {
            val i = Intent(context, ResourceDetailActivity::class.java)
            i.putExtra(PARAM_DATA, resourceData)
            return i
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resource)

        receiveValue()

        setToolbarConfig()

        paintValuesOfResource()
    }

    /** draws at the content view one textview for each item field with value*/
    private fun paintValuesOfResource() {
        mapResource?.let {
            paintData(this, content, it)
        } ?: run {
            finish()
        }
    }

    /** sets the topbar title and back button*/
    private fun setToolbarConfig() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = mapResource?.name
    }

    /** get values from extras*/
    private fun receiveValue() {
        intent?.extras?.getString(PARAM_DATA)?.let {
            try {
                mapResource = Gson().fromJson(it, MapResource::class.java)
            } catch (e: Exception) {
                Log.e("DETAIL", "wrong json params received")
            }
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
            else -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
