package com.example.supermarket2.Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.supermarket2.models.User;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public User getUserById(Long id) {
        String sql = "SELECT * FROM user WHERE id = ?";
        List<User> users = jdbcTemplate.query(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setLong(1, id);
            }
        }, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                return user;
            }
        });
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public User getUserByName(String name) {
        String sql = "SELECT * FROM user WHERE username = ?";
        List<User> users = jdbcTemplate.query(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, name);
            }
        }, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                return user;
            }
        });
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public boolean updateUsername(String oldUsername, String newUsername) {
        String sql = "UPDATE user SET username = ? WHERE username = ?";
        int rowsAffected = jdbcTemplate.update(sql, newUsername, oldUsername);
        return rowsAffected == 1; 
    }

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Override
    public boolean updatePassword(Long userID, String newPassword) {
        String sql = "UPDATE user SET password = ? WHERE id = ?";
        String protectedNewPassword = bCryptPasswordEncoder.encode(newPassword);
        int rowsAffected = jdbcTemplate.update(sql, protectedNewPassword, userID);
        return rowsAffected == 1; 
    }

    @Override
    public boolean updateAddress(Long userID, String address) {
        String sql = "UPDATE user SET address = ? WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, address, userID);
        return rowsAffected == 1;
    }
    
    @Override
    public boolean updateCreditCard(Long userID, Long creditCard) {
        String sql = "UPDATE user SET credit_card = ? WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, creditCard, userID);
        return rowsAffected == 1;
    }
    @Override
    public boolean updateCvv(Long userID,int cvv){
        String sql = "UPDATE user SET cvv = ? WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, cvv, userID);
        return rowsAffected == 1;
    }
}
