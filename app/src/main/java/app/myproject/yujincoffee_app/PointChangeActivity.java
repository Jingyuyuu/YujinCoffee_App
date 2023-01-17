package app.myproject.yujincoffee_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import app.myproject.yujincoffee_app.databinding.ActivityPointChangeBinding;

public class PointChangeActivity extends AppCompatActivity {
//5塊獲得1點
    ActivityPointChangeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityPointChangeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}