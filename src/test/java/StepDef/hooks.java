package StepDef;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;

public class hooks {

	
	public static WebDriver driver;
	public static String url;
	static Connection conn = null;
    static Statement stmt = null;
    static ResultSet resultSet = null;
	@Before(order=0)
	public void beforetest() throws ClassNotFoundException, SQLException
	{
		WebDriverManager.chromedriver().setup();
	     driver  =new ChromeDriver();
	driver.manage().window().maximize();
	System.out.println("Test Started");
	Class.forName("com.mysql.jdbc.Driver");

    // Open a connection
    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hr","root", "Ranjani1234$@@@");
	
	}
	@After
	public void aftertest() throws SQLException
	{
		try
		{
		conn.close();
		}catch(Exception e)
		{
			System.out.println(e);
		}
		
	}

}
