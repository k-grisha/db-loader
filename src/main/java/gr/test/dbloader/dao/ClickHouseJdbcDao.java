package gr.test.dbloader.dao;

import gr.test.dbloader.model.ContactEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.*;

public class ClickHouseJdbcDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClickHouseJdbcDao.class);


    private static final String DB_URL = "jdbc:clickhouse://localhost:8123/default";
    private final Connection conn;

    private final String DB = "gri_db";
    private final String TABLE = DB + ".contact_test";

    public ClickHouseJdbcDao() {

        try {
            conn = DriverManager.getConnection(DB_URL);
            LOGGER.info("Creating tables");

            PreparedStatement statement = conn.prepareStatement("CREATE DATABASE IF NOT EXISTS " + DB);
            ResultSet rs = statement.executeQuery();
            System.out.println(rs);
            statement = conn.prepareStatement("CREATE TABLE IF NOT EXISTS " + TABLE + " (\n" +
                    "    phone FixedString(11),\n" +
                    "    eventDate Date,\n" +
                    "    email String,\n" +
                    "    login String,\n" +
                    "    fio String,\n" +
                    "    inn String,\n" +
                    "    uid String,\n" +
                    "    comment String,\n" +
                    "    source UInt8\n" +
                    ")\n" +
//                    "ENGINE = MergeTree ORDER BY phone;");
                    "ENGINE = Memory;");
            rs = statement.executeQuery();
            System.out.println(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }


    }

    public void save(ContactEvent contact) {
        String query = "INSERT INTO " + TABLE +
                " (phone, email, login, fio, inn, uid, source, comment, date)" +
                " VALUES ('asd', 'qwe', 1.1)";

        long time = System.currentTimeMillis();
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            try (ResultSet rs = statement.executeQuery()) {
                System.out.println(rs);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        System.out.println("Time: " + (System.currentTimeMillis() - time) + " ms");
    }

    public void getAll() {

        String query = "SELECT * FROM " + TABLE + "";

        long time = System.currentTimeMillis();
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            try (ResultSet rs = statement.executeQuery()) {
                System.out.println(rs);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        System.out.println("Time: " + (System.currentTimeMillis() - time) + " ms");
    }

}
