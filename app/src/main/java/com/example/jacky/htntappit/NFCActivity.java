package com.example.jacky.htntappit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.view.View;
import java.util.UUID;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import android.telephony.TelephonyManager;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import static com.example.jacky.htntappit.VerifyBoolean.*;

public class NFCActivity extends Activity implements NfcAdapter.CreateNdefMessageCallback {

    private EditText mEditText;
    public Wallet wallet;

    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;
    private TextView mParaLabel;
    private ImageView Biometrics;

    private KeyStore keyStore;
    private Cipher cipher;
    private String KEY_NAME = "AndroidKey";
    private TextView walletid;
    String imei = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBalance(false);
        setVerify(false);
        String imei = Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);
        wallet = new Wallet(imei);

        String text = wallet.getAddress();
        setContentView(R.layout.activity_nfc);
        NfcAdapter mAdapter = NfcAdapter.getDefaultAdapter(this);

        walletid = (TextView) findViewById(R.id.walletid);
        walletid.setText(text);
        //POST REQ TO SERVER WITH PHONE NUMBER TO CHECK IF ACCT EXISTS
        //IF SO, SEND BACK PUB KEY
        //IF NOT, SEND BACK PUB AND PRIV KEY


        if (!mAdapter.isEnabled()) {
            Toast.makeText(this, "Please enable NFC via Settings.", Toast.LENGTH_LONG).show();
        }
        mAdapter.setNdefPushMessageCallback(this, this);

        mParaLabel = (TextView) findViewById(R.id.paraLabel);
        Biometrics = (ImageView) findViewById(R.id.Biometrics);

        // Check 1: Android version should be greater or equal to Marshmallow
        // Check 2: Device has Fingerprint Scanner
        // Check 3: Have permission to use fingerprint scanner in the app
        // Check 4: Lock screen is secured with atleast 1 type of lock
        // Check 5: Atleast 1 Fingerprint is registered



        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
            keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

            if(!fingerprintManager.isHardwareDetected()){

                mParaLabel.setText("Fingerprint Scanner not detected in Device");

            } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED){

                mParaLabel.setText("Permission not granted to use Fingerprint Scanner");

            } else if (!keyguardManager.isKeyguardSecure()){

                mParaLabel.setText("Add Lock to your Phone in Settings");

            } else if (!fingerprintManager.hasEnrolledFingerprints()){

                mParaLabel.setText("You should add at least 1 Fingerprint to use this Feature");

            } else {
                setBalance(false);
                    mParaLabel.setText("Enter the amount you would like to send");
                    final ImageButton button = findViewById(R.id.SendMoney);
                    // define button, if button pressed, sendbalance(true);
                }

//                mParaLabel.setText("Place your Finger on Scanner to Access the App.");

                generateKey();

                if (cipherInit()){

                    FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(cipher);
                    FingerprintHandler fingerprintHandler = new FingerprintHandler(this);
                    fingerprintHandler.startAuth(fingerprintManager, cryptoObject);

                }
            }

        }
    @TargetApi(Build.VERSION_CODES.M)
    private void generateKey() {

        try {

            keyStore = KeyStore.getInstance("AndroidKeyStore");
            KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");

            keyStore.load(null);
            keyGenerator.init(new
                    KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();

        } catch (KeyStoreException | IOException | CertificateException
                | NoSuchAlgorithmException | InvalidAlgorithmParameterException
                | NoSuchProviderException e) {

            e.printStackTrace();

        }

    }
    @TargetApi(Build.VERSION_CODES.M)
    public boolean cipherInit() {
        try {
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }

        try {

            keyStore.load(null);

            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                    null);

            cipher.init(Cipher.ENCRYPT_MODE, key);

            return true;

        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (KeyStoreException | CertificateException | UnrecoverableKeyException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent nfcEvent) {
        NdefRecord ndefRecord = null;
        try {
            ndefRecord = NdefRecord.createMime("text/plain", (wallet.getAddress()+"//"+wallet.getBalance()).getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        NdefMessage ndefMessage = new NdefMessage(ndefRecord);
        return ndefMessage;
    }
}