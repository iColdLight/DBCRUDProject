package net.coldlight.dbcrudapp.repository.jdbc;

import net.coldlight.dbcrudapp.model.Speciality;
import net.coldlight.dbcrudapp.repository.JdbcUtils;
import net.coldlight.dbcrudapp.repository.SpecialityRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcSpecialityRepositoryImpl implements SpecialityRepository {

    @Override
    public List<Speciality> getAll() {
        List<Speciality> specialtyList = new ArrayList<>();
        try (Statement statement = JdbcUtils.getStatement()) {
            String sql = "select * from specialities";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                long specialityId = resultSet.getLong("id");
                String specialityName = resultSet.getString("speciality_name");
                Speciality specialty = new Speciality(specialityName);
                specialty.setId(specialityId);
                specialtyList.add(specialty);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return specialtyList;
    }

    @Override
    public Speciality getByID(Long id) {
        Speciality specialty = new Speciality(null);
        try (Statement statement = JdbcUtils.getStatement()) {
            String sql = String.format("select * from specialities where id = %s", id);
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            String specialityName = resultSet.getString("speciality_name");
            specialty.setId(id);
            specialty.setSpecialityName(specialityName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return specialty;
    }

    @Override
    public Speciality save(Speciality speciality) {
        try (Statement statement = JdbcUtils.getStatement()) {
            String sql = "insert into specialities (speciality_name) values('" + speciality.getSpecialityName() + "')";
            //String sql = String.format("insert into specialities (speciality_name) values %s", speciality.getSpecialityName());
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return speciality;
    }

    @Override
    public Speciality update(Speciality speciality) {
        String sql = String.format("update specialities set speciality_name=? where id = %s", speciality.getId());
        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(sql)) {
            preparedStatement.setString(1, speciality.getSpecialityName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return speciality;
    }

    @Override
    public void delete(Speciality speciality) {
        try (Statement statement = JdbcUtils.getStatement()) {
            String sql = String.format("delete from specialities where id = %s", speciality.getId());
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
