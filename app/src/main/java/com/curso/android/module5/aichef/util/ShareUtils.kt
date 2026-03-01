package com.curso.android.module5.aichef.util

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.core.content.FileProvider
import com.curso.android.module5.aichef.domain.model.Recipe
import java.io.File
import java.io.FileOutputStream

object ShareUtils {

    fun shareRecipe(context: Context, recipe: Recipe, bitmap: Bitmap) {
        try {
            //Guarda el bitmap en caché temporal
            val cachePath = File(context.cacheDir, "images")
            cachePath.mkdirs()
            val file = File(cachePath, "shared_recipe.png")
            val stream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            stream.close()

            //Obtenemos URI segura con el FileProvider
            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                file
            )

            // Construimos el texto completo de la receta con formato para WhatsApp
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

            // Lanzamos el Intento de compartir
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM, uri)
                putExtra(Intent.EXTRA_TEXT, fullRecipeText) // Pasamos todo el texto acá
                type = "image/png"
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            context.startActivity(Intent.createChooser(shareIntent, "Compartir Receta"))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}