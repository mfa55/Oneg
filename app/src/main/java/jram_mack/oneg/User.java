package jram_mack.oneg;

/**
 * Created by Mario_Abou_Naaman on 2/11/2017.
 */

public class User {

    private String name;
    //private String email;
    private String gender;
    private String city;
    private String password;
    private String phoneNumber;
    private String bloodType;
    private String key;



    public User(String name, String city, String phoneNumber, String gender, String bloodType/*, String password*/) {
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.gender = gender;
        this.bloodType = bloodType;
        //this.password = password;
        setKey();
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getName() {
        return name;
    }

    public void setName(String lastName) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean compare(User user) {
        if(this.name.equals(user.name)) {
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof User){
            User temp = (User) o;
            if(this.name.equals(temp.name) ){
                return true;
            } else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    public String getObjectType(){
        return "Users";
    }

    public void setKey() {
        this.key = getPhoneNumber();
    }

    public String getKey(){
        return key;
    }

    @Override
    public String toString(){
        return getName() + " " + getCity() + " " + getPhoneNumber() + " " + getBloodType();
    }

}
