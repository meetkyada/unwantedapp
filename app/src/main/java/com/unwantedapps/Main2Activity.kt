package com.unwantedapps

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.unwantedapps.adapter.RecyclerViewAdapter
import com.unwantedapps.customeclass.RecyclerTouchListener
import com.unwantedapps.model.ImageUploadInfo
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_display_images.*
import java.util.*


class Main2Activity : AppCompatActivity() {
    // Creating DatabaseReference.
    var databaseReference: DatabaseReference? = null
    var UNINSTALL_REQUEST_CODE = 1
    // Creating RecyclerView.
    var recyclerView: RecyclerView? = null

    var imageView: ImageView? = null
    var textView: TextView? = null
    var lottieAnimationView: LottieAnimationView? = null


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

        imageView = findViewById(R.id.logo)
        textView = findViewById(R.id.goodtext)

        lottieAnimationView = findViewById(R.id.main2Activity)


        displayActivty.visibility=View.GONE
        main2Activity.visibility=View.VISIBLE

        // Assign id to RecyclerView.
        recyclerView = findViewById<View>(R.id.recyclerView) as RecyclerView

        // Setting RecyclerView size true.
        recyclerView!!.setHasFixedSize(true)

        // Setting RecyclerView layout as LinearLayout.
        recyclerView!!.layoutManager = LinearLayoutManager(this@Main2Activity)

        // Assign activity this to progress dialog.
        progressDialog = ProgressDialog(this@Main2Activity)

        // Setting up message in Progress dialog.
        progressDialog!!.setMessage("Scanning Your Device...... ")

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


                    val isInstalled: Boolean =
                        isPackageInstalled(imageUploadInfo!!.appPackage, getPackageManager())
                    if (isInstalled) // Install
                    {
                        list.add(imageUploadInfo)
                    } else
                    // Not install

                    {
                    }


                }
                if(list.size >0)
                {
                    recyclerView!!.visibility=View.VISIBLE
                    uni!!.visibility=View.VISIBLE
                }else
                {
                    recyclerView!!.visibility=View.GONE
                    main2Activity!!.visibility=View.GONE
                 /// imageview visible
                    imageView!!.visibility=View.VISIBLE
                    textView!!.visibility=View.VISIBLE

                    greatwork!!.visibility=View.VISIBLE



                }


                adapter = RecyclerViewAdapter(applicationContext, list)
                recyclerView!!.adapter = adapter

                recyclerView!!.addOnItemTouchListener(
                    RecyclerTouchListener(
                        this@Main2Activity,
                        recyclerView,
                        object : RecyclerTouchListener.ClickListener {
                            override fun onClick(view: View?, position: Int) {

                                val intent = Intent(Intent.ACTION_UNINSTALL_PACKAGE)
                                intent.data = Uri.parse("package:" +list[position]!!.appPackage)
                                intent.putExtra(Intent.EXTRA_RETURN_RESULT, true)
                                startActivityForResult(intent, UNINSTALL_REQUEST_CODE)


                            }

                            override fun onLongClick(view: View?, position: Int) {
                            }
                        })
                )




                // Hiding the progress dialog.
                progressDialog!!.dismiss()
            }

            override fun onCancelled(databaseError: DatabaseError) {

                // Hiding the progress dialog.
                progressDialog!!.dismiss()
            }
        })
    }


    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == UNINSTALL_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Log.d("TAG", "onActivityResult: user accepted the (un)install")
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.d("TAG", "onActivityResult: user canceled the (un)install")
            } else if (resultCode == Activity.RESULT_FIRST_USER) {
                Log.d("TAG", "onActivityResult: failed to (un)install")
            }
        }
    }

    fun isPackageInstalled(
        packageName: String?,
        packageManager: PackageManager
    ): Boolean {
        return try {
            packageManager.getApplicationInfo(packageName, 0).enabled
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }
}
