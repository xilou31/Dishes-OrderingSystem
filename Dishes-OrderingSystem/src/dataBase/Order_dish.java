package dataBase;

public class Order_dish {

    //data field
    private int dish_id;
    private int order_dish_number;

    //Construction
    public Order_dish(int dish_id,int order_dish_number){
        this.dish_id=dish_id;
        this.order_dish_number=order_dish_number;
    }

    //setter and getter
    public int getDish_id() {
        return dish_id;
    }

    public void setDish_id(int dish_id) {
        this.dish_id = dish_id;
    }

    public int getOrder_dish_number() {
        return order_dish_number;
    }

    public void setOrder_dish_number(int order_dish_number) {
        this.order_dish_number = order_dish_number;
    }
}
