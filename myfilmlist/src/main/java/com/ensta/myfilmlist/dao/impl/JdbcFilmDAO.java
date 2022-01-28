package com.ensta.myfilmlist.dao.impl;
import java.sql.*;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ensta.myfilmlist.dao.FilmDAO;
import com.ensta.myfilmlist.form.FilmForm;
import com.ensta.myfilmlist.mapper.FilmMapper;
import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.model.Realisateur;
import com.ensta.myfilmlist.persistence.ConnectionManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcFilmDAO implements FilmDAO {

    private DataSource dataSource = ConnectionManager.getDataSource();

    private JdbcTemplate jdbcTemplate = ConnectionManager.getJdbcTemplate();


    public List<Film> findAll() throws SQLException{
        String query = "SELECT Film.id, titre, duree, nom, prenom, date_naissance, celebre, Realisateur.id FROM Film JOIN Realisateur ON Film.Realisateur_Id = Realisateur.Id;";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery();
        ){


            List<Film> filmList = jdbcTemplate.query(query, (rs, rownum) -> {
                Film film = new Film();
                film.setId(rs.getInt("Film.id"));
                film.setTitre(rs.getString("titre"));
                film.setDuree(rs.getInt("duree"));
                Realisateur real = new Realisateur();
                real.setNom(rs.getString("nom"));
                real.setPrenom(rs.getString("prenom"));
                real.setDateNaissance(rs.getDate("date_naissance").toLocalDate());
                real.setCelebre(rs.getBoolean("celebre"));
                real.setId(rs.getLong("Realisateur.id"));
                film.setRealisateur(real);

                return film;
                        });

            return filmList;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new SQLException("Exception in findAll " + throwables);
        }

    }


    public Film save (Film film){
        String query = "INSERT INTO Film(titre, duree, realisateur_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator creator = conn -> {
            PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, film.getTitre());
            statement.setInt(2, film.getDuree());
            statement.setLong(3, film.getRealisateur().getId());

            return statement;
        };

        jdbcTemplate.update(creator, keyHolder);
        film.setId(keyHolder.getKey().longValue());
        return film;

    }


    @Override
    public Optional<Film> findById(long id) throws SQLException{
        try{
            List<Film> list = findAll();
            for (Film film:list
            ) {
                if (id == film.getId()){
                    return Optional.of(film);
                }
            }
            return Optional.empty();
        }
        catch(Exception e){
            throw new SQLException(" In findbyId (film) : " + e);
        }

    }


    @Override
    public void delete(Film film) {

        String query = "DELETE FROM Film where id = ?;";


         PreparedStatementCreator creator = conn -> {
             PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
             statement.setLong(1, film.getId());
             return statement;
         };

        jdbcTemplate.update(creator);
    }


    @Override
    public List<Film> findByRealisateurId(long realisateurId) throws SQLException {

        String query = "SELECT Film.id, titre, duree, nom, prenom, date_naissance, celebre, Realisateur.id FROM Film JOIN Realisateur ON Film.Realisateur_Id = Realisateur.Id WHERE Realisateur.Id = ?;";

        List<Film> filmList = jdbcTemplate.query(query, (rs, rownum) -> {
            Film film = new Film();
            film.setId(rs.getInt("Film.id"));
            film.setTitre(rs.getString("titre"));
            film.setDuree(rs.getInt("duree"));
            Realisateur real = new Realisateur();
            real.setNom(rs.getString("nom"));
            real.setPrenom(rs.getString("prenom"));
            real.setDateNaissance(rs.getDate("date_naissance").toLocalDate());
            real.setCelebre(rs.getBoolean("celebre"));
            real.setId(rs.getLong("Realisateur.id"));

            film.setRealisateur(real);

            return film;
        }, realisateurId

        );
        return filmList;


    }


    public Film update(long id, FilmForm filmForm){


        String query = "UPDATE Film SET duree = ?, realisateur_id = ?, titre = ? WHERE id = ?";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator creator = conn -> {
            PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, filmForm.getDuree());
            statement.setLong(2, filmForm.getRealisateurId());
            statement.setString(3, filmForm.getTitre());
            statement.setLong(4, id);
            return statement;
        };

        jdbcTemplate.update(creator, keyHolder);

        return FilmMapper.convertFilmFormToFilm(filmForm);


    }


}
