package com.heyhuyen.pawrent.activities;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.heyhuyen.pawrent.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();

    @BindView(R.id.tvUsername) TextView tvUsername;
    @BindView(R.id.tvEmail) TextView tvEmail;
    @BindView(R.id.tvPhotoUrl) TextView tvPhotoUrl;
    @BindView(R.id.tvUid) TextView tvUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            String uid = user.getUid();

            tvUid.setText(uid);
            tvEmail.setText(email);
            tvUsername.setText(name != null ? name: "no name");
            tvPhotoUrl.setText(photoUrl != null ? photoUrl.toString() : "no photo");
        }
    }

    public void logout(View view) {
        Log.d(TAG, "Logging out");
        FirebaseAuth.getInstance().signOut();
        finish();
    }

    public void deleteAccount(View view) {
        FirebaseAuth.getInstance().getCurrentUser().delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User account deleted.");
                            finish();
                        } else {
                            Log.w(TAG, "deleteAccount", task.getException());
                            Toast.makeText(MainActivity.this, "Delete account failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}