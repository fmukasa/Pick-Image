package com.babyapplication.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.babyapplication.MainActivity
import com.babyapplication.R
import com.babyapplication.model.Teacher
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import kotlinx.android.synthetic.main.activity_upload.*


class UploadActivity : AppCompatActivity() {

    private  var  mImageUri : Uri? = null
    private var mStorage: FirebaseStorage? = null
    private var mStorageRef : StorageReference? = null
    private var mDatabaseRef : DatabaseReference? = null
    private var mUploadTask : StorageTask<*>? = null
    private val PICK_IMAGE_REQUEST = 1

   private lateinit var button_choose_image: Button
   private lateinit var upLoadBtn : Button
    private lateinit var nameEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var chooseImageView: ImageView
  //  private lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)


        button_choose_image = findViewById(R.id.button_choose_image)
        upLoadBtn = findViewById(R.id.upLoadBtn)

        nameEditText = findViewById(R.id.nameEditText)
        descriptionEditText = findViewById(R.id.descriptionEditText)
        chooseImageView = findViewById(R.id.chooseImageView)
        
        mStorage = FirebaseStorage.getInstance()
        mStorageRef = FirebaseStorage.getInstance().getReference("TeacherImage")
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Teacher")

        chooseImageView.setOnClickListener { openFileChooser() }
        button_choose_image.setOnClickListener { openFileChooser() }
        upLoadBtn.setOnClickListener {
            if (mUploadTask != null && mUploadTask!!.isInProgress) {
                Toast.makeText(this@UploadActivity,
                    "An Upload is still in Progress", Toast.LENGTH_SHORT).show()
            } else {
                uploadFile()
            }
        }
    }
   // private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
  //  { result: ActivityResult ->
       // if (result.resultCode == Activity.RESULT_OK) {
          //  val intent = result.data
          //  mImageUri = intent!!.data
          //  chooseImageView.setImageURI(mImageUri)
            // Handle the Intent
      //  }
    //}

   // private val openFileChooserForResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
   // { result: ActivityResult ->
      //  if (result.resultCode == Activity.RESULT_OK) {
        //    val intent = result.data
          //  mImageUri = intent!!.data
          //  chooseImageView.setImageURI(mImageUri)
            // handle image from gallery
       // }
   // }

    @Suppress("DEPRECATION")
    private fun openFileChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
       // startForResult.launch(intent)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
       //  openFileChooserForResultLauncher.launch(intent)
    }

   // private fun openFileChooser() {
      //  val pickIntent = Intent(Intent.ACTION_GET_CONTENT)
          //   pickIntent.setDataAndType(
            // MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
            // openFileChooserForResultLauncher.launch(pickIntent)
  // }


    @Suppress("DEPRECATION")
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
            && data != null && data.data != null){
            mImageUri = data.data
            chooseImageView.setImageURI(mImageUri)
        }
    }

    private fun getFileExtension(uri : Uri):String? {
        val cR = contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cR.getType(uri))
    }

    private fun uploadFile() {
        if (mImageUri != null){
            val fileReference = mStorageRef!!.child(
                System.currentTimeMillis().toString() +"."+ getFileExtension(mImageUri!!))
            progressBar.visibility = View.VISIBLE
            progressBar.isIndeterminate = true
            mUploadTask = fileReference.putFile(mImageUri!!)
                .addOnSuccessListener {
                    Handler(Looper.getMainLooper()).postDelayed(
                        {
                            progressBar.visibility = View.VISIBLE
                            progressBar.isIndeterminate = false
                            progressBar.progress = 0
                        }
                        ,500)
                    Toast.makeText(this@UploadActivity, "File Upload Successful", Toast.LENGTH_SHORT).show()
                    val upload = Teacher(
                        nameEditText.text.toString().trim { it <= ' ' },
                        mImageUri.toString(),
                        // descriptionEditText.text.toString().trim {it <= ' ' },
                        descriptionEditText.text.toString()


                    )
                    val uploadId = mDatabaseRef!!.push().key
                    mDatabaseRef!!.child(uploadId!!).setValue(upload)
                    progressBar.visibility = View.INVISIBLE
                    openImageActivity()
                }
                .addOnFailureListener { e->
                    progressBar.visibility = View.INVISIBLE
                    Toast.makeText(this@UploadActivity, e.message, Toast.LENGTH_SHORT).show()
                }
                .addOnProgressListener { tastSnpshot->
                    val mProgess = (100.0 * tastSnpshot.bytesTransferred/tastSnpshot.totalByteCount)
                    progressBar.progress = mProgess.toInt()
                }
        }else{
            Toast.makeText(this@UploadActivity,"You haven't selected any File", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openImageActivity() {
        startActivity(Intent(this@UploadActivity, MainActivity::class.java))
    }
}