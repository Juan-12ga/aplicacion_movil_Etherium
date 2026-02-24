package com.example.etherium_app


import android.app.Activity
import android.app.AlertDialog
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
import com.google.android.material.floatingactionbutton.FloatingActionButton

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

        // Ocultar FAB del Activity
        activity?.findViewById<FloatingActionButton>(R.id.btn_agregar_permiso)?.hide()
        configurarMenuDesplegable()
        botones()
    }
    private fun configurarMenuDesplegable() {
        val opciones = arrayOf("Licencia por luto", "Cita médica", "Personal", "Calamidad Doméstica")
        binding.TipoPermiso.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Seleccione un tipo de permiso")

            var indiceSeleccionado = 0
            builder.setSingleChoiceItems(opciones, indiceSeleccionado) { _, which ->
                indiceSeleccionado = which
            }
            builder.setPositiveButton("Aceptar") { dialog, _ ->
                binding.TipoPermiso.setText(opciones[indiceSeleccionado])
                dialog.dismiss()
            }
            builder.setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }
            builder.create().show()
        }

    }

    private fun botones() {
        binding.btnCerrar.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.tvAdjuntar.setOnClickListener {
            abrirSelectorDeArchivos()
        }

        binding.btnGuardar.setOnClickListener {
            val tipo = binding.TipoPermiso.text.toString()
            val desc = binding.Descripcion.text.toString()

            if (tipo.isEmpty() || desc.isEmpty()) {
                Toast.makeText(requireContext(), "Por favor completa los campos", Toast.LENGTH_SHORT).show()
            } else if (archivoseleccionadoU == null) {
                Toast.makeText(requireContext(), "Debes adjuntar un soporte (JPG, PNG o PDF)", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Enviando: $tipo", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun abrirSelectorDeArchivos() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "*/*"
            val mimeTypes = arrayOf("image/jpeg", "image/png", "application/pdf")
            putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            addCategory(Intent.CATEGORY_OPENABLE)
        }
        lanzadorArchivo.launch(intent)
    }

    override fun onDestroyView() {
        activity?.findViewById<FloatingActionButton>(R.id.btn_agregar_permiso)?.show()
        super.onDestroyView()
        _binding = null
    }
}