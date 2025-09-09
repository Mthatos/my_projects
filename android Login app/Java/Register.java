package com.example.hustle;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    EditText strFirstName, strLastName,strEmail, strPhone,strPassword, strConfirmPassword, strDOB;
    Button btnLogin, btnRegister;

    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private static final String USER = "user";
    private static final String TAG = "Register";
    private User user;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        strFirstName = findViewById(R.id.FirstName);
        strLastName = findViewById(R.id.LastName);
        strEmail = findViewById(R.id.Email);
        strPhone = findViewById(R.id.PhoneNo);
        strDOB = findViewById(R.id.DOB);
        strPassword = findViewById(R.id.Password);
        strConfirmPassword = findViewById(R.id.ConfirmPassword);
        btnLogin = findViewById(R.id.LoginButton);
        btnRegister = findViewById(R.id.RegisterButton);

        database = FirebaseDatabase.getInstance("https://hustle-bd21c-default-rtdb.asia-southeast1.firebasedatabase.app");
        mDatabase = database.getReference(USER);
        mAuth = FirebaseAuth.getInstance();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Register.this, Login.class);
                startActivity(i);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = strEmail.getText().toString();

                String password = strPassword.getText().toString();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Register.this, "All fields are required", Toast.LENGTH_SHORT).show();

                return;
                }


                String firstName = strFirstName.getText().toString();
                String lastName = strLastName.getText().toString();
                String phone = strPhone.getText().toString();
                String DOB = strDOB.getText().toString();
                String confirmPassword = strConfirmPassword.getText().toString();
            if(firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty() || DOB.isEmpty() || confirmPassword.isEmpty()) {
                                               Toast.makeText(Register.this, "All fields are required", Toast.LENGTH_SHORT).show();
                                           }
            else if(!confirmPassword.equals(password)){
                                               Toast.makeText(Register.this, "The passwords do not match", Toast.LENGTH_SHORT).show();
                                           }
            user = new User(email, password, firstName, lastName, phone, DOB);
            RegisterUser(email, password);
            }

            });

    }

public void RegisterUser(String email, String password){
    mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "CreateUserWithEmail:Success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                        Toast.makeText(Register.this, "Registration successful",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());

                        // 1. Get the specific error message
                        String errMessage = "";
                        if (task.getException() != null) {
                            errMessage = task.getException().getMessage();
                        }

                        // 2. Display the error message in a Toast
                        Toast.makeText(Register.this, "Authentication failed: " + errMessage,
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
}

public void updateUI(FirebaseUser currentUser){
        String uid = currentUser.getUid();

        mDatabase.child(uid).setValue(user);

        Intent loginIntent = new Intent(this, MainActivity.class);
        startActivity(loginIntent);
}

}