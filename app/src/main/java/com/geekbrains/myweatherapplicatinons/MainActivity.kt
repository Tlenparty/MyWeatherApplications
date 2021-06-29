package com.geekbrains.myweatherapplicatinons

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.geekbrains.myweatherapplicatinons.framework.ui.threads_fragment.ThreadsFragment
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_screen_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.menu_threads -> {
                supportFragmentManager.apply {
                    beginTransaction()
                        .add(R.id.container, ThreadsFragment.newInstance())
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
                true
            } else -> super.onOptionsItemSelected(item)
        }
    }
}