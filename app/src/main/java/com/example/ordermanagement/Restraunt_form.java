package com.example.ordermanagement;

public class Restraunt_form {
    
        private String name;
        private String location;
        private String phone;
        private String type;
        private String description;
        private String UID;




        public Restraunt_form(String Name, String Location, String Phone, String type, String desc,String UID ) {
            this.name = Name;
            this.location = Location;
            this.phone = Phone;
            this.type = type;
            this.description = desc;
            this.UID = UID;

        }

        public Restraunt_form() {
            this.name = "";
            this.location = "";
            this.phone = "";
            this.type = "";
            this.description = "";
        }

        public Restraunt_form(Restraunt_form toCopy) {
            this.name = toCopy.getName();
            this.location = toCopy.getLocation();
            this.phone = toCopy.getPhone();
            this.type = toCopy.getType();
            this.description = toCopy.getDescription();
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUID() {
            return UID;
        }

        public void setUID(String UID) {
            this.UID = UID;
        }
    }


