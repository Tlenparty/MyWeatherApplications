package com.geekbrains.myweatherapplicatinons

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.geekbrains.myweatherapplicatinons.framework.ui.view.main_fragment.MainFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
}