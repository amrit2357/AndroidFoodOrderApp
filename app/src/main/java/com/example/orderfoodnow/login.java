package com.example.orderfoodnow;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.orderfoodnow.Common.Common;
import com.example.orderfoodnow.Model.User;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class login extends AppCompatActivity {
    Button btnLogin;
    EditText edtPhone , edtPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button)findViewById(R.id.btnLogin);
        edtPhone =  (MaterialEditText) findViewById(R.id.edtPhone);
        edtPassword = (MaterialEditText) findViewById(R.id.edtPassword);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference tabel_user = database.getReference("user");
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog mDialog = new ProgressDialog(login.this);
                mDialog.setMessage("Please Waiting...");
                mDialog.show();
                

                if(edtPhone.getText().length() < 11){
                    mDialog.dismiss();
                    Toast.makeText(login.this, "Please  Enter valid Phone Number", Toast.LENGTH_SHORT).show();
                }else{
                    if(edtPassword.getText()== null || edtPassword.getText().length()==0){
                        mDialog.dismiss();
                        Toast.makeText(login.this, "Please  Enter the Password", Toast.LENGTH_SHORT).show();
                    }else {
                        tabel_user.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                // get the user information
                                mDialog.dismiss();
                                if (dataSnapshot.child(edtPhone.getText().toString()).exists()) {

                                       User user = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);
                                        user.setPhone( edtPhone.getText().toString());
                                    if (user.getPassword().equals(edtPassword.getText().toString())) {

                                        Intent intent = new Intent(login.this,Home.class);
                                        Common.currentUser = user;
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(login.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(login.this, "User does not exists", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                mDialog.dismiss();
                            }
                        });
                    }
                }
                mDialog.dismiss();
            }
        });
    }
}

