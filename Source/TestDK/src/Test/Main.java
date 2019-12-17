package Test;

public class Main {
	 public static void main(String[] args) {
	 TestSignup testSignup = new TestSignup();
	 TestManager testManager= new TestManager();
	 
	 //Kiem thu tu dong tren firefox và chrome
	 //Note: Link is Local
	 System.setProperty("firefoxdriver.gecko.driver","D:/Webdriver/geckodriver.exe");
	 testSignup.startTest(); //Dang ky tai khoan moi tren firefox
	 
	 System.setProperty("chromedriver.driver","D:/Webdriver/chromedriver.exe");
	 testManager.startTestManager(); // Chuyen quyen cua tai khoan bang chrome
 }
}