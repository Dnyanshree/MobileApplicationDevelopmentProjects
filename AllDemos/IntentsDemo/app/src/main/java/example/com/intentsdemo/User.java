package example.com.intentsdemo;

import java.io.Serializable;

/**
 * Created by Dnyanshree on 1/30/2016.
 */
public class User implements Serializable{
    String name;
    double age;

    public User(String name, double age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
