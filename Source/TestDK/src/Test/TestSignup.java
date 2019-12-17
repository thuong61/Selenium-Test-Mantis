package Test;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;


public class TestSignup {
	
private ArrayList<Testcase> list = new ArrayList<>();
	
	private void loadTestcase() {
		//Doc du lieu tu file txt
		File filetest= new File("D:/KCPM-2019/Cá nhân/Testcase.txt");
		try (Scanner sc= new Scanner(filetest)){
			
			while	(sc.hasNext())
			{
				Testcase user = new Testcase(null, null);
				user.setUser (sc.nextLine());
				user.setEmail(sc.nextLine());
				list.add(user);
			}
			
		} catch(Exception e) {
			System.out.println("loi doc file" +e.getMessage());
		};
	}
	
	private void writeResult(final String result ) {
		File file = new File("D:/KCPM-2019/Cá nhân/ResultTest.txt");
		try {
			PrintWriter printWriter = new PrintWriter(file);
			printWriter.println(result);
			printWriter.close();
		}
		catch (Exception e){
			System.out.println("loi ghi file" + e.getMessage());
		}
	}
	
	public void startTest() {
		loadTestcase();
		WebDriver webDriver;
		
		WebElement webElement;
		String result ="";
		
		for (int i = 0, lenght = list.size(); i< lenght; i++) {
			 webDriver = new FirefoxDriver();
			try {
				webDriver.get("http://localhost/mantis/login_page.php");
				webElement = webDriver.findElement(By.cssSelector("a.back-to-login-link.pull-left"));
				Thread.sleep(1000);
				webElement.click();
				Thread.sleep(1000);
				webElement= webDriver.findElement(By.id("username"));
				webElement.sendKeys(list.get(i).getUser());
				Thread.sleep(1000);
				webElement= webDriver.findElement(By.id("email-field"));
				webElement.sendKeys(list.get(i).getEmail());
				Thread.sleep(1000);
				webElement= webDriver.findElement(By.cssSelector("input.width-40.pull-right.btn.btn-success.btn-inverse.bigger-110"));
				webElement.click();
				Thread.sleep(1000);
				webElement = webDriver.findElement(By.cssSelector("div.alert.alert-danger"));
			
				result += "testcase" +(i+1) + ":\t" +webElement.getText() +"\n";
			} 
			catch(Exception e){
				result += "testcase" +(i+1) + ":\t" + "Dang ky thanh cong!\n";
			};
			webDriver.close();
		}
		writeResult (result);
	}
	
}