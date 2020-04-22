package com.raise.raiseanimal.animal_fragment;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class AnimalFavorite implements Serializable {
    @SerializedName("name")
    private String name;
    @SerializedName("photo")
    private String photo;
    @SerializedName("sex")
    private String sex;
    @SerializedName("no_sex")
    private boolean noSex;
    @SerializedName("color")
    private String color;
    @SerializedName("size")
    private String size;
    @SerializedName("number")
    private String number;
    @SerializedName("story")
    private String story;
    @SerializedName("is_favorite")
    private boolean isFavorite;
    @SerializedName("location")
    private String location;
    @SerializedName("found_place")
    private String foundPlace;

    @SerializedName("personality")
    private ArrayList<String> personality;

    public ArrayList<String> getPersonality() {
        return personality;
    }

    public void setPersonality(ArrayList<String> personality) {
        this.personality = personality;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFoundPlace() {
        return foundPlace;
    }

    public void setFoundPlace(String foundPlace) {
        this.foundPlace = foundPlace;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public boolean isNoSex() {
        return noSex;
    }

    public void setNoSex(boolean noSex) {
        this.noSex = noSex;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }
}
