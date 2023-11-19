package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche( nbEtals);
	}
	
	private static class Marche{
		private Etal[] etals;
		
		private Marche( int nbEtals ) {
			this.etals = new Etal[nbEtals];
			for(int i =0; i<nbEtals; i++) {
				this.etals[i] = new Etal();
			}
		}
		
		private void utiliserEtal( int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			this.etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		private int trouverEtalLibre() {
			for (int i =0; i<this.etals.length; i++) {
				if (this.etals[i].isEtalOccupe() == false ) {
					return i;
				}
			}
			return -1;
		}
		
		private Etal[] trouverProduit(String produit) {
			int nbTrouve = 0;
			Etal[] produitDispoTmp = new Etal[this.etals.length];
			
			for (int i = 0; i< this.etals.length; i++) {
				if( this.etals[i].isEtalOccupe() && this.etals[i].contientProduit(produit) )  {
					produitDispoTmp[nbTrouve] = this.etals[i];
					nbTrouve += 1;
				}
			}
			
			// utiliser une arraylist rendrait la fonction plus simple
			Etal[] produitDispo = new Etal[nbTrouve];
			for (int i=0; i<nbTrouve; i++) {
				produitDispo[i]=produitDispoTmp[i];
			}
			
			return produitDispo;
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			
			for (int i = 0; i< this.etals.length; i++) {
				if(this.etals[i].getVendeur() == gaulois && this.etals[i].isEtalOccupe()) {
					return this.etals[i];					
				}
				
			}
			
			return null;
		}
		
		private String afficherMarche() {
			int nbVide = 0;
			String msg ="";
			
			for(int i = 0; i< this.etals.length; i++) {
				if (this.etals[i].isEtalOccupe() ) {
					msg += this.etals[i].afficherEtal();
				}
				else {
					nbVide +=1;
				}
			}
			
			if(nbVide != 0 ) {
				msg += "Il reste "+nbVide+" étals non utilisés dans le marché.\n";
			}
			return msg;
			
		}
		
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() throws VillageSansChefException{
		if(chef == null) {
			throw new VillageSansChefException();
		}
		else {
			StringBuilder chaine = new StringBuilder();
			if (nbVillageois < 1) {
				chaine.append("Il n'y a encore aucun habitant au village du chef "
						+ chef.getNom() + ".\n");
			} else {
				chaine.append("Au village du chef " + chef.getNom()
						+ " vivent les légendaires gaulois :\n");
				for (int i = 0; i < nbVillageois; i++) {
					chaine.append("- " + villageois[i].getNom() + "\n");
				}
			}
			return chaine.toString();
		}
	}
	
	public String installerVendeur( Gaulois vendeur, String produit, int nbProduit) {
		String installation = vendeur.getNom()+" cherche un endroit pour vendre "+ nbProduit + " " + produit +".\n" ;
		int etalDispo = this.marche.trouverEtalLibre();
		if ( etalDispo != -1) {
			this.marche.utiliserEtal(etalDispo, vendeur, produit, nbProduit);
			installation += "Le vendeur "+ vendeur.getNom() +" vend des "+ produit + " à l'étal n°" + etalDispo + ".\n";
		}
		else {
			installation += "Malheureusement aucun étal n'est disponible "+vendeur.getNom()+" ne s'installe donc pas.\n";
		}
		
		return installation;
	}
	
	public String rechercherVendeursProduit(String produit) {
		Etal[] vendeursProduit = this.marche.trouverProduit(produit);
		String msg;
		
		if( vendeursProduit.length != 0 ) {
			msg = "Les vendeurs qui proposent des "+produit+ " sont :\n";
			
			for(int i=0; i< vendeursProduit.length; i++ ) {
				msg += "- "+ vendeursProduit[i].getVendeur().getNom() +"\n";
			}
		}
		else {
			msg = "Aucun vendeur de propose de "+produit+".\n";
		}
		
		return msg;
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return this.marche.trouverVendeur(vendeur);	
	}
	
	public String partirVendeur(Gaulois vendeur) {
		int indiceVendeur =-1;
		for (int i = 0; i< this.marche.etals.length; i++) {
			if(this.marche.etals[i].getVendeur() == vendeur && this.marche.etals[i].isEtalOccupe()) {
				indiceVendeur = i;					
			}
		}
		if(indiceVendeur != -1) {
			return this.marche.etals[indiceVendeur].libererEtal();
		}
		else {
			return "Il n'y a pas d'étal à libérer";
		}
	}
	
	public String afficherMarche() {
		return "Le marché du village "+this.getNom()+" possède plusieurs étals :\n"+this.marche.afficherMarche();
	}
	
	
}