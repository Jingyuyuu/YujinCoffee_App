package app.myproject.yujincoffee_app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import app.myproject.yujincoffee_app.databinding.ActivityIndextPageBinding;

public class indextPageActivity extends AppCompatActivity {

    ActivityIndextPageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityIndextPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.menubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(indextPageActivity.this,菜單頁面.class);
                startActivity(intent);
            }
        });
        binding.seasonnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(indextPageActivity.this,seasonnew.class);
                startActivity(intent);
            }
        });
    }
}