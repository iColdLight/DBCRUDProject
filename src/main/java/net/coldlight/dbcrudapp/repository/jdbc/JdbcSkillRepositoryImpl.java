package net.coldlight.dbcrudapp.repository.jdbc;

import net.coldlight.dbcrudapp.model.Skill;
import net.coldlight.dbcrudapp.repository.JdbcUtils;
import net.coldlight.dbcrudapp.repository.SkillRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcSkillRepositoryImpl implements SkillRepository {

    @Override
    public List<Skill> getAll() {
        List<Skill> skillList = new ArrayList<>();
        try (Statement statement = JdbcUtils.getStatement()) {
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
        try (Statement statement = JdbcUtils.getStatement()) {
            String sql = String.format("select * from skills where id = %s", id);
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
        try (Statement statement = JdbcUtils.getStatement()) {
            String sql = "insert into skills (skill_name) values('" + skill.getSkillName() + "')";
            //String sql = String.format("insert into skills (skill_name) values %s", skill.getSkillName());
            statement.executeUpdate(sql);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return skill;
    }

    @Override
    public Skill update(Skill skill) {
        String sql = String.format("update skills set skill_name=? where id = %s", skill.getId());
        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(sql)) {
            preparedStatement.setString(1, skill.getSkillName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return skill;
    }

    @Override
    public void delete(Skill skill) {
        try (Statement statement = JdbcUtils.getStatement()) {
            String sql = String.format("delete from skills where id = %s", skill.getId());
            statement.executeUpdate(sql);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
