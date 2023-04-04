package com.example.croprecommendation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class InputPage extends AppCompatActivity {
    TextView nitrogenValue, potValue, phosValue, tempValue, humValue, rainValue, phValue;
    OkHttpClient client = new OkHttpClient();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_page);
        nitrogenValue = findViewById(R.id.nitval);
        potValue = findViewById(R.id.potval);
        phosValue = findViewById(R.id.phos);
        tempValue = findViewById(R.id.tempval);
        humValue = findViewById(R.id.humidval);
        rainValue = findViewById(R.id.raival);
        phValue = findViewById(R.id.phval);
        //String result[] = {String.valueOf(nitrogenValue), String.valueOf(potValue), String.valueOf(phosValue), String.valueOf(tempValue), String.valueOf(humValue), String.valueOf(rainValue), String.valueOf(phValue)};
        Button b1 = (Button) findViewById(R.id.button);



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nit = nitrogenValue.getText().toString();
                //int nitrogen = Integer.parseInt((String) nitrogenValue.getText());
                String phos = phosValue.getText().toString();
                String pot = potValue.getText().toString();
                String temp = tempValue.getText().toString();
                String humid = humValue.getText().toString();
                String h = phValue.getText().toString();
                String rain = rainValue.getText().toString();
                RequestBody formbody
                        = new FormBody.Builder()
                        .add("n", nit)
                        .add("p", phos)
                        .add("k", pot)
                        .add("temp", temp)
                        .add("humidity", humid)
                        .add("ph", h)
                        .add("rainfall", rain)
                        .build();

                Request request = new Request.Builder().url("http://192.168.108.78:5000/inputs").post(formbody).build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        b1.setText(response.body().string());
                        if (!response.body().string().isEmpty()) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "data received", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                    @Override
                    public void onFailure(
                            @NotNull Call call,
                            @NotNull IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "data received", Toast.LENGTH_SHORT).show();

                            }
                        });
                        startActivity(new Intent(InputPage.this, ResultActivity.class));

                        finish();
                    }
                });
            }

        });
    }
}