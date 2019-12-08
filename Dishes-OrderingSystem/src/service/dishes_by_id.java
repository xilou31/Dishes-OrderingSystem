package service;

import DishUtil.Util;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import constant.Constant;
import dataBase.Dish;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/dishes_by_id")
public class dishes_by_id extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        Connection connection = null;
        Statement statement = null;
        Util util = new Util();

        //set respond type
        resp.setContentType("application/json");
        resp.setCharacterEncoding("utf-8");

        //Init return message
        PrintWriter printWriter = resp.getWriter();

        if (util.checkToken(req) == true) {
            //token are correct

            String[] dish_ids = util.stringToArrays(req.getParameter("dish_id"));

            JsonArray dish_categories = new JsonArray();

            try {
                Class.forName(Constant.JDBC_DRIVER);

                connection = DriverManager.getConnection(Constant.DB_URL, Constant.USER, Constant.PASSWORD);

                statement = connection.createStatement();

                for (int i = 0; i < dish_ids.length; i++) {
                    String sql;
                    sql = "SELECT dish_id,dish_name,dish_price,dish_image_url,dish_detail,dish_is_deleted,dish_category_id FROM dish WHERE dish_id ="+dish_ids[i];
                    ResultSet resultSet = statement.executeQuery(sql);

                    while (resultSet.next()){
                        int dish_id = resultSet.getInt("dish_id");
                        String dish_name = resultSet.getString("dish_name");
                        double dish_price = resultSet.getDouble("dish_price");
                        String dish_image_url = resultSet.getString("dish_image_url");
                        String dish_detail = resultSet.getString("dish_detail");
                        int dish_is_deleted = resultSet.getInt("dish_is_deleted");
                        int dish_category_id = resultSet.getInt("dish_category_id");

                        JsonObject dish = new JsonObject();
                        dish.addProperty("dish_id",dish_id);
                        dish.addProperty("dish_name",dish_name);
                        dish.addProperty("dish_price",dish_price);
                        dish.addProperty("dish_image_url",dish_image_url);
                        dish.addProperty("dish_detail",dish_detail);
                        dish.addProperty("dish_is_deleted",dish_is_deleted);
                        dish.addProperty("dish_category_id",dish_category_id);

                        dish_categories.add(dish);
                    }
                }

            } catch (ClassNotFoundException | SQLException e) {
                JsonObject error = new JsonObject();
                error.addProperty("status",0);
                printWriter.write(gson.toJson(error));
                e.printStackTrace();
            }

            JsonObject returnKey = new JsonObject();
            returnKey.addProperty("status",1);
            returnKey.add("dish_categories",dish_categories);
            printWriter.write(gson.toJson(returnKey));
        }
        else {
            //token are incorrect
            JsonObject error = new JsonObject();
            error.addProperty("status",0);
            printWriter.write(gson.toJson(error));
        }
    }
}
