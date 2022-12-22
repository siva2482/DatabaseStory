package StepDef;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	
	public  WebDriver driver;
	List<WebElement> department_list;
	List<String> lcount;
	List<String> lcity;
	List<String> employee_table;
	List<String> database_employee;
	List<String> Department_name=new ArrayList<String>();;
	List<String> Database_Department_name=new ArrayList<String>();
	Map<String,String> Database_City_data=new HashMap<String,String>();
	Map<String,String> Table_City_data=new HashMap<String,String>();
	
	Connection conn = null;
    Statement stmt =null;
    ResultSet resultSet = null;
	public verifyclass()
	{
		driver=hooks.driver;
	conn=hooks.conn;
	}
    int rowcount;
     int colcount;

@Given("user navigates to the url")
public void user_navigates_to_the_url() {
    // Write code here that turns the phrase above into concrete actions
	 
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
   
	
	 
        // Execute a query
        stmt = conn.createStatement();
        resultSet = stmt.executeQuery("select department_name from hr.departments where location_id not in (select location_id from hr.locations where country_id='US');");
        ResultSetMetaData rsMetaData = resultSet.getMetaData();
         colcount = rsMetaData.getColumnCount();
        
     // Print the result untill all the records are printed
     // res.next() returns true if there is any next record else returns false
        
        
        /* Database_Department_name.add(resultSet.getString(colcount));
         for(String s:Database_Department_name)
        	 System.out.println(s +"*****");:*/
         System.out.println("From database") ;
     while (resultSet.next())
     {
    	 
        
     System.out.println(resultSet.getString(1));
     Database_Department_name.add(resultSet.getString(1));

     rowcount++;
     }
     
     System.out.println( Database_Department_name);
}

@Then("assetts it with dropdowwn")
public void assetts_it_with_dropdowwn() throws SQLException {
    // Write code here that turns the phrase above into concrete actions
    if(Department_name.size()==Database_Department_name.size())
    {
    	if(Department_name.equals(Database_Department_name))
    		System.out.println("Data is correctly populated from hrschema");
    	
    }
    else
    	System.out.println("Data not correctly populated from hrschema");
    
}
@When("user select the department from cities")
public void user_select_the_department_from_cities() throws SQLException {
    // Write code here that turns the phrase above into concrete actions
	
	 lcity=new ArrayList<String>();
	List<WebElement> listcount=driver.findElements(By.xpath("//table[@class='adap-table']//tbody//tr//td[1]"));
	List<WebElement> listcity=driver.findElements(By.xpath("//table[@class='adap-table']//tbody//tr//td[2]"));
	for(WebElement w:listcity)
	{
		String s1=w.getText();
		lcity.add(s1);
	}
	 Collections.sort(lcity);
	System.out.println(lcity);
}

@Then("user takes data from database")
public void user_takes_data_from_database() throws SQLException {
    // Write code here that turns the phrase above into concrete actions
	stmt = conn.createStatement();
    resultSet = stmt.executeQuery("select count(d.department_name),l.city from hr.departments d join hr.locations l using(location_id) group by l.city;");
    ResultSetMetaData rsMetaData = resultSet.getMetaData();
     colcount = rsMetaData.getColumnCount();
     lcount=new ArrayList<String>();
    
     System.out.println("From database") ;
       	 
     while (resultSet.next())
     {    	 
        
     //System.out.println(resultSet.getString(1));
     //System.out.println(resultSet.getString(2));
     lcount.add(resultSet.getString(2));

     rowcount++;
     }
     Collections.sort(lcount);
    System.out.print(lcount); 
        
     //System.out.println(resultSet.getString(1));
     //System.out.println(resultSet.getString(2));
    	 
    
   
}



@Then("assertit with the table")
public void assertit_with_the_table() {
    // Write code here that turns the phrase above into concrete actions
	if(lcity.equals(lcount))
	{
		System.out.println("Cities are same in both table and database");
	}
	else
	{
		for(int i=0;i<lcount.size();i++)
		{
			if(!(lcity.contains(lcount.get(i))))
				
			
				System.out.println("THe city " +lcount.get(i) +"  :is missing in table");
		}
	}
    
}
@When("user select employee with thrird largest salary")
public void user_select_employee_with_thrird_largest_salary() {
    // Write code here that turns the phrase above into concrete actions
    List<WebElement> emplist=driver.findElements(By.xpath("//table[@class='salary_employee']//tbody//tr//td"));
    employee_table=new ArrayList<String>();
    for(WebElement w:emplist)
    {
    	String s=w.getText();
    	String[] s1=s.split(":");
    	employee_table.add(s1[1]);
    }
   System.out.println(employee_table); 
}

@Then("user takes employee from database")
public void user_takes_employee_from_database() throws SQLException {
    // Write code here that turns the phrase above into concrete actions
	stmt = conn.createStatement();
    resultSet = stmt.executeQuery("Select   Employee_id,concat(First_name,\" \" ,Last_name ) as Name, round(salary,0) as salary from hr.employees where salary = (select salary from  (select distinct salary from hr.employees order by  salary desc limit 3) x order by salary limit 1);");
    ResultSetMetaData rsMetaData = resultSet.getMetaData();
     colcount = rsMetaData.getColumnCount();
     database_employee=new ArrayList<String>();
     while(resultSet.next())
     {
     for(int i=1;i<=colcount;i++)
	 {
 database_employee.add(resultSet.getString(i));
	 }
     rowcount++;
     }  
     System.out.println(database_employee);
}

@Then("assert it with the web table")
public void assert_it_with_the_web_table() {
	int count=0;

    // Write code here that turns the phrase above into concrete actions
	for(int i=0;i<employee_table.size();i++)
	{
 if(employee_table.contains(database_employee.get(i))) 
 
	  count++;
 //System.out.println(count);
	 
}
	if(count==colcount)
		System.out.println("Emplyee data verified");


}

}
