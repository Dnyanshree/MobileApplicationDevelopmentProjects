package example.com.jsondemo;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dnyanshree on 2/22/2016.
 */
public class Person {
    String name, department;
    int age,id;

    public static Person createPerson(JSONObject js) throws JSONException {
        Person person = new Person();
        person.setName(js.getString("name"));
        person.setId(js.getInt("id"));
        person.setAge(js.getInt("age"));
        person.setDepartment(js.getString("department"));

        return person;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}
