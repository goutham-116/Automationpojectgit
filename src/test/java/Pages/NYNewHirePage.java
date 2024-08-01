package Pages;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.sun.java_cup.internal.runtime.Symbol;

import Util.ConstantsPage;
import Util.DBUtilPage;
import Util.ScreenshotPage;
import Util.SendExceptionMailPage;
import Util.WriteTextUtil;



public class NYNewHirePage 
{
	WebDriver driver;

	@FindBy(id = "ein")
	WebElement FEINnumber;

	@FindBy(id = "password")
	WebElement password;

	@FindBy(css = "button.btn.btn-primary.w-100")
	WebElement signbtn;

	@FindBy(css = "button.btn.btn-primary.mt-1")
	WebElement NewHiredEmpbtn;

	@FindBy(id = "ssn")
	WebElement ssntxtbox;

	@FindBy(id = "lastName")
	WebElement lastnametxtbox;

	@FindBy(id = "firstName")
	WebElement firstnametxtbox;

	@FindBy(id = "middleInitial")
	WebElement middleinitialtxtbox;

	@FindBy(id = "address1")
	WebElement addresstxtbox;

	@FindBy(id = "city1")
	WebElement citytxtbox;

	@FindBy(id = "zip1")
	WebElement ziptxbox;

	@FindBy(id = "hireDate")
	WebElement hiredatetxtbox;

	@FindBy(css = "span.fa.fa-angle-left")
	WebElement leftarrowbtn;

	@FindBy(css = "span.fa.fa-angle-right")
	WebElement rightarrowbtn;

	@FindBy(css = "i.fa.fa-user.fa-2x")
	WebElement tempdown;

	@FindBy(id = "inlineRadio1")
	WebElement yesradiobtn;

	@FindBy(id = "inlineRadio2")
	WebElement Noradiobtn;

	@FindBy(xpath = "//*[@id='wrapper']/section/div/div/app-home/p-dialog[9]/div/div[2]/section/form/div[12]/button[2]")
	WebElement submitbtn;

	@FindBy(xpath = "//*[@id='wrapper']/section/div/div/app-home/p-dialog[8]/div/div[2]/section/p")
	WebElement successmsg;

	@FindBy(xpath = "//*[@id='wrapper']/section/div/div/app-home/p-dialog[8]/div/div[2]/section/div/button")
	WebElement continuebtn;

	@FindBy(css = "a.ng-tns-c4-7.ui-dialog-titlebar-icon.ui-dialog-titlebar-close.ui-corner-all.ng-star-inserted")
	WebElement Popupclosebtn;

	@FindBy(linkText = "Logout")
	WebElement logout;

	@FindBy(css = "div.ui-dialog-content.ui-widget-content")
	WebElement popup;
	
/*	@FindBy(xpath="//*[@id=\"wrapper\"]/section/div/div/change-password/div/div[1]/h3")
	WebElement changepasswordmsg; */
	
	
	@FindBy(xpath="//*[@id=\"wrapper\"]/section/div/div/change-password/div/div[2]/div[2]")
	WebElement changepasswordmsg;
	
	

	public NYNewHirePage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
	}

	public void Login(String FEIN, String Password, String gp_company) throws Exception {
		try {
			WebDriverWait wt = new WebDriverWait(driver, 60);
			driver.navigate().refresh();
			wt.until(ExpectedConditions.visibilityOf(FEINnumber));
			Thread.sleep(5000);
			FEINnumber.sendKeys(FEIN);
			Thread.sleep(5000);
			if (FEINnumber.getText().isEmpty()) {
				FEINnumber.sendKeys(FEIN);
			}
			Thread.sleep(5000);
			password.sendKeys(Password);
			Thread.sleep(5000);
			signbtn.click();
			System.out.println("Login Successfully");
		//	WriteTextUtil.log("Login Successfully");
			
		  //  DBUtilPage.NewHireAuditDetails("Login Successfully", gp_company, "NY");
		    
		    Thread.sleep(3000);
		    	

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Exception found at login(): " + e.getMessage());
			WriteTextUtil.log("Exception found at login(): " + e.getMessage());
			SendExceptionMailPage.sendEmail(ConstantsPage.from, ConstantsPage.to, ConstantsPage.subject,
					"Exception at login():" + e.getMessage() + "\n\n" + ConstantsPage.emailsignature);
		}

	
		
		}
	

	public void NewHire(String gp_company, String state) throws Exception {
		// DBUtilPage dp = new DBUtilPage();
		
		ArrayList<String> allUsersList = DBUtilPage.NYHireData(gp_company, state);
		ScreenshotPage scp = new ScreenshotPage(driver);

		WebDriverWait wt = new WebDriverWait(driver, 60);
		try {
			wt.until(ExpectedConditions.visibilityOf(NewHiredEmpbtn));
			if (allUsersList.size() > 0) {
				System.out.println("No of records to scrape:" + allUsersList.size());
				WriteTextUtil.log("No of records to scrape:" + allUsersList.size());
				DBUtilPage.NewHireAuditDetails("Total no of candidates to submit:" + allUsersList.size(), gp_company,
						"NY");
				for (int i = 0; i < allUsersList.size(); i++) {
					System.out
							.println("***************************Row Number:  " + i + "*****************************");
					WriteTextUtil.log("***************************Row Number:  " + i + "*****************************");
					System.out.println(allUsersList.get(i));
					String strUserDetails = allUsersList.get(i);
					String[] arrUserDetails = strUserDetails.split(",");
					String cand_id = arrUserDetails[0];
					String ssn = arrUserDetails[1];
					String lname = arrUserDetails[2];
					String fname = arrUserDetails[3];
					String middleinitial = arrUserDetails[4];
					String address = arrUserDetails[5];
					String city = arrUserDetails[6];
					String State = arrUserDetails[7];
					String zip = arrUserDetails[8];
					String div = arrUserDetails[9];
					String hiredate = arrUserDetails[10];
					String HealthInsuranceStatus = arrUserDetails[11];

					System.out.println("cand_id:" + cand_id + "ssn:" + ssn + " lname : " + lname + " fname: " + fname
							+ "middle initial:" + middleinitial + " address: " + address + " city: " + city + " state: "
							+ State + " zip: " + zip + " hiredate: " + hiredate + "HealthInsurance:"
							+ HealthInsuranceStatus);
					WriteTextUtil.log("cand_id:" + cand_id + "ssn:" + ssn + " lname : " + lname + " fname: " + fname
							+ "middle initial:" + middleinitial + " address: " + address + " city: " + city + " state: "
							+ State + " zip: " + zip + " hiredate: " + hiredate + "HealthInsurance:"
							+ HealthInsuranceStatus);
					System.out.println("SSN :" + ssn);
					WriteTextUtil.log("SSN :" + ssn);
					String ssn_With_out = arrUserDetails[1].replaceAll("-", "").trim();
				    String NewAddress= arrUserDetails[5].replaceAll(",", "").trim();
					
					System.out.println("New SSN :" + ssn_With_out);
					WriteTextUtil.log("New SSN :" + ssn_With_out);
					Thread.sleep(5000);
					NewHiredEmpbtn.click();
					System.out.println("NewHiredEmployee button clicked successfully");
					WriteTextUtil.log("NewHiredEmployee button clicked successfully");
					wt.until(ExpectedConditions.visibilityOf(ssntxtbox));
					Thread.sleep(5000);

					ssntxtbox.sendKeys(ssn_With_out);
					if (ssntxtbox.getText().isEmpty()) {
						Thread.sleep(3000);
						ssntxtbox.sendKeys(ssn_With_out);
					}
					Thread.sleep(3000);
					lastnametxtbox.sendKeys(lname);
					Thread.sleep(2000);
					firstnametxtbox.sendKeys(fname);
					Thread.sleep(2000);
					middleinitialtxtbox.sendKeys(middleinitial);
					Thread.sleep(2000);
					addresstxtbox.sendKeys(NewAddress);
					
				//	addresstxtbox.sendKeys(NewAddress);
					Thread.sleep(2000);
					citytxtbox.sendKeys(city);
					Thread.sleep(2000);
					Select st = new Select(driver.findElement(By.id("state2")));
					Thread.sleep(2000);
					if (State.equals("ny")) {
						String stateUpper = State.toUpperCase();
						st.selectByValue(stateUpper);
					} else if (State.equals("Ny")) {
						String stateUpper = State.toUpperCase();
						st.selectByValue(stateUpper);

					} else {
						
						st.selectByValue(State);
					}

					Thread.sleep(2000);
					ziptxbox.sendKeys(zip);
					Thread.sleep(2000);
					hiredatetxtbox.click();
					Thread.sleep(2000);
					cal(hiredate);
					Thread.sleep(3000);
					if (HealthInsuranceStatus.equals("NO")) {
						Noradiobtn.click();
						System.out.println("No Option is selected for Health Insurance");
						System.out.println("No Option is selected for Health Insurance");
					} else if (HealthInsuranceStatus.equals("YES")) {
						yesradiobtn.click();
						System.out.println("Yes Option is selected for Health Insurance");
						System.out.println("Yes Option is selected for Health Insurance");
					}
					Actions actions = new Actions(driver);
					actions.moveToElement(popup).keyDown(Keys.CONTROL).sendKeys(Keys.END).perform();
					System.out.println("Scrolled");
					actions.moveToElement(submitbtn);
					System.out.println("moved");
					submitbtn.click();
					System.out.println("Submit button clicked successfully");
					WriteTextUtil.log("Submit button clicked successfully");
					Thread.sleep(15000);
					if (successmsg.isDisplayed()) {
						String Successmsg = successmsg.getText();
						System.out.println(Successmsg);
						WriteTextUtil.log(Successmsg);
						Thread.sleep(2000);
						continuebtn.click();
						System.out.println("Details are  Submitted  for the candidate:" + lname + " " + fname + " "
								+ "cand_id:" + cand_id);
						WriteTextUtil.log("Details are  Submitted  for the candidate::" + lname + " " + fname + " "
								+ "cand_id:" + cand_id);
						int Cand_id = Integer.valueOf(cand_id);
						DBUtilPage.NYHireDetailsUpdate(Cand_id, "P", Successmsg);
						System.out.println("Inserted details  for the candidate:" + lname + " " + fname + " "
								+ "cand_id:" + cand_id);
						WriteTextUtil.log("Inserted details  for the candidate:" + lname + " " + fname + " "
								+ "cand_id:" + cand_id);
					} else if (!successmsg.isDisplayed()) {

						System.out.println("Details are not Submitted  for the candidate:" + lname + " " + fname + " "
								+ "cand_id:" + cand_id);
						WriteTextUtil.log("Details are not Submitted  for the candidate:" + lname + " " + fname + " "
								+ "cand_id:" + cand_id);
						String imagepath = scp.takescreenshotFile();
						String CandidateDetailsSubject = "New Hire Details Have not been Submitted for the Candidate_ID :"
								+ cand_id;
						SendExceptionMailPage.sendInLineImageEmail(ConstantsPage.from, ConstantsPage.to,
								CandidateDetailsSubject, ConstantsPage.CandidateDetails, imagepath);
						Thread.sleep(2000);
						Popupclosebtn.click();
						System.out.println("popup has been closed Successfully");
						driver.navigate().refresh();

					}
				}
			} else {
				System.out.println("No details in table to scrape");
				WriteTextUtil.log("No details in table to scrape");
				DBUtilPage.NewHireAuditDetails("No candidates to submit", gp_company, "NY");
			}

		} catch (Exception e) {
			//Popupclosebtn.click();
			System.out.println("Exception found at NewHire(): " + e.getMessage());
			WriteTextUtil.log("Exception found at NewHire(): " + e.getMessage());
			SendExceptionMailPage.sendEmail(ConstantsPage.from, ConstantsPage.to, ConstantsPage.subject,
					"Exception at NewHire():" + e.getMessage() + "\n\n" + ConstantsPage.emailsignature);
		}

	}
		
	

	public void logout(String gp_company) throws InterruptedException {
		try {
			Actions act = new Actions(driver);
			act.moveToElement(tempdown).build().perform();
			WebDriverWait wt = new WebDriverWait(driver, 60);
			wt.until(ExpectedConditions.elementToBeClickable(logout));
			logout.click();
			System.out.println("Logout successfully");
			WriteTextUtil.log("Logout successfully");
			DBUtilPage.NewHireAuditDetails("Successfully Logout", gp_company, "NY");

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Unable to Logout");
			WriteTextUtil.log("Unable to Logout");
		}

	}

	public void cal(String enterDate) throws Exception {
		Calendar cal = Calendar.getInstance();
		int systemYear = cal.get(Calendar.YEAR);
		int systemMonth = cal.get(Calendar.MONTH) + 1;
		int systemdate = cal.get(Calendar.DAY_OF_MONTH);
		System.out.println("Today's date: " + systemMonth + "-" + systemdate + "-" + systemYear);

		// User enter date
		System.out.println("User enter date: " + enterDate);
		String userDateArray[] = new String[3];
		userDateArray = enterDate.split("-");
		int userYear = Integer.parseInt(userDateArray[0]);
		int userMonth = Integer.parseInt(userDateArray[1]);
		int userdate = Integer.parseInt(userDateArray[2]);

		// Print the values
		System.out.println("Entered Year: " + userDateArray[0]);
		System.out.println("Entered month: " + userDateArray[1]);
		System.out.println("Entered Date: " + userDateArray[2]);
		// Difference between the months
		if (systemMonth > userMonth) {
			int pastmonth = systemMonth - userMonth;
			for (int i = 1; i<= pastmonth; i++) {
				
				leftarrowbtn.click();
				
			}
				WebElement day = driver.findElement(By
						.xpath("//td[not(contains(@class,'ui-datepicker-other-month'))]/a[text()='" + userdate + "']"));
				Thread.sleep(6000);
				day.click();
					
			}
		
		if (systemMonth == userMonth) {
			WebElement day1 = driver.findElement(
					By.xpath("//td[not(contains(@class,'ui-datepicker-other-month'))]/a[text()='" + userdate + "']"));
			Thread.sleep(6000);
			day1.click();
		}

	}
	
	public void ChangePasswordAlert(String gp_comapny) 
	 {
		 
		try {
			  
			 if(changepasswordmsg.isDisplayed())
			 
			 {
				 
			    String resetpwd =changepasswordmsg.getText();
				    
				 System.out.println(resetpwd); 
				 SendExceptionMailPage.sendEmail(ConstantsPage.from, ConstantsPage.to, ConstantsPage.alertsubject, 
						 changepasswordmsg	+ gp_comapny +   "\n\n" + ConstantsPage.emailsignature);
			 }
			 
			 else 
			 {
				 
				 System.out.println(" No Alert is displayed.");
				 
			 }
			 
	    }catch(Exception e)
		{
		 
		 WriteTextUtil.log("Exception found at NewHire(): " + e.getMessage());
			SendExceptionMailPage.sendEmail(ConstantsPage.from, ConstantsPage.to, ConstantsPage.subject,
					"Exception at NewHire():" + e.getMessage() + "\n\n" + ConstantsPage.emailsignature);
		 
		}
	 }

}
