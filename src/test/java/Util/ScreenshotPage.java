package Util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class ScreenshotPage {
	
	
	WebDriver driver;
	String extension = ".png";

	public ScreenshotPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
	}

	
	public String takescreenshotFile() throws Exception {
	//	File SrcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File filename = new File("screenshot_" + "_" + timestamp() + "" + extension);
		Thread.sleep(2000);
		String filepath = ConstantsPage.screenshot_path + filename;
		File DestFile = new File(filepath);
	//	FileUtils.copyFile(SrcFile, DestFile);
		return filepath;
	}

	public String timestamp() {
		return new SimpleDateFormat("yyyyMMdd" + "_" + "HH-mm-ss").format(new Date());
	}


}
