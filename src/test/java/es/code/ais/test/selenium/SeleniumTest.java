package es.code.ais.test.selenium;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

//import java.util.NoSuchElementException;
import org.openqa.selenium.NoSuchElementException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import es.urjc.code.daw.library.Application;
import io.github.bonigarcia.wdm.WebDriverManager;

@SpringBootTest(
classes = Application.class,
webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SeleniumTest {
	@LocalServerPort
	int port;
	
	WebDriver driver;
	
	@BeforeAll
	public static void setupClass() {
		WebDriverManager.chromedriver().setup();
	}
	@BeforeEach
	public void setup() {
		driver = new ChromeDriver();
	}
	@AfterEach 
	public void teardown() {
		if(driver != null) 
			driver.quit();
	}
	@Test
	void bookSaveSeleniumTest() {
		//given
		driver.get("http://localhost:"+this.port+"/");		
		
		//when
	    driver.findElement(By.xpath("//button[@onclick=\"location.href='/newbook'\"]")).click();
		driver.findElement(By.name("title")).sendKeys("Reina Roja");
		driver.findElement(By.name("description")).sendKeys("Misterio");
		driver.findElement(By.id("Save")).click();
		driver.findElement(By.xpath("//button[@onclick=\"location.href='/'\"]")).click();
			
		
		//then
		assertNotNull(driver.findElement(By.partialLinkText("Roja")));
		
		
	}
	
	@Test 
	void bookDeleteSeleniumTest() {
		String url;
		int i;
		String s;
		
		//given
		driver.get("http://localhost:"+this.port+"/");	
		
		//when
	    driver.findElement(By.xpath("//button[@onclick=\"location.href='/newbook'\"]")).click();
		driver.findElement(By.name("title")).sendKeys("el secreto de tus ojos");
		driver.findElement(By.name("description")).sendKeys("amor");
		driver.findElement(By.id("Save")).click();
		driver.findElement(By.xpath("//button[@onclick=\"location.href='/'\"]")).click();
		
		//then
		assertNotNull(driver.findElement(By.partialLinkText("ojos")));
		
		driver.findElement(By.linkText("el secreto de tus ojos")).click();
		
		url = driver.getCurrentUrl();
		i = Character.getNumericValue(url.charAt(url.length()-1));
		s = "//button[@onclick=\"location.href='/removebook/" + i + "'\"]";
		driver.findElement(By.xpath(s)).click();
		
		By by = By.partialLinkText("ojos");
		assertThrows(NoSuchElementException.class, ()->{
			driver.findElement(by);
		});
		
			
	}
	
}