package app.myproject.yujincoffee_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MenuTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_test);

        ShoppingCart a= ShoppingCart.newInstance();
        indextPageActivity.Drink coffee = new indextPageActivity.Drink("奶茶","123","冰","0.5");
        a.drinkItems.add(coffee);
        Log.e("購物車",a.drinkItems.get(0).getName()
                +" "+a.drinkItems.get(0).getIce()
                +" "+a.drinkItems.get(0).getSugar()
                +" "+a.drinkItems.get(0).getPrice());

    }
}