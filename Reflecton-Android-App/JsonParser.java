package reflecton.reflecton_android_app;

//
// JsonParser.java
// Reflecton
//
// Created by Marco Hennermann on 19.12.17
// Copyright © 2017 Marco Hennermann. All rights reserved.
//
        import org.apache.http.HttpEntity;
        import org.apache.http.HttpResponse;
        import org.apache.http.client.ClientProtocolException;
        import org.apache.http.client.HttpClient;
        import org.apache.http.client.entity.UrlEncodedFormEntity;
        import org.apache.http.client.methods.HttpGet;
        import org.apache.http.client.methods.HttpPost;
        import org.apache.http.client.utils.URLEncodedUtils;
        import org.apache.http.impl.client.DefaultHttpClient;
        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.io.UnsupportedEncodingException;
        import java.util.ArrayList;

//https://www.journaldev.com/12607/android-login-registration-php-mysql

public class JsonParser
{
    static InputStream is = null;
    static JSONObject jObj = null;
    static JSONArray jArr = null;
    static String json = "";
    static String error = "";

    // constructor
    public JsonParser()
    {}

    // function get json from url
    // by making HTTP POST or GET mehtod
    public static JSONObject makeHttpRequest(String url, String method, ArrayList params)
    {
        // Making HTTP request
        try
        {
            if (method.equals("POST"))
            {
                // request method is POST
                // defaultHttpClient
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(params));

                HttpResponse httpResponse = httpClient.execute(httpPost);
                error = String.valueOf(httpResponse.getStatusLine().getStatusCode());
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();

            } else if (method.equals("GET"))
            {
                // request method is GET
                DefaultHttpClient httpClient = new DefaultHttpClient();
                String paramString = URLEncodedUtils.format(params, "utf-8");
                url += "?" + paramString;
                HttpGet httpGet = new HttpGet(url);

                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
            }

        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        catch (ClientProtocolException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
                sb.append(line + "\n");

            is.close();
            json = sb.toString();
        } catch (Exception e)
        {
            e.printStackTrace();

        }

        // try parse the string to a JSON object
        try
        {
            jObj = new JSONObject(json);
            jObj.put("error_code", error);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        // return JSON String
        return jObj;
    }

    private String convertStreamToString(InputStream is) throws Exception
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null)
        {
            sb.append(line);
        }
        is.close();
        return sb.toString();
    }
}

