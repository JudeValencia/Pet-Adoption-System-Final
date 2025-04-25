/**
 * Represents general information about an individual, including basic
 * personal details such as name, age, birthdate, and gender.
 *
 * This class provides getter and setter methods to access and modify
 * the attributes. It can be extended by other classes to represent
 * specific entities by adding additional fields and behavior.
 */
public class General_Information {
    private String name;
    private int  age, birthMonth, birthDay,birthYear;
    private char gender;

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

    public int getBirthMonth() {
        return birthMonth;
    }

    public void setBirthMonth(int birthMonth) {
        this.birthMonth = birthMonth;
    }

    public int getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(int birthDay) {
        this.birthDay = birthDay;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }
}
