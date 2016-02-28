package example.com.inclass05;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

/*
Assignment - InClass05
Name: Dnyanshree Shengulwar
FileName - RequestParams.java
 */
/**
 * Created by Dnyanshree on 2/15/2016.
 */
public class RequestParams {
    String method, baseURL;
    HashMap<String, String> params = new HashMap<String, String>();

    public RequestParams(String method, String baseURL) {
        this.method = method;
        this.baseURL = baseURL;
    }

    public void addParams(String key, String value) {
        params.put(key, value);
    }

    public String getEncodedParams() {
        //loop over key value pair of the params
        //append  to a stringbuilder key=value
        //figure out how to append the &

        StringBuilder sb = new StringBuilder();
        for (String key : params.keySet()) {
            //encoding the value
            try {
                String value = URLEncoder.encode(params.get(key), "UTF-8");
                if (sb.length() > 0) {
                    sb.append("&");
                }
                sb.append(key + "=" + value);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public String getEncodedURL(){
        return this.baseURL + "?" + getEncodedParams();
    }

    public HttpURLConnection setupConnection() throws IOException {
        if(method.equals("GET")){
            URL url = new URL(getEncodedURL());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            return con;

        }else{//POST
            URL url = new URL(baseURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
            writer.write(getEncodedParams());
            writer.flush();
            return con;
        }
    }
}
