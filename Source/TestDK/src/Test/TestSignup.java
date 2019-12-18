package Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import java.util.Iterator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;


public class TestSignup {
	
private ArrayList<Testcase> list = new ArrayList<>();
	
//Doc testcase tu file txt
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
	
// Doc du lieu tu file .docx	
	private void loadTestcaseDocx() {
		try {
			FileInputStream filetest= new FileInputStream("D:/KCPM-2019/Cá nhân/Testcase.docx");
		      XWPFDocument doc = new XWPFDocument(OPCPackage.open(filetest));
		      List<XWPFParagraph> paragraphList = doc.getParagraphs();
		      for (XWPFParagraph paragraph : paragraphList) {
		    	  Testcase user = new Testcase(null, null);
		    	  user.setUser(paragraph.getText());
		    	  user.setEmail(paragraph.getText());
		        }
		      doc.close();
			
		} catch(Exception e) {
			System.out.println("loi doc file" +e.getMessage());
		};
	}
	
	// Doc du lieu file excel
	private void loadTestcaseExcel()
	{
		try {
			FileInputStream filetest = new FileInputStream(new File("\"D:/KCPM-2019/Cá nhân/Testcase.xls"));
			 HSSFWorkbook workbook = new HSSFWorkbook(filetest);
			 HSSFSheet sheet = workbook.getSheetAt(0);
			 Iterator<Row> rowIterator = sheet.iterator();
			 
			 while (rowIterator.hasNext()) {
		           Row row = rowIterator.next();
		           Iterator<Cell> cellIterator = row.cellIterator();
		           int dem= 0;
		           Testcase user = new Testcase(null, null);
		        	   while (cellIterator.hasNext()) {
		                   Cell cell = cellIterator.next();
		               
		                   CellType cellType = cell.getCellTypeEnum();
		     
		                   switch (cellType) {
		                   case _NONE:
		                       if(dem == 0)
		                    	   user.setUser("");
		                       else
		                    	   user.setEmail("");
		                       dem++;
		                       break;
		                   case BOOLEAN:
		                	   if(dem == 0)
		                    	   user.setUser(Boolean.toString(cell.getBooleanCellValue()));
		                       else
		                    	   user.setEmail(Boolean.toString(cell.getBooleanCellValue()));
		                       dem++;
		                       break;
		                   case BLANK:
		                	   if(dem == 0)
		                    	   user.setUser("");
		                       else
		                    	   user.setEmail("");
		                       dem++;
		                       break;
		                   case FORMULA:
		           
		                	   if(dem == 0)
		                    	   user.setUser("####");
		                       else
		                    	   user.setEmail("####");
		                       dem++;
		                       break;
		                   case NUMERIC:
		                	   if(dem == 0)
		                    	   user.setUser(Double.toString(cell.getNumericCellValue()));
		                       else
		                    	   user.setEmail(Double.toString(cell.getNumericCellValue()));
		                       dem++;
		                       break;
		                   case STRING:
		                	   if(dem == 0)
		                    	   user.setUser(cell.getStringCellValue());
		                       else
		                    	   user.setEmail(cell.getStringCellValue());
		                       dem++;
		                       break;
		                   case ERROR:
		                   if(dem == 0)
	                    	   user.setUser("!");
	                       else
	                    	   user.setEmail("!");
	                       dem++;
		                       break;
		                   }
		     
		               }
		           
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