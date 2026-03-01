package com.curso.android.module5.aichef.util

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import com.curso.android.module5.aichef.domain.model.Recipe
import java.io.File
import java.io.FileOutputStream
import androidx.core.graphics.createBitmap

object ShareUtils {

    fun shareRecipe(context: Context, recipe: Recipe, recipeImage: Bitmap?) {
        // Crea una vista de Compose "invisible" en memoria
        val composeView = ComposeView(context).apply {
            setContent {
                MaterialTheme {
                    //LLAMAMOS el Diseño
                    RecipeShareLayout(recipe = recipe, recipeImage = recipeImage)
                }
            }
        }

        //1080 x 1920
        val width = 1080
        val height = 1920
        composeView.measure(
            View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)
        )
        composeView.layout(0, 0, width, height)

        //Tomamos la captura de pantalla al diseño
        val bitmap = createBitmap(width, height)
        val canvas = Canvas(bitmap)
        composeView.draw(canvas)

        // Guarda temp en caché
        val cachePath = File(context.cacheDir, "images")
        cachePath.mkdirs()
        val file = File(cachePath, "shared_recipe.png")
        val stream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        stream.close()

        // Obtenemos URI y share
        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )

        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, uri)
            putExtra(Intent.EXTRA_TEXT, "¡Mira esta increíble receta generada por IA: ${recipe.title}!")
            type = "image/png"
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        context.startActivity(Intent.createChooser(shareIntent, "Compartir Receta"))
    }
}

/**
 * Diseño
 */
@Composable
private fun RecipeShareLayout(recipe: Recipe, recipeImage: Bitmap?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(48.dp)
    ) {
        //if imagen la muestra
        recipeImage?.let { bitmap ->
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .padding(bottom = 24.dp)
            )
        }

        Text(
            text = recipe.title,
            fontSize = 42.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Ingredientes:",
            fontSize = 28.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.DarkGray
        )

        Spacer(modifier = Modifier.height(16.dp))

        recipe.ingredients.take(10).forEach { ingredient ->
            Text(
                text = "• $ingredient",
                fontSize = 24.sp,
                color = Color.Gray,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        //marca de agua
        Text(
            text = "Generado por AI Chef App. Buon appetito 👨‍🍳",
            fontSize = 20.sp,
            color = Color.LightGray,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}