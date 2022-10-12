package net.coldlight.dbcrudapp.repository;


import net.coldlight.dbcrudapp.model.Developer;
import net.coldlight.dbcrudapp.model.Skill;
import net.coldlight.dbcrudapp.model.Speciality;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeveloperRepositoryImpl implements DeveloperRepository {
    private static final Connection connection = DataBaseConnection.getInstance().getConnection();
    private final SkillRepository skillRepository = new SkillRepositoryImpl();
    private final SpecialityRepository specialityRepository = new SpecialityRepositoryImpl();

    @Override
    public List<Developer> getAll() {
        List<Developer> developerList = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            String sql = "select * from developers";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                long developerId = resultSet.getLong("id");
                String developerFirstName = resultSet.getString("first_name");
                String developerLastName = resultSet.getString("last_name");
                long specialityId = resultSet.getLong("speciality_id");
                Developer developer = new Developer(developerId, developerFirstName, developerLastName);
                if (specialityId != 0) {
                    Speciality speciality = specialityRepository.getByID(specialityId);
                    developer.setSpeciality(speciality);
                }
                List<Skill> skillList = getSkillListByDeveloperId(developerId);
                if (!skillList.isEmpty()) {
                    developer.setSkills(skillList);
                }
                developerList.add(developer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return developerList;
    }

    private List<Skill> getSkillListByDeveloperId(long id) {
        List<Skill> skillList = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            String sql = "select skill_id from developers_skills where developer_id = " + id;
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Skill skill = skillRepository.getByID(resultSet.getLong("skill_id"));
                skillList.add(skill);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return skillList;

    }

    @Override
    public Developer getByID(Long id) {
        List<Developer> developerList = getAll();
        for (Developer developer : developerList) {
            if (developer.getId() == (long) id) {
                return developer;
            }
        }
        return null;
    }

    @Override
    public Developer save(Developer developer) {
        String sql = "insert into developers (first_name, last_name) values (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
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
        String sql = "update developers set first_name=?, last_name=? where id ='" + developer.getId() + "'";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
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
        try (Statement statement = connection.createStatement()) {
            String sql = "delete from developers where id = '" + developer.getId() + "'";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addSkill(Developer developer, Skill skill) {
        String sql = "insert into developers_skills values (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
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
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
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
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
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
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, developer.getSpeciality().getId());
            preparedStatement.setLong(2, developer.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}