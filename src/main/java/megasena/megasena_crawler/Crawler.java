package megasena.megasena_crawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import gui.Synchronizer;
import gui.TelaInicio;

public class Crawler {

	private String url = "http://loterias.caixa.gov.br/wps/portal/loterias/landing/megasena";
	private Analyzer analyzer;
	private File log;
	private Process process = null;
	private String path;
	private Synchronizer synchronizer = new Synchronizer();
	private boolean running;
	private boolean paused;
	private boolean stoped;
	private boolean alreadyStarted;
	private Runnable logWriter;
	private JFrame telaInicio;
	private Runnable startListeningCommands;
	private Runnable stopListeningCommands;
	private Runnable saveListeningCommands;

	public Crawler() {
		this.analyzer = new Analyzer();
		this.running = false;
		this.stoped = false;
		this.paused = false;
		createLogFile();
		this.logWriter = new LogWriter(this.log);

		startListeningCommands = new Runnable() {
			public void run() {
				while (true) {
					try {
						synchronized (Crawler.this.synchronizer.getSync()) {
							Crawler.this.synchronizer.getSync().wait();
						}
						if (Crawler.this.synchronizer.getSync().getAction().equals("START")) {
							Crawler.this.running = true;
							Crawler.this.stoped = false;
							synchronized(this){
								this.notify();
							}
						} else {
							Thread.sleep(0);
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		
		stopListeningCommands = new Runnable() {

			public void run() {
				while (true) {
					synchronized (Crawler.this.synchronizer.getSync()) {
						try {
							Crawler.this.synchronizer.getSync().wait();
							if (Crawler.this.synchronizer.getSync().getAction().equals("STOP")) {
								Crawler.this.stoped = true;
								Crawler.this.running = false;
							} else {
								Thread.sleep(0);
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		};
		
		saveListeningCommands = new Runnable() {
			public void run() {
				while (true) {
					synchronized (Crawler.this.synchronizer.getSync()) {
						try {
							Crawler.this.synchronizer.getSync().wait();
							if (Crawler.this.synchronizer.getSync().getAction().equals("SAVE")) {
								if (Crawler.this.logWriter != null)
									new Thread(((LogWriter) Crawler.this.logWriter).setListNumSorteados(
											Crawler.this.analyzer.getNumerosSorteados()), "LogWriter").start();
							} else {
								Thread.sleep(0);
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		};
	}

	public void start() {
		this.telaInicio = new TelaInicio();
		startListeners(500);
		PaginaNumSorteados paginaNumSorteados = null;
		synchronized (this.startListeningCommands) {
			try {
				this.startListeningCommands.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
		}
		JOptionPane.showMessageDialog(Crawler.this.telaInicio, "running!");
		while (!stoped) {
			if (!alreadyStarted) {
				paginaNumSorteados = new PaginaNumSorteados(url);
				this.alreadyStarted = true;
			}
			while (!paused && !stoped) {
				while (paginaNumSorteados.seMaisResultados()) {
					this.analyzer.addReceivedNumbers(paginaNumSorteados.getNumbers());
					paginaNumSorteados.rolarPaginaUP();
					paginaNumSorteados.retroceder();
				}
			}
		}
	}

	public static void main(String[] args) {
		Crawler crawler = new Crawler();
		crawler.start();

	}

	public void startListeners(long time){
		new ThreadListeners(this.saveListeningCommands, "SAVE BUTTON LISTENING...", time).start();
		new ThreadListeners(this.startListeningCommands, "START BUTTON LISTENING...", time + 100).start();
		new ThreadListeners(this.stopListeningCommands, "STOP BUTTON LISTENING...", time + 200).start();
	}
	
	private boolean createLogFile() {
		try {
			String temp;
			Process process = Runtime.getRuntime().exec("cmd /c echo %homepath%");
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
			while ((temp = br.readLine()) != null) {
				this.path = "C:" + temp;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.log = new File(this.path + "\\Desktop\\mega_sena_log.txt");
		if (!this.log.exists())
			try {
				this.log.createNewFile();
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return false;
	}

	public class LogWriter implements Runnable {

		private ArrayList<NumSorteado> numerosSorteados;
		private FileWriter fileWriter;
		int lastNumSorteadoIndex = 0;

		public LogWriter(File logFile) {
			try {
				this.fileWriter = new FileWriter(logFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public LogWriter setListNumSorteados(ArrayList<NumSorteado> numerosSorteados) {
			this.numerosSorteados = numerosSorteados;
			return this;
		}

		public void run() {
			try {
				for (; this.lastNumSorteadoIndex < numerosSorteados.size(); this.lastNumSorteadoIndex++) {
					this.fileWriter.append(numerosSorteados.get(lastNumSorteadoIndex).getNumSorteadoInfo());
					this.fileWriter.flush();
				}
				JOptionPane.showMessageDialog(Crawler.this.telaInicio, "saved!");
				// this.fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void stop() {
			try {
				this.fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private class ThreadListeners extends Thread{

		private Runnable listener;
		private long timeDelay;
		private String threadName;
		
		public ThreadListeners(Runnable listener, String threadName, long timeDelay){
			this.listener = listener;
			this.timeDelay = timeDelay;
			this.threadName = threadName;
		}
		
		public ThreadListeners(Runnable listener, long timeDelay){
			this.listener = listener;
			this.timeDelay = timeDelay;
			this.threadName = "";
		}
		
		public void run() {
			try {
				Thread.sleep(this.timeDelay);
				if(this.threadName.length() != 0)
					new Thread(this.listener, this.threadName).start();
				new Thread(this.listener).start();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
}