package com.geekbrains.myweatherapplicatinons.framework.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.geekbrains.myweatherapplicatinons.R
import com.geekbrains.myweatherapplicatinons.framework.ui.view.contacts_fragment.ContactsFragment
import com.geekbrains.myweatherapplicatinons.framework.ui.view.threads_fragment.ThreadsFragment
import com.geekbrains.myweatherapplicatinons.framework.ui.view.history_fragment.HistoryFragment
import com.geekbrains.myweatherapplicatinons.framework.ui.view.main_fragment.MainFragment
import com.geekbrains.myweatherapplicatinons.framework.ui.view.maps_fragment.MapsFragment


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
        menuInflater.inflate(R.menu.main_screen_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_threads -> {
                openFragment(ThreadsFragment.newInstance())
                true
            }
            R.id.menu_history -> {
                openFragment(HistoryFragment.newInstance())
                true
            }
            R.id.menu_contacts_provider ->{
                openFragment(ContactsFragment.newInstance())
                true
            }
            R.id.menu_google_maps ->{
                openFragment(MapsFragment.newInstance())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.apply {
            beginTransaction()
                .add(R.id.container, fragment)
                .addToBackStack("")
                .commitAllowingStateLoss()
        }
    }
}