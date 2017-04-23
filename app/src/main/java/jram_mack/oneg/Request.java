package jram_mack.oneg;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Mario_Abou_Naaman on 2/16/17.
 */

public class Request {

    protected String bloodType;
    protected String hospital;
    protected String city;
    protected int units;
    protected String phoneNumber;
    protected String email;
    private String key;
    private boolean status;
    protected String userID;
    private String name;
    private String phoneNumberOnListView;

    public String getCurrentDateTime() {
        return currentDateTime;
    }

    protected String currentDateTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public void acceptUnit(){
        if(getUnits() != 0){
            setUnits(getUnits() - 1);
        }
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getObjectType(){
        return "Requests";
    }

    public void setKey(String s){
        this.key = s;
    }

    public String getKey(){
        return key;
    }

    public boolean getStatus(){
        return status;
    }

    public void reverseStatus(){
        this.status = !status;
    }

    @Override
    public String toString(){
        return "name: " + getName() + "\nPhone Number: " + getPhoneNumberOnListView() + "\nCity: " + getCity() + "\nDate & Time Issued: " + getCurrentDateTime() + "\nNumber Of Units: " + getUnits();
    }

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

    public void setPhoneNumberOnListView(String phoneNumberOnListView){
        this.phoneNumberOnListView = phoneNumberOnListView;
    }

    @Override
    public boolean equals(Object o){
        Request temp = (Request) o;
        if(temp.getPhoneNumber() == this.phoneNumber){
            return true;
        }
        return false;

    }

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
