package com.unwantedapps

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.unwantedapps.model.DisplayImagesActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class MainActivity : AppCompatActivity() {
    var Storage_Path = "All_Image_Uploads/"
    var ChooseButton: Button? = null
    var UploadButton: Button? = null
    var bt_your_phone_apps:Button?=null
    var DisplayImageButton: Button? = null
    var appName: EditText? = null
    var appDesc: EditText? = null
    var appPackage: EditText? = null
    var SelectImage: ImageView? = null
    var FilePathUri: Uri? = null
    lateinit var storageReference: StorageReference
    lateinit var databaseReference: DatabaseReference

    // Image request code for onActivityResult() .
    var Image_Request_Code = 7
    var progressDialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseApp.initializeApp(this@MainActivity)
        storageReference = FirebaseStorage.getInstance().reference
        databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path)
        DisplayImageButton =
            findViewById<View>(R.id.DisplayImagesButton) as Button

        bt_your_phone_apps=findViewById(R.id.bt_your_phone_apps) as Button
        // Assign ID'S to image view.

        // Assigning Id to ProgressDialog.
        progressDialog = ProgressDialog(this@MainActivity)

        // Adding click listener to Choose image button.



        // Adding click listener to Upload image button.

        DisplayImageButton!!.setOnClickListener {
            val intent = Intent(this@MainActivity, DisplayImagesActivity::class.java)
            startActivity(intent)
        }
        bt_your_phone_apps!!.setOnClickListener {
            val intent = Intent(this@MainActivity, Main2Activity::class.java)
            startActivity(intent)
        }

    }



    // Creating Method to get the selected image file Extension from File Path URI.


    // Creating UploadImageFileToFirebaseStorage method to upload image on storage.


    companion object {
        // Root Database Name for Firebase Database.
        const val Database_Path = "All_Image_Uploads_Database"
    }
}
