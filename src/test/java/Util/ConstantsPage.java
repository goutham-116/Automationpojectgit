package Util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ConstantsPage {
	
	static String user_dir = System.getProperty("user.dir");

	public static final String log_fileName = "C:\\logs\\NYNewHireScrapping\\";

	public static final String fileName = log_fileName + "LogsFileForTheDateNYNewHireScrapping"
			+ new SimpleDateFormat("MMddyyyyhhmm a'.txt'").format(new Date());
	
	public static String SERVER_HOST = "tempositions-com.mail.protection.outlook.com";//""127.0.0.1";

	public static String from = "it-alert@tempositions.com";

	public static String bcc = "roopas@nfcsolutionsusa.com, gouthamg@nfcsolutionsusa.com,venkatesh@nfcsolutionsusa.com, vishnuvardhank@nfcsolutionsusa.com";

	public static String cc = "gouthamg@nfcsolutionsusa.com,vishnuvardhank@nfcsolutionsusa.com";
	
	public static String to = "gouthamg@nfcsolutionsusa.com,vishnuvardhank@nfcsolutionsusa.com";

	public static String subject = "NYC NewHire Scraping Exceptions Information";
	
	public static String alertsubject=" NYC NewHire Scrapping Change Password Alert";
	
	public static String Changepasswordbody=" Your password has expired. Please reset your password.";
	
	public static final String screenshot_path = user_dir + "\\Screenshots\\";
	
	public static String Login = "NYC-NewHire Scraping Site- unable to Login";
	
	public static String CandidateDetails ="The Following details of candidate Provided in Screenshot are not Submitted ";
	
	public static String SiteUnavailable="NY-New Hire Site Partially Down";
	
	public static final String emailsignature = "Thanks, " + "\n" + "TemPositions";


}
