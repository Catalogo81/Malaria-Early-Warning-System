package com.example.malariaearlywarningsystemmews.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.malariaearlywarningsystemmews.R;

public class FacebookSignInActivity extends Login {

    //CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        callbackManager = CallbackManager.Factory.create();
//
//        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
//        LoginManager.getInstance().registerCallback(callbackManager,
//                new FacebookCallback<LoginResult>() {
//                    @Override
//                    public void onSuccess(LoginResult loginResult) {
//                        handleFacebookAccessToken(loginResult.getAccessToken());
//                    }
//
//                    @Override
//                    public void onCancel() {
//                        // App code
//                    }
//
//                    @Override
//                    public void onError(FacebookException exception) {
//                        // App code
//                    }
//                });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // Pass the activity result back to the Facebook SDK
//        callbackManager.onActivityResult(requestCode, resultCode, data);
//    }
//
//    private void handleFacebookAccessToken(AccessToken token) {
//
//        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
//        firebaseAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            FirebaseUser user = firebaseAuth.getCurrentUser();
//                            updateUI(user);
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Toast.makeText(FacebookSignInActivity.this, "Authentication failed: " + task.getException(),
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }
//
//    private void updateUI(FirebaseUser user) {
//        Intent intent = new Intent(FacebookSignInActivity.this, Home.class);
//        startActivity(intent);
//    }

}