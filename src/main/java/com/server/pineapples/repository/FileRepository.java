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
        String sql = "SELECT b.total_payment, b.portal_comission FROM bookings b WHERE b.portal_reference LIKE ?";
        return jdbcTemplate.queryForList(sql, "%" + descricao + "%");
    }
}
