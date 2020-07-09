package com.example.wordwizard;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
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

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {
     private EditText editTextNickname, editTextFirstname, editTextLastname, editTextEmail, editTextPassword, editTextIsactive;
     private Button buttonRegister;
     private ProgressDialog progressDialog;

     @Override
     public void onCreate(@Nullable Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_registration);

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
               registerUser();
     }
}
/* public void connectionString(){
          //String type = params[0];
          String login_url = "https://mixmim.net/registerUser.php";

          //if(type.equals("login")){
          try {
               String user_name = "wwit488";
               String password = "4tX9!31h4";

               URL url = new URL(login_url);
               HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();

               httpURLConnection.setRequestMethod("POST");
               httpURLConnection.setDoOutput(true);
               httpURLConnection.setDoInput(true);
               OutputStream outputStream = httpURLConnection.getOutputStream();

          } catch (MalformedURLException e) {
               e.printStackTrace();
          } catch (IOException e) {
               e.printStackTrace();
          }
     }
}*/
