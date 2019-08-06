package com.fyp.ModelsClass;

public class DetailsModel {
    String username, address, user_desc, product_id, product_name, product_description, product_price, provider_id, service_id, img;

    public DetailsModel(String username, String address, String user_desc, String product_id, String product_name, String product_description, String product_price, String provider_id, String service_id, String img) {
        this.username = username;
        this.address = address;
        this.user_desc = user_desc;
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_description = product_description;
        this.product_price = product_price;
        this.provider_id = provider_id;
        this.service_id = service_id;
        this.img = img;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUser_desc() {
        return user_desc;
    }

    public void setUser_desc(String user_desc) {
        this.user_desc = user_desc;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(String provider_id) {
        this.provider_id = provider_id;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "DetailsModel{" +
                "username='" + username + '\'' +
                ", address='" + address + '\'' +
                ", user_desc='" + user_desc + '\'' +
                ", product_id='" + product_id + '\'' +
                ", product_name='" + product_name + '\'' +
                ", product_description='" + product_description + '\'' +
                ", product_price='" + product_price + '\'' +
                ", provider_id='" + provider_id + '\'' +
                ", service_id='" + service_id + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}