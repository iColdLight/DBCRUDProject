package net.coldlight.dbcrudapp.view;


import net.coldlight.dbcrudapp.controller.DeveloperController;
import net.coldlight.dbcrudapp.model.Developer;

import java.util.Scanner;

public class DeveloperView {
    private final DeveloperController developerController;
    private final Scanner scanner = new Scanner(System.in);

    public DeveloperView(DeveloperController developerController) {
        this.developerController = developerController;
    }

    public void createDeveloper() {
        System.out.println("Enter developer First name: ");
        String firstName = scanner.nextLine();
        System.out.println("Enter developer Last name: ");
        String lastName = scanner.nextLine();
        Developer developer = developerController.createDeveloper(firstName, lastName);
        System.out.println("Created developer: " + developer);
    }

    public void getAllDevelopers (){
        developerController.getAllDevelopers();
    }

    public void getDeveloperByID (){
        System.out.println("Enter developer ID: ");
        Long id = scanner.nextLong();
        developerController.getDeveloperByID(id);
    }

    public void updateDeveloper(){
        System.out.println("Enter developer id you want to update: ");
        Long id = scanner.nextLong();
        System.out.println("Enter new First name: ");
        String newFirstName = scanner.nextLine();
        System.out.println("Enter new Last name: ");
        String newLastName = scanner.nextLine();
        developerController.updateDeveloper(id, newFirstName, newLastName);
    }

    public void deleteDeveloper(){
        System.out.println("Enter developer ID you want to delete: ");
        Long developerID = scanner.nextLong();
        developerController.deleteDeveloper(developerID);
    }

}
