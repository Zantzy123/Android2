package com.example.android2;

import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android2.R;

public class MainActivity extends AppCompatActivity {

    // Login credentials
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";

    private EditText usernameEditText;
    private EditText passwordEditText;
    private ImageButton passwordEye;
    private Button loginButton;
    private TextView forgotPassword;

    private boolean passwordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Find views
        usernameEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        passwordEye = findViewById(R.id.passwordEye);
        loginButton = findViewById(R.id.loginButton);
        forgotPassword = findViewById(R.id.forgotPassword);

        // Login button
        loginButton.setOnClickListener(v -> login());

        // Show / hide password
        passwordEye.setOnClickListener(v -> togglePassword());

        // Login when pressing keyboard Done
        passwordEditText.setOnEditorActionListener((v, actionId, event) -> {

            boolean enterPressed =
                    event != null
                            && event.getKeyCode() == KeyEvent.KEYCODE_ENTER
                            && event.getAction() == KeyEvent.ACTION_DOWN;

            if (actionId == EditorInfo.IME_ACTION_DONE || enterPressed) {
                login();
                return true;
            }

            return false;
        });

        // Forgot password
        forgotPassword.setOnClickListener(v -> {
            Toast.makeText(
                    MainActivity.this,
                    "Please contact the administrator.",
                    Toast.LENGTH_SHORT
            ).show();
        });
    }

    private void login() {

        String username = usernameEditText
                .getText()
                .toString()
                .trim();

        String password = passwordEditText
                .getText()
                .toString();

        // Check username
        if (username.isEmpty()) {
            usernameEditText.setError("Please enter username");
            usernameEditText.requestFocus();
            return;
        }

        // Check password
        if (password.isEmpty()) {
            passwordEditText.setError("Please enter password");
            passwordEditText.requestFocus();
            return;
        }

        // Check credentials
        if (username.equals(USERNAME)
                && password.equals(PASSWORD)) {

            Toast.makeText(
                    MainActivity.this,
                    "Login successful!",
                    Toast.LENGTH_SHORT
            ).show();

            // No dashboard.
            // Stay on this screen.

        } else {

            passwordEditText.setError("Invalid username or password");
            passwordEditText.requestFocus();

            Toast.makeText(
                    MainActivity.this,
                    "Invalid username or password",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private void togglePassword() {

        if (passwordVisible) {

            // Hide password
            passwordEditText.setInputType(
                    InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_PASSWORD
            );

            passwordEditText.setTransformationMethod(
                    PasswordTransformationMethod.getInstance()
            );

            passwordEye.setContentDescription("Show password");

            passwordVisible = false;

        } else {

            // Show password
            passwordEditText.setInputType(
                    InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            );

            passwordEditText.setTransformationMethod(
                    HideReturnsTransformationMethod.getInstance()
            );

            passwordEye.setContentDescription("Hide password");

            passwordVisible = true;
        }

        passwordEditText.setSelection(
                passwordEditText.length()
        );
    }
}