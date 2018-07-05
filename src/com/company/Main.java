package com.company;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.X509Certificate;

public class Main {

    public static void main(String[] args){
        disableSSL();
        try {
            String responce = getLatestExchangeRates();
            if(responce == null){
                return;
            }
            writeToFile(responce);
            System.out.println(responce);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private static String getLatestExchangeRates() throws Exception {
        String url = "https://exchangeratesapi.io/api/latest";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            return response.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static void disableSSL() {
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }};
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private String readFromFile(){
        return null;
    }
    private static void writeToFile(String text){

        try(FileOutputStream fos = new FileOutputStream("Test.txt");
            BufferedOutputStream out = new BufferedOutputStream(fos)) {

            byte[] bytes = text.getBytes();
            out.write(bytes);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
