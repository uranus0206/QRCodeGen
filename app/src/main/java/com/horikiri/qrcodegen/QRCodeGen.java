package com.horikiri.qrcodegen;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.horikiri.libqrcodegenerator.QrCodeGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class QRCodeGen extends AppCompatActivity {

    ImageView imageView;
    Button button;
    EditText editText;
    String EditTextValue;
    public final static int QRcodeWidth = 276;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_gen);
        imageView = (ImageView) findViewById(R.id.imageView);
        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //QrCodeGenerator gneQrCode = new QrCodeGenerator(QRCodeGen.this, editText.getText().toString());
                QrCodeGenerator qr = new QrCodeGenerator();
                qr.generateQRCode(QRCodeGen.this, editText.getText().toString());

//                if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//                    Log.v("QQQQ: ","Permission is granted");
//                    QrCodeGenerator gneQrCode = new QrCodeGenerator(QRCodeGen.this, editText.getText().toString());
//                    //File write logic here
//                } else {
//                    Log.v("QQQQ: ","P");
//                    ActivityCompat.requestPermissions(QRCodeGen.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//                }

                //Get shared preference
//                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(QRCodeGen.this);
//                String str = sharedPreferences.getString("Afobot_QrCode", null).toString();
//                byte[] imageByte = Base64.decode(str,Base64.DEFAULT);
//                Bitmap decodeByte = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
                imageView.setImageBitmap(qr.getStoredQRCode(QRCodeGen.this));
            }
        });
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
//            QrCodeGenerator gneQrCode = new QrCodeGenerator(QRCodeGen.this, editText.getText().toString());
//            Log.v("UUU","Permission: "+permissions[0]+ "was "+grantResults[0]);
//            //resume tasks needing this permission
//        }
//    }

}
