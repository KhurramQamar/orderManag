package com.example.ordermanagement;

import java.util.ArrayList;

public class OrderForm {


        private String order_num;
        private String rest_id;
        private String client_id;
        private String status;
        private double total_price = 0;

        private ArrayList<Dish_Add> dishs_orderd = new ArrayList<Dish_Add>();

        public OrderForm(String order_num, String rest_id, String client_id, String status) {
            this.client_id = client_id;
            this.order_num = order_num;
            this.rest_id = rest_id;
            this.status = status;

        }

        public OrderForm(OrderForm ord) {
            this.client_id = ord.client_id;
            this.order_num = ord.order_num;
            this.rest_id = ord.rest_id;
            this.status = ord.status;

        }

        public void addDish(Dish_Add dish) {
            this.dishs_orderd.add(dish);
            total_price += dish.getPrice();
        }


        @Override
        public String toString() {
            String strToRet = "Status: " + status.toUpperCase() + ", " + "\n" +
                    "Order Number: " + order_num.replaceAll("[^0-9]", "") + ", " + "\n" ;

            strToRet += "Dishes: ";
            for (Dish_Add dish : dishs_orderd) {
                strToRet += dish.getDish_name();
            }
            strToRet += "\n";
            strToRet += "Total Price: " + total_price + "\n";
            return strToRet;
        }

        public void removeDish(Dish_Add dish) {
            this.dishs_orderd.remove(dish);
            total_price -= dish.getPrice();
        }

        public String getClient_id() {
            return client_id;
        }

        public String getOrder_num() {
            return order_num;
        }

        public String getRest_id() {
            return rest_id;
        }

        public String getStatus() {
            return status;
        }

        public ArrayList<Dish_Add> getDishs_orderd() {
            return dishs_orderd;
        }

        public double getTotal_price() {
            return total_price;
        }


        public void setStatus(String status) {
            this.status = status;
        }
    }


