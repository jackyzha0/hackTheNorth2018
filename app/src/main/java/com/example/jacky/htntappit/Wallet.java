package com.example.jacky.htntappit;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


public class Wallet {
    public String address = null;

    private void setAddress(String _address) {
        address = _address;
    }
    public static String convertStreamToString(java.io.InputStream is)
    {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public static String ping(String body) throws Exception {
        URL url = new URL("http://45.77.5.252/api/generateClient?uuid=" + body);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in_ = new BufferedInputStream(urlConnection.getInputStream());
            //System.out.println("Input stream>> "+convertStreamToString(in_).split(",")[2].split(":")[1].replace("\"", ""));
            //return convertStreamToString(in_);
            return (convertStreamToString(in_).split(",")[2].split(":")[1].replace("\"", ""));
        } finally {
            urlConnection.disconnect();
        }
    }
    public static boolean etherpay(String privkey, String pubkey, String to, double amt) throws Exception{
        URL url = new URL("http://45.77.5.252/api/transferEther?privkey="+privkey+"&pubkey="+pubkey+"&to="+to+"&amt="+amt);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in_ = new BufferedInputStream(urlConnection.getInputStream());
            if (convertStreamToString(in_) != null) {
                return true;
            }
        } finally {
            urlConnection.disconnect();
            return true;
        }
    }
    public static boolean perfTransaction(double amt, String to) {
        //check if parties have enough money
        //check for biometric confirmation
        //send moneey
        //log transaction in wallet
        return true;
    }

    public String getAddress () {
        System.out.println("ADDRESS>> "+address);
        return address;
        //return address;
    }

    public double getBalance() throws Exception {
        URL url = new URL("http://45.77.5.252/api/getBalance/"+address);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in_ = new BufferedInputStream(urlConnection.getInputStream());
            return Double.parseDouble(convertStreamToString(in_));
        } finally {
            urlConnection.disconnect();
        }
    }

    public Wallet(String deviceID) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        System.out.println("DEVICE ID>>" + deviceID);
        try {
            setAddress(ping(deviceID));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
        //        public static String ping(String body) {
//            if (deviceID=="f092fec5-5589-4e6e-a40f-75b4d0997c1a") {
//                setAddress("0x6B51a34d1a3A848bF498b3BEb21671F9D1fF726A");
//            } else {
//                setAddress("0x96bf5692c156e721f23135ddccbad8d68829e68a");
//            }
//            System.out.println(deviceID);
//            URL url = new URL("45.77.5.252/api/generateClient?uuid="+body);
//        }
//    }
//


//    @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    public String ping(String body) {
//        URL url = null;
//        try {
//            url = new URL("45.77.5.252/api/generateClient"+body);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
//            for (String line; (line = reader.readLine()) != null;) {
//                System.out.println(line);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return "0k";
//    }
//    public String ping(String body) {
//        String result = null;
//        try {
//            Runtime r = Runtime.getRuntime();
//
//            Process p = r.exec("curl 45.77.5.252/api/generateClient?uuid="+body);
//
//            BufferedReader in =
//                    new BufferedReader(new InputStreamReader(p.getInputStream()));
//            String inputLine;
//            while ((inputLine = in.readLine()) != null) {
//                System.out.println(inputLine);
//                result += inputLine;
//            }
//            in.close();
//
//        } catch (IOException e) {
//            System.out.println(e);
//        }
//        System.out.println(result);
//        return result;
//    }
//    public String sendPost(String completeUrl, String body) {
//        try {
//            System.out.println("TESTPOST5");
//            URL url = new URL(completeUrl);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            System.out.println("TESTPOST4");
//            conn.setRequestMethod("POST");
//            conn.setRequestProperty("uuid",body);
//            conn.setDoInput(true);
//            conn.setDoOutput(true);
//            System.out.println("TESTPOST3");
//            Uri.Builder builder = new Uri.Builder()
//                    .appendQueryParameter("uuid", body);
//            String query = builder.build().getEncodedQuery();
//            System.out.println("TESTPOST2");
//            OutputStream os = conn.getOutputStream();
//            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));
//            System.out.println("TESTPOST1");
//            writer.write(query);
//            writer.flush();
//            writer.close();
//            os.close();
//
//            conn.connect();
//            System.out.println(query);
//            return query;
//        } catch (Exception e) {
//            System.out.println("LMFAO UR BAD "+e);
//        }
//        return "0x6B51a34d1a3A848bF498b3BEb21671F9D1fF726A";
//    }
