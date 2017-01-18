package com.heyhuyen.pawrent.activities;

import android.support.annotation.NonNull;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.heyhuyen.pawrent.R;

import butterknife.BindView;

public class SignupActivity extends BaseActivity {
    private static final String TAG = SignupActivity.class.getName();

    @BindView(R.id.etEmail) EditText etEmail;
    @BindView(R.id.etPassword) EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initialize(SignupActivity.this);
    }

    public void signup(View view) {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        if (TextUtils.isEmpty(email)) {
            error(SignupActivity.this, "Empty email");
        } else if (TextUtils.isEmpty(password)) {
            error(SignupActivity.this, "Empty password");
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            error(SignupActivity.this, "Authentication failed.");
                        }

                        // Success!
                        Toast.makeText(SignupActivity.this, "Signup Success!!",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }
}
