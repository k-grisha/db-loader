package gr.test.dbloader.dao;

import gr.test.dbloader.model.ContactEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ClickHouseJdbcDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClickHouseJdbcDao.class);


    private static final String DB_URL = "jdbc:clickhouse://localhost:8123/default";
    private final Connection conn = null;

    private final String DB = "gri_db";
    private final String TABLE = DB + ".contact_test";

    public ClickHouseJdbcDao() {
/*
        try {
            conn = DriverManager.getConnection(DB_URL);
            LOGGER.info("Creating tables");

            PreparedStatement statement = conn.prepareStatement("CREATE DATABASE IF NOT EXISTS " + DB);
            ResultSet rs = statement.executeQuery();
            System.out.println(rs);
            statement = conn.prepareStatement("CREATE TABLE IF NOT EXISTS " + TABLE + " (\n" +
                    "    phone FixedString(11),\n" +
//                    "    email String,\n" +
//                    "    login String,\n" +
//                    "    fio String,\n" +
//                    "    inn String,\n" +
//                    "    uid String,\n" +
//                    "    comment String,\n" +
//                    "    source UInt8,\n" +
//                    "    eventDate DateTime,\n" +
                    "    sortDate Date\n" +
                    ")\n" +
//                    "ENGINE = MergeTree ORDER BY sortDate;");
                    "ENGINE = MergeTree(sortDate, (phone), 8192 );");
            rs = statement.executeQuery();
            System.out.println(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

*/
    }

    DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");

    public void save(ContactEvent contact) {
//        String query = "INSERT INTO " + TABLE +
//                " (phone, " +
////                "email, login, fio, inn, uid, source, comment, eventDate, " +
//                "sortDate)" +
//                " VALUES ('"
//                + contact.getPhone() + "', '"
////                + contact.getEmail() + "', '"
////                + contact.getLogin() + "', '"
////                + contact.getFio() + "', '"
////                + contact.getInn() + "', '"
////                + contact.getUid() + "', "
////                + contact.getSource() + ", '"
////                + contact.getComment() + "', '"
////                + contact.getDateTime().format(formater) + "', '"
//                + contact.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "' )";
        String query = "INSERT INTO " + TABLE +
                " (phone,  sortDate)" +
                " VALUES (?,? )";

//        long time = System.currentTimeMillis();
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            for (int z = 0; z<10; z++){
                statement.setString(1, "12345678901");
//                statement.setString(2, contact.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                statement.addBatch();
            }

            statement.executeBatch();
            conn.commit();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
//        System.out.println("Time: " + (System.currentTimeMillis() - time) + " ms");
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
