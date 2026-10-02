package org.iesalixar.daw2.carlosmartel.daos;

import org.iesalixar.daw2.carlosmartel.entities.Region;

import java.sql.SQLException;
import java.util.List;

public interface RegionDAO {
    List<Region> listAllRegions() throws SQLException;
    void insertRegion(Region region) throws  SQLException;
    void updateRegion(Region region) throws SQLException;
    Region getRegionById(int id) throws SQLException;
    boolean existRegionByCode(String code) throws SQLException;
    boolean existsRegionByCodeAndNotId(String code, int id) throws SQLException;
}
