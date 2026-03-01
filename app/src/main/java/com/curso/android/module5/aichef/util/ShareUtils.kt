package com.curso.android.module5.aichef.util

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri // IMPORTANTE: No olvides esta importación
import androidx.core.content.FileProvider
import com.curso.android.module5.aichef.domain.model.Recipe
import java.io.File
import java.io.FileOutputStream

object ShareUtils {
    fun shareRecipe(context: Context, recipe: Recipe, bitmaps: List<Bitmap>) {
        try {
            val cachePath = File(context.cacheDir, "images").apply { mkdirs() }
            val uris = arrayListOf<Uri>()

            // Guardamos cada una de las 3 partes
            bitmaps.forEachIndexed { index, bitmap ->
                val file = File(cachePath, "recipe_part_$index.png")
                FileOutputStream(file).use { stream ->
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                }
                val uri = FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.fileprovider",
                    file
                )
                uris.add(uri)
            }

            // Construimos el texto enriquecido para WhatsApp
            val fullRecipeText = buildString {
                appendLine("🍳 *${recipe.title}*")
                appendLine()
                appendLine("🛒 *Ingredientes:*")
                recipe.ingredients.forEach { appendLine("• $it") }
                appendLine()
                appendLine("👨‍🍳 *Preparación:*")
                recipe.steps.forEachIndexed { index, step ->
                    appendLine("${index + 1}. $step")
                }
                appendLine()
                appendLine("✨ Generado por AI Chef App")
            }

            //  Configuramos el Intent para enviar múltiples archivos y el texto
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND_MULTIPLE
                putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris)
                // IMPORTANTE: Aquí pasamos el 'fullRecipeText' que construimos arriba
                putExtra(Intent.EXTRA_TEXT, fullRecipeText)
                type = "image/png"
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            context.startActivity(Intent.createChooser(shareIntent, "Compartir Receta"))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}