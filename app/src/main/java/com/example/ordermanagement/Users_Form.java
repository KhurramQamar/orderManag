package com.example.ordermanagement;

public class Users_Form {

        private String FirstName;
        private String LastName;
        private String Email;
        private String Password;
        private String owner;


        public Users_Form(String FirstName, String Email, String LastName, String Password, String owner) {
            this.Email = Email;
            this.FirstName = FirstName;
            this.LastName = LastName;
            this.Password = Password;
            this.owner = owner;


        }

        public Users_Form() {

            this.owner = "";
            this.Email = "";
            this.FirstName = "";
            this.LastName = "";
            this.Password = "";
        }

       
        public void setOwner(String owner) {
            this.owner = owner;
        }


}


