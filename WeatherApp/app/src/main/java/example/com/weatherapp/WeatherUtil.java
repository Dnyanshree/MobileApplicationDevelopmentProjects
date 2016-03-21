package example.com.weatherapp;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

public class WeatherUtil {
    static public class WeatherPullParser{
    static ArrayList<Weather> weatherParser(InputStream in) throws XmlPullParserException, IOException {
        XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
        parser.setInput(in, "UTF-8");
        Weather weather = null;
        ArrayList<String> english = null;
        ArrayList<Weather> weatherList = new ArrayList<Weather>();
        int event = parser.getEventType();
        ArrayList arrayList = new ArrayList();
        String windDir = "";
        String temp = "";

        while(event != XmlPullParser.END_DOCUMENT){
            switch (event){
                case XmlPullParser.START_TAG:
                    if(parser.getName().equals("forecast")){
                        weather = new Weather();
                        english = new ArrayList<String>();
                        windDir = "";
                    }else  if(parser.getName().equals("civil")){
                            weather.setTime(parser.nextText());
                    }else if(parser.getName().equals("english")) {
                        english.add(parser.nextText());
                    }else if(parser.getName().equals("condition")){
                        weather.setClouds(parser.nextText());
                    }else if(parser.getName().equals("icon_url")){
                        weather.setIconUrl(parser.nextText());
                    }else if (parser.getName().equals("degrees")){
                        windDir = parser.nextText() + (char)0x00B0;
                    }else if (parser.getName().equals("dir")){
                        temp = parser.nextText().substring(0,1);

                        if(temp.equalsIgnoreCase("E"))
                             windDir = windDir + "East";
                       else if(temp.equalsIgnoreCase("W"))
                            windDir = windDir + "West";
                        else if(temp.equalsIgnoreCase("N"))
                            windDir = windDir + "North";
                       else if(temp.equalsIgnoreCase("S"))
                            windDir = windDir + "South";
                        weather.setWindDirection(windDir);
                    }else if(parser.getName().equals("wx")){
                        weather.setClimateType(parser.nextText());
                    }else if(parser.getName().equals("humidity")){
                        weather.setHumidity(parser.nextText());
                    }else if(parser.getName().equals("metric")) {
                        weather.setPressure(parser.nextText());
                    }
                    break;

                case XmlPullParser.END_TAG:
                    if(parser.getName().equals("forecast")){
                        weather.setTemperature(english.get(0));
                        arrayList.add(english.get(0));
                        weather.setDewpoint(english.get(1));
                        weather.setWindSpeed(english.get(2));
                        weather.setFeelsLike(english.get(5));
                        weatherList.add(weather);
                        weather = null;
                        english = null;
                        windDir = "";
                        temp = "";
                    }
                    break;
                default:
                    break;
            }
            event = parser.next();
        }
        for(int i=0; i<weatherList.size();i++){
            weatherList.get(i).setMinimumTemp((String) Collections.min(arrayList));
            weatherList.get(i).setMaximumTemp((String) Collections.max(arrayList));
        }
        return  weatherList;
    }
    }

    static public class WeatherForecastPullParser {
        static ArrayList<Weather> weatherParser(InputStream in) throws XmlPullParserException, IOException {
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            boolean flag=false;
            parser.setInput(in, "UTF-8");
            Weather weather = null;
            ArrayList<Weather> weatherList = new ArrayList<Weather>();
            int event = parser.getEventType();

            while(event!=XmlPullParser.END_DOCUMENT){

                switch(event){
                    case XmlPullParser.START_TAG:
                        if(parser.getName().equals("simpleforecast")){
                            flag=true;
                            Log.d("Parse","simpleforecast");
                        }else if(parser.getName().equals("forecastday") && flag==true){
                            weather = new Weather();

                        } else if(parser.getName().equals("pretty") && flag==true){
                            weather.setTime(parser.nextText());//Used here to denote Day, whereas in Hourly weather forecast it is used as time

                        }else if(parser.getName().equals("high") && flag==true){
                            parser.nextTag();
                            weather.setMaximumTemp(parser.nextText());

                        }else if(parser.getName().equals("low") && flag==true){
                            parser.nextTag();
                            weather.setMinimumTemp(parser.nextText());

                        }else if(parser.getName().equals("conditions")){
                            weather.setClouds(parser.nextText());
                        }else if(parser.getName().equals("icon_url") && flag==true){
                            weather.setIconUrl(parser.nextText());
                        }break;

                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("forecastday")){
                            if(weather!=null)weatherList.add(weather);
                            weather=null;

                        }
                }
                event=parser.next();
            }

            return weatherList;
        }

    }
}