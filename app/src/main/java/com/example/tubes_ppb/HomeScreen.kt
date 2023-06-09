package com.example.tubes_ppb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.NonNull
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInApi
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.tubes_ppb.ui.skins.allskins.SkinActivity
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class HomeScreen : AppCompatActivity(), View.OnClickListener {


    private lateinit var appBarConfiguration: AppBarConfiguration
    internal lateinit var profileCircleImageView: CircleImageView
    internal var profileImageUrl = ""

    companion object {
        const val EXTRA_UID = "extra_uid"
        const val EXTRA_PERSON = "extra_person"
        const val TAG = "Home Screen"
    }
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var storageref: StorageReference
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

//        val person = intent.getParcelableExtra<Person>(EXTRA_PERSON) as Person
//        val tvObject: TextView = findViewById(R.id.tv_object_received)
//        val text = "Name : ${person.username.toString()}\n" + "Riottag : ${person.tag.toString()} \nEmail : ${person.email.toString()}"
//        tvObject.text = text
        storageref = FirebaseStorage.getInstance().getReference()
        auth = Firebase.auth
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        userId = auth.currentUser!!.uid
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        val fileref: StorageReference = storageref.child("users/"+userId+"/profile.jpg")

        val btnAgent: Button = findViewById(R.id.agent_ui_button)
        btnAgent.setOnClickListener(this)

        val btnSkins: Button = findViewById(R.id.skins_ui_button)
        btnSkins.setOnClickListener(this)

        val btnLogout: Button = findViewById(R.id.logoutBtn)
        btnLogout.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.agent_ui_button -> {
                val agentIntent = Intent(this@HomeScreen, Agent::class.java)
                startActivity(agentIntent)
            }
            R.id.skins_ui_button -> {
                val moveIntent = Intent(this@HomeScreen, SkinActivity::class.java)
                startActivity(moveIntent)
            }
            R.id.logoutBtn -> {
                Log.i(TAG, "Logout")
                auth.signOut()
                googleSignInClient.signOut()
                //Auth.GoogleSignInApi.signOut()
                val logoutIntent = Intent(this, MainActivity::class.java)
                logoutIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(logoutIntent)
            }
        }
    }
}