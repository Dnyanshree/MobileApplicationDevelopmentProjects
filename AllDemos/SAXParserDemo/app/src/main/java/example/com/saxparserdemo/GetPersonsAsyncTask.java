package example.com.saxparserdemo;

import android.os.AsyncTask;
import android.util.Log;
import org.xml.sax.SAXException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Dnyanshree on 2/22/2016.
 */
public class GetPersonsAsyncTask extends AsyncTask<String, Void, ArrayList<Person>> {
    @Override
    protected ArrayList<Person> doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode = con.getResponseCode();
            if(statusCode == HttpURLConnection.HTTP_OK){
                InputStream in = con.getInputStream();
                return PersonsUtil.PersonsSAXParser.parsePerson(in);

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Person> persons) {
        super.onPostExecute(persons);
        if(persons != null){
            Log.d("Demo", persons.toString());
        }
    }
}
