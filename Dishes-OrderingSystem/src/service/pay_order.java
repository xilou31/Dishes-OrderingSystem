package service;

import DishUtil.Util;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import constant.Constant;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@WebServlet("/pay_order")
public class pay_order extends HttpServlet {
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

        BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream(), "utf-8"));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        JsonObject post_data = gson.fromJson(sb.toString(), JsonObject.class);
        JsonArray order_dishes = (JsonArray) post_data.get("order_dishes");
        String token = post_data.get("token").toString();
        token = token.substring(1, token.length() - 1);

        if (util.checkTokenInJson(token) == true) {
            //token are correct
            try {
                Class.forName(Constant.JDBC_DRIVER);
                //connect to DataBase
                connection = DriverManager.getConnection(constant.Constant.DB_URL, constant.Constant.USER, constant.Constant.PASSWORD);

                statement = connection.createStatement();
            } catch (ClassNotFoundException e) {
                JsonObject error = new JsonObject();
                error.addProperty("status", 0);
                printWriter.write(gson.toJson(error));
                e.printStackTrace();
                return;
            } catch (SQLException e) {
                JsonObject error = new JsonObject();
                error.addProperty("status", 0);
                printWriter.write(gson.toJson(error));
                e.printStackTrace();
                return;
            }

            String order_dish_total_number = post_data.get("order_dish_total_number").toString();
            String order_total_price = post_data.get("order_total_price").toString();

            int order_id = 0;
            //get current time
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
            simpleDateFormat.applyPattern("yyyy-MM-dd HH:mm:ss");
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
            Date date = new Date();
            String currentTime = simpleDateFormat.format(date);

            String sql;
            sql = String.format("INSERT INTO orders (order_create_time,order_is_finish,order_dish_number,order_total_price,order_is_deleted) VALUES ('%s','1','%s','%s','0')", currentTime, order_dish_total_number, order_total_price);
//            sql = "INSERT INTO orders (order_create_time,order_is_finish,order_dish_number,order_total_price,order_is_deleted) VALUES (current_time,1,order_dish_total_number,order_total_price,0)";
            try {
                int resultSet = statement.executeUpdate(sql);
            } catch (SQLException e) {
                JsonObject error = new JsonObject();
                error.addProperty("status", 0);
                printWriter.write(gson.toJson(error));
                e.printStackTrace();
                return;
            }

            sql = "SELECT max(order_id) FROM orders";

            try {
                ResultSet resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                    order_id = resultSet.getInt("max(order_id)");
                    System.out.println(order_id);
                }
            } catch (SQLException e) {
                JsonObject error = new JsonObject();
                error.addProperty("status", 0);
                printWriter.write(gson.toJson(error));
                e.printStackTrace();
                return;
            }

            for (int i = 0; i < order_dishes.size(); i++) {
                JsonObject dish = (JsonObject) order_dishes.get(i);
                String dish_id = dish.get("dish_id").toString();
                String order_dish_number = dish.get("order_dish_number").toString();
//                int order_dish_total_number = Integer.parseInt(dish.get("order_dish_total_number").toString());
//                double order_total_price = Double.valueOf(dish.get("order_total_price").toString());
                sql = String.format("INSERT INTO order_dish (dish_id,order_id,order_dish_number,order_dish_is_deleted) VALUES (%s,%d,%s,0)", dish_id, order_id, order_dish_number);
//                sql = "INSERT INTO order_dish (dish_id,order_id,order_dish_number,order_dish_is_deleted) VALUES (dish_id,order_id,order_dish_number,0)";

                try {
                    statement.executeUpdate(sql);
                } catch (SQLException e) {
                    JsonObject error = new JsonObject();
                    error.addProperty("status", 0);
                    printWriter.write(gson.toJson(error));
                    e.printStackTrace();
                    return;
                }

            }


            JsonObject success = new JsonObject();
            success.addProperty("status", 1);
            printWriter.write(gson.toJson(success));
            return;
        } else {
            JsonObject error = new JsonObject();
            error.addProperty("status", 0);
            printWriter.write(gson.toJson(error));
            return;
            //token are incorrect
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
