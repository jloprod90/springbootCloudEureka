package com.formacionbanca.springbootserviceitem.models;

public class Item {

    private Product product;

    private Integer amount;

    public Item() { }


    public Item(Product product, int amount) {
        this.product = product;
        this.amount = amount;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


    // functions
    public Double getTotal() {
        return product.getPrice() * amount.doubleValue();
    }
}
