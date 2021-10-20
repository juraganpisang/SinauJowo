package com.juraganpisang.sinaujowo;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private EditText name, email, pass, confimPass;
    private Button signUpBtn;
    private ImageView backBtn;
    private FirebaseAuth mAuth;
    private String emailStr, passStr, nameStr, confirmStr;
    private Dialog progressDialog;
    private TextView dialogText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.usernameDaftarET);
        email = findViewById(R.id.emailDaftarET);
        pass = findViewById(R.id.passwordDaftarET);
        confimPass = findViewById(R.id.ulangpasswordDaftarET);
        signUpBtn = findViewById(R.id.daftarBtn);
        backBtn = findViewById(R.id.backIv);

        progressDialog = new Dialog(SignUpActivity.this);
        progressDialog.setContentView(R.layout.dialog_layout);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialogText = progressDialog.findViewById(R.id.dialog_text);
        dialogText.setText("Proses Pendaftaran...");

        mAuth = FirebaseAuth.getInstance();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()) {
                    signUpNewUser();
                }
            }
        });
    }

    private boolean validate(){
        nameStr = name.getText().toString().trim();
        passStr = pass.getText().toString().trim();
        emailStr = email.getText().toString().trim();
        confirmStr = confimPass.getText().toString().trim();

        if(nameStr.isEmpty()){
            name.setError("Ngapunten, Nami Panjenengan");
            return false;
        }

        if(emailStr.isEmpty()){
            email.setError("Ngapunten, Email Panjenengan");
            return false;
        }
        if(passStr.isEmpty()){
            pass.setError("Ngapunten, Password Panjenengan");
            return false;
        }
        if(confirmStr.isEmpty()){
            confimPass.setError("Ngapunten, Password Panjenengan");
            return false;
        }
        if(!passStr.equals(confirmStr)){
            Toast.makeText(SignUpActivity.this, "Password Panjenengan mboten sami", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
    private void signUpNewUser() {

        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(emailStr, passStr)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(SignUpActivity.this,"Pendaftaran Berhasil", Toast.LENGTH_SHORT).show();;

                            DBQuery.createUserData(emailStr, nameStr, new MyCompleteListener(){

                                @Override
                                public void onSuccess() {

                                    DBQuery.loadData(new MyCompleteListener() {
                                        @Override
                                        public void onSuccess() {

                                            progressDialog.dismiss();

                                            Intent i = new Intent(SignUpActivity.this, MainActivity.class);
                                            startActivity(i);
                                            SignUpActivity.this.finish();
                                        }

                                        @Override
                                        public void onFailure() {
                                            progressDialog.dismiss();
                                            Toast.makeText(SignUpActivity.this, "Ngapunten, enten sing salah. Panjenengan cobi maleh", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                                @Override
                                public void onFailure() {
                                    progressDialog.dismiss();
                                    Toast.makeText(SignUpActivity.this, "Ngapunten, enten sing salah. Panjenengan cobi maleh", Toast.LENGTH_SHORT).show();
                                }
                            });


                        } else {

                            Log.w(TAG, "createUserWithEmail:failure", task.getException());

                            progressDialog.dismiss();

                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignUpActivity.this, "Ngapunten, pendaftaran gagal",
                                    Toast.LENGTH_SHORT).show();
//                            Toast.makeText(SignUpActivity.this, ""+task.getException().getMessage(),
//                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}