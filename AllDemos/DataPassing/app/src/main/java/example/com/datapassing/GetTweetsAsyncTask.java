package example.com.datapassing;

import android.content.Context;
import android.os.AsyncTask;
import java.util.LinkedList;

public class GetTweetsAsyncTask extends AsyncTask<String, Void, LinkedList<String>> {
    IData activity;

    public GetTweetsAsyncTask(IData activity) {
        this.activity = activity;
    }
    @Override
    protected LinkedList<String> doInBackground(String... params) {
        LinkedList<String> tweets = new LinkedList<String>();
        tweets.add("Tweet 0");
        tweets.add("Tweet 1");
        tweets.add("Tweet 2");
        tweets.add("Tweet 3");
        tweets.add("Tweet 4");
        return tweets;
    }
    @Override
    protected void onPostExecute(LinkedList<String> result) {
        activity.setupData(result);
        super.onPostExecute(result);
    }
    public static interface IData{
        public void setupData(LinkedList<String> result);
        public Context getContext();
    }
}
