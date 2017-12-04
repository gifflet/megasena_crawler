package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TelaInicio extends TelaPadrao {

	private ArrayList<NumberGUI> numbers;
	private JPanel painelPrincipal;
	private JButton btnStart, btnPause, btnStop, btnSave;
	private JLabel isRunning;
	private Synchronizer synchronizer = new Synchronizer();
	private Runnable updateScreenListener;
	private GUINotifier guiNotifier;

	public TelaInicio() {
		super();
		this.numbers = new ArrayList<NumberGUI>();
		this.painelPrincipal = new JPanel();
		this.painelPrincipal.setLayout(null);
		repaint();
		setContentPane(this.painelPrincipal);
		setScreen();
		this.painelPrincipal.repaint();
		this.repaint();
		this.setSize(551, 350);
		setButtons();
		setLabels();
		guiNotifier = new GUINotifier();
		this.updateScreenListener = new Runnable() {
			public void run() {
				while (true) {
					guiNotifier.getSync();
					TelaInicio.this.updateNumbersInfoOnScreen(guiNotifier.getNumerosAdicionados());
				}
			}
		};
		new Thread(this.updateScreenListener).start();

	}

	public void setNumbers() {
		int number = 1;
		int posLinhaAtualX = 20;
		int posLinhaAtualY = 10;
		for (int i = 1; i <= 6; i++)
			for (int j = 0; j < 10; j++) {
				NumberGUI numberGUI = new NumberGUI(number);
				numberGUI.setBounds((20 + j * 50), (20 + i * 40), 40, 30);
				numbers.add(numberGUI);
				number++;
			}
	}

	public void setScreen() {
		setNumbers();
		for (NumberGUI numberGUI : this.numbers) {
			this.painelPrincipal.add(numberGUI);
		}
	}

	public void setButtons() {
		this.btnStart = new JButton("Start");
		this.btnStart.setBounds(20, 20, 70, 30);
		this.btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaInicio.this.synchronizer.setSync("START");
			}
		});
		this.painelPrincipal.add(this.btnStart);
		this.btnPause = new JButton("Pause");
		this.btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaInicio.this.synchronizer.setSync("PAUSE");
			}
		});
		this.btnPause.setBounds(110, 20, 70, 30);
		this.painelPrincipal.add(this.btnPause);

		/*
		 * this.btnStop = new JButton("Stop");
		 * this.btnStop.addActionListener(new ActionListener() { public void
		 * actionPerformed(ActionEvent e) {
		 * TelaInicio.this.synchronizer.setSync("STOP"); } });
		 * this.btnStop.setBounds(200, 20, 70, 30);
		 * this.painelPrincipal.add(this.btnStop);
		 */
		this.btnSave = new JButton("Save");
		this.btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaInicio.this.synchronizer.setSync("SAVE");
			}
		});
		this.btnSave.setBounds(200, 20, 70, 30);
		this.painelPrincipal.add(this.btnSave);
		repaint();
	}

	private void setLabels() {
		this.isRunning = new JLabel("");
		this.isRunning.setText("OFF");
		this.isRunning.setBounds(290, 20, 40, 25);
		this.painelPrincipal.add(this.isRunning);
		repaint();
	}

	public void updateNumbersInfoOnScreen(ArrayList<Integer> numerosAdicionados) {
		for (Integer numeroAdicionado : numerosAdicionados) {
			for (NumberGUI numberGUI : numbers) {
				if (numeroAdicionado == numberGUI.getNumber()) {
					numberGUI.increment();
					break;
				}
			}
		}
	}

	public static void main(String[] args) {
		new TelaInicio();
	}

}
