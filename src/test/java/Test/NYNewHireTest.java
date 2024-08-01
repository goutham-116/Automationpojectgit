package Test;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import Pages.NYNewHirePage;
import Util.DBUtilPage;
import Util.ScreenshotPage;


public class NYNewHireTest {
	
	WebDriver driver;

	@Test
	public void NewHireScraping() throws Exception {
		ScreenshotPage scp = new ScreenshotPage(driver);
		NYNewHirePage NHP = new NYNewHirePage(driver);
		ArrayList<String> NewHireLogins = DBUtilPage.GetloginDetails();
		for (int i = 0; i < NewHireLogins.size(); i++) {
			System.out.println(NewHireLogins.get(i));
			String strUserDetails = NewHireLogins.get(i);
			String[] arrLoginDetails = strUserDetails.split(",");
			String gp_comapny = arrLoginDetails[0];
			String app_login = arrLoginDetails[1];
			String app_pwd = arrLoginDetails[2];
			System.out.println("*******************************" + gp_comapny + "," + app_login + "," + app_pwd
					+ "***********************");
			WriteTextUtil.log("*******************************" + gp_comapny + "," + app_login + "," + app_pwd
					+ "***********************");
			if (driver.getTitle().contains("Login")) {
				NHP.Login(app_login, app_pwd, gp_comapny);
				Thread.sleep(10000);
				
			
			}
			 if (driver.getTitle().contains("Change Password - New York New Hire")) {
					
					NHP.ChangePasswordAlert(gp_comapny);
					NHP.logout(gp_comapny);
	                 
				}
			      
			 else if (!driver.getTitle().contains("Login")) {
					NHP.NewHire(gp_comapny, "NY");
					NHP.logout(gp_comapny);
				}
				else if (driver.getTitle().contains("Login")) {
					System.out.println("Unable to Login the site initially");
					String LoginBody = "NY-HireSite not able to login Using below Credentials :" + "\n" + "Username:"
							+ app_login + "\n" + "Password:" + app_pwd;
					String imagepath = scp.takescreenshotFile();
					System.out.println("Attempted to login,but unable to login to NY-NewHire Site");
					WriteTextUtil.log("Attempted to login,but unable to login to NY-NewHire Site");
					SendExceptionMailPage.sendInLineImageEmail(ConstantsPage.from, ConstantsPage.to,
							ConstantsPage.Login, LoginBody, imagepath);
				}
			 else if (!driver.getTitle().contains("Login")) {
				System.out.println("NY-NewHire is Partially Down");
				WriteTextUtil.log("NY-NewHire is Partially Down");
				SendExceptionMailPage.sendEmail(ConstantsPage.from, ConstantsPage.to, ConstantsPage.SiteUnavailable,
						"NY-NewHire Site is Partially Down:" + "\n\n" + ConstantsPage.emailsignature);
		
				break;

			}
		}
	}
	

	@BeforeMethod
	public void Browserstart() {
		try {
			System.out.println("Searching for the driver");
			WriteTextUtil.log("Searching for the driver");
			String user_dir = System.getProperty("user.dir");
			System.setProperty("webdriver.chrome.driver", user_dir + "\\chromedriver.exe");
			driver = new ChromeDriver();
			driver.get("https://www.nynewhire.com/#/login");
			System.out.println("Site loaded successfully");
			WriteTextUtil.log("Site loaded Successfully");
			driver.manage().window().maximize();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Unable to get the driver");
			WriteTextUtil.log("Unable to get the driver");
			SendExceptionMailPage.sendEmail(ConstantsPage.from, ConstantsPage.to, ConstantsPage.subject,
					e.getMessage());
		}

	}

	@AfterMethod
	public void Browserclose() {
		driver.quit();
		driver.close();
		System.out.println("Browser Closed Successfully");
		WriteTextUtil.log("Browser Closed Successfully");
		
	}

}


