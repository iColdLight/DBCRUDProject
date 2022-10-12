package net.coldlight.dbcrudapp.repository;

import net.coldlight.dbcrudapp.model.Developer;
import net.coldlight.dbcrudapp.model.Speciality;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SpecialityRepositoryImpl implements SpecialityRepository{
    private static final Connection connection = DataBaseConnection.getInstance().getConnection();

    @Override
    public List<Speciality> getAll() {
        List<Speciality> specialtyList = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            String sql = "select * from specialities";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                long specialityId = resultSet.getLong("id");
                String specialityName = resultSet.getString("speciality_name");
                Speciality specialty = new Speciality(specialityName);
                specialty.setId(specialityId);
                specialtyList.add(specialty);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return specialtyList;
    }

    @Override
    public Speciality getByID(Long id) {
        Speciality specialty = new Speciality(null);
        try (Statement statement = connection.createStatement()) {
            String sql = "select * from specialities where id = " + (long)id;
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            String specialityName = resultSet.getString("speciality_name");
            specialty.setId(id);
            specialty.setSpecialityName(specialityName);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return specialty;
    }

    @Override
    public Speciality save(Speciality speciality) {
        try (Statement statement = connection.createStatement()) {
            String sql = "insert into specialities (speciality_name) values('" + speciality.getSpecialityName() + "')";
            statement.executeUpdate(sql);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return speciality;
    }

    @Override
    public Speciality update(Speciality speciality) {
        String sql = "update speciality speciality_name=? where id ='" + speciality.getId() + "'";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, speciality.getSpecialityName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return speciality;
    }

    @Override
    public void delete(Speciality speciality) {
        try (Statement statement = connection.createStatement()) {
            String sql = "delete from specialities where speciality_name = '" + speciality.getSpecialityName() + "'";
            statement.executeUpdate(sql);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
