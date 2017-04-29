package org.qtum.mromanovsky.sampleqtumsdk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.qtum.mromanovsky.qtumsdk.QtumPaymentActivity;

public class MainActivity extends AppCompatActivity {

    EditText mEditTextAmount;
    Button mButtonQtumPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditTextAmount = (EditText) findViewById(R.id.et_amount);

        mButtonQtumPayment = (Button) findViewById(R.id.button);

        mButtonQtumPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QtumPaymentActivity.openQtumPaymentActivity(MainActivity.this,mEditTextAmount.getText().toString());
            }
        });
    }

}
