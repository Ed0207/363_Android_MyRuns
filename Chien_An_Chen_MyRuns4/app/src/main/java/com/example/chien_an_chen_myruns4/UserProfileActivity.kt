package com.example.chien_an_chen_myruns4

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.FileProvider
import java.io.File

class UserProfileActivity : AppCompatActivity() {

    private val SHARE_PREF:String = "userProfile"
    private val USERNAME:String = "userName"
    private val USEREMAIL:String = "userEmail"
    private val USERPHONE:String = "userPhone"
    private val USERCOURSE:String = "userCourse"
    private val USERMAJOR:String = "userMajor"

    // Image and camera variable
    private lateinit var picURI: Uri
    private lateinit var cameraPhoto: ActivityResultLauncher<Intent>
    private lateinit var imageView : ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        val imgFile = File(getExternalFilesDir(null), "profilePic.jpg")
        picURI = FileProvider.getUriForFile(this,"com.example.chien_an_chen_myruns4" , imgFile)

        if(imgFile.exists()){
            val bitmap = Util.getBitmap(this, picURI)
            imageView.setImageBitmap(bitmap)
        }

        loadProfileInput()

        // Setting up onclick listener
        val changeButton: Button     = findViewById(R.id.changeButton)
        val saveButton: Button      = findViewById(R.id.userProfileSave)
        val cancelButton: Button    = findViewById(R.id.userProfileCancel)

        cancelButton.setOnClickListener{
            finish()
        }

        saveButton.setOnClickListener{
            saveProfileInput()
            finish()
        }

        changeButton.setOnClickListener{

        }
    }





    // For saving previuos user profile input
    private fun saveProfileInput(){

        // setting up share_preference
        val sharePref = getSharedPreferences(SHARE_PREF, MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharePref.edit()
        editor.clear()

        val userName: EditText = findViewById(R.id.userName)
        val userEmail: EditText = findViewById(R.id.userEmail)
        val userPhone: EditText = findViewById(R.id.userPhone)
        val userCourse: EditText = findViewById(R.id.userCourse)
        val userMajor: EditText = findViewById(R.id.userMajor)

        // more data to be saved

        editor.putString(USERNAME, userName.text.toString())
        editor.putString(USEREMAIL,userEmail.text.toString())
        editor.putString(USERPHONE,userPhone.text.toString())
        editor.putString(USERCOURSE,userCourse.text.toString())
        editor.putString(USERMAJOR,userMajor.text.toString())

        println("data saved")
        editor.commit()
    }

    // Load up the previously entered data from last session
    private fun loadProfileInput(){
        val sharePref = getSharedPreferences(SHARE_PREF, MODE_PRIVATE)

        val userName: EditText = findViewById(R.id.userName)
        val userEmail: EditText = findViewById(R.id.userEmail)
        val userPhone: EditText = findViewById(R.id.userPhone)
        val userCourse: EditText = findViewById(R.id.userCourse)
        val userMajor: EditText = findViewById(R.id.userMajor)

        userName.setText(sharePref.getString(USERNAME, ""))
        userEmail.setText(sharePref.getString(USEREMAIL, ""))
        userPhone.setText(sharePref.getString(USERPHONE, ""))
        userCourse.setText(sharePref.getString(USERCOURSE, ""))
        userMajor.setText(sharePref.getString(USERMAJOR, ""))

        println("data loaded")
    }
}