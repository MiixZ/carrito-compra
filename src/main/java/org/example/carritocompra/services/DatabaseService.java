package org.example.carritocompra.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@Service
@AllArgsConstructor
public class DatabaseService {
    private final DataSource dataSource;

    public byte[] exportDatabaseToSql() {
        String sql = "SCRIPT TO 'carritodb.sql'";
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ) {

            stmt.execute(sql);
            try (ResultSet rs = stmt.executeQuery("SCRIPT TO 'carritodb.sql'")) {
                while (rs.next()) {
                    baos.write(rs.getString(1).getBytes(StandardCharsets.UTF_8));
                }
            }

            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error exporting database to SQL", e);
        }
    }
}