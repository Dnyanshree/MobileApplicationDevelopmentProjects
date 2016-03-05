package example.com.inclass07app;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/*
* Assignment: InClass07
* Filename: StoryUtil.java
* Full names of group members:
*   Kedar Vijay Kulkarni
*   Dnyanshree Shengulwar
*   Marissa McLaughlin
* */
public class StoryUtil {


    static public class StoriesJSONParser {

        static ArrayList<Story> parseStory(String in) throws JSONException {

            ArrayList<Story> storyList = new ArrayList<Story>();

            JSONObject root = new JSONObject(in);
            JSONArray storyJSONArray = root.getJSONArray("results");

            for (int i = 0; i < storyJSONArray.length(); i++) {
                JSONObject storyJSONObject = storyJSONArray.getJSONObject(i);
                Story story = new Story();
                story.setStoryTitle(storyJSONObject.getString("title"));
                story.setStoryByline(storyJSONObject.getString("byline"));
                story.setStoryAbstract(storyJSONObject.getString("abstract"));
                String dateStr = storyJSONObject.getString("created_date").split("T")[0];
                String formatedDate = "";
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                DateFormat writeFormat = new SimpleDateFormat("MM/dd");
                Date date = null;
                try {
                    date = (Date) formatter.parse(dateStr);
                    formatedDate = writeFormat.format(date);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                story.setStoryCreatedDate(formatedDate);

                JSONArray urlJSONArray;
                        /*try {
                            urlJSONArray = storyJSONObject.getJSONArray("multimedia");
                        }catch (JSONException e){
                            return storyList;
                        }*/
                if (!storyJSONObject.getString("multimedia").isEmpty()) {

                    urlJSONArray = storyJSONObject.getJSONArray("multimedia");
                    for (int j = 0; j < urlJSONArray.length(); j++) {
                        JSONObject urlJSONObject = urlJSONArray.getJSONObject(j);

                        if (urlJSONObject.getString("format").equalsIgnoreCase("Standard Thumbnail"))
                            story.setStoryThumbImageUrl(urlJSONObject.getString("url"));
                        if (urlJSONObject.getString("format").equalsIgnoreCase("Normal"))
                            story.setStoryNormalImageUrl(urlJSONObject.getString("url"));
                    }

                    storyList.add(story);

                }

            }
            return storyList;
        }

    }
}
