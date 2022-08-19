package ramizbek.aliyev.firebasechatapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.btnLogin
import kotlinx.android.synthetic.main.activity_sign_up.btnSignUp
import kotlinx.android.synthetic.main.activity_sign_up.etEmail
import kotlinx.android.synthetic.main.activity_sign_up.etPassword
import ramizbek.aliyev.firebasechatapp.R

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()

//        if (auth.currentUser != null) {
//            val intent = Intent(this@SignUpActivity, UsersActivity::class.java)
//            startActivity(intent)
//            finish()
//        }

        btnSignUp.setOnClickListener {
            val userName = etName.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()

            if (TextUtils.isEmpty(userName)) {
                Toast.makeText(applicationContext, "username is required", Toast.LENGTH_SHORT)
                    .show()
            }

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(applicationContext, "email is required", Toast.LENGTH_SHORT).show()
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(applicationContext, "password is required", Toast.LENGTH_SHORT)
                    .show()
            }

            if (TextUtils.isEmpty(confirmPassword)) {
                Toast.makeText(
                    applicationContext,
                    "confirm password is required",
                    Toast.LENGTH_SHORT
                ).show()
            }

            if (password == confirmPassword) {
                registerUser(userName, email, password)
                Toast.makeText(applicationContext, "successfully registered", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(applicationContext, "confirm password is not equal to password", Toast.LENGTH_SHORT).show()
            }

        }
        btnLogin.setOnClickListener {
            val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun registerUser(userName: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    val user: FirebaseUser? = auth.currentUser
                    val userId: String = user!!.uid

                    databaseReference =
                        FirebaseDatabase.getInstance().getReference("Users").child(userId)

                    val hashMap: HashMap<String, String> = HashMap()
                    hashMap.put("userId", userId)
                    hashMap.put("userName", userName)
                    hashMap.put("profileImage", "")

                    databaseReference.setValue(hashMap).addOnCompleteListener(this) {
                        if (it.isSuccessful) {
                            //open home activity
                            etName.setText("")
                            etEmail.setText("")
                            etPassword.setText("")
                            etConfirmPassword.setText("")
                            val intent = Intent(this@SignUpActivity, UsersActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }
            }
    }
}