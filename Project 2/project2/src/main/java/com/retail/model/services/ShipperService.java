package com.retail.model.services;

import com.retail.model.dao.ShipperDAO;
import com.retail.model.entities.Shipper;

import java.sql.SQLException;

public class ShipperService {
    private final ShipperDAO shipperDAO;

    public ShipperService(ShipperDAO shipperDAO) {
        this.shipperDAO = shipperDAO;
    }

    public int getRandomShipperId() throws SQLException {
        return shipperDAO.fetchRandomShipperId();
    }

    public Shipper getShipperById(int shipperId) throws SQLException {
        return shipperDAO.getById(shipperId);
    }
}
