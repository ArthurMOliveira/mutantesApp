package com.example.mutantesapp;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements Response.Listener, Response.ErrorListener{

    public static final String REQUEST_TAG = "UserAutentication";
    private EditText mEditTextUser, mEditTextPass;
    private TextView mTextView;
    private Button mButton;
    private RequestQueue mQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditTextUser = (EditText) findViewById(R.id.usuarioTxt);
        mEditTextPass = (EditText) findViewById(R.id.senhaTxt);
        mTextView = (TextView)findViewById(R.id.textView);
        mButton = (Button) findViewById(R.id.loginBtn);

    }

    @Override
    protected  void  onStart(){
        super.onStart();
        mQueue = CustomVolleyRequestQueue.getInstance(this.getApplicationContext()).getRequestQueue();
        String url = "http://uri trabalho";
        final CustomJSONObjectRequest jsonRequest = new CustomJSONObjectRequest(Request.Method.GET, url,
                new JSONObject(), this, this);
        jsonRequest.setTag(REQUEST_TAG);

        mButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                mQueue.add(jsonRequest);
            }
        });
    }

    @Override
    protected void onStop(){
        super.onStop();
        if(mQueue != null){
            mQueue.cancelAll(REQUEST_TAG);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        AlertDialog alerta;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Usario ou senha incorretos!");
        alerta = builder.create();
        alerta.show();
    }

    @Override
    public void onResponse(Object response) {
        mTextView.setText((String)response);
        try{
            mTextView.setText(mTextView.getText() + "\n\n" + ((JSONObject)response).getString("message"));
            //INTENT PRA MUDAR DE TELA!
        } catch (JSONException e){
            e.printStackTrace();
        }
    }
}
