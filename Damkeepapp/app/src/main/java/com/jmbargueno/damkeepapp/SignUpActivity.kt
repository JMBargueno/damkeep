package com.jmbargueno.damkeepapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import butterknife.BindView
import butterknife.ButterKnife
import com.jmbargueno.damkeepapp.client.request.LoginRequest
import com.jmbargueno.damkeepapp.common.MyApp
import com.jmbargueno.damkeepapp.viewmodel.AppUserViewModel
import javax.inject.Inject

class SignUpActivity : AppCompatActivity() {

    @Inject
    lateinit var appUserViewModel: AppUserViewModel

    @BindView(R.id.editTextRegisterUsername)
    lateinit var editTextRegisterUsername: EditText

    @BindView(R.id.editTextRegisterPassword)
    lateinit var editTextRegisterPassword: EditText

    @BindView(R.id.buttonRegister)
    lateinit var buttonRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        (applicationContext as MyApp).getAppCompontent().inject(this)
        ButterKnife.bind(this)

        setListeners()
    }

    fun setListeners() {
        buttonRegister.setOnClickListener(View.OnClickListener {
            appUserViewModel.signup(
                LoginRequest(
                    editTextRegisterUsername.text.toString(),
                    editTextRegisterPassword.text.toString()
                )
            ).observe(this, Observer {
                if (it == null) {
                    Toast.makeText(this, "Fallo en la autenticación", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Registro realizado", Toast.LENGTH_LONG).show()
                    val toLogin: Intent = Intent(MyApp.instance, LoginActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                    startActivity(toLogin)
                    finish()
                }
            })
        })
    }
}