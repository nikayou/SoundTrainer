import javax.swing.JFrame;


@SuppressWarnings("serial")
public class Fenetre extends JFrame{
	
	public Fenetre(){
	this.setSize(716,518);
	this.setLocationRelativeTo(null);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setVisible(true);
	this.setTitle("Sound Trainer");
	this.setContentPane(new Menu());
	}
}
