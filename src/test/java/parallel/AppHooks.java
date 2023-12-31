package parallel;

import java.io.FileInputStream;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import qa.ConfigReader;
import qa.DriverFactory;

public class AppHooks {
	ConfigReader cr;
	
	DriverFactory driverfactory;
	
	WebDriver driver;
	
	@Before
	public void launchBrowser()
	{
		 cr = new ConfigReader();
	
	String browser_maven=System.getProperty("browser");
		 
	String browsername = cr.readProp("browser");
	
	driverfactory = new DriverFactory();
	
	String browser = browser_maven!=null ? browser_maven : browsername ;
	
	
	driver = driverfactory.initBrowser(browser);
	
	driver.manage().window().maximize();
	
	}
	
	
	@After(order = 1)
	public void quitBrowser()
	{
		driver.quit();
	}
	
	
	@After(order = 2)
	public void tearDown(Scenario scenario)
	{
		boolean isScenarioFailed = scenario.isFailed();
		
		if(isScenarioFailed)
		{
			String scenarioname = scenario.getName();
			
			String screenshotname = scenarioname.replaceAll(" ", "_");
			
			TakesScreenshot ts = (TakesScreenshot)driver;
			
			byte[] source = ts.getScreenshotAs(OutputType.BYTES);
			
			scenario.attach(source, "image/png", screenshotname);
		}
	}

}
