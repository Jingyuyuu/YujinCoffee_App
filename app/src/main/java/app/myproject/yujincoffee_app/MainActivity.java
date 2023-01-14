package app.myproject.yujincoffee_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import app.myproject.yujincoffee_app.databinding.ActivityMainBinding;
import okhttp3.Request;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Request request;
                Intent intent = new Intent(MainActivity.this, logPageActivity.class);
                Bundle datas = new Bundle();
                startActivity(intent);
            }
        });
    }
}