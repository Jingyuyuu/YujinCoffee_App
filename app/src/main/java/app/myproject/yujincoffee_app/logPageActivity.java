package app.myproject.yujincoffee_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import app.myproject.yujincoffee_app.databinding.ActivityLogPageBinding;
import okhttp3.Request;

public class logPageActivity extends AppCompatActivity {
    ActivityLogPageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLogPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.RegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Request request;
                Intent intent = new Intent(logPageActivity.this, registtPageActivity.class);
                Bundle datas = new Bundle();
                startActivity(intent);
            }
        });


    }
}