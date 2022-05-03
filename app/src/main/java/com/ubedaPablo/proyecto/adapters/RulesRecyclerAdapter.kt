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

    var files: MutableList<String> = ArrayList()
    lateinit var context: Context

    fun RulesRecyclerAdapter(files: MutableList<String>, context: Context) {
        this.files = files
        this.context = context
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = files.get(position)
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
        val title = view.findViewById(R.id.txtTitle) as TextView

        fun bind(fileString: String, context: Context) {
            title.text = fileString
            itemView.setOnClickListener(View.OnClickListener {
                copyAssets(context);

                val file = File(context.getExternalFilesDir(null), fileString)
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    FileProvider.getUriForFile(
                        context,
                        context.applicationContext.packageName + ".provider",
                        file
                    )
                )
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                context.startActivity(intent)
            })
        }

        fun copyAssets(context: Context) {
            //TODO Change to only desired pdf
            val assetManager: AssetManager = context.assets
            val files: Array<String>?
            try {
                files = assetManager.list("rulesPDF")

                if (files != null) for (filename in files) {
                    var inputStream: InputStream? = null
                    var outputStream: OutputStream? = null
                    inputStream.use { }
                    try {
                        inputStream = assetManager.open("rulesPDF/$filename")
                        val outFile = File(context.getExternalFilesDir(null), filename)
                        outputStream = FileOutputStream(outFile)
                        copyFile(inputStream, outputStream)
                    } catch (e: IOException) {
                        Log.e("File", "Failed to copy asset file: $filename", e)
                        Toast.makeText(context, "Error, no se puede abrir el archivo", Toast.LENGTH_LONG).show()
                    } finally {
                        inputStream?.close()
                        outputStream?.close()
                    }
                }

            } catch (e: IOException) {
                Log.e("tag", "Failed to get asset file list.", e)
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