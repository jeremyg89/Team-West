package com.example.wordwizard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.DisplayMetrics;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {
     private EditText editTextNickname, editTextFirstname, editTextLastname, editTextEmail, editTextPassword, editTextIsactive;
     private Button buttonRegister;
     private ProgressDialog progressDialog;

     @Override
     public void onCreate(@Nullable Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_registration);

          //create the display menu
          DisplayMetrics dm = new DisplayMetrics();
          getWindowManager().getDefaultDisplay().getMetrics(dm);
          WindowManager.LayoutParams wlp = getWindow().getAttributes();


          int width = dm.widthPixels;
          int height = dm.heightPixels;
          //change the size and location of the window
          wlp.gravity = Gravity.TOP;
          getWindow().setLayout((int)(width), (int)(height*.6));


          editTextEmail = findViewById(R.id.email);
          editTextFirstname = findViewById(R.id.firstName);
          editTextLastname = findViewById(R.id.lastName);
          editTextNickname = findViewById(R.id.nickname);
          editTextPassword = findViewById(R.id.password);
          //editTextIsactive = findViewById(R.id.isactive);

          buttonRegister = (Button) findViewById(R.id.btnSubmit);

          progressDialog = new ProgressDialog(this);

          buttonRegister.setOnClickListener(this);
     }

     public void registerUser() {
          final String Email = editTextEmail.getText().toString().trim();
          final String FirstName = editTextFirstname.getText().toString().trim();
          final String LastName = editTextLastname.getText().toString().trim();
          final String Nickname = editTextNickname.getText().toString().trim();
          final String Password = editTextPassword.getText().toString().trim();
          //final String IsActive = editTextIsactive.getText().toString().trim();

          progressDialog.setMessage("Registering user...");
          progressDialog.show();

          StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_REGISTER,
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
                    params.put("nickname", Nickname);
                    params.put("first_name", FirstName);
                    params.put("last_name", LastName);
                    params.put("email", Email);
                    params.put("password", Password);
                    params.put("is_active", "1");
                    return params;
               }
          };
          RequestQueue requestQueue = Volley.newRequestQueue(this);
          requestQueue.add(stringRequest);
     }

     @Override
     public void onClick(View view) {
          if (view == buttonRegister)
               if (confirmInput()) {
               }
               else{
                    registerUser();
               }
     }

     private boolean validateEmail(){
          String Email = editTextEmail.getText().toString().trim();

          if (Email.isEmpty()){
               editTextEmail.setError("Field can't be empty!");
               return false;
          }
          else if(!EMAIL_ADDRESS_PATTERN.matcher(Email).matches()){
               editTextEmail.setError("Please insert a valid email address. example@example.com");
               return false;
          }
          else{
               editTextEmail.setError(null);
               return true;
          }
     }
     private boolean validatePassword(){
          String Password = editTextPassword.getText().toString().trim();

          if (Password.isEmpty()){
               editTextPassword.setError("Field can't be empty");
               return false;
          } else if (!PASSWORD_PATTERN.matcher(Password).matches()){
               editTextPassword.setError("Password too weak. aA1@aA1@");
               return false;
          } else if (Password.length() > 60){
               editTextPassword.setError("Password is too long. It must be less than 60 characters.");
               return false;
          }
          else{
               editTextPassword.setError(null);
               return true;
          }
     }
     private boolean validateNickname(){
          String Nickname = editTextNickname.getText().toString().trim();
          if (Nickname.isEmpty()){
               editTextNickname.setError("Field can't be empty");
               return false;
          } else if (Nickname.length() > 60){
               editTextNickname.setError("Nickname is too long. It must be less than 60 characters.");
               return false;
          }
          else{
               editTextNickname.setError(null);
               return true;
          }
     }
     private boolean validateFName(){
          String FName = editTextFirstname.getText().toString().trim();
          if (FName.isEmpty()){
               editTextFirstname.setError("Field can't be empty");
               return false;
          } else if (FName.length() > 60){
               editTextFirstname.setError("First Name is too long. It must be less than 60 characters.");
               return false;
          }
          else{
               editTextFirstname.setError(null);
               return true;
          }
     }
     private boolean validateLName(){
          String LName = editTextLastname.getText().toString().trim();
          if (LName.isEmpty()){
               editTextLastname.setError("Field can't be empty");
               return false;
          } else if (LName.length() > 60){
               editTextLastname.setError("Last Name is too long. It must be less than 60 characters.");
               return false;
          }
          else{
               editTextLastname.setError(null);
               return true;
          }
     }
     public boolean confirmInput(){
          if (!validateEmail() | !validatePassword() | !validateNickname() | !validateFName() | !validateLName()){
               return true;
          }
          return false;
     }
     //pattern for the email verification
     public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
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
             "(?=.*[@#$%^&+=])" +  //at least 1 special character
             "(?=\\S+$)" +  //no white spaces
             ".{8,}" +  //at least 8 characters
             "$");
     public void closeRegister(){
          finish();
          startActivity(new Intent(RegisterUser.this, MainActivity.class));
     }
}
