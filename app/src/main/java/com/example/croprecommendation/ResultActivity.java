package com.example.croprecommendation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    Button logoutbtn,fertilizerbutton;
    TextView result,fertilizer;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        result = findViewById(R.id.result);
        fertilizer = findViewById(R.id.fertdes);
        logoutbtn = findViewById(R.id.logout);
        fertilizerbutton = findViewById(R.id.fertbtn);
        int crop = getIntent().getIntExtra("crop",3);
        switch(crop) {
            case 0:
                result.setText("Apple");
                break;
            case 1:
                result.setText("Banana");
                break;
            default:
                result.setText("Rice");
                break;
        }
            /*if(crop>=70 && crop<=99)
       {
           result.setText("Rice");
       }
       else
       {
           result.setText("Watermelon");
       }*/

        fertilizerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fertilizer.setText("The most suitable fertilizer for rice is Ammonium sulphate.");
            }
        });
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultActivity.this,Dashboard.class);
                startActivity(intent);
                finish();
                Toast.makeText(ResultActivity.this, "Logout successful", Toast.LENGTH_SHORT).show();
            }
        });
    }
}