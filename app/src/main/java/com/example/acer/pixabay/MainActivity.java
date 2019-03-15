package com.example.acer.pixabay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    EditText ed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed=findViewById(R.id.edittext);

    }

    public void save(View view)
    {
        String s=ed.getText().toString();
        if(s.isEmpty())
        {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(this, ImageDetails.class);
            intent.putExtra("data", s);
            startActivity(intent);
        }
    }
}
