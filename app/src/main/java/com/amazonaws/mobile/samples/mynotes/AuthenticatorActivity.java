package com.amazonaws.mobile.samples.mynotes;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.amazonaws.mobile.auth.core.DefaultSignInResultHandler;
import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.auth.core.IdentityProvider;
import com.amazonaws.mobile.auth.ui.AuthUIConfiguration;
import com.amazonaws.mobile.auth.ui.SignInActivity;

public class AuthenticatorActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticator);

        final IdentityManager identityManager = AWSProvider.getInstance().getIdentityManager();
        // Set up the callbacks to handle the authentication response
        identityManager.login(this, new DefaultSignInResultHandler() {
            @Override
            public void onSuccess(Activity activity, IdentityProvider identityProvider) {
                Toast.makeText(AuthenticatorActivity.this,
                        String.format("Logged in as %s", identityManager.getCachedUserID()),
                        Toast.LENGTH_LONG).show();
                // Go to the main activity
                final Intent intent = new Intent(activity, NoteListActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                activity.startActivity(intent);
                activity.finish();
            }

            @Override
            public boolean onCancel(Activity activity) {
                return false;
            }
        });

        // Start the authentication UI
        int lightPurple = 0xFFDFC5FF;
        AuthUIConfiguration config = new AuthUIConfiguration.Builder()
                .userPools(true)
                .backgroundColor(lightPurple)
                .logoResId(R.drawable.leaguelook_icon)
                .build();
        SignInActivity.startSignInActivity(this, config);
        AuthenticatorActivity.this.finish();
    }
}
