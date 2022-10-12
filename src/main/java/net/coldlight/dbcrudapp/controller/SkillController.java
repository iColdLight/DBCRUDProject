package net.coldlight.dbcrudapp.controller;

import net.coldlight.dbcrudapp.model.Skill;
import net.coldlight.dbcrudapp.service.SkillService;

import java.util.List;

public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    public Skill createSkill (String name) {
        return skillService.createSkill(name);
    }

    public List<Skill> getAllSkills (){
        return skillService.getAllSkills();
    }

    public Skill getSkillByID (Long id){
        return skillService.getSkillByID(id);
    }

    public Skill updateSkill (Long id, String newSkill){
        return skillService.updateSkill(id, newSkill);
    }

    public void deleteSkill (Long id){
        skillService.deleteSkillByID(id);
    }
}

