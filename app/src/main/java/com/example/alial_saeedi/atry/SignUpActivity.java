package com.example.alial_saeedi.atry;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by alialsaeedi on 11/7/16.
 */

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText nameText;
    private EditText emailText;
    private EditText passwordText;
    private EditText reEnterPass;
    private Button createAccountButton;
    private TextView loginView;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "EmailPassword";
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private String adminPass = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        createAccountButton = (Button) findViewById(R.id.btn_signup);
        nameText = (EditText) findViewById(R.id.input_name);
        emailText = (EditText) findViewById(R.id.input_email);
        passwordText = (EditText) findViewById(R.id.input_password);
        reEnterPass = (EditText) findViewById(R.id.input_reEnterPassword);
        loginView = (TextView) findViewById(R.id.login_view);

        loginView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            goToLogin();
            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        firebaseAuth = FirebaseAuth.getInstance();
        createAccountButton.setOnClickListener(this);
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

    }

    private void goToLogin() {
        finish();
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if (view == createAccountButton) {
            createAccount();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            firebaseAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private boolean validate() {
        boolean valid = true;
        String email = emailText.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailText.setError("Email should be filled!!");
            valid = false;
        }
        String pass = passwordText.getText().toString();
        if (TextUtils.isEmpty(pass)) {
            passwordText.setError("Password should be filled!!");
            valid = false;
        }
        String conformPass = reEnterPass.getText().toString();
        if (TextUtils.isEmpty(conformPass)) {
            reEnterPass.setError("Conform Password should be done!!");
            valid = false;
        }
        String name = nameText.getText().toString();
        if (TextUtils.isEmpty(name)) {
            nameText.setError("Name should be filled!!");
            valid = false;
        }
        if (!(TextUtils.equals(conformPass, pass))) {
            reEnterPass.setError("The passwords doesn't match!!");
            valid = false;
        }
        return valid;

    }

    public void createAccount() {
        String email = emailText.getText().toString();
        final String password = passwordText.getText().toString();
        final String name = nameText.getText().toString();
        if (validate()) {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                            if (!task.isSuccessful()) {
                                Toast.makeText(SignUpActivity.this, R.string.auth_failed,
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                savePasswordCache(password);
                                FirebaseUser user = task.getResult().getUser();

                                DatabaseReference myRef = databaseReference.child(user.getUid());
                                myRef.child("Name").setValue(name);
                                myRef.child("Email").setValue(user.getEmail());
//                                myRef.child("Events");

//                                firebaseAuth.signOut();

                                Intent main = new Intent(SignUpActivity.this, MainBoardUser.class);
                                startActivity(main);
                            }


                            // ...
                        }
                    });
        }
    }

    private void savePasswordCache(String data) {
        File file;
        FileOutputStream outputStream;
        try {
            // file = File.createTempFile("MyCache", null, getCacheDir()); //pass getFilesDir() to save file
            file = new File(getCacheDir(), "PasswordCache");

            outputStream = new FileOutputStream(file);
            outputStream.write(data.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private void readPasswordCache() {
//        BufferedReader input = null;
//        File file = null;
//        try {
//            file = new File(getCacheDir(), "PasswordCache"); // Pass getFilesDir() to read file
//
//            input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
//            String line;
//            StringBuffer buffer = new StringBuffer();
//            while ((line = input.readLine()) != null) {
//                buffer.append(line);
//            }
//
//            Log.d(TAG, buffer.toString());
//            password = buffer.toString();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}
