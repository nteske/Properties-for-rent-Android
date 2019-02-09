package com.example.tesic.projekat.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Property {
    public static String url="http://propertyrent.herokuapp.com/propertie/";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;
    private String street;

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getImage4() {
        return image4;
    }

    public void setImage4(String image4) {
        this.image4 = image4;
    }

    private String image1;
    private String image2;
    private String image3;
    private String image4;
    private Boolean internet;
    private Boolean cableTV;
    private Boolean bBed;
    private Boolean sBed;
    private Boolean stove;
    private Boolean washer;
    private Boolean landline;
    private Boolean fridge;
    private Boolean fireplace;
    private Boolean bathub;
    private Boolean conditioner;
    private Boolean microwave;
    private String id;
    private int deposit;
    private int rooms;
    private int area;
    private String date;
    private String user;

    private static ArrayList<Property> allOf=new ArrayList<Property>();
    public Property() {
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public Property(String title, String street, String image1, String image2, String image3, String image4, Boolean internet,
                    Boolean cableTV, Boolean bBed, Boolean sBed, Boolean stove, Boolean washer, Boolean landline, Boolean fridge,
                    Boolean fireplace, Boolean bathub, Boolean conditioner, Boolean microwave, String id, int deposit, int rooms,
                    int area, String date, String user) {
        this.title = title;
        this.street = street;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.image4 = image4;
        this.internet = internet;
        this.cableTV = cableTV;
        this.bBed = bBed;
        this.sBed = sBed;
        this.stove = stove;
        this.washer = washer;
        this.landline = landline;
        this.fridge = fridge;
        this.fireplace = fireplace;
        this.bathub = bathub;
        this.conditioner = conditioner;
        this.microwave = microwave;
        this.id = id;
        this.deposit = deposit;
        this.rooms = rooms;
        this.area = area;
        this.date = date;
        this.user = user;
    }

    public static Property CreateFromJson(JSONObject ap){
        try {
        return new Property(
                ap.getString("title"),
                ap.getString("street"),
                ap.getString("image1"),
                ap.getString("image2"),
                ap.getString("image3"),
                ap.getString("image4"),
                Boolean.parseBoolean(ap.getString("internet")),
                Boolean.parseBoolean(ap.getString("cableTV")),
                Boolean.parseBoolean(ap.getString("bBed")),
                Boolean.parseBoolean(ap.getString("sBed")),
                Boolean.parseBoolean(ap.getString("stove")),
                Boolean.parseBoolean(ap.getString("washer")),
                Boolean.parseBoolean(ap.getString("landline")),
                Boolean.parseBoolean(ap.getString("fridge")),
                Boolean.parseBoolean(ap.getString("fireplace")),
                Boolean.parseBoolean(ap.getString("bathub")),
                Boolean.parseBoolean(ap.getString("conditioner")),
                Boolean.parseBoolean(ap.getString("microwave")),
                ap.getString("_id"),
                Integer.parseInt( ap.getString("deposit")),
                Integer.parseInt( ap.getString("rooms")),
                Integer.parseInt( ap.getString("area")),
                ap.getString("date"),
                ap.getString("user"));
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public String toString() {
        return "Property{" +
                "title='" + title + '\'' +
                ", street='" + street + '\'' +
                ", image1='" + image1 + '\'' +
                ", image2='" + image2 + '\'' +
                ", image3='" + image3 + '\'' +
                ", image4='" + image4 + '\'' +
                ", internet=" + internet +
                ", cableTV=" + cableTV +
                ", bBed=" + bBed +
                ", sBed=" + sBed +
                ", stove=" + stove +
                ", washer=" + washer +
                ", landline=" + landline +
                ", fridge=" + fridge +
                ", fireplace=" + fireplace +
                ", bathub=" + bathub +
                ", conditioner=" + conditioner +
                ", microwave=" + microwave +
                ", _id='" + id + '\'' +
                ", deposit=" + deposit +
                ", rooms=" + rooms +
                ", area=" + area +
                ", date='" + date + '\'' +
                ", user='" + user + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /*{"title":"dsaasdasdasd","street":"sasdadsasd1","image1":"e61054740178ddea0ec6cca1901df7e51542452867015.jpg",
    "image2":"3b5fcaf17f4cf79676e910f55e8708b61542452871902.jpg","image3":"4b135a559c7c3ab8e7131f69b6a0d1071542452878681.jpg",
    "image4":"c2cdfa70e09f8e41f5eb2b5808873eec1542453525551.jpg",
    "internet":true,"cableTV":false,"bBed":false,"sBed":false,"stove":true,"washer":true,"landline":true,
    "fridge":true,"fireplace":true,"bathub":false,"conditioner":false,"microwave":false,"_id":"5beff68fffa6330014f0b6d8",
    "deposit":234,"rooms":2,"area":24,"date":"2018-11-17T11:07:59.746Z",
    "user":"5beff50fffa6330014f0b6d7","__v":0}
     */
    public Property(JSONObject obj){
        try {
            this.title=obj.getString("title");
            this.street = obj.getString("street");
            this.image1 = obj.getString("image1");
            this.image2 = obj.getString("image2");
            this.image3 = obj.getString("image3");
            this.image4 = obj.getString("image4");
            this.internet = Boolean.parseBoolean(obj.getString("internet"));
            this.cableTV = Boolean.parseBoolean(obj.getString("cableTV"));
            this.bBed = Boolean.parseBoolean(obj.getString("bBed"));
            this.sBed = Boolean.parseBoolean(obj.getString("sBed"));
            this.stove = Boolean.parseBoolean(obj.getString("stove"));
            this.washer = Boolean.parseBoolean(obj.getString("washer"));
            this.landline = Boolean.parseBoolean(obj.getString("landline"));
            this.fridge =Boolean.parseBoolean(obj.getString("fridge"));
            this.fireplace = Boolean.parseBoolean(obj.getString("fireplace"));
            this.bathub = Boolean.parseBoolean(obj.getString("bathub"));
            this.conditioner = Boolean.parseBoolean(obj.getString("conditioner"));
            this.microwave = Boolean.parseBoolean(obj.getString("microwave"));
            this.id = obj.getString("_id");
            this.deposit = Integer.parseInt(obj.getString("deposit"));
            this.rooms = Integer.parseInt(obj.getString("rooms"));
            this.area = Integer.parseInt(obj.getString("area"));
            this.date = obj.getString("date");
            this.user = obj.getString("user");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public Property(String id, String title, String image1, String street, int deposit){
        this.id=id;
        this.title=title;
        this.image1=image1;
        this.street=street;
        this.deposit=deposit;
    }

    public Boolean getInternet() {
        return internet;
    }

    public void setInternet(Boolean internet) {
        this.internet = internet;
    }

    public Boolean getCableTV() {
        return cableTV;
    }

    public void setCableTV(Boolean cableTV) {
        this.cableTV = cableTV;
    }

    public Boolean getbBed() {
        return bBed;
    }

    public void setbBed(Boolean bBed) {
        this.bBed = bBed;
    }

    public Boolean getsBed() {
        return sBed;
    }

    public void setsBed(Boolean sBed) {
        this.sBed = sBed;
    }

    public Boolean getStove() {
        return stove;
    }

    public void setStove(Boolean stove) {
        this.stove = stove;
    }

    public Boolean getWasher() {
        return washer;
    }

    public void setWasher(Boolean washer) {
        this.washer = washer;
    }

    public Boolean getLandline() {
        return landline;
    }

    public void setLandline(Boolean landline) {
        this.landline = landline;
    }

    public Boolean getFridge() {
        return fridge;
    }

    public void setFridge(Boolean fridge) {
        this.fridge = fridge;
    }

    public Boolean getFireplace() {
        return fireplace;
    }

    public void setFireplace(Boolean fireplace) {
        this.fireplace = fireplace;
    }

    public Boolean getBathub() {
        return bathub;
    }

    public void setBathub(Boolean bathub) {
        this.bathub = bathub;
    }

    public Boolean getConditioner() {
        return conditioner;
    }

    public void setConditioner(Boolean conditioner) {
        this.conditioner = conditioner;
    }

    public Boolean getMicrowave() {
        return microwave;
    }

    public void setMicrowave(Boolean microwave) {
        this.microwave = microwave;
    }

    public int getRooms() {
        return rooms;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
