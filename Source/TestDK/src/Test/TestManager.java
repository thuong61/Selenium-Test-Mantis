package Test;


import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
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
	
// Load testcase bang file docx
private void loadTestcaseDoxManager() {
		
		
		try {
			FileInputStream filetest= new FileInputStream("D:/KCPM-2019/Cá nhân/TestcaseManager.docx");
		      XWPFDocument doc = new XWPFDocument(OPCPackage.open(filetest));
		      List<XWPFParagraph> paragraphList = doc.getParagraphs();
		      for (XWPFParagraph paragraph : paragraphList) {
		    	  Testcase user = new Testcase(null, null);
		    	  user.setUser(paragraph.getText());
		        }
		      doc.close();
		} catch(Exception e) {
			System.out.println("loi doc file" +e.getMessage());
		};
}
	
	// Load testcase bang file excel 
private void loadTestcaseExcelManager()
{
	try {
		FileInputStream filetest = new FileInputStream(new File("\"D:/KCPM-2019/Cá nhân/TestcaseManager.xls"));
		 HSSFWorkbook workbook = new HSSFWorkbook(filetest);
		 HSSFSheet sheet = workbook.getSheetAt(0);
		 Iterator<Row> rowIterator = sheet.iterator();
		 
		 while (rowIterator.hasNext()) {
	           Row row = rowIterator.next();
	           Iterator<Cell> cellIterator = row.cellIterator();
	           Testcase user = new Testcase(null, null);
	        	   while (cellIterator.hasNext()) {
	                   Cell cell = cellIterator.next();
	               
	                   CellType cellType = cell.getCellTypeEnum();
	     
	                   switch (cellType) {
	                   case _NONE:
	                    	   user.setUser("");
	                     
	                       break;
	                   case BOOLEAN:
	                    	   user.setUser(Boolean.toString(cell.getBooleanCellValue()));
	                      
	                       break;
	                   case BLANK:
	                    	   user.setUser("");
	                       break;
	                   case FORMULA:
	                    	   user.setUser("####");
	                       break;
	                   case NUMERIC:
	                    	   user.setUser(Double.toString(cell.getNumericCellValue()));
	                      
	                       break;
	                   case STRING:
	                    	   user.setUser(cell.getStringCellValue());
	                      
	                       break;
	                   case ERROR:
                    	   user.setUser("!");
                    
	                       break;
	                   }
	     
	               }
	           
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

