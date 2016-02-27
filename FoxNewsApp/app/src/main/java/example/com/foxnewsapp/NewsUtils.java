package example.com.foxnewsapp;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/*
* Assignment: InClass6
* Filename: NewsUtils.java
* Full names of group members:
*   Kedar Vijay Kulkarni
*   Dnyanshree Shengulwar
*   Marissa McLaughlin
* */
public class NewsUtils {

    static public class NewsPullParser{

        static  ArrayList<News> newsParser(InputStream in) throws XmlPullParserException, IOException {
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(in, "UTF-8");
            News news = null;
            ArrayList<News> newsList = new ArrayList<News>();
            int event = parser.getEventType();
            boolean flag =false;

            while(event != XmlPullParser.END_DOCUMENT){
                switch (event){
                    case XmlPullParser.START_TAG:
                        if(parser.getName().equals("item")){
                            news = new News();
                            flag=true;

                        }else if(parser.getName().equals("guid")){
                            news.setLink(parser.nextText());

                        }else if(parser.getName().equals("pubDate")){
                            news.setPubDate(parser.nextText());

                        }else if(parser.getName().equals("description") && flag==true){

                            news.setDescription(parser.nextText());

                        }
                        else if(parser.getName().equals("title") && flag==true){
                            news.setTitle(parser.nextText());

                        }

                        else if(parser.getName().equals("media:thumbnail")){
                           news.setThumbnail(parser.getAttributeValue(null, "url"));
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("item")){
                            newsList.add(news);
                            news = null;
                        }
                        break;
                    default:
                        break;
                }


                event = parser.next();
            }
            return  newsList;
        }
    }
}
