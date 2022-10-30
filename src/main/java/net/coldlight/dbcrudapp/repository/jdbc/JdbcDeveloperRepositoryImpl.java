package net.coldlight.dbcrudapp.repository.jdbc;


import net.coldlight.dbcrudapp.model.Developer;
import net.coldlight.dbcrudapp.model.Skill;
import net.coldlight.dbcrudapp.model.Speciality;
import net.coldlight.dbcrudapp.repository.JdbcUtils;
import net.coldlight.dbcrudapp.repository.DeveloperRepository;
import net.coldlight.dbcrudapp.repository.SkillRepository;
import net.coldlight.dbcrudapp.repository.SpecialityRepository;

import java.sql.*;
import java.util.*;

public class JdbcDeveloperRepositoryImpl implements DeveloperRepository {
    private final SkillRepository skillRepository = new JdbcSkillRepositoryImpl();
    private final SpecialityRepository specialityRepository = new JdbcSpecialityRepositoryImpl();

    @Override
    public List<Developer> getAll() {
        Map<Long, Developer> devMap = new HashMap<>();
        try (Statement statement = JdbcUtils.getStatement()) {
            String sql = "select d.id as dev_id, " +
                    "d.first_name, " +
                    "d.last_name, " +
                    "sp.id as sp_id, " +
                    "sp.speciality_name as sp_name, " +
                    "sk.id as sk_id, " +
                    "sk.skill_name as sk_name from developers d " +
                    "left join developers_skills dsk on d.id = dsk.developer_id " +
                    "left join skills sk on dsk.skill_id = sk.id " +
                    "left join specialities sp on d.speciality_id = sp.id";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                long developerId = resultSet.getLong("dev_id");
                Developer existedDeveloper = devMap.get(developerId);
                if(existedDeveloper == null){
                    String developerFirstName = resultSet.getString("first_name");
                    String developerLastName = resultSet.getString("last_name");
                    long specialityId = resultSet.getLong("sp_id");
                    String specialityName = resultSet.getString("sp_name");
                    long skillId = resultSet.getLong("sk_id");
                    String skillName = resultSet.getString("sk_name");
                    Developer developer = new Developer(developerId, developerFirstName, developerLastName,
                            new Speciality(specialityId, specialityName), List.of(new Skill(skillId, skillName)));
                    devMap.put(developerId, developer);
                } else{
                    List<Skill> skills = existedDeveloper.getSkills();
                    long skillId = resultSet.getLong("sk_id");
                    String skillName = resultSet.getString("sk_name");
                    skills.add(new Skill(skillId, skillName));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<Developer>(devMap.values());
    }

    private List<Skill> getSkillListByDeveloperId(long id) {
        List<Skill> skillList = new ArrayList<>();
        try (Statement statement = JdbcUtils.getStatement()) {
            String sql = String.format("select skill_id from developers_skills where developer_id = %s", id);
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Skill skill = skillRepository.getByID(resultSet.getLong("skill_id"));
                skillList.add(skill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return skillList;

    }

    @Override
    public Developer getByID(Long id) {

        try (Statement statement = JdbcUtils.getStatement()) {
            String sql = String.format("select * from developers where id = %s", id);
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            if(firstName == null) {
                throw new RuntimeException("Developer with id = " + id + " not found");
            }
            return new Developer(id, firstName, lastName);
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error from database");
        }

    }

    @Override
    public Developer save(Developer developer) {
        String sql = "insert into developers (first_name, last_name) values (?, ?)";
        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(sql)) {
            preparedStatement.setString(1, developer.getFirstName());
            preparedStatement.setString(2, developer.getLastName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return developer;
    }

    @Override
    public Developer update(Developer developer) {
        String sql = String.format("update developers set first_name=?, last_name=? where id = %s", developer.getId());
        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(sql)) {
            preparedStatement.setString(1, developer.getFirstName());
            preparedStatement.setString(2, developer.getLastName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return developer;
    }

    @Override
    public void delete(Developer developer) {
        try (Statement statement = JdbcUtils.getStatement()) {
            String sql = String.format("delete from developers where id = %s", developer.getId());
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addSkill(Developer developer, Skill skill) {
        String sql = "insert into developers_skills values (?, ?)";
        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(sql)) {
            preparedStatement.setLong(1, developer.getId());
            preparedStatement.setLong(2, skill.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteSkill(Developer developer, Skill skill) {
        String sql = "delete from developers_skills where developer_id = ? and skill_id = ?";
        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(sql)) {
            preparedStatement.setLong(1, developer.getId());
            preparedStatement.setLong(2, skill.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean haveSkill(long developerId, long skillId) {
        String sql = "select skill_id from developers_skills where developer_id = ?";
        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(sql)) {
            preparedStatement.setLong(1, developerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getLong("skill_id") == skillId) return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void updateSpeciality(Developer developer) {
        String sql = "update developers specialties. Set speciality_id = ? where developer_id = ?";
        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(sql)) {
            preparedStatement.setLong(1, developer.getSpeciality().getId());
            preparedStatement.setLong(2, developer.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}