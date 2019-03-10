package com.example.orderfoodnow;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.orderfoodnow.Common.Common;
import com.example.orderfoodnow.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class signUp extends AppCompatActivity {

    Button btnSignUp;
    EditText edtPhone, edtPassword, edtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        edtPassword = (MaterialEditText) findViewById(R.id.edtPassword);
        edtPhone = (MaterialEditText) findViewById(R.id.edtPhone);
        edtName = (MaterialEditText) findViewById(R.id.edtName);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference tabel_user = database.getReference("user");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog mDialog = new ProgressDialog(signUp.this);
                mDialog.setMessage("Please Waiting...");
                mDialog.show();
                if(edtName.getText().length() == 0 || edtName.getText() == null){
                    mDialog.dismiss();
                    Toast.makeText(signUp.this, "Please Enter Your Name", Toast.LENGTH_SHORT).show();
                }else {
                    if(edtPhone.getText().length() < 11) {
                        mDialog.dismiss();
                        Toast.makeText(signUp.this, "Please  Enter valid Phone Number", Toast.LENGTH_SHORT).show();
                    }else{
                        if (edtPassword.getText() == null || edtPassword.getText().length() == 0) {
                            mDialog.dismiss();
                            Toast.makeText(signUp.this, "Please  Enter the Password", Toast.LENGTH_SHORT).show();
                        } else {
                            tabel_user.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    // get the user information
                                    mDialog.dismiss();
                                    if (!dataSnapshot.child(edtPhone.getText().toString()).exists()) {
                                        User user = new User(edtName.getText().toString() , edtPassword.getText().toString());
                                        tabel_user.child(edtPhone.getText().toString()).setValue(user);
                                        // naviagate to home page through activity
                                        user.setPhone( edtPhone.getText().toString());
                                        Common.currentUser = user;
                                        Toast.makeText(signUp.this, "SignUp Successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(signUp.this , Home.class);
                                        startActivity( intent );

                                    } else {
                                        Toast.makeText(signUp.this, "User already exists", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    mDialog.dismiss();
                                }
                            });
                        }
                    }
                }
            }
        });
    }
}
