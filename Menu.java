import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class Menu extends JPanel implements ActionListener {
	//interactions : nouvelle note, nouvel accord, voir le nom, voir piano, voir guitare, changer instruments

	JButton newNote = new JButton("Nouvelle note");
	JButton newChord = new JButton("Nouvel accord");
	JButton seeName = new JButton("Voir Note");
	JButton seeGuitare = new JButton("Voir Guitare");
	JButton seePiano = new JButton("Voir Piano");
	JButton play=new JButton("Ecouter");
	JComboBox chooseInstrument=new JComboBox();
	JComboBox chooseMode=new JComboBox();
	JCheckBox diese=new JCheckBox("inclure les dièses");
	JCheckBox mineur=new JCheckBox("inclure la gamme mineure");
	JCheckBox left=new JCheckBox("mode gaucher");
	JPanel check=new JPanel();
	boolean mode;//vaut true si on est en mode son (identifier la note jouée), false pour le mode note (jouer la note affichée)
	JPanel nord=new JPanel();
	JPanel est=new JPanel();
	boolean note,manche,clavier,gaucher;




	public Menu(){
		chooseMode.addItem("Mode Son");
		chooseMode.addItem("Mode Note");
		chooseMode.addItemListener(new ItemState());	
		chooseInstrument.addItem("Guitare");
		chooseInstrument.addItem("Piano");
		chooseInstrument.addItemListener(new ItemState());	
		check.setLayout(new GridLayout(2,1));
		check.setOpaque(false);
		diese.setOpaque(false);
		mineur.setOpaque(false);
		left.setOpaque(false);
		check.add(diese);
		check.add(mineur);

		nord.setPreferredSize(new Dimension(380,100));
		nord.setOpaque(false);
		nord.add(chooseMode);
		nord.add(newNote);
		nord.add(newChord);
		nord.add(new JLabel("Instrument :"));
		nord.add(chooseInstrument);
		nord.add(check);
		nord.add(play);

		est.setLayout(new GridLayout(4,1,20,80));
		est.setOpaque(false);

		est.add(seeName);
		est.add(seeGuitare);
		est.add(seePiano);
		est.add(left);


		this.setOpaque(false);
		this.setSize(700,480);
		this.setLayout(new BorderLayout());
		this.add(nord, BorderLayout.NORTH);
		this.add(est, BorderLayout.EAST);
		JLabel credit=new JLabel("Entièrement créé par Nicolas Cailloux");
		credit.setForeground(new Color(210,150,60));
		this.add(credit, BorderLayout.SOUTH);
		play.addActionListener(this);
		newNote.addActionListener(this);
		newChord.addActionListener(this);
		seeName.addActionListener(this);
		seeGuitare.addActionListener(this);
		seePiano.addActionListener(this);
		mode=true;
		note=false;
		manche=false;
		clavier=false;

	}

	public void paintComponent(Graphics gr){
		Graphics2D g=(Graphics2D)gr;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setPaint(new GradientPaint(this.getWidth()/2,0,new Color(100,160,220),this.getWidth()/2,this.getHeight(),new Color(50,80,110)));
		g.fillRect(0,0,this.getWidth(),this.getHeight());
		g.setColor(new Color(50,80,110));
		g.fillRect(301, 105, 100, 58);
		g.setColor(Color.red);
		int d=0;
		if(note||!mode){
			d=0;
			g.setFont(new Font("Arial",Font.BOLD,40));
			if(Musique.courant.nom.contains("#")||Musique.courante.nom.contains("#")) d+=10;
			if(Musique.courant.nom.contains("m")) d+=16;
			if(Musique.accord)g.drawString(Musique.courant.nom, 336-d, 150);
			else g.drawString(Musique.courante.nom,326-d,150);;
			
		}


		try {
			Image gt;
			if(left.isSelected()){gt = ImageIO.read(new File("res/"+"guitareL.png"));}
			else{gt = ImageIO.read(new File("res/"+"guitare.png"));}
			g.drawImage(gt, 10, 200, this);
			Image pn= ImageIO.read(new File("res/"+"piano.png"));
			g.drawImage(pn, 25, 320, this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		d=0;
		if(Musique.accord){
			for(int i=0;i<6;i++){
				if(manche){
					if(Musique.courant.notesG.get(i)!=null){
						int c=Musique.courant.notesG.get(i).corde-1;
						int f=Musique.courant.notesG.get(i).fret-1;
						d=26;		
						if(f<0){f=0;d=10;}
						if(left.isSelected()){d=d*-1+586;f*=-1;}
						g.fillOval(d+27*f, 271-14*c, 10, 10);
					}
				}
				if(clavier){
					if(Musique.courant.notesP.get(i/2)!=null){
						double p=Musique.courant.notesP.get(i/2).posPiano;
						d=0;
						if(Musique.courant.notesP.get(i/2).nom.contains("#")) d=30;
						g.fillOval((int)(31+19*p), 380-d, 10, 10);//marqueur note piano
					}
				}

			}
		}else{
			if(manche){
				int c=Musique.courante.corde-1;
				int f=Musique.courante.fret-1;
				d=26;
				if(f<0){f=0;d=10;}
				if(left.isSelected()){d=d*-1+586;f*=-1;}
				g.fillOval(d+27*f, 271-14*c, 10, 10);//marqueur notes guitare
			}if(clavier){
				double p=Musique.courante.posPiano;
				System.out.println("posPiano : "+p);
				d=0;
				if(Musique.courante.nom.contains("#")) d=30;
				g.fillOval((int)(31+19*p), 380-d, 10, 10);//marqueur note piano
			}
		}
	}

	class ItemState implements ItemListener{

		public void itemStateChanged(ItemEvent e) {
			System.out.println("son : "+mode);
			if(e.getSource()==chooseInstrument){
				if(e.getItem()=="Guitare")Musique.instrument=1;
				if(e.getItem()=="Piano")Musique.instrument=2;
			}else{
				if(e.getItem()=="Mode Son")	mode=true;
				if(e.getItem()=="Mode Note")mode=false;

			}

		}

	}


	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==play){
			if(!Musique.accord)Son.startMidi("res/"+Musique.courante.nom+".mid");
			else{
				try {
					Musique.playAccord();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
		if(e.getSource()==newNote){
			Musique.accord=false;
			clavier=false;
			manche=false;
			int k=Musique.notes.size();
			Musique.courante=Musique.notes.get((int)(Math.random()*k));
			if(!diese.isSelected()){
				while(Musique.courante.nom.contains("#")){
					Musique.courante=Musique.notes.get((int)(Math.random()*k));
				}
			}
			if(mode){
				note=false;
				Son.startMidi("res/"+Musique.courante.nom+".mid");
			}
			else note=true;
		}
		if(e.getSource()==newChord){
			int k=Musique.accords.size();
			if(!mineur.isSelected()) k-=12;
			int a=(int)(Math.random()*k);
			Musique.accord=true;
			Musique.courant=Musique.accords.get(a);
			if(!diese.isSelected()){
				while(Musique.courant.nom.contains("#")){
					a=(int)(Math.random()*k);
					Musique.courant=Musique.accords.get(a);
				}
			}
		
		if(mode){
			note=false;
			try {
				Musique.playAccord();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		else note=true;
		clavier=false;
		manche=false;
	}
	if(e.getSource()==seeName){note=!note;}
	if(e.getSource()==seePiano){clavier=!clavier;}
	if(e.getSource()==seeGuitare){manche=!manche;}
	nord.getParent().repaint();
}



}
