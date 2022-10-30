package net.coldlight.dbcrudapp.controller;

import net.coldlight.dbcrudapp.model.Speciality;
import net.coldlight.dbcrudapp.repository.jdbc.JdbcSpecialityRepositoryImpl;
import net.coldlight.dbcrudapp.service.SpecialityService;

import java.util.List;

public class SpecialityController {
    private final SpecialityService specialityService = new SpecialityService(new JdbcSpecialityRepositoryImpl());



    public Speciality createSpeciality (String name) {
        return specialityService.createSpeciality(name);
    }
    public List<Speciality> getAllSpecialities (){
        return specialityService.getAllSpecialities();
    }

    public Speciality getSpecialityByID (Long id){
        return specialityService.getSpecialityByID(id);
    }

    public Speciality updateSpeciality (Long id, String newSpeciality){
       return specialityService.updateSpeciality(id, newSpeciality);
    }

    public void deleteSpeciality (Long id){
        specialityService.deleteSpeciality(id);
    }


}

