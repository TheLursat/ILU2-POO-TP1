package histoire;

import personnages.Chef;
import personnages.Druide;
import personnages.Gaulois;
import villagegaulois.Etal;
import villagegaulois.Village;
import villagegaulois.VillageSansChefException;


public class ScenarioCasDegrade {
	
	public static void main(String[] args) throws VillageSansChefException {
//		Etal etal = new Etal();
//		etal.libererEtal();
		
		Village village = new Village("le village des irréductibles", 10, 5);
		Chef abraracourcix = new Chef("Abraracourcix", 10, village);
		village.setChef(abraracourcix);
		Druide druide = new Druide("Panoramix", 2, 5, 10);
		Gaulois obelix = new Gaulois("Obélix", 25);
		Gaulois asterix = new Gaulois("Astérix", 8);
		Gaulois assurancetourix = new Gaulois("Assurancetourix", 2);
		Gaulois bonemine = new Gaulois("Bonemine", 7);
		
		village.ajouterHabitant(bonemine);
		village.ajouterHabitant(assurancetourix);
		village.ajouterHabitant(asterix);
		village.ajouterHabitant(obelix);
		village.ajouterHabitant(druide);
		village.ajouterHabitant(abraracourcix);
		village.afficherVillageois();
		
		
		System.out.println(village.installerVendeur(bonemine, "fleurs", -20));
//		Etal etalFleur = new Etal();
		Etal etalFleur = village.rechercherEtal(bonemine);
		System.out.println(etalFleur.acheterProduit(10, null));
		try {
			System.out.println(etalFleur.acheterProduit(-4, abraracourcix));
		}catch(IllegalArgumentException e) {
			e.printStackTrace();
		}
		etalFleur.libererEtal();
		try {
			System.out.println(etalFleur.acheterProduit(10, abraracourcix));
		}catch(IllegalStateException e) {
			e.printStackTrace();
		}
		village.installerVendeur(bonemine, "fleurs", -20);
		etalFleur = village.rechercherEtal(bonemine);
		System.out.println(etalFleur.acheterProduit(10, abraracourcix));
		
		Village villageSansChef = new Village("le village sans chef",10,5);
		try {
			villageSansChef.afficherVillageois();
		}catch(VillageSansChefException e) {
			e.printStackTrace();
		}
		
		System.out.println("fin du test");
	}
}
