package Test;


import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class TestManager  {
private ArrayList<Testcase> list = new ArrayList<>();
	
	private void loadTestCaseAdmin() {
		
		
		File filetest= new File("D:/KCPM-2019/Cá nhân/TestcaseManager.txt");
		try (Scanner sc= new Scanner(filetest)){
			
			while	(sc.hasNext())
			{
				Testcase user = new Testcase(null, null);
				
				user.setUser(sc.nextLine());
				
				list.add(user);
			}
			
		} catch(Exception e) {
			System.out.println("loi doc file" +e.getMessage());
		};
	
	}
	private void writeResultAdmin(String result) {
		File file = new File("D:/KCPM-2019/Cá nhân/ResultTestManager.txt");
		
		try {
			PrintWriter printWriter= new PrintWriter(file);
			printWriter.println(result);
			printWriter.close();
		} catch(Exception e) {
			System.out.println("loi doc file" +e.getMessage());
		};
	}
	public void startTestManager()
	{
		WebDriver webDriver;
		WebElement webElement;
		String result ="";
		
		
		loadTestCaseAdmin();
		for(int i=0, length= list.size();i<length;i++)
		{
			String[] temp= list.get(i).getUser().split("_");
			int number= Integer.parseInt(temp[1]);
			webDriver=   new ChromeDriver();
			try {
				
				webDriver.get("http://localhost/mantis/login_page.php");
				webElement = webDriver.findElement(By.id("username"));
				webElement.sendKeys("administrator");
				Thread.sleep(1000);
				
				webElement = webDriver.findElement(By.cssSelector("input.width-40.pull-right.btn.btn-success.btn-inverse.bigger-110"));
				webElement.click();
				Thread.sleep(1000);
				
				webElement = webDriver.findElement(By.id("password"));
				webElement.sendKeys("root");
				Thread.sleep(1000);
				
				webElement = webDriver.findElement(By.cssSelector("input.width-40.pull-right.btn.btn-success.btn-inverse.bigger-110"));
				webElement.click();
				Thread.sleep(1000);

				webDriver.get("http://localhost/mantis/manage_user_page.php");
				Thread.sleep(1000);
				
			
				
				webElement = webDriver.findElement(By.id("username"));
				webElement.sendKeys(list.get(i).getUser());
				Thread.sleep(1000);
				
				
				webElement.sendKeys(Keys.ENTER);
				Thread.sleep(1000);
				
				Select role= new Select(webDriver.findElement(By.id("edit-access-level")));
				if(number%2==1) 
				{
					
					role.selectByValue("55");
				}
				else {
					
					role.selectByValue("70");
				}
				Thread.sleep(1000);
				webElement = webDriver.findElement(By.cssSelector("input.btn.btn-primary.btn-white.btn-round"));
				webElement.click();
				Thread.sleep(1000);
				result += "testcase" +(i+1) + ":\t" + "Chuyen quyen thanh cong!\n";
			} 
			catch(Exception e){
				System.out.println("loi" +e.getMessage());
			};
			webDriver.close();
		}
		writeResultAdmin(result);
		
}
}

