package org.qtum.mromanovsky.qtumsdk;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import org.json.JSONException;
import org.json.JSONObject;


public class QtumPaymentActivity extends AppCompatActivity {
    Button mButtonOpenInWallet;
    Button mButtonCancel;
    TextView mTextViewAddress;
    TextView mTextViewAmountQtum;
    ImageView mImageViewQrCode;
    private final String PKG = "org.qtum.mromanovsky.qtum";
    private final String CLS = "org.qtum.mromanovsky.qtum.ui.activity.MainActivity.MainActivity";
    private final String SEND_FROM_SDK = "qtum.intent.action.SEND_FROM_SDK";
    private final String SEND_ADDRESS = "qtum.intent.extra.SEND_ADDRESS";
    private final String SEND_AMOUNT = "qtum.intent.extra.SEND_AMOUNT";

    String address = "testaddress";
    String amount = "15";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qtum_payment);

        mButtonCancel = (Button) findViewById(R.id.bt_cancel);
        mButtonOpenInWallet = (Button) findViewById(R.id.bt_open_in_wallet);
        mTextViewAddress = (TextView) findViewById(R.id.tv_address_qtum);
        mTextViewAmountQtum = (TextView) findViewById(R.id.tv_amount_qtum);
        mImageViewQrCode = (ImageView) findViewById(R.id.iv_qr_code_qtum);

        mTextViewAmountQtum.setText(amount);
        mTextViewAddress.setText(address);

        mTextViewAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) QtumPaymentActivity.this.getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", address);
                clipboard.setPrimaryClip(clip);
                //TODO: change toast
                Toast.makeText(QtumPaymentActivity.this, "Coped", Toast.LENGTH_SHORT).show();
            }
        });

        JSONObject json = new JSONObject();
        try {
            json.put("amount", amount);
            json.put("publicAddress", address);
            mImageViewQrCode.setImageBitmap(TextToImageEncode(json.toString()));
        } catch (JSONException | WriterException e) {
            e.printStackTrace();
        }

        mButtonOpenInWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(PKG, CLS));
                if(getPackageManager().resolveActivity(intent, 0) != null) {
                    intent.setAction(SEND_FROM_SDK);
                    intent.putExtra(SEND_ADDRESS,address);
                    intent.putExtra(SEND_AMOUNT,amount);

                    startActivity(intent);
                } else {
                    Toast.makeText(getBaseContext(), "No app installed that can perform this action", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishActivity();
            }
        });
    }

    private Bitmap TextToImageEncode(String Value) throws WriterException {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        Display display = this.getWindowManager().getDefaultDisplay();
        display.getMetrics(displayMetrics);
        int QRCodeWidth = displayMetrics.heightPixels / 3;
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.QR_CODE,
                    QRCodeWidth, QRCodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();
        int bitMatrixHeight = bitMatrix.getHeight();
        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];
        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        Color.BLACK : Color.WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);
        bitmap.setPixels(pixels, 0, QRCodeWidth, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

    private void finishActivity(){
        onBackPressed();
    }
}
