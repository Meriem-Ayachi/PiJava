package tn.esprit;

import tn.esprit.services.UserService;

public class Main {
    public static void main(String[] args)
    {

       UserService us = new UserService();

      //  User user = new User("test","ROLE_USER","test", (byte) 0, "nour", "qahman", 55221152);
      //  us.add(user);

       // us.update(new User(24,"test2","ROLE_USER","2536",(byte) 1, "qahman","Nour",52660662));
       // us.delete(24);
        System.out.println(us.getAll());

    }
}