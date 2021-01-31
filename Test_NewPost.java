package com.fca.calidad.test;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class Test_NewPost {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
	  //C:\Users\jcnt9\OneDrive\Documentos\Código\chromedriver.exe
	System.setProperty("webdriver.chrome.driver", "C:\\Users\\jcnt9\\OneDrive\\Documentos\\Código\\chromedriver.exe");
    driver = new ChromeDriver();
    baseUrl = "https://www.google.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    
  }
  
//PRUEBAS PARA AGREGAR UN NUEVO POST.
//**Aquí utilizamos la sección de "Just now" para ver si nuestra publicación de verdad se creó inmediatamente**
  @Test
  public void testAgregar() throws Exception {
    driver.get("https://www.jmkleger.com/demo/ajax_crud#");
    driver.findElement(By.xpath("//a/li")).click();
    driver.findElement(By.id("title_add")).click();
    driver.findElement(By.id("title_add")).clear();
    driver.findElement(By.id("title_add")).sendKeys("Nuevo post");
    driver.findElement(By.id("content_add")).clear();
    driver.findElement(By.id("content_add")).sendKeys("nuevo post");
    driver.findElement(By.xpath("(//button[@type='button'])[3]")).click();
    driver.findElement(By.xpath("//html/body/div[2]/div/div[2]/div/div[1]/ul/a/li")).click();
    // Warning: assertTextPresent may require manual changes
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*Just now![\\s\\S]*$"));
  }

  //PRUEBAS PARA MODIFICAR UNA PUBLICACIÓN.
  //**Buscamos la notificación del texto actualizado, así que esperamos 5segundos**
  
	@Test
  public void testModificar() throws Exception {
    driver.get("https://www.jmkleger.com/demo/ajax_crud#");
    driver.findElement(By.xpath("//table[@id='postTable']/tbody/tr/td[6]/button[2]")).click();
    driver.findElement(By.xpath("//div[@id='editModal']/div/div/div[2]/form/div[3]")).click();
    driver.findElement(By.id("content_edit")).clear();
    driver.findElement(By.id("content_edit")).sendKeys("nuevo texto para el contenido");
    driver.findElement(By.xpath("(//button[@type='button'])[8]")).click();
    // Warning: assertTextPresent may require manual changes
    pause(5000);
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*Successfully updated Post![\\s\\S]*$"));
  }
	//PRUEBAS PARA ELIMINAR UNA PUBLICACION
	@Test
	  public void testEliminar() throws Exception {
	    driver.get("https://www.jmkleger.com/demo/ajax_crud#");
	    driver.findElement(By.xpath("//table[@id='postTable']/tbody/tr/td[6]/button[3]")).click();
	    driver.findElement(By.xpath("(//button[@type='button'])[11]")).click();
	    // Warning: assertTextPresent may require manual changes
	    pause(5000);
	    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*Successfully deleted Post![\\s\\S]*$"));
	  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
  private void pause(long mils) {
	  try {
		  Thread.sleep(mils);
	  }catch(Exception e){
		  e.printStackTrace();
	  }
  }


}
