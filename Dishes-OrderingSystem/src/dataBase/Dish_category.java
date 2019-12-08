package dataBase;

public class Dish_category {

    //Data Field
    private int categoryId;
    private String categoryName;
    private int categoryIsDeleted;

    //Construction
    public Dish_category(int categoryId,String categoryName,int categoryIsDeleted){
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryIsDeleted = categoryIsDeleted;
    }
    public Dish_category(String categoryName,int categoryId){
        this.categoryName = categoryName;
        this.categoryId = categoryId;
    }

    //Setter and Getter
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryIsDeleted() {
        return categoryIsDeleted;
    }

    public void setCategoryIsDeleted(int categoryIsDeleted) {
        this.categoryIsDeleted = categoryIsDeleted;
    }
}
