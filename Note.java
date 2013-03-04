
public class Note {
	//une note est caractérisée par : son nom (hauteur), sa position sur le piano, sa position sur les cordes, le son qu'elle produit
	public String nom;
	double posPiano;
	int corde,fret;
	Son son;
	
	public Note(String s, int p){
		nom=s;
		posPiano=p;
		if(Musique.add) Musique.notes.add(this);
	}

	public Note(String s, int c, int f){
		nom=s;
		corde=c;
		fret=f;
		Musique.notes.add(this);
	}
	
	public Note(String s, double p, int c, int f){
		nom=s;
		posPiano=p;
		corde=c;
		fret=f;
		Musique.notes.add(this);
	}

}
