package com.raise.raiseanimal.staff_place;

import android.util.Log;

import com.raise.raiseanimal.connect.gson_object.AnimalObject;

import java.util.ArrayList;

public class StaffActivityPresenterImpl implements StaffActivityPresenter {

    private StaffActivityVu mView;

    private String message;

    private ArrayList<AnimalObject> catchFirebaseArray;

    private String sex, noSex, size, color;

    private ArrayList<AnimalObject> filterArray;

    boolean isSexAll,isNoSexAll,isSizeAll,isColorAll;

    public StaffActivityPresenterImpl(StaffActivityVu mView) {
        this.mView = mView;
        sex = "全部";
        noSex = "全部";
        size = "全部";
        color = "全部";
        isSexAll = true;
        isNoSexAll = true;
        isSizeAll = true;
        isColorAll = true;
    }

    @Override
    public void onLoginButtonClickListener(String account, String password) {
        if (account.equals("root") && password.equals("root")){
            mView.hideLoginView(true);
            mView.showRecycler(true);
            mView.saveAccountAndPassword(account,password);
            mView.showProgress(true);
            mView.searchData();
        }else {
            message = "帳號密碼錯誤";
            mView.showToast(message);
        }
    }

    @Override
    public void onCatchData(ArrayList<AnimalObject> dataArray) {
        this.catchFirebaseArray = dataArray;
        Log.i("Michael", "json != null");
        mView.showProgress(false);
        mView.setRecyclerView(dataArray);

        //這邊顯示篩選
        ArrayList<String> colorArray = new ArrayList<>();
        ArrayList<String> noSexArray = new ArrayList<>();
        ArrayList<String> sexArray = new ArrayList<>();
        ArrayList<String> sizeArray = new ArrayList<>();
        noSexArray.add("結育類");
        noSexArray.add("全部");
        noSexArray.add("已結育");
        noSexArray.add("未結育");
        sexArray.add("性別類");
        sexArray.add("全部");
        sexArray.add("公");
        sexArray.add("母");
        sizeArray.add("體型類");
        sizeArray.add("全部");
        sizeArray.add("大型");
        sizeArray.add("中型");
        sizeArray.add("小型");
        colorArray.add("顏色類");
        colorArray.add("全部");
        for (AnimalObject data : dataArray) {
            if (colorArray.size() == 0) {
                colorArray.add(data.getAnimalColour());
            } else {
                boolean isEqual = false;
                for (String name : colorArray) {
                    if (name.equals(data.getAnimalColour())) {
                        isEqual = true;
                    }
                }
                if (!isEqual) {
                    colorArray.add(data.getAnimalColour());
                }
            }
        }
        mView.showFilterView(colorArray, noSexArray, sexArray, sizeArray);
    }

    @Override
    public void onBackButtonClickListener() {
        mView.closePage();
    }

    @Override
    public void onFilterItemClickListener(String name, String value) {
        filterArray = new ArrayList<>();
        if (name.contains("公") || name.contains("母")) {
            if (name.equals("公")) {
                sex = "M";
            } else {
                sex = "F";
            }
            isSexAll = false;
        } else if (name.equals("已結育") || name.equals("未結育")) {
            if (name.equals("已結育")) {
                noSex = "T";
            } else {
                noSex = "F";
            }
            isNoSexAll = false;
        } else if (name.contains("型")) {
            if (name.equals("大型")) {
                size = "BIG";
            } else if (name.equals("中型")) {
                size = "MEDIUM";
            } else {
                size = "SMALL";
            }
            isSizeAll = false;
        } else if (name.contains("色")) {
            color = name;
            isColorAll = false;
        }
        if (name.equals("全部") && value.equals("性別類")){
            isSexAll = true;
        }
        if (name.equals("全部") && value.equals("結育類")){
            isNoSexAll = true;
        }

        if (name.equals("全部") && value.equals("體型類")){
            isSizeAll = true;
        }

        if (name.equals("全部") && value.equals("顏色類")){
            isColorAll = true;
        }


        Log.i("Michael", "性別 : " + sex + " , 節育 : " + noSex + " , 形體 : " + size + " , 顏色 : " + color);

        for (AnimalObject data : catchFirebaseArray) {
            if (sex.equals(data.getAnimalSex()) &&
                    noSex.equals(data.getAnimalSterilization()) &&
                    size.equals(data.getAnimalBodyType()) &&
                    color.equals(data.getAnimalColour())) {
                filterArray.add(data);
            }
            //1
            if (isSexAll && !isNoSexAll && !isSizeAll && !isColorAll){
                if (noSex.equals(data.getAnimalSterilization()) &&
                        size.equals(data.getAnimalBodyType()) &&
                        color.equals(data.getAnimalColour())) {
                    filterArray.add(data);
                }
            }
            //12
            if (isSexAll && isNoSexAll && !isSizeAll && !isColorAll){
                if (size.equals(data.getAnimalBodyType()) &&
                        color.equals(data.getAnimalColour())) {
                    filterArray.add(data);
                }

            }
            //123
            if (isSexAll && isNoSexAll && isSizeAll && !isColorAll){
                if (color.equals(data.getAnimalColour())) {
                    filterArray.add(data);
                }
            }
            //13
            if (isSexAll && !isNoSexAll && isSizeAll && !isColorAll){
                if (noSex.equals(data.getAnimalSterilization()) &&
                        color.equals(data.getAnimalColour())) {
                    filterArray.add(data);
                }
            }
            //14
            if (isSexAll && !isNoSexAll && !isSizeAll && isColorAll){
                if (noSex.equals(data.getAnimalSterilization()) &&
                        size.equals(data.getAnimalBodyType())) {
                    filterArray.add(data);
                }
            }
            //124
            if (isSexAll && isNoSexAll && !isSizeAll && isColorAll){
                if (size.equals(data.getAnimalBodyType())) {
                    filterArray.add(data);
                }
            }
            //134
            if (isSexAll && !isNoSexAll && isSizeAll && isColorAll){
                if (noSex.equals(data.getAnimalSterilization())) {
                    filterArray.add(data);
                }
            }

            //2
            if (!isSexAll && isNoSexAll && !isSizeAll && !isColorAll){
                if (sex.equals(data.getAnimalSex()) &&
                        size.equals(data.getAnimalBodyType()) &&
                        color.equals(data.getAnimalColour())) {
                    filterArray.add(data);
                }
            }
            //23
            if (!isSexAll && isNoSexAll && isSizeAll && !isColorAll){
                if (sex.equals(data.getAnimalSex()) &&
                        color.equals(data.getAnimalColour())) {
                    filterArray.add(data);
                }
            }
            //24
            if (!isSexAll && isNoSexAll && !isSizeAll && isColorAll){
                if (sex.equals(data.getAnimalSex()) &&
                        size.equals(data.getAnimalBodyType())) {
                    filterArray.add(data);
                }
            }
            //234
            if (!isSexAll && isNoSexAll && isSizeAll && isColorAll){
                if (sex.equals(data.getAnimalSex())) {
                    filterArray.add(data);
                }
            }
            //3
            if (!isSexAll && !isNoSexAll && isSizeAll && !isColorAll){
                if (sex.equals(data.getAnimalSex()) &&
                        noSex.equals(data.getAnimalSterilization()) &&
                        color.equals(data.getAnimalColour())) {
                    filterArray.add(data);
                }
            }
            //34
            if (!isSexAll && !isNoSexAll && isSizeAll && isColorAll){
                if (sex.equals(data.getAnimalSex()) &&
                        noSex.equals(data.getAnimalSterilization())) {
                    filterArray.add(data);
                }
            }
            //4
            if (!isSexAll && !isNoSexAll && !isSizeAll && isColorAll){
                if (sex.equals(data.getAnimalSex()) &&
                        noSex.equals(data.getAnimalSterilization()) &&
                        size.equals(data.getAnimalBodyType())) {
                    filterArray.add(data);
                }
            }
        }
        if (isColorAll && isSizeAll && isNoSexAll && isSexAll){
            mView.setRecyclerView(catchFirebaseArray);
        }else {
            mView.setRecyclerView(filterArray);
            if (filterArray.size() != 0){
                mView.showSearchNoData(false);
            }else {
                mView.showSearchNoData(true);
            }
        }
    }

    @Override
    public void onAnimalItemClickListener(AnimalObject data, int itemPosition) {
        mView.intentToEditPage(data,itemPosition,catchFirebaseArray);
    }
}
