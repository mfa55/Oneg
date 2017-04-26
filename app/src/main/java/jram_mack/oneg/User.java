package jram_mack.oneg;

/**
 * @author  JRAM-MACK
 * @author  CMPS253
 * @since 2/11/2017
 *
 * @version 1.0
 *
 * This is a User java object
 * When a user tries to login or sign in, an instance of this class is created and the user's information are stored in this object
 *
 *
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


    /**
     *
     * @param name : name of the user
     * @param city : city where the user lives
     * @param phoneNumber : user's phone number
     * @param gender : user's gender
     * @param bloodType : user's blood type
     */
    public User(String name, String city, String phoneNumber, String gender, String bloodType/*, String password*/) {
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.gender = gender;
        this.bloodType = bloodType;
        setKey();
    }

    /**
     *
     * @return blood type of the user
     */
    public String getBloodType() {
        return bloodType;
    }

    /**
     *
     * @param bloodType sets the blood type of the user to a new blood type
     */
    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    /**
     *
     * @return name of the user
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name sets the last name of the user to a new last name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return gender of the user
     */
    public String getGender() {
        return gender;
    }

    /**
     *
     * @param gender sets the gender of the user to a new gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     *
     * @return city where the user lives
     */
    public String getCity() {
        return city;
    }

    /**
     *
     * @param city sets the city where the user lives to a new city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     *
     * @return phone number of the user
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     *
     * @param phoneNumber sets the phone number of the user to a new phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     *
     * @param o User that needs to be compared to another user according to their names
     * @return true if the users names are equal
     * false if the users are not equal
     *
     * Overrides the standard equals method
     */
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

    /**
     *
     * @return the type of the java object
     */
    public String getObjectType(){
        return "Users";
    }

    /**
     * The users key is his phone number since every phone number is single and unique
     */
    public void setKey() {
        this.key = getPhoneNumber();
    }

    /**
     *
     * @return the phone number of the user which represents his key in the database
     */
    public String getKey(){
        return key;
    }

    /**
     *
     * @return user's information in a String
     */
    @Override
    public String toString(){
        return getName() + " " + getCity() + " " + getPhoneNumber() + " " + getBloodType();
    }

}
