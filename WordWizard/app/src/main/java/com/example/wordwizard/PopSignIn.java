package com.example.wordwizard;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class PopSignIn extends AppCompatActivity implements View.OnClickListener{
    private EditText  editEmail, editPassword;
    private Button buttonSignIn;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pop_signin);

        //call to change the attributes of the window
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        WindowManager.LayoutParams wlp = getWindow().getAttributes();

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        //align the window to the top of the screen
        wlp.gravity = Gravity.TOP;
        //set the window dimensions
        getWindow().setLayout((int)(width), (int)(height*.45));

        //popup Menu button
        Button btnMenu = (Button) findViewById(R.id.btnSup);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vMenu) {
                Intent i = new Intent(getApplicationContext(), RegisterUser.class);
                startActivity(i);
                overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up);
            }
        });
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        buttonSignIn = (Button) findViewById(R.id.btnLogin);

        progressDialog = new ProgressDialog(this);

        buttonSignIn.setOnClickListener(this);
}
    public void login() {
        final String Email = editEmail.getText().toString().trim();
        final String Password = editPassword.getText().toString().trim();
        //final String IsActive = editTextIsactive.getText().toString().trim();

        progressDialog.setMessage("Logging in...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_SIGNIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @NotNull
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", Email);
                params.put("password", Password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    @Override
    public void onClick(View view) {
        if (view == buttonSignIn)
            if (confirmInput()) {
            }
            else{
                login();
            }
    }

    private boolean validateEmail(){
        String Email = editEmail.getText().toString().trim();

        if (Email.isEmpty()){
            editEmail.setError("Field can't be empty!");
            return false;
        }
        else if(!EMAIL_ADDRESS_PATTERN.matcher(Email).matches()){
            editEmail.setError("Please insert a valid email address. example@example.com");
            return false;
        }
        else{
            editEmail.setError(null);
            return true;
        }
    }
    private boolean validatePassword(){
        String Password = editPassword.getText().toString().trim();

        if (Password.isEmpty()){
            editPassword.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(Password).matches()){
            editPassword.setError("Password too weak. aA1@aA1@");
            return false;
        } else if (Password.length() > 60){
            editPassword.setError("Password is too long. It must be less than 60 characters.");
            return false;
        }
        else{
            editPassword.setError(null);
            return true;
        }
    }
    public boolean confirmInput(){
        if (!validateEmail() | !validatePassword()){
            return true;
        }
        return false;
    }
    //pattern for the email verification
    private static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^" +
            "(?=.*[0-9])" +  //at least 1 digit
            "(?=.*[a-z])" +  //at least 1 lower case letter
            "(?=.*[A-Z])" +  //at least 1 upper case letter
            "(?=.*[@#$%^&+=!])" +  //at least 1 special character
            "(?=\\S+$)" +  //no white spaces
            ".{8,}" +  //at least 8 characters
            "$");
}
