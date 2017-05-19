package jram_mack.oneg;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * This is a Request java object. When a user submits a request, an instance of this class is created and the request's information are stored in this object
 * @author  JRAM-MACK
 * @author  CMPS253
 * @since 2/11/2017
 *
 * @version 1.0
 *
 *
 *
 */
public class Request {

    protected String bloodType;
    protected String hospital;
    protected String city;
    protected int units;
    protected String phoneNumber;
    private String key;
    private boolean status;
    protected String userID;
    private String name;
    private String phoneNumberOnListView;
    protected String currentDateTime;


    /**
     *
     * @return the date of the request's submission
     */
    public String getCurrentDateTime() {
        return currentDateTime;
    }

    /**
     *
     * @return name of the recipient
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name sets the name of the recipient to a new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return hospital for the blood donation operation
     */
    public String getHospital() {
        return hospital;
    }

    /**
     *
     * @param hospital sets the name of the hospital to a new name
     */
    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    /**
     *
     * @return blood type of the recipient
     */
    public String getBloodType() {
        return bloodType;
    }

    /**
     *
     * @param bloodType sets the blood type of the recipient to a new blood type
     */
    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    /**
     *
     * @return city of the recipient's residence
     */
    public String getCity() {
        return city;
    }

    /**
     *
     * @param city sets the recipient's city to a new city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     *
     * @return number of units required for the blood donation
     */
    public int getUnits() {
        return units;
    }

    /**
     *
     * @param units sets the number of units required for the blood donation to a new number
     */
    public void setUnits(int units) {
        this.units = units;
    }

    /**
     * this method decreases the number of units of the request by 1
     * 0 is the minimum number of units in a blood donation
     */
    public void acceptUnit(){
        if(getUnits() != 0){
            setUnits(getUnits() - 1);
        }
    }

    /**
     *
     * @return phone number of the recipient
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     *
     * @param phoneNumber sets the phone number of the recipient to a new phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     *
     * @return the type of the java object
     */
    public String getObjectType(){
        return "Requests";
    }

    /**
     *
     * @param s key of the request, this value is gotten from the firebase database
     */
    public void setKey(String s){
        this.key = s;
    }

    /**
     *
     * @return the key of the request
     */
    public String getKey(){
        return key;
    }

    /**
     *
     * @return true when the request is still available,  false when the request is completed
     */
    public boolean getStatus(){
        return status;
    }

    /**
     * flips the stauts of the request. In all cases, this method flips the status of any request to false making it unavailable or canceled
     */
    public void reverseStatus(){
        this.status = !status;
    }

    /**
     *
     * @return request's information in a String
     */
    @Override
    public String toString(){
        return "name: " + getName() + "\nPhone Number: " + getPhoneNumberOnListView() + "\nCity: " + getCity() + "\nDate & Time Issued: " + getCurrentDateTime() + "\nNumber Of Units: " + getUnits();
    }

    /**
     *
     * @return phone number of the recipiert provided with the country code +961
     * if the number starts with 0 digit, this digit is removed and the rest of the string concatenated with +961
     */
    public String getPhoneNumberOnListView(){
        String temp = this.phoneNumberOnListView;
        if(!(temp.charAt(0) + "" ).equals("+")){
            if ((temp.charAt(0) + "").equals("0")) {
                temp = "+961" + temp.substring(1, temp.length());
            } else {
                temp = "+961" + temp;
            }

        }
        return temp;
    }

    /**
     *
     * @param phoneNumberOnListView sets the phone number of the recipient with +961 code
     */
    public void setPhoneNumberOnListView(String phoneNumberOnListView){
        this.phoneNumberOnListView = phoneNumberOnListView;
    }

    /**
     *
     * @param o Request that needs to be compared to another request according to their requester's phone number
     * @return true if the users names are equal
     * false if the users are not equal
     *
     * Overrides the standard equals method
     */
    @Override
    public boolean equals(Object o){
        Request temp = (Request) o;
        if(temp.getPhoneNumber() == this.phoneNumber){
            return true;
        }
        return false;

    }


    /**
     *
     * @param name : name of the recipient
     * @param bloodType : blood type of the recipient
     * @param hospital : hospital where of the recipient is staying
     * @param city : city where the recipient lives
     * @param units : number of units that the recipient needs
     * @param phoneNumber : phone numbe rof the requester
     * @param key : key of the request
     */
    public Request(String name, String bloodType, String hospital, String city, int units, String phoneNumber, String key){
        this.bloodType = bloodType;
        this.hospital = hospital;
        this.city = city;
        this.units = units;
        this.phoneNumber = RegisterActivity.user.getPhoneNumber();
        this.status = true;
        this.name = name;
        this.userID = phoneNumber;
        this.key = key;
        SimpleDateFormat date = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        this.currentDateTime = date.format(new Date());
        setPhoneNumberOnListView(phoneNumber);


    }



}
