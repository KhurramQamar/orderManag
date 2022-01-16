package com.example.ordermanagement;

import java.util.ArrayList;

public class Menu_Form {
    private ArrayList<Dish_Add> starters_list;
    private ArrayList<Dish_Add> food_list;
    private ArrayList<Dish_Add> drink_list;
    private ArrayList<Dish_Add> others_list;




    public Menu_Form() {
        
        food_list = new ArrayList<Dish_Add>();
        drink_list = new ArrayList<Dish_Add>();
        others_list = new ArrayList<Dish_Add>();

    }



    public void setfood_list(ArrayList<Dish_Add> food_list) {
        this.food_list.addAll(food_list);
    }

    public void setDrink_list(ArrayList<Dish_Add> drink_list) {
        this.drink_list.addAll(drink_list);
    }

    public void setothers_list(ArrayList<Dish_Add> others_list) {
        this.others_list.addAll(others_list);
    }





    public ArrayList<Dish_Add> getfood_list() {
        return food_list;
    }

    public ArrayList<Dish_Add> getDrink_list() {
        return drink_list;
    }

    public ArrayList<Dish_Add> getothers_list() {
        return others_list;
    }
    public boolean exist(Dish_Add dish){
        for (int i = 0; i < this.others_list.size(); i++){
            if (others_list.get(i).check_equal(dish)){
                return true;
            }
        }
        for (int i = 0; i < food_list.size(); i++){
            if(food_list.get(i).check_equal(dish)){
                return true;
            }
        }
        for (int i = 0; i < drink_list.size(); i++){
            if (drink_list.get(i).check_equal(dish)){
                return true;
            }
        }


        return false;

    }

    public boolean add_food_dish(Dish_Add dish){
        return food_list.add(dish);

    }
    public boolean add_drink(Dish_Add drink){
        return drink_list.add(drink);
    }
    public boolean add_other_dish(Dish_Add desert){
        return others_list.add(desert);
    }
    public int get_numof_dishes(){
        int sum =  drink_list.size() + food_list.size() + others_list.size();
        return sum;
    }
    public boolean remove_dish(String name, String type){
        if (type == "foods"){
            for (int i = 0; i < food_list.size(); i++){
                String str = food_list.get(i).getDish_name();
                if (str.equals(name)){
                    Dish_Add temp = food_list.get(i);
                    food_list.remove(temp);
                    return true;
                }
            }
            return false;
        }
        else if (type == "Drink"){
            for (int i = 0; i < drink_list.size(); i++){
                String str = drink_list.get(i).getDish_name();
                if (str.equals(name)){
                    Dish_Add temp = drink_list.get(i);
                    drink_list.remove(temp);
                    return true;
                }
            }
            return false;
        }
        else if (type == "others"){
            for (int i = 0; i < others_list.size(); i++){
                String str = others_list.get(i).getDish_name();
                if (str.equals(name)){
                    Dish_Add temp = others_list.get(i);
                    others_list.remove(temp);
                    return true;
                }
            }
            return false;
        }
        else{
            return false;
        }
    }

    public void clear_foods(){
        food_list.clear();
    }
    public void clear_drink(){
        drink_list.clear();
    }
    public void clea_others(){
        others_list.clear();
    }
    public void replace_dish(String name ,Dish_Add dish){
        if (food_list.contains(dish)){
            for (int i = 0; i < food_list.size(); i++){
                if (food_list.get(i).getDish_name().equals(name)){
                    food_list.set(i, dish);
                }
            }
        }
        else if (drink_list.contains(dish)){
            for (int i = 0; i < drink_list.size(); i++){
                if (drink_list.get(i).getDish_name().equals(name)){
                    drink_list.set(i, dish);
                }
            }
        }
        else if (others_list.contains(dish)){
            for (int i = 0; i < others_list.size(); i++){
                if (others_list.get(i).getDish_name().equals(name)){
                    others_list.set(i, dish);
                }
            }
        }
    }
}
