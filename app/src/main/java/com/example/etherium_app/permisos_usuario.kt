package com.example.etherium_app

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.etherium_app.databinding.ActivityPermisosUsuarioBinding

class permisos_usuario : AppCompatActivity() {
    private lateinit var binding: ActivityPermisosUsuarioBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPermisosUsuarioBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        botonesNavegacion()
    }

    private fun botonesNavegacion() {
        // Para volver atrás
        binding.icRetrocederBtn.setOnClickListener {
            finish()
        }

        // Para mostrar el Fragment
        binding.btnAgregarPermiso.setOnClickListener {
            // Obtenemos el manager de fragmentos
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)

                // Aquí usamos R.id.main porque replace necesita la referencia del "hueco" a llenar
                .replace(R.id.main, crearPermisoFragment())

                .addToBackStack(null)
                .commit()
        }
    }

}