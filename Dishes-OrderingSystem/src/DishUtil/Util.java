package DishUtil;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import constant.Constant;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Util {
    public boolean checkToken(HttpServletRequest req) throws IOException, ServletException {
//        String token = new String();

//        if(req.getContentType().equals("multipart/form-data")){
//            Part part = req.getPart("token");
//        //输入流
//            InputStream inputStream = part.getInputStream();
//        //读取缓冲区
//            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
//        //读取token
//            token = br.readLine();
//        }else if(req.getContentType().equals("application/x-www-form-urlencoded")){
//            token = req.getParameter("token");
//        }

        String token = req.getParameter("token");

        if(token.equals(Constant.token)){
            return true;
        }else{
            return false;
        }
    }

    public String[] stringToArrays(String s){
        s = s.substring(1,s.length()-1);
        String[] dish_id = s.split(",");
        return dish_id;
    }

    public boolean checkTokenInJson(String  token){
        if(token.equals(Constant.token)){
            return true;
        }else {
            return false;
        }
    }

}
