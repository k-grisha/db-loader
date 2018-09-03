package gr.test.dbloader.dao;

import gr.test.dbloader.model.ContactEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;

@Component
public class ClickHouseDao {

    private NamedParameterJdbcTemplate jdbcTemplate;

    private final String DB = "gri_db";
    private final String TABLE_name = "contactTest";
    private final String TABLE = DB + "." + TABLE_name;
    private final String TABLE_BUFFER = DB + ".contactTest_buff";

    private String createTable = "CREATE TABLE IF NOT EXISTS " + TABLE + " (\n" +
            "    phone FixedString(11),\n" +
            "    email String,\n" +
            "    login String,\n" +
            "    fio String,\n" +
            "    inn String,\n" +
            "    uid String,\n" +
            "    comment String,\n" +
            "    source UInt8,\n" +
            "    eventDate DateTime\n" +
            ")\n" +
            "ENGINE = MergeTree ORDER BY (phone, eventDate);";

    private String createBuffer = "CREATE TABLE IF NOT EXISTS " + TABLE_BUFFER + " AS " + TABLE + " ENGINE = Buffer(" + DB + ", " + TABLE_name + ", " +
            "1, " +    // num_layers
            "10, " +    // min_time
            "100, " +   // max_time
            "10000, " + // min_rows
            "100000, " +   // max_rows
            "10000000, " +  // min_bytes
            "100000000)";   // max_bytes

    @Autowired
    public ClickHouseDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        jdbcTemplate.execute(createTable, PreparedStatement::execute);
        jdbcTemplate.execute(createBuffer, PreparedStatement::execute);
    }

    public void save(List<ContactEvent> contacts) {

//        List<MapSqlParameterSource> params = new ArrayList<>(contacts.size());
//        for (ContactEvent contactEvent : contacts) {
//            MapSqlParameterSource namedParameters = new MapSqlParameterSource();
//            namedParameters.addValue("phone", contactEvent.getPhone());
//            namedParameters.addValue("email", contactEvent.getEmail());
//            namedParameters.addValue("login", contactEvent.getLogin());
//            namedParameters.addValue("fio", contactEvent.getFio());
//            namedParameters.addValue("inn", contactEvent.getInn());
//            namedParameters.addValue("uid", contactEvent.getUid());
//            namedParameters.addValue("comment", contactEvent.getComment());
//            namedParameters.addValue("source", contactEvent.getSource());
//            namedParameters.addValue("eventDate", new java.sql.Timestamp(System.currentTimeMillis()));
//            params.add(namedParameters);
//        }


        String saveSql = "INSERT INTO " + TABLE_BUFFER +
                " (phone,  email, login, fio, inn, uid, comment, source, eventDate)" +
                " VALUES (:phone,  :email, :login, :fio, :inn, :uid, :comment, :source, :eventDate)";

        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(contacts.toArray());

        jdbcTemplate.batchUpdate(saveSql, batch);

//        jdbcTemplate.update(saveSql, namedParameters);
    }
}
