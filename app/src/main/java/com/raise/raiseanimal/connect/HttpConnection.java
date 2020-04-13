package com.raise.raiseanimal.connect;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnection extends AsyncTask<String,Void,String> {

    private OnHttpConnectionListener listener;

    public void setOnHttpConnectionListener(OnHttpConnectionListener listener){
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... strings) {

        URL url;
        BufferedReader reader = null;
        StringBuilder stringBuilder = null;
        try{
            url = new URL(strings[0]);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            connection.setRequestMethod("GET");

            connection.setReadTimeout(5000);

            connection.connect();

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            stringBuilder = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null){
                stringBuilder.append(line);
                stringBuilder.append('\r');
            }
            return stringBuilder.toString();
        }catch (Exception e){
            e.printStackTrace();

            return "錯誤 : "+e.toString();

        }finally {
            if (reader != null){
                try{
                    reader.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (result.startsWith("錯誤")){
            listener.onFailure(result);
        }else {
            listener.onSuccess(result);
        }
    }


    public interface OnHttpConnectionListener{
        void onSuccess(String result);

        void onFailure(String errorCode);
    }
}
