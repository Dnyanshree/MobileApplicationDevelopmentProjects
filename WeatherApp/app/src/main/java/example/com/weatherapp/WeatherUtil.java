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
//[Weather{time='null', temperature='null', dewpoint='null', clouds='Clear', iconUrl='http://icons.wxug.com/i/c/k/clear.gif', windSpeed='null', windDirection='null', climateType='Sunny', humidity='40', feelsLike='null', maximumTemp='null', minimumTemp='null', pressure='null'}, Weather{time='null', temperature='null', dewpoint='null', clouds='Clear', iconUrl='http://icons.wxug.com/i/c/k/nt_clear.gif', windSpeed='null', windDirection='null', climateType='Clear', humidity='48', feelsLike='null', maximumTemp='null', minimumTemp='null', pressure='null'}, Weather{time='null', temperature='null', dewpoint='null', clouds='Clear', iconUrl='http://icons.wxug.com/i/c/k/nt_clear.gif', windSpeed='null', windDirection='null', climateType='Clear', humidity='52', feelsLike='null', maximumTemp='null', minimumTemp='null', pressure='null'}, Weather{time='null', temperature='null', dewpoint='null', clouds='Clear', iconUrl='http://icons.wxug.com/i/c/k/nt_clear.gif', windSpeed='null', windDirection='null', climateType='Clear', humidity='59', feelsLike='null', maximumTemp='null', minimumTemp='null', pressure='null'}, Weather{time='null', temperature='null', dewpoint='null', clouds='Clear', iconUrl='http://icons.wxug.com/i/c/k/nt_clear.gif', windSpeed='null', windDirection='null', climateType='Clear', humidity='63', feelsLike='null', maximumTemp='null', minimumTemp='null', pressure='null'}, Weather{time='null', temperature='null', dewpoint='null', clouds='Clear', iconUrl='http://icons.wxug.com/i/c/k/nt_clear.gif', windSpeed='null', windDirection='null', climateType='Clear', humidity='68', feelsLike='null', maximumTemp='null', minimumTemp='null', pressure='null'}, Weather{time='null', temperature='null', dewpoint='null', clouds='Clear', iconUrl='http://icons.wxug.com/i/c/k/nt_clear.gif', windSpeed='null', windDirection='null', climateType='Clear', humidity='71', feelsLike='null', maximumTemp='null', minimumTemp='null', pressure='null'}, Weather{time='null', temperature='null', dewpoint='null', clouds='Clear', iconUrl='http://icons.wxug.com/i/c/k/nt_clear.gif', windSpeed='null', windDirection='null', climateType='Clear', humidity='73', feelsLike='null', maximumTemp='null', minimumTemp='null', pressure='null'}, Weather{time='null', temperature='null', dewpoint='null', clouds='Clear', iconUrl='http://icons.wxug.com/i/c/k/nt_clear.gif', windSpeed='null', windDirection='null', climateType='Clear', humidity='74', feelsLike='null', maximumTemp='null', minimumTemp='null', pressure='null'}, Weather{time='null', temperature='null', dewpoint='null', clouds='Clear', iconUrl='http://icons.wxug.com/i/c/k/nt_clear.gif', windSpeed='null', windDirection='null', climateType='Clear', humidity='76', feelsLike='null', maximumTemp='null', minimumTemp='null', pressure='null'}, Weather{time='null', temperature='null', dewpoint='null', clouds='Clear', iconUrl='http://icons.wxug.com/i/c/k/nt_clear.gif', windSpeed='null', windDirection='null', climateType='Clear', humidity='81', feelsLike='null', maximumTemp='null', minimumTemp='null', pressure='null'}, Weather{time='null', temperature='null', dewpoint='null', clouds='Clear', iconUrl='http://icons.wxug.com/i/c/k/nt_clear.gif', windSpeed='null', windDirection='null', climateType='Clear', humidity='86', feelsLike='null', maximumTemp='null', minimumTemp='null', pressure='null'}, Weather{time='null', temperature='null', dewpoint='null', clouds='Clear', iconUrl='http://icons.wxug.com/i/c/k/nt_clear.gif', windSpeed='null', windDirection='null', climateType='Clear', humidity='87', feelsLike='null', maximumTemp='null', minimumTemp='null', pressure='null'}, Weather{time='null', temperature='null', dewpoint='null', clouds='Clear', iconUrl='http://icons.wxug.com/i/c/k/clear.gif', windSpeed='null', windDirection='null', climateType='Sunny', humidity='89', feelsLike='null', maximumTemp='null', minimumTemp='null', pressure='null'}, Weather{time='null', temperature='null', dewpoint='null', clouds='Clear', iconUrl='http://icons.wxug.com/i/c/k/clear.gif', windSpeed='null', windDirection='null', climateType='Sunny'
//[Weather{time='8:00 PM', temperature='51', dewpoint='31', clouds='Partly Cloudy', iconUrl='http://icons.wxug.com/i/c/k/nt_partlycloudy.gif', windSpeed='1', windDirection='null', climateType='Partly Cloudy', humidity='45', feelsLike='51', maximumTemp='null', minimumTemp='null', pressure='null'}, Weather{time='9:00 PM', temperature='49', dewpoint='30', clouds='Clear', iconUrl='http://icons.wxug.com/i/c/k/nt_clear.gif', windSpeed='2', windDirection='null', climateType='Mostly Clear', humidity='48', feelsLike='49', maximumTemp='null', minimumTemp='null', pressure='null'}, Weather{time='10:00 PM', temperature='47', dewpoint='31', clouds='Clear', iconUrl='http://icons.wxug.com/i/c/k/nt_clear.gif', windSpeed='1', windDirection='null', climateType='Mostly Clear', humidity='53', feelsLike='47', maximumTemp='null', minimumTemp='null', pressure='null'}, Weather{time='11:00 PM', temperature='43', dewpoint='32', clouds='Clear', iconUrl='http://icons.wxug.com/i/c/k/nt_clear.gif', windSpeed='2', windDirection='null', climateType='Clear', humidity='66', feelsLike='43', maximumTemp='null', minimumTemp='null', pressure='null'}, Weather{time='12:00 AM', temperature='41', dewpoint='33', clouds='Clear', iconUrl='http://icons.wxug.com/i/c/k/nt_clear.gif', windSpeed='2', windDirection='null', climateType='Clear', humidity='72', feelsLike='41', maximumTemp='null', minimumTemp='null', pressure='null'}, Weather{time='1:00 AM', temperature='41', dewpoint='32', clouds='Clear', iconUrl='http://icons.wxug.com/i/c/k/nt_clear.gif', windSpeed='2', windDirection='null', climateType='Clear', humidity='72', feelsLike='41', maximumTemp='null', minimumTemp='null', pressure='null'}, Weather{time='2:00 AM', temperature='40', dewpoint='33', clouds='Clear', iconUrl='http://icons.wxug.com/i/c/k/nt_clear.gif', windSpeed='2', windDirection='null', climateType='Clear', humidity='75', feelsLike='40', maximumTemp='null', minimumTemp='null', pressure='null'}, Weather{time='3:00 AM', temperature='39', dewpoint='32', clouds='Clear', iconUrl='http://icons.wxug.com/i/c/k/nt_clear.gif', windSpeed='2', windDirection='null', climateType='Clear', humidity='76', feelsLike='39', maximumTemp='null', minimumTemp='null', pressure='null'}, Weather{time='4:00 AM', temperature='38', dewpoint='33', clouds='Clear', iconUrl='http://icons.wxug.com/i/c/k/nt_clear.gif', windSpeed='1', windDirection='null', climateType='Clear', humidity='82', feelsLike='38', maximumTemp='null', minimumTemp='null', pressure='null'}, Weather{time='5:00 AM', temperature='37', dewpoint='33', clouds='Clear', iconUrl='http://icons.wxug.com/i/c/k/nt_clear.gif', windSpeed='2', windDirection='null', climateType='Clear', humidity='85', feelsLike='37', maximumTemp='null', minimumTemp='null', pressure='null'}, Weather{time='6:00 AM', temperature='37', dewpoint='33', clouds='Clear', iconUrl='http://icons.wxug.com/i/c/k/nt_clear.gif', windSpeed='2', windDirection='null', climateType='Clear', humidity='87', feelsLike='37', maximumTemp='null', minimumTemp='null', pressure='null'}, Weather{time='7:00 AM', temperature='37', dewpoint='34', clouds='Clear', iconUrl='http://icons.wxug.com/i/c/k/clear.gif', windSpeed='2', windDirection='null', climateType='Sunny', humidity='89', feelsLike='37', maximumTemp='null', minimumTemp='null', pressure='null'}, Weather{time='8:00 AM', temperature='41', dewpoint='35', clouds='Clear', iconUrl='http://icons.wxug.com/i/c/k/clear.gif', windSpeed='2', windDirection='null', climateType='Sunny', humidity='82', feelsLike='41', maximumTemp='null', minimumTemp='null', pressure='null'}, Weather{time='9:00 AM', temperature='48', dewpoint='37', clouds='Clear', iconUrl='http://icons.wxug.com/i/c/k/clear.gif', windSpeed='5', windDirection='null', climateType='Sunny', humidity='68', feelsLike='48', maximumTemp='null', minimumTemp='null', pressure='null'}, Weather{time='10:00 AM', temperature='54', dewpoint='38', clouds='Clear', iconUrl='http://icons.wxug.com/i/c/k/clear.gif', windSpeed='7', windDirection='null', climateType='Sunny', humidity='54', feelsLike='54', maximumTemp='null
//[Weather{time='8:00 PM', temperature='52', dewpoint='31', clouds='Partly Cloudy', iconUrl='http://icons.wxug.com/i/c/k/nt_partlycloudy.gif', windSpeed='2', windDirection='134', climateType='Partly Cloudy', humidity='44', feelsLike='52', maximumTemp='null', minimumTemp='null', pressure='1025'}, Weather{time='9:00 PM', temperature='50', dewpoint='31', clouds='Clear', iconUrl='http://icons.wxug.com/i/c/k/nt_clear.gif', windSpeed='2', windDirection='143', climateType='Mostly Clear', humidity='48', feelsLike='50', maximumTemp='null', minimumTemp='null', pressure='1026'}, Weather{time='10:00 PM', temperature='47', dewpoint='31', clouds='Clear', iconUrl='http://icons.wxug.com/i/c/k/nt_clear.gif', windSpeed='2', windDirection='145', climateType='Mostly Clear', humidity='52', feelsLike='47', maximumTemp='null', minimumTemp='null', pressure='1027'}, Weather{time='11:00 PM', temperature='43', dewpoint='32', clouds='Clear', iconUrl='http://icons.wxug.com/i/c/k/nt_clear.gif', windSpeed='2', windDirection='149', climateType='Clear', humidity='66', feelsLike='43', maximumTemp='null', minimumTemp='null', pressure='1027'}, Weather{time='12:00 AM', temperature='41', dewpoint='33', clouds='Clear', iconUrl='http://icons.wxug.com/i/c/k/nt_clear.gif', windSpeed='2', windDirection='145', climateType='Clear', humidity='72', feelsLike='41', maximumTemp='null', minimumTemp='null', pressure='1027'}, Weather{time='1:00 AM', temperature='41', dewpoint='32', clouds='Clear', iconUrl='http://icons.wxug.com/i/c/k/nt_clear.gif', windSpeed='2', windDirection='146', climateType='Clear', humidity='72', feelsLike='41', maximumTemp='null', minimumTemp='null', pressure='1026'}, Weather{time='2:00 AM', temperature='40', dewpoint='32', clouds='Clear', iconUrl='http://icons.wxug.com/i/c/k/nt_clear.gif', windSpeed='2', windDirection='150', climateType='Clear', humidity='74', feelsLike='40', maximumTemp='null', minimumTemp='null', pressure='1026'}, Weather{time='3:00 AM', temperature='39', dewpoint='32', clouds='Clear', iconUrl='http://icons.wxug.com/i/c/k/nt_clear.gif', windSpeed='2', windDirection='149', climateType='Clear', humidity='76', feelsLike='39', maximumTemp='null', minimumTemp='null', pressure='1026'}, Weather{time='4:00 AM', temperature='37', dewpoint='32', clouds='Clear', iconUrl='http://icons.wxug.com/i/c/k/nt_clear.gif', windSpeed='1', windDirection='151', climateType='Clear', humidity='82', feelsLike='37', maximumTemp='null', minimumTemp='null', pressure='1027'}, Weather{time='5:00 AM', temperature='37', dewpoint='33', clouds='Clear', iconUrl='http://icons.wxug.com/i/c/k/nt_clear.gif', windSpeed='2', windDirection='156', climateType='Clear', humidity='85', feelsLike='37', maximumTemp='null', minimumTemp='null', pressure='1027'}, Weather{time='6:00 AM', temperature='36', dewpoint='33', clouds='Clear', iconUrl='http://icons.wxug.com/i/c/k/nt_clear.gif', windSpeed='2', windDirection='155', climateType='Clear', humidity='87', feelsLike='36', maximumTemp='null', minimumTemp='null', pressure='1027'}, Weather{time='7:00 AM', temperature='37', dewpoint='33', clouds='Clear', iconUrl='http://icons.wxug.com/i/c/k/clear.gif', windSpeed='2', windDirection='157', climateType='Sunny', humidity='87', feelsLike='37', maximumTemp='null', minimumTemp='null', pressure='1028'}, Weather{time='8:00 AM', temperature='40', dewpoint='35', clouds='Clear', iconUrl='http://icons.wxug.com/i/c/k/clear.gif', windSpeed='2', windDirection='171', climateType='Sunny', humidity='81', feelsLike='40', maximumTemp='null', minimumTemp='null', pressure='1028'}, Weather{time='9:00 AM', temperature='47', dewpoint='37', clouds='Clear', iconUrl='http://icons.wxug.com/i/c/k/clear.gif', windSpeed='5', windDirection='182', climateType='Sunny', humidity='68', feelsLike='44', maximumTemp='null', minimumTemp='null', pressure='1029'}, Weather{time='10:00 AM', temperature='53', dewpoint='38', clouds='Clear', iconUrl='http://icons.wxug.com/i/c/k/clear.gif', windSpeed='7', windDirection='188', climateType='Sunny', humidity='55', feelsLike='53', maximumTemp='null', minimumTemp=

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
                       Log.d("Demo", "Temp: " + temp);
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
}