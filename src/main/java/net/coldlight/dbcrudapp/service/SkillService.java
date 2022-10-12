package net.coldlight.dbcrudapp.service;


import net.coldlight.dbcrudapp.model.Developer;
import net.coldlight.dbcrudapp.model.Skill;
import net.coldlight.dbcrudapp.repository.SkillRepository;
import net.coldlight.dbcrudapp.repository.SkillRepositoryImpl;

import java.util.List;

public class SkillService {

    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public Skill createSkill (String name) {
        Skill skill = new Skill();
        skill.setSkillName(name);
        return skillRepository.save(skill);
    }

    public List<Skill> getAllSkills (){
        return skillRepository.getAll();
    }

    public Skill getSkillByID (Long id){
        return skillRepository.getByID(id);
    }

    public Skill updateSkill (Long id, String newSkill){
        List<Skill> allSkills = getAllSkills();
        Skill skill = allSkills.stream().filter(s -> s.getId().equals(id)).findFirst().orElse(null);
        if (skill != null) {
            skill.setSkillName(newSkill);
            return skillRepository.update(skill);
        }
        throw new RuntimeException("Skill with ID = " + id + "not found");
    }

    public void deleteSkillByID (Long id){
        Skill skill = skillRepository.getByID(id);
        if (skill == null){
            throw new RuntimeException("Developer with ID = " + id + "not found");
        }
        skillRepository.delete(skill);
    }
}
