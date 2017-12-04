package gui;

import java.awt.Font;
import java.awt.font.TextAttribute;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class NumberGUI extends JPanel {

	private JLabel lblNumber;
	private JLabel lblQuant;
	private int quant;
	private int number;
	
	public NumberGUI(int number){
		this.quant = 0;
		this.number = number;
		this.lblNumber = new JLabel(number + "");
		this.lblNumber.setFont(new Font("arial", Font.BOLD, 12));
		this.lblQuant = new JLabel(quant + "");
		this.lblQuant.setFont(new Font("arial", Font.PLAIN, 9));
		add(lblNumber);
		add(lblQuant);
	}
	
	public void increment(){
		this.quant++;
		this.lblQuant.setText(this.quant + "");
		repaint();
	}
	
	public int getNumber(){
		return this.number;
	}	
}
