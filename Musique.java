import java.util.ArrayList;

/**
 * 
 * @author Nicolas Cailloux
 *
 * Attention : ce programme a été codé "à la volée", pour un usage personnel, sans aucune précaution de sécurité.
 * Il ne faut donc pas faire n'importe quoi avec les variables et les classes.
 */
public class Musique {
	static ArrayList<Note> notes=new ArrayList<Note>();
	static ArrayList<Accord> accords=new ArrayList<Accord>();
	static Note courante;
	static Accord courant;
	public static boolean accord,add=true; //indique si on doit jouer un accord ou une seule note;
	public static byte instrument; //1=guitare, 2=synthé

	static final Note C2 = new Note("C2",0,-1,0);
	static final Note Cc2 = new Note("C#2",0.5,-1,0);
	static final Note D2 = new Note("D2",1,-1,0);
	static final Note Dc2 = new Note("D#2",1.5,-1,0);
	static final Note E2 = new Note("E2",2,1,0);
	static final Note F2 = new Note("F2",3,1,1);
	static final Note Fc2 = new Note("F#2",3.5,1,2);
	static final Note G2 = new Note("G2",4,1,3);
	static final Note Gc2 = new Note("G#2",4.5,1,4);
	static final Note A2 = new Note("A2",5,2,0);
	static final Note Ac2 = new Note("A#2",5.5,2,1);
	static final Note B2= new Note("B2",6,2,2);
	static final Note C3= new Note("C3",7,2,3);
	static final Note Cc3= new Note("C#3",7.5,2,4);
	static final Note D3= new Note("D3",8,3,0);
	static final Note Dc3= new Note("D#3",8.5,3,1);
	static final Note E3= new Note("E3",9,3,2);
	static final Note F3= new Note("F3",10,3,3);
	static final Note Fc3= new Note("F#3",10.5,3,4);
	static final Note G3= new Note("G3",11,4,0);
	static final Note Gc3= new Note("G#3",11.5,4,1);
	static final Note A3= new Note("A3",12,4,2);
	static final Note Ac3= new Note("A#3",12.5,4,3);
	static final Note B3= new Note("B3",13,5,0);
	static final Note C4= new Note("C4",14,5,1);
	static final Note Cc4= new Note("C#4",14.5,5,2);
	static final Note D4= new Note("D4",15,5,3);
	static final Note Dc4= new Note("D#4",15.5,5,4);
	static final Note E4= new Note("E4",16,6,0);
	static final Note F4= new Note("F4",17,6,1);
	static final Note Fc4= new Note("F#4",17.5,6,2);
	static final Note G4= new Note("G4",18,6,3);
	static final Note Gc4= new Note("G#4",18.5,6,4);
	static final Note A4= new Note("A4",19,6,5);
	static final Note Ac4= new Note("A#4",19.5,6,6);
	static final Note B4= new Note("B4",20,6,7);
	static final Note C5= new Note("C5",21,6,8);
	static final Note Cc5= new Note("C#5",21.5,6,9);
	static final Note D5= new Note("D5",22,6,10);
	static final Note Dc5= new Note("D#5",22.5,6,11);
	static final Note E5= new Note("E5",23,6,12);
	static final Note F5= new Note("F5",24,6,13);
	static final Note Fc5= new Note("F#5",24.5,6,14);
	static final Note G5= new Note("G5",25,6,15);
	static final Note Gc5= new Note("G#5",25.5,6,16);
	static final Note A5= new Note("A5",26,6,17);
	static final Note Ac5= new Note("A#5",26.5,6,18);
	static final Note B5= new Note("B5",27,6,19);
	static final Note C6= new Note("C6",28,6,20);
	static final Note Cc6= new Note("C#6",28.5,6,21);
	static final Note D6= new Note("C#6",29,6,22);

	final static Accord C=new Accord("C");
	static final Accord Cc=new Accord("C#");	
	static final Accord D=new Accord("D");	
	static final Accord Dc=new Accord("D#");	
	static final Accord E=new Accord("E");	
	static final Accord F=new Accord("F");	
	static final Accord Fc=new Accord("F#");	
	static final Accord G=new Accord("G");	
	static final Accord Gc=new Accord("G#");
	static final Accord A=new Accord("A");
	static final Accord Ac=new Accord("A#");
	static final Accord B=new Accord("B");

	static final Accord Cm= new Accord("Cm");
	static final Accord Ccm= new Accord("C#m");
	static final Accord Dm= new Accord("Dm");
	static final Accord Dcm= new Accord("D#m");
	static final Accord Em= new Accord("Em");
	static final Accord Fm= new Accord("Fm");
	static final Accord Fcm= new Accord("F#m");
	static final Accord Gm= new Accord("Gm");
	static final Accord Gcm= new Accord("G#m");
	static final Accord Am= new Accord("Am");
	static final Accord Acm= new Accord("A#m");
	static final Accord Bm= new Accord("Bm");
	



	public static void main (String []args) throws InterruptedException{
		System.out.println("La base de données contient "+notes.size()+" notes. \nIl y a "+accords.size()+" accords.");
		instrument=1;
		add=false;
		C.setGuitare(E4,C4,G3,E3,C3,null);
		C.setPiano(C3,E3,G3);
		Cc.setGuitare(Gc4,new Note("F4",24,5,6),new Note("C#4",20,4,6),new Note("G#3",15,3,6),Cc3,null);
		Cc.setPiano(Cc3, F3, Gc3);
		D.setGuitare(Fc4,D4,A3,D3,null,null);
		D.setPiano(D3,Fc3,A3);
		Dc.setGuitare(Ac4,new Note("G4",26,5,8),new Note("D#4",22,4,8),new Note("A#3",17,3,8),new Note("D#3",2,6),null);
		Dc.setPiano(Dc3,G3,Ac3);
		E.setGuitare(E4,B3,Gc3,E3,B2,E2);
		E.setPiano(E3,Gc3,B3);
		F.setGuitare(F4,C4,A3,F3,C3,F2);
		F.setPiano(F3,A3,C4);
		Fc.setGuitare(Fc4,Cc4,Ac3,Fc3,Cc3,Fc2);
		Fc.setPiano(Fc3,Ac3,Cc4);
		G.setGuitare(G4,B3,G3,D3,B2,G2);
		G.setPiano(G3,B3,D4);
		Gc.setGuitare(G4,Dc4,Ac3,new Note("G3",3,5),null,null);
		Gc.setPiano(Gc3,C4,Dc4);
		A.setGuitare(E4,Cc4,A3,E3,A2,null);
		A.setPiano(A3,Cc4,E4);
		Ac.setGuitare(F4,D4,Ac3,F3,Ac2,null);
		Ac.setPiano(Ac3,D4,F4);
		B.setGuitare(Fc4,Dc4,new Note("B3",19,4,4),Fc3,B2,null);
		B.setPiano(B3,Dc4,Fc4);
		
		Cm.setGuitare(G4,Dc4,C4,G3,C3,null);
		Ccm.setGuitare(Gc4,E4,Cc4,Gc3,Cc3,null);
		Dm.setGuitare(F4,D4,A3,D3,null,null);
		Dcm.setGuitare(Ac4,Fc4,Dc4,Ac3,Dc3,null);
		Em.setGuitare(E4,B3,G3,E3,B2,E2);
		Fm.setGuitare(F4,C4,Gc3,F3,C3,F2);
		Fcm.setGuitare(Fc4,Cc4,A3,Fc3,Cc3,Fc2);
		Gm.setGuitare(G4,D4,Ac3,G3,D3,G2);
		Gcm.setGuitare(Gc4,Dc4,B3,Gc3,Dc3,Gc2);
		Am.setGuitare(E4,C4,A3,E3,A3,null);
		Acm.setGuitare(F4,Cc4,Ac3,F3,Ac3,null);
		Bm.setGuitare(Fc4,D4,B3,Fc3,B3,null);
		Cm.setPiano(C3,Dc3,G3);
		Ccm.setPiano(Cc3,E3,Gc3);
		Dm.setPiano(D3,F3,A3);
		Dcm.setPiano(Dc3,Gc3,Ac3);
		Em.setPiano(E3,G3,B3);
		Fm.setPiano(F3,Gc3,C4);
		Fcm.setPiano(Fc3,A3,Cc4);
		Gm.setPiano(G3,Ac3,D4);
		Gcm.setPiano(Gc3,B3,Dc4);
		Am.setPiano(A3,C4,E4);
		Acm.setPiano(Ac3,Cc4,F4);
		Bm.setPiano(B3,D4,Fc4);
		courante=D3;
		courant=D;
		Fenetre f=new Fenetre();
		f.setContentPane(new Menu());

	}


	public static void playAccord() throws InterruptedException{
		switch (instrument){
		case 1: 
			for(int i=0;i<courant.notesG.size();i++){
				if(courant.notesG.get(i)!=null) Son.startMidi("res/"+courant.notesG.get(i).nom+".mid");
				Thread.sleep(15);		
			}
			break;
		case 2:
			for(int i=0;i<courant.notesP.size();i++){
				if(courant.notesP.get(i)!=null) Son.startMidi("res/"+courant.notesP.get(i).nom+".mid");
				Thread.sleep(15);		
			}
			break;
		}
	}
}
