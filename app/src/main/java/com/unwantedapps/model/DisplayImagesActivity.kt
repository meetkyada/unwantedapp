package com.unwantedapps.model

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.unwantedapps.MainActivity
import com.unwantedapps.R
import com.unwantedapps.adapter.RecyclerViewAdapter
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_display_images.*
import java.util.*

class DisplayImagesActivity : AppCompatActivity() {
    // Creating DatabaseReference.
    var databaseReference: DatabaseReference? = null

    // Creating RecyclerView.
    var recyclerView: RecyclerView? = null

    // Creating RecyclerView.Adapter.
    var adapter: RecyclerView.Adapter<*>? = null

    // Creating Progress dialog
    var progressDialog: ProgressDialog? = null

    // Creating List of ImageUploadInfo class.
    var list: MutableList<ImageUploadInfo?> =
        ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_images)

        displayActivty.visibility=View.VISIBLE
        main2Activity.visibility=View.GONE
        allchinatext.visibility=View.VISIBLE

        // Assign id to RecyclerView.
        recyclerView = findViewById<View>(R.id.recyclerView) as RecyclerView

        // Setting RecyclerView size true.
        recyclerView!!.setHasFixedSize(true)

        // Setting RecyclerView layout as LinearLayout.
        recyclerView!!.layoutManager = LinearLayoutManager(this@DisplayImagesActivity)

        // Assign activity this to progress dialog.
        progressDialog = ProgressDialog(this@DisplayImagesActivity)

        // Setting up message in Progress dialog.
        progressDialog!!.setMessage("Scanning Your Device......")

        // Showing progress dialog.
        progressDialog!!.show()

        // Setting up Firebase image upload folder path in databaseReference.
        // The path is already defined in MainActivity.
        databaseReference = FirebaseDatabase.getInstance().getReference(MainActivity.Database_Path)

        // Adding Add Value Event Listener to databaseReference.
        databaseReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {
                    Log.e("TAG", "Display Data " + postSnapshot.value)
                    val imageUploadInfo = postSnapshot.getValue(
                        ImageUploadInfo::class.java
                    )
                    list.add(imageUploadInfo)
                }
                adapter = RecyclerViewAdapter(applicationContext, list)
                recyclerView!!.adapter = adapter

                // Hiding the progress dialog.
                progressDialog!!.dismiss()
            }

            override fun onCancelled(databaseError: DatabaseError) {

                // Hiding the progress dialog.
                progressDialog!!.dismiss()
            }
        })
    }
}