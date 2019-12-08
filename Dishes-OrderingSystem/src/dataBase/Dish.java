package dataBase;

public class Dish {

    //Data Field
    private int dishId;
    private String dishName;
    private double dishPrice;
    private String dishImageUrl;
    private String dishDetail;
    private int dishIsDeleted;
    private int dishCategoryId;

    //Construction
    public Dish(int dishId,String dishName,double dishPrice,String dishImageUrl,String dishDetail,int dishIsDeleted,int dishCategoryId){
        this.dishCategoryId = dishCategoryId;
        this.dishDetail = dishDetail;
        this.dishId = dishId;
        this.dishName = dishName;
        this.dishPrice = dishPrice;
        this.dishImageUrl = dishImageUrl;
        this.dishIsDeleted = dishIsDeleted;
    }

    //Setter and Getter
    public int getDishId() {
        return dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public double getDishPrice() {
        return dishPrice;
    }

    public void setDishPrice(double dishPrice) {
        this.dishPrice = dishPrice;
    }

    public String getDishImageUrl() {
        return dishImageUrl;
    }

    public void setDishImageUrl(String dishImageUrl) {
        this.dishImageUrl = dishImageUrl;
    }

    public String getDishDetail() {
        return dishDetail;
    }

    public void setDishDetail(String dishDetail) {
        this.dishDetail = dishDetail;
    }

    public int getDishIsDeleted() {
        return dishIsDeleted;
    }

    public void setDishIsDeleted(int dishIsDeleted) {
        this.dishIsDeleted = dishIsDeleted;
    }

    public int getDishCategoryId() {
        return dishCategoryId;
    }

    public void setDishCategoryId(int dishCategoryId) {
        this.dishCategoryId = dishCategoryId;
    }
}
