package com.skywell.car.datasourcesample

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.properties.ReadOnlyProperty

private val Context.dataStore by preferencesDataStore(
    name = "settings"
)

val EXAMPLE_COUNTER = intPreferencesKey("example_counter")

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.write).setOnClickListener {
            GlobalScope.launch {
                dataStore.edit {settings ->
                    val origin = settings[EXAMPLE_COUNTER]
                    val value = (origin?: 0) + 1
                    Log.d("ruanyandong", "write Value: counter $value")
                    settings[EXAMPLE_COUNTER] = value
                }
            }
        }

        findViewById<View>(R.id.read).setOnClickListener {
            GlobalScope.launch {
                val exampleCounterFlow: Flow<Int> = dataStore.data.map {
                    it[EXAMPLE_COUNTER]?: 0
                }

                val value = exampleCounterFlow.first()
                Log.d("ruanyandong", "readValue: counter $value")
            }
        }

    }
}
