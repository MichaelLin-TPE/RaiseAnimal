package com.raise.raiseanimal.connect.gson_object;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AnimalObject implements Serializable {
    @SerializedName("animal_id")
    private int animalId;
    @SerializedName("animal_subid")
    private String animalSubid;
    @SerializedName("animal_area_pkid")
    private int animalAreaPkId;
    @SerializedName("animal_shelter_pkid")
    private int animalShlterPkId;
    @SerializedName("animal_place")
    private String animalPlace;
    @SerializedName("animal_kind")
    private String animalKind;
    @SerializedName("animal_sex")
    private String animalSex;
    @SerializedName("animal_bodytype")
    private String animalBodyType;
    @SerializedName("animal_colour")
    private String animalColour;
    @SerializedName("animal_age")
    private String animalAge;
    @SerializedName("animal_sterilization")
    private String animalSterilization;
    @SerializedName("animal_bacterin")
    private String animalBacterin;
    @SerializedName("animal_foundplace")
    private String animalFoundPlace;
    @SerializedName("animal_title")
    private String animalTitle;
    @SerializedName("animal_status")
    private String animalStatus;
    @SerializedName("animal_remark")
    private String animalRemark;
    @SerializedName("animal_caption")
    private String animalCaption;
    @SerializedName("animal_opendate")
    private String animalOpenDate;
    @SerializedName("animal_closeddate")
    private String animalCloseDate;
    @SerializedName("animal_update")
    private String animalUpdate;
    @SerializedName("animal_createtime")
    private String animalCreateTime;
    @SerializedName("shelter_name")
    private String shleterName;
    @SerializedName("album_file")
    private String albumFile;
    @SerializedName("album_update")
    private String albumUpdate;
    @SerializedName("cDate")
    private String cDate;
    @SerializedName("shelter_address")
    private String shelterAddress;
    @SerializedName("shelter_tel")
    private String shelterTel;


    public String getAnimalUpdate() {
        return animalUpdate;
    }

    public void setAnimalUpdate(String animalUpdate) {
        this.animalUpdate = animalUpdate;
    }

    public String getShleterName() {
        return shleterName;
    }

    public void setShleterName(String shleterName) {
        this.shleterName = shleterName;
    }

    public int getAnimalId() {
        return animalId;
    }

    public void setAnimalId(int animalId) {
        this.animalId = animalId;
    }

    public String getAnimalSubid() {
        return animalSubid;
    }

    public void setAnimalSubid(String animalSubid) {
        this.animalSubid = animalSubid;
    }

    public int getAnimalAreaPkId() {
        return animalAreaPkId;
    }

    public void setAnimalAreaPkId(int animalAreaPkId) {
        this.animalAreaPkId = animalAreaPkId;
    }

    public int getAnimalShlterPkId() {
        return animalShlterPkId;
    }

    public void setAnimalShlterPkId(int animalShlterPkId) {
        this.animalShlterPkId = animalShlterPkId;
    }

    public String getAnimalPlace() {
        return animalPlace;
    }

    public void setAnimalPlace(String animalPlace) {
        this.animalPlace = animalPlace;
    }

    public String getAnimalKind() {
        return animalKind;
    }

    public void setAnimalKind(String animalKind) {
        this.animalKind = animalKind;
    }

    public String getAnimalSex() {
        return animalSex;
    }

    public void setAnimalSex(String animalSex) {
        this.animalSex = animalSex;
    }

    public String getAnimalBodyType() {
        return animalBodyType;
    }

    public void setAnimalBodyType(String animalBodyType) {
        this.animalBodyType = animalBodyType;
    }

    public String getAnimalColour() {
        return animalColour;
    }

    public void setAnimalColour(String animalColour) {
        this.animalColour = animalColour;
    }

    public String getAnimalAge() {
        return animalAge;
    }

    public void setAnimalAge(String animalAge) {
        this.animalAge = animalAge;
    }

    public String getAnimalSterilization() {
        return animalSterilization;
    }

    public void setAnimalSterilization(String animalSterilization) {
        this.animalSterilization = animalSterilization;
    }

    public String getAnimalBacterin() {
        return animalBacterin;
    }

    public void setAnimalBacterin(String animalBacterin) {
        this.animalBacterin = animalBacterin;
    }

    public String getAnimalFoundPlace() {
        return animalFoundPlace;
    }

    public void setAnimalFoundPlace(String animalFoundPlace) {
        this.animalFoundPlace = animalFoundPlace;
    }

    public String getAnimalTitle() {
        return animalTitle;
    }

    public void setAnimalTitle(String animalTitle) {
        this.animalTitle = animalTitle;
    }

    public String getAnimalStatus() {
        return animalStatus;
    }

    public void setAnimalStatus(String animalStatus) {
        this.animalStatus = animalStatus;
    }

    public String getAnimalRemark() {
        return animalRemark;
    }

    public void setAnimalRemark(String animalRemark) {
        this.animalRemark = animalRemark;
    }

    public String getAnimalCaption() {
        return animalCaption;
    }

    public void setAnimalCaption(String animalCaption) {
        this.animalCaption = animalCaption;
    }

    public String getAnimalOpenDate() {
        return animalOpenDate;
    }

    public void setAnimalOpenDate(String animalOpenDate) {
        this.animalOpenDate = animalOpenDate;
    }

    public String getAnimalCloseDate() {
        return animalCloseDate;
    }

    public void setAnimalCloseDate(String animalCloseDate) {
        this.animalCloseDate = animalCloseDate;
    }

    public String getAnimalCreateTime() {
        return animalCreateTime;
    }

    public void setAnimalCreateTime(String animalCreateTime) {
        this.animalCreateTime = animalCreateTime;
    }

    public String getAlbumFile() {
        return albumFile;
    }

    public void setAlbumFile(String albumFile) {
        this.albumFile = albumFile;
    }

    public String getAlbumUpdate() {
        return albumUpdate;
    }

    public void setAlbumUpdate(String albumUpdate) {
        this.albumUpdate = albumUpdate;
    }

    public String getcDate() {
        return cDate;
    }

    public void setcDate(String cDate) {
        this.cDate = cDate;
    }

    public String getShelterAddress() {
        return shelterAddress;
    }

    public void setShelterAddress(String shelterAddress) {
        this.shelterAddress = shelterAddress;
    }

    public String getShelterTel() {
        return shelterTel;
    }

    public void setShelterTel(String shelterTel) {
        this.shelterTel = shelterTel;
    }
}
