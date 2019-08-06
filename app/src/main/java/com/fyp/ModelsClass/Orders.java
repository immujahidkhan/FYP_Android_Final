
package com.fyp.ModelsClass;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Orders {

    @SerializedName("OrdersList")
    @Expose
    private ArrayList<OrdersList> ordersList = null;

    public ArrayList<OrdersList> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(ArrayList<OrdersList> ordersList) {
        this.ordersList = ordersList;
    }

}
