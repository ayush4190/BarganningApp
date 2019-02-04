package com.example.ayush.finalapp;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.L;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginPage extends AppCompatActivity {
    EditText email, password;
    FirebaseAuth firebaseAuth;
    Button button;
    FirebaseUser user;
    FirebaseDatabase data;
    DatabaseReference ref ,mroot;
    String s;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        firebaseAuth = FirebaseAuth.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow ();
            window.addFlags (WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor (getResources ().getColor (R.color.colorPrimary));
        }
        if (firebaseAuth.getCurrentUser() != null) {
//           if()
//            {
//                startActivity (new Intent (LoginPage.this,Negotiator_dash.class));
//            }
//            else
//            {
//                startActivity (new Intent (LoginPage.this,Negotiator_dash.class));
//            }


        }
        email =(EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        button = (Button)findViewById(R.id.login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userlogin();


            }
        });
        data = FirebaseDatabase.getInstance ();
        ref = FirebaseDatabase.getInstance ().getReference ();
        user =firebaseAuth.getCurrentUser();




    }
    private void userlogin()
    {
        String memail = email.getText().toString().trim();
        String mpassword = password.getText().toString().trim();

       firebaseAuth.signInWithEmailAndPassword(memail,mpassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful())

                {
                    Toast.makeText (LoginPage.this,"you are logged in in testing mode",Toast.LENGTH_SHORT).show ();
                   startActivity (new Intent (LoginPage.this,Negotiator_dash.class));
                }
                else
                {
                    Toast.makeText(LoginPage.this,"not a valid client",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}