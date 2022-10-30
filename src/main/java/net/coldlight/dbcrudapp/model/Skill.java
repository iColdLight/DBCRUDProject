package net.coldlight.dbcrudapp.model;

import java.util.Objects;

public class Skill {

    private Long id;
    private String skillName;

    public Skill() {
    }

    public Skill(Long id, String skillName) {
        this.id = id;
        this.skillName = skillName;
    }

    public Skill(String skillName) {
        this.skillName = skillName;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    @Override
    public String toString() {
        return "Skill{" +
                "id=" + id +
                ", name='" + skillName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skill skill = (Skill) o;
        return Objects.equals(id, skill.id) && Objects.equals(skillName, skill.skillName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, skillName);
    }
}
