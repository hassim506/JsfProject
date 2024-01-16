/**
 * 
 */
package com.gesetudiants.bean;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * 
 */

@RequestScoped
@Named
public class EtudiantBean {
	private EtudiantFacade etudiantFacade;
	private Etudiant etudiant;
	private List<Etudiant> listeEtudiant = new ArrayList<Etudiant>();
	private List<Etudiant> listEtudiant;
	private int idEtudiant;
	private boolean Modifiable;
	
	public EtudiantBean() {
		etudiant = new Etudiant();
		setEtudiantFacade(new EtudiantFacade());

	}
	
	//recuperation liste etudiant
	public List<Etudiant> chargerListeEtudiant() {
		return etudiantFacade.findAll();

	}
	
	// Nouvelle méthode pour récupérer un étudiant par ID
	public void getEtudiantParId(int id) {
	    idEtudiant = id;
	    etudiant = etudiantFacade.findById(id);
	}

    
	// Getter pour la propriété etudiant
    public Etudiant getEtudiant() {
        return etudiant;
    }

    // Getter et Setter pour la propriété idEtudiant
    public int getIdEtudiant() {
        return idEtudiant;
    }

    public void setIdEtudiant(int idEtudiant) {
        this.idEtudiant = idEtudiant;
    }

	
	
	public String ajouterEtudiant() {
		etudiantFacade.create(etudiant);
		setListEtudiant(etudiantFacade.findAll());
		return "List?faces-redirect=true";
	}
	
	public String supprimerEtudiant(int etudiantId) {
	        etudiantFacade.delete(etudiantId);
	        listEtudiant = etudiantFacade.findAll();
	        return "success"; // Opération réussie
	}
	
	public String preparerModification(Etudiant etu) {
	    // Prépare l'objet etudiant pour la modification
	    etudiant = etu;
	    Modifiable = true;
	    System.out.println("## Préparation de la modification : " + etudiant.toString());
	    return null;
	}

	public String modifier() {
	    if (etudiant != null) {
	        // Effectue la modification réelle dans la base de données
	        etudiantFacade.update(etudiant);

	        // Met à jour la liste des étudiants après la modification
	        listEtudiant = etudiantFacade.findAll();

	        // Réinitialise les variables après la modification réussie
	        etudiant = new Etudiant();
	        Modifiable = false;

	        System.out.println("## Modification réussie.");
	    } else {
	        System.out.println("## Aucun étudiant à modifier.");
	    }
	    return "list";
	}


	
    // Autres méthodes et getters/setters
	/**
	 * @return the etudiant
	 */

	/**
	 * @param etudiant the etudiant to set
	 */
	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}
	/**
	 * @return the listeEtudiant
	 */
	public List<Etudiant> getListeEtudiant() {
		return listeEtudiant;
	}
	/**
	 * @param listeEtudiant the listeEtudiant to set
	 */
	public void setListeEtudiant(List<Etudiant> listeEtudiant) {
		this.listeEtudiant = listeEtudiant;
	}

	public EtudiantFacade getEtudiantFacade() {
		return etudiantFacade;
	}

	public void setEtudiantFacade(EtudiantFacade etudiantFacade) {
		this.etudiantFacade = etudiantFacade;
	}

	public List<Etudiant> getListEtudiant() {
		return listEtudiant;
	}

	public void setListEtudiant(List<Etudiant> listEtudiant) {
		this.listEtudiant = listEtudiant;
	}

	public boolean isModifiable() {
		return Modifiable;
	}

	public void setModifiable(boolean modifiable) {
		Modifiable = modifiable;
	}

	
	

}
