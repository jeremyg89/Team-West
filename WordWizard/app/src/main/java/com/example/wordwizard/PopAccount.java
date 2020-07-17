package com.example.wordwizard;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PopAccount extends AppCompatActivity implements View.OnClickListener {
    private EditText editAccountEmail, editAccountNickname;
    private ImageButton btnEditNickname, btnEditEmail;
    private ProgressDialog progressDialog;

    private final int id = SharedPrefManager.getInstance(this).getID();
    private final String nickname = SharedPrefManager.getInstance(this).getNickname();
    private final String email = SharedPrefManager.getInstance(this).getEmail();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pop_account);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .9), (int) (height * .7));

        editAccountNickname = findViewById(R.id.editAccountNickname);
        editAccountNickname.setText(nickname);

        editAccountEmail = findViewById(R.id.editAccountEmail);
        editAccountEmail.setText(email);

        TextView account_id = findViewById(R.id.txtUserId);
        account_id.setText(Integer.toString(id));

        //create the progress dialog
        progressDialog = new ProgressDialog(this);

        btnEditEmail = (ImageButton) findViewById(R.id.btnEditEmail);
        btnEditEmail.setOnClickListener(this);

        btnEditNickname = (ImageButton) findViewById(R.id.btnEditNickname);
        btnEditNickname.setOnClickListener(this);
    }

    public void updateAccountEmail() {
        final String Email = editAccountEmail.getText().toString();
        final String ID = Integer.toString(id);
        //final String IsActive = editTextIsactive.getText().toString().trim();

        progressDialog.setMessage("Updateing Email...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_UPDATE_EMAIL,
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
                        Toast.makeText(getApplicationContext(), "Email updated successfully.", Toast.LENGTH_LONG).show();
                        SharedPrefManager.getInstance(PopAccount.this).updateEmail(Email);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @NotNull
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", ID);
                params.put("email", Email);
                return params;
            }
        };
        RequestHandler.getInstance(this).clearRequestQueue();
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void updateAccountNickname() {
        final String Nickname = editAccountNickname.getText().toString();
        final String ID = Integer.toString(id);
        //final String IsActive = editTextIsactive.getText().toString().trim();

        progressDialog.setMessage("Updateing Nickname...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_UPDATE_NICKNAME,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            //get the values form the jsonobject
                            //jsonObject.getJSONObject(response);
                            //id = jsonObject.getInt("id");
                            //nickname = jsonObject.getString("nickname").toString();

                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(getApplicationContext(), "Nickname updated successful.", Toast.LENGTH_LONG).show();
                        SharedPrefManager.getInstance(PopAccount.this).updateNickname(Nickname);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @NotNull
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", ID);
                params.put("nickname", Nickname);
                return params;
            }
        };
        RequestHandler.getInstance(this).clearRequestQueue();
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
        //RequestQueue requestQueue = Volley.newRequestQueue(this);
        //requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnEditEmail:
                updateAccountEmail();
                break;
            case R.id.btnEditNickname:
                updateAccountNickname();
                break;
            default:
                throw new RuntimeException("Unknown button ID");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
