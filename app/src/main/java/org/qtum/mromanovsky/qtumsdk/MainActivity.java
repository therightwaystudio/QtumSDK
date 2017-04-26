package org.qtum.mromanovsky.qtumsdk;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button mButtonOpenInWallet;
    Button mButtonCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonCancel = (Button) findViewById(R.id.bt_cancel);
        mButtonOpenInWallet = (Button) findViewById(R.id.bt_open_in_wallet);

        mButtonOpenInWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("org.qtum.mromanovsky.qtum", "org.qtum.mromanovsky.qtum.ui.activity.MainActivity.MainActivity"));
                if(getPackageManager().resolveActivity(intent, 0) != null) {
                    intent.putExtra("test",true);
                    startActivity(intent);
                } else {
                    Toast.makeText(getBaseContext(), "No app installed that can perform this action", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
