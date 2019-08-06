package com.fyp.ModelsClass;

public class ProductList {
    String product_id, provider_id, name, desc, image, price;

    public ProductList(String product_id, String provider_id, String name, String desc, String image, String price) {
        this.product_id = product_id;
        this.provider_id = provider_id;
        this.name = name;
        this.desc = desc;
        this.image = image;
        this.price = price;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(String provider_id) {
        this.provider_id = provider_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ProductList{" +
                "product_id='" + product_id + '\'' +
                ", provider_id='" + provider_id + '\'' +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", image='" + image + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}