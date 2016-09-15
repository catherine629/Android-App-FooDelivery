package foodeliver.entities;

import android.graphics.Bitmap;

import java.util.ArrayList;

import foodeliver.remote.DBTask;
import foodeliver.remote.GetPicture;
import foodeliver.utility.DataSearch;
import foodeliver.utility.PictureSearch;

/**
 * Food Menu class.
 * @author Yu Qiu
 */
public class FoodMenu extends Menu<Food> implements DataSearch, PictureSearch {

    @Override
    public void createItem(String name, double price, Byte[] picture) {

    }

    @Override
    public void readItem(String email) {
        this.setRestaurant(email);
        String sql = "SELECT * FROM MENU WHERE RestaurantEmail='" + email +"';";
        DBTask dbT = new DBTask();
        ArrayList<String> list = new ArrayList<String>();
        list.add(sql);
        dbT.getResult(list, FoodMenu.this, "result");
    }

    @Override
    public void updateItem(String name, double price, Byte[] picture) {

    }

    @Override
    public void deleteItem(String name) {

    }

    @Override
    public void dbResultReady(String result) {
        System.out.println("SQL Result: " + result);
        ArrayList<String> searchPics = new ArrayList<String>();
        String[] foods = result.split("\n");
        for (String food: foods){
            String[] elements = food.split("\t");
            this.items.add(new Food(elements[0], elements[2], elements[3], elements[4]));
            searchPics.add(elements[4]);
        }
        GetPicture gp = new GetPicture();
        System.out.println("gongpaojiding:"+searchPics.get(0));
        gp.search(searchPics, FoodMenu.this);
    }

    @Override
    public void updateReady(String result) {
    }

    @Override
    public void dbDetailReady(String result) {
    }

    @Override
    public void deleteReady(String result) {

    }

    @Override
    public void pictureReady(ArrayList<Bitmap> pictures) {
        for (int i = 0; i < pictures.size(); i++){
            System.out.println("picture:"+pictures.get(i));
            items.get(i).setPicture(pictures.get(i));
        }
    }
}
