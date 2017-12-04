package megasena.megasena_crawler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class PaginaNumSorteados {

	private WebDriver driver;
	private String url;
	private static int timeQuant = 10;
	
	public PaginaNumSorteados(String url){
		this.driver = new FirefoxDriver();
		this.driver.manage().window().maximize();
		this.implicitlyWait();
		this.url = url;
		this.driver.get(url);
	}
	
	public PaginaNumSorteados retroceder(){
		this.driver.findElement(By.xpath("//a[@title='Anterior']")).click();
		return this;
	}
	
	public PaginaNumSorteados avancar(){
		this.driver.findElement(By.name("Próximo")).click();
		return this;
	}
	
	public void implicitlyWait(){
		this.driver.manage().timeouts().pageLoadTimeout(timeQuant, TimeUnit.SECONDS);
	}
	
	public ArrayList<Integer> getNumbers(){
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		List<WebElement> listNumbers = this.driver.findElements(By.xpath("//div[@class='resultado-loteria']/ul/li"));
		for(WebElement number : listNumbers){
			numbers.add(Integer.parseInt(number.getText()));
			System.out.println("Numeros adicionados: " + numbers.size() + " | Valor: " + numbers.get(numbers.size() - 1));
		}
		return numbers;
	}
	
	public void rolarPaginaUP(){
		((JavascriptExecutor)this.driver).executeScript("window.scroll(0,-500);", "");
	}
	public boolean seMaisResultados(){
		String dadosConsurso = this.driver.findElement(By.xpath("//div[@id='resultados']/div/div/h2/span")).getText();
		return (Integer.parseInt(dadosConsurso.split(" ")[1]) > 1);
	}
}
