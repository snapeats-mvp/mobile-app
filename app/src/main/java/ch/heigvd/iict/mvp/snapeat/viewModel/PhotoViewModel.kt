package ch.heigvd.iict.mvp.snapeat.viewModel

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class PhotoViewModel : ViewModel() {

    var selectedImageUri by mutableStateOf<Uri?>(null)
        private set

    var capturedBitmap by mutableStateOf<Bitmap?>(null)
        private set

    fun setSelectedImage(uri : Uri?){
        selectedImageUri = uri
    }

    fun setCapturedBitmap(bitmap : Bitmap?){
        capturedBitmap = bitmap
    }
}