package app.myproject.yujincoffee_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import app.myproject.yujincoffee_app.Modle.Util.SimpleeAPIWorker;
import app.myproject.yujincoffee_app.databinding.ActivityMemberdataaPageBinding;
import app.myproject.yujincoffee_app.databinding.ActivityRegisttPageBinding;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class memberdataaPageActivity extends AppCompatActivity {
    SharedPreferences memberDataSharePre;
    ActivityMemberdataaPageBinding binding;
    ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMemberdataaPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        memberDataSharePre = getSharedPreferences("memberDataPre", MODE_PRIVATE);
        //getSharedPreferences只是建立檔名 可以放在最前面get出來讓大家用
        String memberDataCheck=memberDataSharePre.getString("name","查無資料");
        if(memberDataCheck.equals("查無資料")) {
            //如果SharedPreferance裡面的memberDataPre檔案裡的name沒有資料，就從網路下載會員資料
            executorService = Executors.newSingleThreadExecutor();
            JSONObject packet = new JSONObject();
            try {
                JSONObject memberEmail = new JSONObject();
                memberEmail.put("email", "wu@gmail.com");
                packet.put("memberEmail", memberEmail);

                Log.e("JSON", "這裡是從網路下載的會員資料");
                Toast.makeText(memberdataaPageActivity.this, "已送出EMAIL抓取會員資料", Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            MediaType mType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(packet.toString(), mType);
            //VM IP=20.187.101.131
            Request request = new Request.Builder()
                    .url("http://192.168.255.123:8216/api/member/getMemberData")
                    .post(body)
                    .build();
            SimpleeAPIWorker apiCaller = new SimpleeAPIWorker(request, memberDataHandler);
            //產生Task準備給executor執行
            executorService.execute(apiCaller);

        }else{
            String PointsData=memberDataSharePre.getString("points","查無資料");
            String nameData=memberDataSharePre.getString("name","查無資料");
            String emailData=memberDataSharePre.getString("email","查無資料");
            String phoneData=memberDataSharePre.getString("phone","查無資料");

            binding.memberPointsTX.setText(PointsData);
            binding.memberNameT.setText(nameData);
            binding.memberEmailTX.setText(emailData);
            binding.memberPhoneT.setText(phoneData);
            Log.e("JSON", "這裡是從SharePreferance取出的會員資料");
        }





        binding.memberReviseBtn.setOnClickListener(new View.OnClickListener() {
                    //傳送修改資料給Server寫入資料庫
              @Override
              public void onClick(View view) {
              //請將使用者資料 封裝成JSON格式 回傳給SpringBoot Controller進行驗證
              //下拉選單範例https://github.com/miscoder002/ReivewApphttps://github.com/miscoder002/ReivewApp
              JSONObject packet=new JSONObject();
              try {

                  JSONObject newMemberRegData=new JSONObject();
                  newMemberRegData.put("name","吳鯨魚");//binding.memberNameT.getText().toString()
                  newMemberRegData.put("pwd","000");//binding.memberPointsTX.getText().toString()
                  newMemberRegData.put("phone","0912332110");//binding.memberPhoneT.getText().toString()
                  newMemberRegData.put("email","li@gmail.com");//binding.memberEmailTX.getText().toString()
                  packet.put("NewMemberData",newMemberRegData);

                  Log.e("JSON",packet.toString(4));
                  Toast.makeText(memberdataaPageActivity.this, "已送出訊息", Toast.LENGTH_SHORT).show();
              } catch (JSONException e) {
                  e.printStackTrace();
              }

              MediaType mType=MediaType.parse("application/json");
              RequestBody body=RequestBody.create(packet.toString(),mType);
              //VM IP=20.187.101.131
                  Request request=new Request.Builder()
                          .url("http://192.168.255.123:8216/api/member/reNewMemberData")
                          .post(body)
                          .build();
                  SimpleeAPIWorker apiCaller=new SimpleeAPIWorker(request,memberDataHandler);
                  //產生Task準備給executor執行
                  executorService.execute(apiCaller);
            }
        });


    }

    Handler memberDataHandler =new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Bundle bundle=msg.getData();
            if(bundle.getInt("status")==123){
                Toast.makeText(memberdataaPageActivity.this, bundle.getString("mesg"), Toast.LENGTH_LONG).show();
            }else if(bundle.getInt("status")==465) {
                Toast.makeText(memberdataaPageActivity.this, bundle.getString("mesg"), Toast.LENGTH_LONG).show();
            }else if(bundle.getInt("status")==999) {
                Toast.makeText(memberdataaPageActivity.this, bundle.getString("email"), Toast.LENGTH_LONG).show();
                binding.memberPointsTX.setText(bundle.getString("points"));
                binding.memberNameT.setText(bundle.getString("name"));
                binding.memberEmailTX.setText(bundle.getString("email"));
                binding.memberPhoneT.setText(bundle.getString("phone"));


                SharedPreferences.Editor editor=memberDataSharePre.edit();
                editor.putString("points",binding.memberPointsTX.getText().toString());
                editor.putString("name",binding.memberNameT.getText().toString());
                editor.putString("email",binding.memberEmailTX.getText().toString());
                editor.putString("phone",binding.memberPhoneT.getText().toString());
                editor.apply();
            }else{
                Toast.makeText(memberdataaPageActivity.this, bundle.getString("mesg"), Toast.LENGTH_LONG).show();
            }



        }
    };
    /*
    class SimpleAPIWorker implements Runnable{
        OkHttpClient client;
        Request request;

        public SimpleAPIWorker(Request request){
            client=new OkHttpClient();
            this.request=request;
        }
        @Override
        public void run() {
            try {
                Response response=client.newCall(request).execute();
                String responseString=response.body().string();
                Log.e("API回應",responseString);
                //Response也應該是JASON格式回傳 由APP端確認登入結果

                JSONObject result=new JSONObject(responseString);
                Message m=memberDataHandler.obtainMessage();
                Bundle bundle=new Bundle();
                if(result.getInt("status")==123){
                    bundle.putString("mesg",result.getString("mesg"));
                    bundle.putInt("status",result.getInt("status"));
                }else{
                    bundle.putString("mesg","會員更新失敗，請洽程式開發人員");
                    bundle.putInt("status",result.getInt("status"));
                }
                m.setData(bundle);
                memberDataHandler.sendMessage(m);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


     */


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
            Intent intent=new Intent(memberdataaPageActivity.this,memberdataaPageActivity.class);
            startActivity(intent);
            return true;
        }
        /*
        else if(id == R.id.myorder){
            Intent intent=new Intent(memberdataaPageActivity.this,我的訂單頁面.class);
            startActivity(intent);
            return true;
        }
        else if(id == R.id.itemmenu){
            Intent intent=new Intent(memberdataaPageActivity.this,MenuList.class);
            startActivity(intent);
            return true;
        }
        else if(id == R.id.historyorder){
            Intent intent=new Intent(memberdataaPageActivity.this,歷史訂單頁面.class);
            startActivity(intent);
            return true;
        }
        */
        else if(id == R.id.myfavorite){
            Intent intent=new Intent(memberdataaPageActivity.this,MyFavoriteActivity.class);
            startActivity(intent);
            return true;
        }
        else if(id == R.id.pointchange){
            Intent intent=new Intent(memberdataaPageActivity.this,PointChangeActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}