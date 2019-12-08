package service;

import DishUtil.Util;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import constant.Constant;
import dataBase.Dish;
import dataBase.Dish_category;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

@WebServlet("/dish_list")
@MultipartConfig
public class dish_list extends HttpServlet {

    public dish_list() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //TO-DO
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //return service.dish_list

        //Init the code
        Gson gson = new Gson();
        Connection connection = null;
        Statement statement = null;
        Util util = new Util();

        //set respond type
        resp.setContentType("application/json");
        resp.setCharacterEncoding("utf-8");

        //Init return message
        PrintWriter printWriter = resp.getWriter();

        //Judge the token
        if(util.checkToken(req)==true){

            System.out.println("correct");

//        ArrayList<Dish> dishArrayList = new ArrayList<>();
            ArrayList<Dish_category> dishCategories = new ArrayList<>();
            JsonArray dish_categories = new JsonArray();
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

                    JsonArray dishes = new JsonArray();

                    while (resultSet.next()){
                        //循环10次
                        int dish_id = resultSet.getInt("dish_id");
                        String dish_name = resultSet.getString("dish_name");
                        double dish_price = resultSet.getDouble("dish_price");
                        String dish_image_url = resultSet.getString("dish_image_url");
                        String dish_detail = resultSet.getString("dish_detail");
                        int dish_is_deleted = resultSet.getInt("dish_is_deleted");
                        int dish_category_id = resultSet.getInt("dish_category_id");

                        //construction
//                    Dish dish = new Dish(dish_id,dish_name,dish_price,dish_image_url,dish_detail,dish_is_deleted,dish_category_id);

                        //let the dish to list
//                    dishArrayList.add(dish);

                        JsonObject dish = new JsonObject();
                        dish.addProperty("dish_id",dish_id);
                        dish.addProperty("dish_name",dish_name);
                        dish.addProperty("dish_price",dish_price);
                        dish.addProperty("dish_image_url",dish_image_url);
                        dish.addProperty("dish_detail",dish_detail);
                        dish.addProperty("dish_is_deleted",dish_is_deleted);
                        dish.addProperty("dish_category_id",dish_category_id);
                        dishes.add(dish);
                    }

                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("category",dishCategories.get(i).getCategoryName());
                    jsonObject.add("dishes", dishes);
                    dish_categories.add(jsonObject);

//                JsonObject category = new JsonObject();
//                category.addProperty("category",dishCategories.get(i).getCategoryName());
//                category.addProperty("dishes", String.valueOf(dishes));
//                dish_categories.add(category);
                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
            //TO-DO

            JsonObject dish_list = new JsonObject();
            dish_list.addProperty("status",1);
            dish_list.add("dish_categories",dish_categories);
            printWriter.write(gson.toJson(dish_list));

        }else {

            System.out.println("incorrect!");
            //Token are incorrect
            JsonObject error = new JsonObject();
            error.addProperty("status",0);
            printWriter.write(gson.toJson(error));
        }


    }
}
