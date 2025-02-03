package com.server.pineapples.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class FileRepository {

    private final JdbcTemplate jdbcTemplate;

    public FileRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, Object>> findByResult(String descricao) {
        String sql = "SELECT * FROM bookings b WHERE portal_reference LIKE '%descricao%'";
        return jdbcTemplate.queryForList(sql, descricao);
    }
}
