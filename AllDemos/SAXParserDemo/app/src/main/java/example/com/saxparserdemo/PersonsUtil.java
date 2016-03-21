package example.com.saxparserdemo;

import android.util.Xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Dnyanshree on 2/22/2016.
 */
public class PersonsUtil {

    static public class PersonsSAXParser extends DefaultHandler{
        ArrayList<Person> personsList;
        Person person;
        StringBuilder xmlInnerText;

        static public ArrayList<Person> parsePerson(InputStream in) throws IOException, SAXException {
            PersonsSAXParser parser = new PersonsSAXParser();
            Xml.parse(in, Xml.Encoding.UTF_8, parser);

            return parser.getPersonsList();
        }

        public ArrayList<Person> getPersonsList() {
            return personsList;
        }

        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
            personsList = new ArrayList<Person>();
            xmlInnerText = new StringBuilder();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);
            if(localName.equals("person")){
                person = new Person();
                person.setId(attributes.getValue("id"));
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);
            if(localName.equals("person")){
                personsList.add(person);
            }else if(localName.equals("name")){
                person.setName(xmlInnerText.toString().trim());
            }else if(localName.equals("age")){
                person.setAge(xmlInnerText.toString().trim());
            }else if(localName.equals("department")){
                person.setDepartment(xmlInnerText.toString().trim());
            }
            xmlInnerText.setLength(0);

        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            xmlInnerText.append(ch, start, length);
        }
    }
}
