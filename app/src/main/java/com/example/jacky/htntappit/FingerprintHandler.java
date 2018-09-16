package com.example.jacky.htntappit;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.media.Image;
import android.os.Build;
import android.os.CancellationSignal;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import static com.example.jacky.htntappit.VerifyBoolean.*;

@TargetApi(Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {

    private Context context;
    private EditText editText;

    public FingerprintHandler(Context context){
        this.context = context;
    }

    public void startAuth(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject){

        CancellationSignal cancellationSignal = new CancellationSignal();
        fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, this, null);

    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {

        this.update("There was an Auth Error. " + errString, false);
        setVerify(false);
    }

    @Override
    public void onAuthenticationFailed() {

        this.update("Auth Failed. ", false);
        setVerify(false);
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {

        this.update("Error: " + helpString, false);
        setVerify(false);
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        if(getBalance()) {
            this.update("Your balance can now be sent.", true);
            setVerify(true);
        }
    }

    private void update(String s, boolean b) {

        TextView paraLabel = (TextView) ((Activity)context).findViewById(R.id.paraLabel);
        //ImageView imageView = (ImageView) ((Activity)context).findViewById(R.id.fingerprintImage);

        paraLabel.setText(s);

        //paraLabel.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
        if(b == false) {
//            EditText editText = (EditText) ((Activity)context).findViewById(R.id.editText);
//            if (editText.getText().toString() != "") {
//                System.out.println(editText.getText().toString());
//            }
            //Toast.makeText(this, "Please enable NFC via Settings.", Toast.LENGTH_LONG).show();
        } else {

            //paraLabel.setTextColor(ContextCompat.getColor(context, "0x97CC04"));
            //imageView.setImageResource(R.mipmap.action_done);

        }

    }
}