package StepDef;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class verifyclass {
	
	public static WebDriver driver;
	List<WebElement> department_list;
	List<String> Department_name=new ArrayList<String>();;
	List<String> Database_Department_name=new ArrayList<String>();;
	Connection conn = null;
    Statement stmt = null;
    ResultSet resultSet = null;
    
    int rowcount;
     int colcount;

@Given("user navigates to the url")
public void user_navigates_to_the_url() {
    // Write code here that turns the phrase above into concrete actions
	 WebDriverManager.chromedriver().setup();
     driver  =new ChromeDriver();
     driver.get("http://databasetesting.s3-website-us-west-2.amazonaws.com/");
     driver.manage().window().maximize();
     driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
    
}

@When("user selects the dropdown")
public void user_selects_the_dropdown() {
    // Write code here that turns the phrase above into concrete actions
   department_list=driver.findElements(By.xpath("//option"));
   
   for(int i=0;i<department_list.size();i++)
   {
	   Department_name.add(department_list.get(i).getText());
   
		  
   }
   Collections.sort(Department_name);
   for(String s:Department_name)
	   System.out.println(s);

}
@Then("takes data from the database")
public void takes_data_from_the_database() throws ClassNotFoundException, SQLException {
    // Write code here that turns the phrase above into concrete actions
	// Register JDBC driver (JDBC driver name and Database URL)
    Class.forName("com.mysql.jdbc.Driver");

    // Open a connection
    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hr","root", "Ranjani1234$@@@");

        // Execute a query
        stmt = conn.createStatement();
        resultSet = stmt.executeQuery("select department_name from hr.departments where location_id not in (select location_id from hr.locations where country_id='US');");
        ResultSetMetaData rsMetaData = resultSet.getMetaData();
         colcount = rsMetaData.getColumnCount();
        
     // Print the result untill all the records are printed
     // res.next() returns true if there is any next record else returns false
        
        
         Database_Department_name.add(resultSet.getString(colcount));
         for(String s:Database_Department_name)
        	 System.out.println(s +"*****");
         System.out.println("From database") ;
     while (resultSet.next())
     {
    	 
        
     System.out.println(resultSet.getString(1));
    

     rowcount++;
     }
}

@Then("assetts it with dropdowwn")
public void assetts_it_with_dropdowwn() {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
}


}
