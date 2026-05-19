public class Passenger {
    protected int age;
    protected String name, gender;

    String getName() {
        return name;
    }

    public Passenger(int age, String name, String gender) {
        this.age = age;
        this.name = name;
        this.gender = gender;
    }

    int getAge() {
        return age;
    }

    String getGender() {
        return gender;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
