package gui;

import javax.swing.JFrame;

public class TelaPadrao extends JFrame {

	public TelaPadrao(){
		super("MegaSena :: Crawler");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setSize(550, 350);
		setLayout(null);
		setResizable(false);
		repaint();
	}
	
	public TelaPadrao(String nomeJanela, int altura, int largura){
		super(nomeJanela);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		setSize(largura, altura);
		repaint();
	}
}
