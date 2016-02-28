package foodapp.kk.com.howgeekyareyou;

/**
 * Created by Sairam on 2/16/2016.
 */

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

public class RequestParams {

    String method, baseURL;
    HashMap<String,String> params = new HashMap<String,String>();

    public RequestParams(String method, String baseURL){
        super();
        this.method=method;
        this.baseURL=baseURL;
    }

    public void addParams(String key, String value){
        params.put(key,value);
    }

    public String getEncodedParams(){
        StringBuilder sb= new StringBuilder();

        for(String key: params.keySet()){
            try {
                String value = URLEncoder.encode(params.get(key), "UTF-8");
                if(sb.length()>0){
                    sb.append("&");
                }
                sb.append(key+"="+value);
            }catch (UnsupportedEncodingException e){
                e.printStackTrace();
            }

        }

        return sb.toString();
    }

    public String getEncodedUrl(){
        return baseURL+"?"+getEncodedParams();
    }

    public HttpURLConnection setupConnection() throws IOException{


        if(method.equals("GET")){
            URL url= new URL(getEncodedUrl());
            HttpURLConnection con= (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            return con;
        }else {//POST
            URL url = new URL(this.baseURL);
            HttpURLConnection con= (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            OutputStreamWriter out= new OutputStreamWriter(con.getOutputStream());
            out.write(getEncodedParams());
            out.flush();
            return con;
        }


    }
}
