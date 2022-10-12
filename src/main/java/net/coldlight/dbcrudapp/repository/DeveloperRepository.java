package net.coldlight.dbcrudapp.repository;

import net.coldlight.dbcrudapp.model.Developer;
import net.coldlight.dbcrudapp.model.Skill;

public interface DeveloperRepository extends GenericRepository<Developer, Long> {
    void addSkill(Developer developer, Skill skill);
    void deleteSkill(Developer developer, Skill skill);
    boolean haveSkill(long developerId, long skillId);
    void updateSpeciality(Developer developer);
}
