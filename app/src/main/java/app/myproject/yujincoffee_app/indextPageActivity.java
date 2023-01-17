package app.myproject.yujincoffee_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import app.myproject.yujincoffee_app.databinding.ActivityIndextPageBinding;

public class indextPageActivity extends AppCompatActivity {


    ActivityIndextPageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIndextPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.menubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(indextPageActivity.this, 菜單頁面.class);
                startActivity(intent);
            }
        });
        binding.seasonnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(indextPageActivity.this, seasonnew.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        //用id判斷點了哪個選項
        if (id == R.id.membersetting) {
            Intent intent = new Intent(indextPageActivity.this, 會員設定頁面.class);
            startActivity(intent);
        } else if (id == R.id.myorder) {
            Intent intent = new Intent(indextPageActivity.this, 我的訂單頁面.class);
            startActivity(intent);
        } else if (id == R.id.itemmenu) {
            Intent intent = new Intent(indextPageActivity.this, MenuList.class);
            startActivity(intent);
        } else if (id == R.id.historyorder) {
            Intent intent = new Intent(indextPageActivity.this, 歷史訂單頁面.class);
            startActivity(intent);
        } else if (id == R.id.myfavorite) {
            Intent intent = new Intent(indextPageActivity.this, MyFavoriteActivity.class);
            startActivity(intent);
        } else if (id == R.id.pointchange) {
            Intent intent = new Intent(indextPageActivity.this, PointChangeActivity.class);
            startActivity(intent);
        }
        return true;
    }
}