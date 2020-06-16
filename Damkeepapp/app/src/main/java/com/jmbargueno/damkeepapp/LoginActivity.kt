package com.jmbargueno.damkeepapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import butterknife.BindView
import butterknife.ButterKnife
import com.jmbargueno.damkeepapp.client.request.LoginRequest
import com.jmbargueno.damkeepapp.common.Constants
import com.jmbargueno.damkeepapp.common.MyApp
import com.jmbargueno.damkeepapp.common.SharedPreferenceModule
import com.jmbargueno.damkeepapp.viewmodel.AppUserViewModel
import javax.inject.Inject


class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var appUserViewModel: AppUserViewModel

    @BindView(R.id.editTextUsername)
    lateinit var editTextUsername: EditText

    @BindView(R.id.editTextPassword)
    lateinit var editTextPassword: EditText

    @BindView(R.id.buttonLogin)
    lateinit var buttonLogin: Button

    @BindView(R.id.textViewRegister)
    lateinit var textViewRegister: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val app = application as MyApp
        (applicationContext as MyApp).getAppCompontent().inject(this)
        ButterKnife.bind(this)

        Log.d("CREATED", "LOGIN ACTIVITY")

        SharedPreferenceModule().setStringValue(
            Constants.SHARED_PREFERENCES_TOKEN,
            ""
        )

        setListeners()
    }

    fun setListeners() {
        buttonLogin.setOnClickListener(View.OnClickListener {
            appUserViewModel.doLogin(
                LoginRequest(
                    username = editTextUsername.text.toString(),
                    password = editTextPassword.text.toString()
                )
            ).observe(this, Observer {
                if (it == null) {
                    Toast.makeText(this, "Fallo en la autenticación", Toast.LENGTH_LONG).show()
                } else {
                    val toMain: Intent = Intent(MyApp.instance, MainActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                    startActivity(toMain)
                    finish()
                }
            })
        })
        textViewRegister.setOnClickListener(View.OnClickListener {
            val signUp: Intent = Intent(MyApp.instance, SignUpActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(signUp)
            finish()
        })


    }
}