package example.com.jsondemo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Dnyanshree on 2/22/2016.
 */
public class PersonsUtil {

    static public class PersonsJSONParser {
        static ArrayList<Person> parsePersons(String in) throws JSONException {
            ArrayList<Person> personsList = new ArrayList<Person>();

            JSONObject root = new JSONObject(in);
            JSONArray personsJSONArray = root.getJSONArray("persons");

            for(int i=0; i<personsJSONArray.length(); i++){
                JSONObject personJSONObject = personsJSONArray.getJSONObject(i);
               /* Extract data here:
                Person person = new Person();
                person.setName(personJSONObject.getString("name"));
                person.setId(personJSONObject.getInt("id"));
                person.setAge(personJSONObject.getInt("age"));
                person.setDepartment(personJSONObject.getString("department"));
                */
                //Using method in Person.java
                Person person = Person.createPerson(personJSONObject);
                personsList.add(person);
            }
            return personsList;
        }
    }
}
