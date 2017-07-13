package com.horikiri.libqrcodegenerator;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * Created by chungyi on 7/11/17.
 */


public class QrCodeGenerator {
    private final int QRcodeWidth = 276;
    private Activity activity;
    private String defaultLocation = "/Android/data/com.qnap.afobot.qrcode/";
    private String mImageName = "AfobotQRCode.jpg";

    public void generateQRCode(Activity activity, String EditTextValue) {
        this.activity = activity;
        Bitmap bitmap;

        try {
            bitmap = TextToImageEncode(EditTextValue);
            //Save to Image
            //storeImage(bitmap);
            //AfuUtil.setProp("persist.sys.afobot_qrcode", defaultLocation);
            saveToSharedPreference(bitmap);
        } catch(WriterException e) {
            e.printStackTrace();
        }
    }

    public Bitmap getStoredQRCode(Activity activity) {
        this.activity = activity;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.activity);
        String str = sharedPreferences.getString("Afobot_QrCode", null).toString();
        byte[] imageByte = Base64.decode(str,Base64.DEFAULT);
        Bitmap decodeByte = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);

        return decodeByte;
    }

    private Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value, BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null);
        } catch(IllegalArgumentException Illeagalargumentexception) {
            return null;
        }

        int bitMatrixWidth = bitMatrix.getWidth();
        int bitMatrixHeight = bitMatrix.getHeight();
        int[] pixels = new int[bitMatrixWidth*bitMatrixHeight];
        ContextCompat contextCompat = null;
        for (int y=0; y<bitMatrixHeight; y++) {
            int offset = y*bitMatrixWidth;
            for (int x=0; x<bitMatrixWidth; x++) {
                pixels[offset + x] = bitMatrix.get(x, y) ? contextCompat.getColor(this.activity, R.color.QRCodeBlackColor): contextCompat.getColor(this.activity, R.color.QRCodeWhiteColor);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, 276, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

    private void saveToSharedPreference(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();

        String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        SharedPreferences shre = PreferenceManager.getDefaultSharedPreferences(this.activity);
        SharedPreferences.Editor edit=shre.edit();
        edit.putString("Afobot_QrCode",encodedImage);
        edit.commit();
    }

    private void storeImage(Bitmap image) {
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            Log.d("STORE_IMAGE", "Error Creating File");
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d("STORE_IMAGE", "File Not Found: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("STORE_IMAGE", "IO_Error: "+ e.getMessage());
            e.printStackTrace();
        }
    }

    private File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + defaultLocation);

        if (! mediaStorageDir.exists()) {
            if (! mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        File mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }

}

