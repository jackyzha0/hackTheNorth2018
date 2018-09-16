package com.example.jacky.htntappit;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by mshrestha on 7/23/2014.
 */
public class NFCDisplayActivity extends Activity {

    private TextView mTextView;

//    private TextView mClient_Merch;
//    private TextView mTransaction_Amt;
//    private boolean state = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_display);
        mTextView = (TextView) findViewById(R.id.text_view);
    }

    @Override
    protected void onResume(){
        super.onResume();
        //setContentView(R.layout.activity_nfc_display);
        Intent intent = getIntent();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            Parcelable[] rawMessages = intent.getParcelableArrayExtra(
                    NfcAdapter.EXTRA_NDEF_MESSAGES);

            NdefMessage message = (NdefMessage) rawMessages[0]; // only one message transferred
            String tmpstr = new String(message.getRecords()[0].getPayload());
            mTextView.setText(tmpstr);
        } else
            mTextView.setText("Waiting for NDEF Message");
    }
}
