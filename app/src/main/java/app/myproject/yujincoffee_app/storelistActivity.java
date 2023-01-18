package app.myproject.yujincoffee_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import app.myproject.yujincoffee_app.databinding.ActivityStorelistBinding;

public class storelistActivity extends AppCompatActivity {

     ActivityStorelistBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityStorelistBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}