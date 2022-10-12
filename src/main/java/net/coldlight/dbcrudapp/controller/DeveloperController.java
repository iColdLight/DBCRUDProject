package net.coldlight.dbcrudapp.controller;

import net.coldlight.dbcrudapp.model.Developer;
import net.coldlight.dbcrudapp.repository.DeveloperRepositoryImpl;
import net.coldlight.dbcrudapp.service.DeveloperService;

import java.util.List;

public class DeveloperController {
    private final DeveloperService developerService;

    public DeveloperController(DeveloperService developerService) {
        this.developerService = developerService;
    }

    public Developer createDeveloper (String firstName, String lastName){
        return developerService.createDeveloper(firstName, lastName);
    }

    public List<Developer> getAllDevelopers(){
        return developerService.getAllDevelopers();
    }

    public Developer getDeveloperByID (Long id){
        return developerService.getDeveloperByID(id);
    }

    public Developer updateDeveloper (Long id, String newFirstName, String newLastName){
            return developerService.updateDeveloper(id, newFirstName, newLastName);
    }

    public void deleteDeveloper (Long id){
        developerService.deleteDeveloperByID(id);
    }

}

