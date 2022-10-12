package net.coldlight.dbcrudapp.service;

import net.coldlight.dbcrudapp.model.Developer;
import net.coldlight.dbcrudapp.repository.DeveloperRepository;
import net.coldlight.dbcrudapp.repository.DeveloperRepositoryImpl;

import java.util.List;

public class DeveloperService {
    private final DeveloperRepository developerRepository;

    public DeveloperService(DeveloperRepository developerRepository) {
        this.developerRepository = developerRepository;
    }

    public Developer createDeveloper (String firstName, String lastName){
        Developer developer = new Developer();
        developer.setFirstName(firstName);
        developer.setLastName(lastName);
        return developerRepository.save(developer);
    }

    public List<Developer> getAllDevelopers(){
        return developerRepository.getAll();
    }

    public Developer getDeveloperByID (Long id){
        return developerRepository.getByID(id);
    }

    public Developer updateDeveloper (Long id, String newFirstName, String newLastName){
        List<Developer> allDevelopers = getAllDevelopers();
        Developer developer = allDevelopers.stream().filter(s -> s.getId().equals(id)).findFirst().orElse(null);
        if (developer != null) {
            developer.setFirstName(newFirstName);
            developer.setLastName(newLastName);
            return developerRepository.update(developer);
        }
        throw new RuntimeException("Developer with ID = " + id + "not found");

    }

    public void deleteDeveloperByID (Long id){
        Developer developer = developerRepository.getByID(id);
        if (developer == null){
            throw new RuntimeException("Developer with ID = " + id + "not found");
        }
        developerRepository.delete(developer);
    }
}
