package com.example.root.stayintouch;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import android.content.Intent;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class EditProfileActivity extends AppCompatActivity {

    TextView tvName;
    EditText etName,etEmail,etPhone,etPassword;
    ImageView profilePic;
    private int PICK_IMAGE_REQUEST = 1;
    String img_str;
    User loggedInUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


        tvName = (TextView) findViewById(R.id.textViewFullName);
        etName = (EditText) findViewById(R.id.editTextName);
        etEmail = (EditText) findViewById(R.id.editTextEmail);
        etPassword = (EditText) findViewById(R.id.editTextPassword);
        etPhone = (EditText) findViewById(R.id.editTextPhone);
        profilePic = (ImageView) findViewById(R.id.imageViewProfilePic);

        final Firebase myFirebaseRef = new Firebase(MainActivity.URL_PATH + "/users/");
        final Firebase UserRef = myFirebaseRef.child(myFirebaseRef.getAuth().getProviderData().get("email").toString().split("@")[0]);

        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    loggedInUser = dataSnapshot.getValue(User.class);
                    tvName.setText(loggedInUser.getName());
                    etName.setText(loggedInUser.getName());
                    etPhone.setText(loggedInUser.getPhone());
                    etPassword.setText(loggedInUser.getPassword());
                    etEmail.setText(loggedInUser.getEmail());
                    img_str = loggedInUser.getProfilePic();
                    byte[] decodedString = Base64.decode(loggedInUser.getProfilePic(), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    profilePic.setImageBitmap(decodedByte);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });


        findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.buttonUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etEmail.getText().toString().isEmpty()&&!etPassword.getText().toString().isEmpty()&&
                        !etName.getText().toString().isEmpty()&&
                        !etPhone.getText().toString().isEmpty()){
                    final User user=new User(etEmail.getText().toString(),etPassword.getText().toString(),
                            etName.getText().toString(),etPhone.getText().toString(),img_str);
                    if(!user.getEmail().equals(loggedInUser.getEmail())){
                        myFirebaseRef.createUser(etEmail.getText().toString(), etPassword.getText().toString(), new Firebase.ResultHandler() {
                            @Override
                            public void onSuccess() {
                                UserRef.setValue(null);
                                UserRef.removeValue();
                                myFirebaseRef.child(etEmail.getText().toString().split("@")[0]).setValue(user);
                                myFirebaseRef.unauth();

                                //Removing the user with old email
                                myFirebaseRef.removeUser(loggedInUser.getEmail(), loggedInUser.getPassword(), new Firebase.ResultHandler() {
                                    @Override
                                    public void onSuccess() {
                                    }

                                    @Override
                                    public void onError(FirebaseError firebaseError) {

                                    }
                                });


                                //trying to authenticate with new credentials
                                myFirebaseRef.authWithPassword(etEmail.getText().toString(), etPassword.getText().toString(), new Firebase.AuthResultHandler() {
                                    @Override
                                    public void onAuthenticated(AuthData authData) {

                                        Intent intent = new Intent(EditProfileActivity.this, ConversationActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }

                                    @Override
                                    public void onAuthenticationError(FirebaseError firebaseError) {
                                        System.out.println("ERROR"+firebaseError.getMessage());
                                    }
                                });

                                Toast.makeText(EditProfileActivity.this, "Saved Successfully!", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onError(FirebaseError firebaseError) {
                                Toast.makeText(EditProfileActivity.this, firebaseError.getMessage(), Toast.LENGTH_LONG).show();

                            }
                        });
                    }else if(!user.getPassword().equals(loggedInUser.getPassword())){

                        myFirebaseRef.changePassword(user.getEmail(), loggedInUser.getPassword(), user.getPassword(), new Firebase.ResultHandler() {
                            public void onSuccess() {

                                myFirebaseRef.child(etEmail.getText().toString().split("@")[0]).setValue(user);
                                myFirebaseRef.unauth();
                                //trying to authenticate with new credentials
                                myFirebaseRef.authWithPassword(etEmail.getText().toString(), etPassword.getText().toString(), new Firebase.AuthResultHandler() {
                                    @Override
                                    public void onAuthenticated(AuthData authData) {

                                        Intent intent = new Intent(EditProfileActivity.this, ConversationActivity.class);
                                        startActivity(intent);
                                        finish();

                                    }

                                    @Override
                                    public void onAuthenticationError(FirebaseError firebaseError) {
                                        System.out.println("ERROR" + firebaseError.getMessage());
                                    }
                                });
                                Toast.makeText(EditProfileActivity.this, "Saved Successfully, Since Password is changed please login again!", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onError(FirebaseError firebaseError) {
                                Toast.makeText(EditProfileActivity.this, firebaseError.getMessage(), Toast.LENGTH_LONG).show();

                            }
                        });
                    }
                    else{
                        UserRef.setValue(user);
                        Intent intent = new Intent(EditProfileActivity.this, ConversationActivity.class);
                        startActivity(intent);
                        finish();
                    }

                }else
                    Toast.makeText(EditProfileActivity.this,"One or more fields are Empty!",Toast.LENGTH_LONG).show();

            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                Bitmap bitmap1=bitmap;
                profilePic.setImageBitmap(bitmap1);
                ByteArrayOutputStream os= new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, os);
                byte[] image=os.toByteArray();

                img_str = Base64.encodeToString(image, 1);



            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
