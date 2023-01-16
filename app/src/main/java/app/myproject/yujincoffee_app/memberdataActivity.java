package app.myproject.yujincoffee_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import app.myproject.yujincoffee_app.databinding.ActivityMemberdataBinding;

public class memberdataActivity extends AppCompatActivity {

    ActivityMemberdataBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMemberdataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //製作Menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();

        //用id判斷點了哪個選項
        if(id == R.id.membersetting){
            Intent intent=new Intent(memberdataActivity.this,會員設定頁面.class);
            return true;
        }
        else if(id == R.id.myorder){
            Intent intent=new Intent(memberdataActivity.this,我的訂單頁面.class);
            return true;
        }
        else if(id == R.id.itemmenu){
            Intent intent=new Intent(memberdataActivity.this,商品菜單頁面.class);
            return true;
        }
        else if(id == R.id.historyorder){
            Intent intent=new Intent(memberdataActivity.this,歷史訂單頁面.class);
            return true;
        }
        else if(id == R.id.myfavorite){
            Intent intent=new Intent(memberdataActivity.this,我的最愛頁面.class);
            return true;
        }
        else if(id == R.id.pointchange){
            Intent intent=new Intent(memberdataActivity.this,點數兌換頁面.class);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}