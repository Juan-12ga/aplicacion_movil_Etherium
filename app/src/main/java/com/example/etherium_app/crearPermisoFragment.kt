package com.example.etherium_app

import android.R
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.etherium_app.databinding.FragmentCrearPermisoBinding

class crearPermisoFragment : Fragment() {
    private var _binding: FragmentCrearPermisoBinding? = null
    private val binding get() = _binding!!

    private var archivoseleccionadoU: Uri? = null

    private val lanzadorArchivo = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            archivoseleccionadoU = data?.data

            if (archivoseleccionadoU != null) {
                // Cambiamos el texto del botón para que el usuario sepa que funcionó
                binding.tvAdjuntar.text = "Archivo seleccionado con éxito"
                Toast.makeText(requireContext(), "Archivo listo", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCrearPermisoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        configurarMenuDesplegable()
        botones()
    }
    private fun configurarMenuDesplegable() {
        val opciones = arrayOf("licencia por luto", "Cita médica", "Personal", "Calamidad Doméstica" )

        // Usamos un layout simple que ya viene en Android (simple_dropdown_item_1line)
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_dropdown_item_1line, opciones)

        // Asignamos el adaptador al AutoCompleteTextView
        binding.TipoPermiso.setAdapter(adapter)
    }
    private fun botones() {
        binding.btnCerrar.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        // Botón Adjuntar: Lanza el selector con filtros específicos
        binding.tvAdjuntar.setOnClickListener {
            abrirSelectorDeArchivos()
        }

        // Botón Guardar: Valida y procesa
        binding.btnGuardar.setOnClickListener {
            val tipo = binding.TipoPermiso.text.toString()
            val desc = binding.Descripcion.text.toString()

            if (tipo.isEmpty() || desc.isEmpty()) {
                Toast.makeText(requireContext(), "Por favor completa los campos", Toast.LENGTH_SHORT).show()
            } else if (archivoseleccionadoU == null) {
                Toast.makeText(requireContext(), "Debes adjuntar un soporte (JPG, PNG o PDF)", Toast.LENGTH_SHORT).show()
            } else {
                // Aquí llamaremos a tu API de PHP en el futuro
                Toast.makeText(requireContext(), "Enviando: $tipo", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun abrirSelectorDeArchivos() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "*/*" // Tipo general requerido por el Intent
            val mimeTypes = arrayOf("image/jpeg", "image/png", "application/pdf")
            putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes) // Filtro real
            addCategory(Intent.CATEGORY_OPENABLE)
        }
        lanzadorArchivo.launch(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Siempre limpiar el binding en fragmentos
    }

}