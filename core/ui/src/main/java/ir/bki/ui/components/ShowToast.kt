package ir.bki.ui.components

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable

@Composable
fun showToast(
    context: Context,
    message: String,
) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
