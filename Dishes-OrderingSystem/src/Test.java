import com.google.gson.Gson;
import com.google.gson.JsonArray;
import constant.Constant;
import dataBase.Dish;
import dataBase.Dish_category;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Test {
    public static void main(String[] args) {
        Gson gson = new Gson();
        Connection connection = null;
        Statement statement = null;
        ArrayList<Dish> dishArrayList = new ArrayList<>();
        ArrayList<Dish_category> dishCategories = new ArrayList<>();
        ArrayList<HashMap> unionList = new ArrayList<>();
        try {
            Class.forName(constant.Constant.JDBC_DRIVER);

            //connect to DataBase
            connection = DriverManager.getConnection(constant.Constant.DB_URL, constant.Constant.USER, constant.Constant.PASSWORD);

            statement = connection.createStatement();

            String sql;
            sql = "SELECT category_name,category_id FROM dish_category";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){

                String category_name = resultSet.getString("category_name");
                int category_id = resultSet.getInt("category_id");

                Dish_category dishCategory = new Dish_category(category_name,category_id);

                dishCategories.add(dishCategory);
            }


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try{
            Class.forName(Constant.JDBC_DRIVER);

            connection = DriverManager.getConnection(Constant.DB_URL,Constant.USER,Constant.PASSWORD);

            statement = connection.createStatement();

            String sql;
            for (int i = 0; i < dishCategories.size(); i++) {
                sql = "SELECT dish_id,dish_name,dish_price,dish_image_url,dish_detail,dish_is_deleted,dish_category_id FROM dish WHERE dish_category_id ="+dishCategories.get(i).getCategoryId();
                ResultSet resultSet = statement.executeQuery(sql);

                while (resultSet.next()){
                    int dish_id = resultSet.getInt("dish_id");
                    String dish_name = resultSet.getString("dish_name");
                    double dish_price = resultSet.getDouble("dish_price");
                    String dish_image_url = resultSet.getString("dish_image_url");
                    String dish_detail = resultSet.getString("dish_detail");
                    int dish_is_deleted = resultSet.getInt("dish_is_deleted");
                    int dish_category_id = resultSet.getInt("dish_category_id");

                    //construction
                    Dish dish = new Dish(dish_id,dish_name,dish_price,dish_image_url,dish_detail,dish_is_deleted,dish_category_id);

                    //let the dish to list
                    dishArrayList.add(dish);
                }
                //
                HashMap<String, String> hashMap1 = new HashMap<>();
                HashMap<String, ArrayList<Dish>> hashMap2= new HashMap<>();
//                HashMap<HashMap,HashMap> hashMap3 = new HashMap<>();
//                ArrayList<HashMap> unionList = new ArrayList<>();
                hashMap1.put("category",dishCategories.get(i).getCategoryName());
                hashMap2.put("dishes",dishArrayList);
                unionList.add(hashMap1);
                unionList.add(hashMap2);
            }
            HashMap<String,ArrayList<HashMap>> hashMap4 = new HashMap<>();
            hashMap4.put("dish_categories",unionList);
            String hashMap44 = gson.toJson(hashMap4);
            HashMap<String,Integer> hashMap5 = new HashMap<>();
            hashMap5.put("status",1);
            String hashMap55 = gson.toJson(hashMap5);
//            ArrayList<HashMap> hashMaps = new ArrayList<>();
            JsonArray hashMaps = new JsonArray();
//            hashMaps.add(hashMap55);
//            hashMaps.add(hashMap44);
            String returnKey = gson.toJson(hashMaps);
            System.out.println(returnKey);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
//        for (int i = 0; i < dishArrayList.size(); i++) {
//            System.out.println(dishArrayList.get(i).getDishId()+" "+dishArrayList.get(i).getDishCategoryId());
//        }


    }
}