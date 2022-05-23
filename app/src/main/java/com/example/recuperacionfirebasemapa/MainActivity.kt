package com.example.recuperacionfirebasemapa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth

        val btnRegistrar: Button = findViewById(R.id.btnRegistrar)
        btnRegistrar.setOnClickListener{
            val email: EditText = findViewById(R.id.cuadroEmail)
            val contraseña: EditText = findViewById(R.id.cuadroContraseña)
            registrarCuenta(email.text.toString(), contraseña.text.toString())
        }

        val btnIniciar: Button = findViewById(R.id.btnIniciar)
        btnIniciar.setOnClickListener{
            val email: EditText = findViewById(R.id.cuadroEmail)
            val contraseña: EditText = findViewById(R.id.cuadroContraseña)
            iniciarSesion(email.text.toString(), contraseña.text.toString())
        }
    }
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            reload();
        }
    }

    private fun registrarCuenta(email: String, contraseña: String){
        auth.createUserWithEmailAndPassword(email, contraseña)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.i("Correcto", "Creacion de usuario correcta")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Log.i("Error", "Error al crear usuario", task.exception)
                    Toast.makeText(baseContext, "Error en el registro.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }


    private fun iniciarSesion (email: String, contraseña: String){
        auth.signInWithEmailAndPassword(email, contraseña)
            .addOnCompleteListener(this){ task ->
                if (task.isSuccessful){
                    Log.d("Correcto","Inicio de sesión correcto")
                    val user = auth.currentUser
                    updateUI(user)
                    Toast.makeText(baseContext, "Correcto",
                        Toast.LENGTH_SHORT).show()
                }else {
                    Log.w("Error","Error al iniciar sesión",task.exception)
                    Toast.makeText(baseContext, "Error en el inicio de sesión.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
    }
    private fun reload() {
    }
}