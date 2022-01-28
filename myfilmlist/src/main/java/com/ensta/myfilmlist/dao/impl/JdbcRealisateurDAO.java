package com.ensta.myfilmlist.dao.impl;

import com.ensta.myfilmlist.dao.RealisateurDAO;
import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.model.Realisateur;
import com.ensta.myfilmlist.persistence.ConnectionManager;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcRealisateurDAO implements RealisateurDAO {

    private DataSource dataSource = ConnectionManager.getDataSource();

    private JdbcTemplate jdbcTemplate = ConnectionManager.getJdbcTemplate();


    @Override
    public List<Realisateur> findAll() throws SQLException {
        String query = "SELECT id, nom, prenom, date_naissance, celebre FROM Realisateur;";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery();
        ){
            List<Realisateur> realList = jdbcTemplate.query(query, (rs, rownum) -> {
                Realisateur real = new Realisateur();
                real.setId(rs.getInt("id"));
                real.setNom(rs.getString("nom"));
                real.setPrenom(rs.getString("prenom"));
                real.setDateNaissance(rs.getDate("date_naissance").toLocalDate());
                real.setCelebre(rs.getBoolean("celebre"));

                return real;
            });
            for (Realisateur real:realList
                 ) {
            }
            return realList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new SQLException("Exception in findAll (Realisateurs) " + throwables);
        }

    }

    @Override
    public Realisateur findByNomAndPrenom(String nom, String prenom) throws Exception{
        try{
            Realisateur blub = null;
            int cpt = 0;
            List<Realisateur> list = findAll();

            for (Realisateur real:list
                 ) {
                if (real.getPrenom().equals(prenom) && real.getNom().equals(nom)){
                    blub = real;
                    cpt ++;
                }
            }
            if (cpt == 1){
                return blub;
            }
            if (cpt >1){
                throw new RuntimeException();
            }
            return null;
        }
        catch(EmptyResultDataAccessException e){
            return null;
        }
        catch(RuntimeException e){
            throw e;
        }
    }

    @Override
    public Optional<Realisateur> findById(long id) throws SQLException {
        Optional<Realisateur> blub;
        try{
            List<Realisateur> list = findAll();
            for (Realisateur real:list
                 ) {
                if (real.getId()==id){
                    return Optional.of(real);
                }
            }
            return Optional.empty();
        }
        catch(Exception e){
            throw new SQLException(e);
        }

    }


    public Realisateur update(Realisateur realisateur) {

        String query = "UPDATE Realisateur SET nom = ?, prenom = ?, date_naissance = ?, celebre = ?  WHERE id = ?";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator creator = conn -> {
            PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, realisateur.getNom());
            statement.setString(2, realisateur.getPrenom());
            statement.setDate(3, java.sql.Date.valueOf(realisateur.getDateNaissance()));
            statement.setBoolean(4, realisateur.isCelebre());
            statement.setLong(5, realisateur.getId());

            return statement;
        };

        jdbcTemplate.update(creator, keyHolder);
        return realisateur;


    }


}
