package ir.smrahmadi.everynightsalavat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Switch Sw_Service ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        Sw_Service = (Switch) findViewById(R.id.Sw_Service);

        if(SharedPreferencesConnector.exist(MainActivity.this,"Service")){
            Sw_Service.setChecked(true);
            startService(new Intent(MainActivity.this , TaskService.class));
        }

        Sw_Service.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    startService(new Intent(MainActivity.this , TaskService.class));
                    SharedPreferencesConnector.writeBoolean(MainActivity.this,"Service",true);
                    Toast.makeText(MainActivity.this, "سرویس فعال شد", Toast.LENGTH_SHORT).show();
                }else{
                    stopService(new Intent(getApplicationContext(), TaskService.class));
                    SharedPreferencesConnector.delete(MainActivity.this,"Service");
                    Toast.makeText(MainActivity.this, "سرویس غیر فعال شد", Toast.LENGTH_SHORT).show();

                }
            }
        });



    }
}
