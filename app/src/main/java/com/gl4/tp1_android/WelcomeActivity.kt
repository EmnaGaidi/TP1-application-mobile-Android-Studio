package com.gl4.tp1_android

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import android.Manifest
import android.app.Activity
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat

class WelcomeActivity : AppCompatActivity() {
    lateinit var txtWelcome : TextView
    private lateinit var imagePicker: ActivityResultLauncher<Intent>
    private lateinit var imageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        val email = intent.getStringExtra("email")
        txtWelcome = findViewById(R.id.WelcomeView)
        txtWelcome.text = "Bienvenue $email"
        txtWelcome.setTypeface(null, Typeface.BOLD)

        imageView = findViewById(R.id.imageView2)
        val buttonOpenGallery = findViewById<Button>(R.id.button3)

        // Vérifiez si la permission d'accéder au stockage externe est accordée
        // PackageManager.PERMISSION_GRANTED est une constante qui indique que l'autorisation est accordée.
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // Demandez la permission si elle n'est pas déjà accordée
            // arrayOf : C'est un tableau contenant les autorisations que vous demandez. Dans ce cas, il s'agit de l'autorisation de lecture du stockage externe.
            // 1 : C'est un code de demande qui vous permettra de gérer la réponse de l'utilisateur lorsque la permission est demandée. Vous utiliserez ce code de demande pour identifier la réponse lorsque l'utilisateur prend une décision.
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                1
            )
        }

        // Créez un gestionnaire de résultats d'activité avec registerForActivityResult
        // La fonction de rappel définie dans registerForActivityResult est exécutée lorsqu'une réponse est reçue de l'activité de la galerie de photos.
        imagePicker = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            // Cela signifie que l'activité externe (dans ce cas, la galerie de photos) s'est terminée avec succès, et que l'utilisateur a sélectionné une image.
            if (result.resultCode == Activity.RESULT_OK) {
                // Cet intent contient des informations sur l'image sélectionnée par l'utilisateur.
                val data: Intent? = result.data
                // Gérez ici le résultat de l'activité de la galerie
                if (data != null) {
                    // vous obtenez l'URI de l'image à partir de data.data. L'URI est une référence à l'emplacement de l'image sélectionnée.
                    val selectedImageUri: Uri? = data.data
                    // Affichez l'image sélectionnée dans l'ImageView
                    imageView.setImageURI(selectedImageUri)
                }
            }
        }

        buttonOpenGallery.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            // Lorsque l'activité de la galerie de photos se termine, le gestionnaire de résultats d'activité que vous avez créé avec registerForActivityResult gérera la réponse.
            imagePicker.launch(intent)
        }

    }

}