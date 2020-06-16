package com.jmbargueno.damkeepapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jmbargueno.damkeepapp.common.MyApp
import com.jmbargueno.damkeepapp.viewmodel.AppUserViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var appUserViewModel: AppUserViewModel

    @BindView(R.id.floatingActionButton)
    lateinit var fab: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (applicationContext as MyApp).getAppCompontent().inject(this)
        ButterKnife.bind(this)
        Log.d("CREATED", "MAIN ACTIVITY")

        fab.setOnClickListener {
            /*Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()*/
            val toAddNote: Intent = Intent(MyApp.instance, AddNoteActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(toAddNote)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.top_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_logout -> {
                var result = appUserViewModel.logOut()
                if (result) {
                    val toLogin: Intent = Intent(MyApp.instance, LoginActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                    startActivity(toLogin)
                    finish()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

}