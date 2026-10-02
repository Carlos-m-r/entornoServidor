package org.iesalixar.daw2.carlosmartel.daos;


import org.iesalixar.daw2.carlosmartel.entities.Region;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class RegionDAOImpl implements RegionDAO {
    @Override
    public List<Region> listAllRegions() throws SQLException {
        List<Region> regions = new ArrayList<>();
        String query = "SELECT * FROM regions";

        try (Connection connection = DatabaseConnectionManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String code = rs.getString("code");
                String name = rs.getString("name");
                regions.add(new Region(id, code, name));
            }

        }

        return regions;
    }

    /**
     *
     * @param region
     * @throws SQLException
     */
    @Override
    public void insertRegion(Region region) throws SQLException {
        String query = "INSERT INTO regions (code, name) VALUES (?, ?)";

        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)){

            preparedStatement.setString(1, region.getCode());
            preparedStatement.setString(2, region.getName());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void updateRegion(Region region) throws SQLException {
        String query = "UPDATE regions SET code = ?, name = ? WHERE id = ?";
        try (Connection connection = DatabaseConnectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, region.getCode());
            preparedStatement.setString(2, region.getName());
            preparedStatement.setInt(3, region.getId());
            preparedStatement.executeUpdate();

        }
    }

    @Override
    public Region getRegionById(int id) throws SQLException {
        String query = "SELECT * FROM regions WHERE id = ?";
        Region region = null;

        try (Connection connection = DatabaseConnectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query)){

            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()) {
                String code = rs.getString("code");
                String name = rs.getString("name");
                region = new Region(id, code, name);
            }

        }
        return region;
    }

    /**
     *
     * @param code
     * @return
     * @throws SQLException
     */
    @Override
    public boolean existRegionByCode(String code) throws SQLException {
        String sql = "SELECT COUNT(*) FROM regions WHERE UPPER(code) = ?";
        try (Connection connection = DatabaseConnectionManager.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, code.toUpperCase());
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                resultSet.next();
                return resultSet.getInt(1) > 0;
            }
        }
    }

    @Override
    public boolean existsRegionByCodeAndNotId(String code, int id) throws SQLException {
        String sql = "SELECT COUNT(*) FROM regions WHERE UPPER(code) = ? AND id != ?";
        try (Connection connection = DatabaseConnectionManager.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, code.toUpperCase());
            preparedStatement.setInt(2, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                return resultSet.getInt(1) > 0;
            }
        }
    }
}
