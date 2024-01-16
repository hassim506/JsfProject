package com.gesetudiants.bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EtudiantFacade {
    private static final Logger LOGGER = Logger.getLogger(EtudiantFacade.class.getName());

    public List<Etudiant> findAll() {
        List<Etudiant> etudiants = new ArrayList<>();

        // Utilisation try-with-resources pour simplifier la gestion des ressources
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bdcrudjsf", "root", "");
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM etudiant");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Etudiant etudiant = new Etudiant();
                etudiant.setId(resultSet.getInt("id"));
                etudiant.setNom(resultSet.getString("nom"));
                etudiant.setPrenom(resultSet.getString("prenom"));
                etudiant.setDateNais(resultSet.getDate("dateNais"));
                etudiants.add(etudiant);
            }

        } catch (SQLException e) {
            // Loguer l'erreur au lieu d'imprimer la trace de la pile
            LOGGER.log(Level.SEVERE, "Erreur lors de la récupération des étudiants", e);
        }

        return etudiants;
    }


    // Méthode pour récupérer un étudiant par ID
    public Etudiant findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Etudiant etudiant = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bdcrudjsf", "root", "");

            String sql = "SELECT * FROM etudiant WHERE id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                etudiant = new Etudiant();
                etudiant.setId(resultSet.getInt("id"));
                etudiant.setNom(resultSet.getString("nom"));
                etudiant.setPrenom(resultSet.getString("prenom"));
                etudiant.setDateNais(resultSet.getDate("dateNais"));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de la récupération de l'étudiant par ID", e);
        } finally {
            // Fermer les ressources
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return etudiant;
    }

    public void update(Etudiant etudiant) {
    	System.out.println("## Update called for student with ID: " + etudiant.getId());
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bdcrudjsf", "root", "");
             PreparedStatement statement = connection.prepareStatement("UPDATE etudiant SET nom = ?, prenom = ?, dateNais = ? WHERE id = ?")) {

            statement.setString(1, etudiant.getNom());
            statement.setString(2, etudiant.getPrenom());
            statement.setDate(3, new java.sql.Date(etudiant.getDateNais().getTime()));
            statement.setInt(4, etudiant.getId());

            statement.executeUpdate();

            LOGGER.log(Level.INFO, "Étudiant mis à jour avec succès.");

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de la mise à jour de l'étudiant", e);
            throw new RuntimeException("Erreur lors de la mise à jour de l'étudiant", e);
        }
    }


    // Méthode pour créer un nouvel étudiant
    public void create(Etudiant etudiant) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bdcrudjsf", "root", "");
             PreparedStatement statement = connection.prepareStatement("INSERT INTO etudiant (nom, prenom, dateNais) VALUES (?, ?, ?)")) {

            statement.setString(1, etudiant.getNom());
            statement.setString(2, etudiant.getPrenom());

            // Convertir java.util.Date en java.sql.Date si nécessaire
            java.util.Date utilDate = etudiant.getDateNais();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

            statement.setDate(3, sqlDate);

            // Exécuter la requête
            statement.executeUpdate();

            LOGGER.log(Level.INFO, "Étudiant ajouté avec succès.");

        } catch (SQLException e) {
            // Loguer l'erreur au lieu d'imprimer la trace de la pile
            LOGGER.log(Level.SEVERE, "Erreur lors de l'ajout de l'étudiant", e);
            throw new RuntimeException("Erreur lors de l'ajout de l'étudiant", e);
        }
    }


 // Méthode pour supprimer un étudiant par son ID
    public void delete(int etudiantId) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bdcrudjsf", "root", "");
             PreparedStatement statement = connection.prepareStatement("DELETE FROM etudiant WHERE id = ?")) {

            statement.setInt(1, etudiantId);

            // Exécuter la requête
            statement.executeUpdate();

            LOGGER.log(Level.INFO, "Étudiant supprimé avec succès.");

        } catch (SQLException e) {
            // Loguer l'erreur au lieu d'imprimer la trace de la pile
            LOGGER.log(Level.SEVERE, "Erreur lors de la suppression de l'étudiant", e);
            throw new RuntimeException("Erreur lors de la suppression de l'étudiant", e);
        }
    }



    
}
