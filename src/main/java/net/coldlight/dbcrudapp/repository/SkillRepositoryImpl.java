package net.coldlight.dbcrudapp.repository;

import net.coldlight.dbcrudapp.model.Developer;
import net.coldlight.dbcrudapp.model.Skill;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SkillRepositoryImpl implements SkillRepository{
    private static final Connection connection = DataBaseConnection.getInstance().getConnection();

    @Override
    public List<Skill> getAll() {
        List<Skill> skillList = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            String sql = "select * from skills";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                long skillId = resultSet.getLong("id");
                String skillName = resultSet.getString("skill_name");
                Skill skill = new Skill(skillName);
                skill.setId(skillId);
                skillList.add(skill);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return skillList;
    }

    @Override
    public Skill getByID(Long id) {
        Skill skill = new Skill(null);
        try (Statement statement = connection.createStatement()) {
            String sql = "select * from skills where id = " + (long)id;
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            skill.setId(id);
            String skillName = resultSet.getString("skill_name");
            skill.setSkillName(skillName);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return skill;
    }

    @Override
    public Skill save(Skill skill) {
        try (Statement statement = connection.createStatement()) {
            String sql = "insert into skills (skill_name) values('" + skill.getSkillName() + "')";
            statement.executeUpdate(sql);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return skill;
    }

    @Override
    public Skill update(Skill skill) {
        String sql = "update skill set skill_name=? where id ='" + skill.getId() + "'";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, skill.getSkillName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return skill;
    }

    @Override
    public void delete(Skill skill) {
        try (Statement statement = connection.createStatement()) {
            String sql = "delete from skills where skill_name = '" + skill.getSkillName() + "'";
            statement.executeUpdate(sql);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
