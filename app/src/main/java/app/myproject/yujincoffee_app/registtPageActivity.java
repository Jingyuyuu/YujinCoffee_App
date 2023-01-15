package app.myproject.yujincoffee_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import app.myproject.yujincoffee_app.databinding.ActivityRegisttPageBinding;

public class registtPageActivity extends AppCompatActivity {
    ActivityRegisttPageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityRegisttPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backLogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}