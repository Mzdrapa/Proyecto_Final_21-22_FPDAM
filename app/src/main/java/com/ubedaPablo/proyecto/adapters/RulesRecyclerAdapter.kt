package com.ubedaPablo.proyecto.adapters

import android.content.Context
import android.content.Intent
import android.content.res.AssetManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.RecyclerView
import com.ubedaPablo.proyecto.R
import java.io.*

class RulesRecyclerAdapter : RecyclerView.Adapter<RulesRecyclerAdapter.ViewHolder>() {

    private var files: MutableList<String> = ArrayList()
    lateinit var context: Context

    fun rulesRecyclerAdapterBuilder(
        files: MutableList<String>,
        context: Context
    ) {
        this.files = files
        this.context = context
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = files[position]
        holder.bind(item, context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_rules_list, parent, false))
    }

    override fun getItemCount(): Int {
        return files.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title = view.findViewById(R.id.txtTitle) as TextView

        fun bind(fileString: String, context: Context) {
            title.text = fileString
            itemView.setOnClickListener {
                //No necesito permisos porque son archivos que se crea la misma aplicación y en el directorio de la aplicación
                copyAssets(context, fileString)
                val file = File(context.getExternalFilesDir(null), fileString)
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    FileProvider.getUriForFile(
                        context,
                        context.applicationContext.packageName + ".provider",
                        file
                    )
                )
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                context.startActivity(intent)
            }
        }

        private fun copyAssets(context: Context, fileToCopy: String) {
            val assetManager: AssetManager = context.assets
            try {
                var inputStream: InputStream? = null
                var outputStream: OutputStream? = null
                try {
                    inputStream = assetManager.open("rulesPDF/$fileToCopy")
                    val outFile = File(context.getExternalFilesDir(null), fileToCopy)
                    outputStream = FileOutputStream(outFile)
                    copyFile(inputStream, outputStream)
                } catch (e: IOException) {
                    Toast.makeText(
                        context,
                        "Error, no se puede abrir el archivo",
                        Toast.LENGTH_LONG
                    ).show()
                } finally {
                    inputStream?.close()
                    outputStream?.close()
                }
            } catch (e: IOException) {
                Log.e("tag", "Ha habido un fallo", e)
            }

        }

        @Throws(IOException::class)
        private fun copyFile(inputStream: InputStream?, outputStream: OutputStream) {
            val buffer = ByteArray(1024)
            var read: Int?
            while (inputStream?.read(buffer).also { read = it!! } != -1) {
                read?.let { outputStream.write(buffer, 0, it) }
            }
        }

    }
}