package Test;

public class Testcase {
	private String user;
	private String email;
	
	public Testcase(String user, String email) {
		this.user = user;
		this.email = email;			
	}
	
	public String getUser()
	{
		return user;
	}
	public void setUser(String user)
	{
		this.user= user;
	}
	
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email= email;
	}
	
}
