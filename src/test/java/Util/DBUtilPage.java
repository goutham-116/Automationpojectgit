package Util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;

import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;

public class DBUtilPage {
	
	
	static Connection con = null;
	static String dbUrl = "jdbc:sqlserver://hq210rs;databaseName=SRS1;";
	static String username = "nfcsolutions";
	static String password = "P@ssw0rd1!";

	public static void main(String[] args) throws Exception {
		// NYHireData("Temp", "NY");
		// NYHireDetailsUpdate(260302, "P", "Candidate submitted successfully");
		//GetloginDetails();
		//NewHireAuditDetails("Successfully","TEMP", "NY");
	}

	public static ArrayList<String> NYHireData(String gp_company, String state) throws Exception {
		ArrayList<String> employeeFullName = new ArrayList<String>();
		ResultSet rs = null;
		LocalDate dt = LocalDate.now();
		LocalDate lastSunday = dt.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));
		SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yyyy");
		Date date = Date.valueOf(lastSunday);
		String currentbilldate = format1.format(date);
		System.out.println(currentbilldate);
		try {
			con = DriverManager.getConnection(dbUrl, username, password);
			System.out.println("Connected to database Successfully");
			WriteTextUtil.log("Connected to database Successfully");
			CallableStatement cs = con.prepareCall("{call uspGet_NYNewHireNewCandidatesWorkingByBilldate (?,?,?)}");
			System.out.println("Query executed successfully");
			WriteTextUtil.log("Query executed successfully");
			cs.setEscapeProcessing(true);
			cs.setQueryTimeout(90);

			cs.setString(1, gp_company);
			

			cs.setString(2, state);

		//	cs.setString(3, currentbilldate);
			
			cs.setString(3, "11/27/2022");
			
			
			

			boolean results = cs.execute();
			int rowsAffected = 0;
			// Protects against lack of SET NOCOUNT in stored prodedure
			while (results || rowsAffected != -1) {
				if (results) {
					rs = cs.getResultSet();
					break;
				} else {
					rowsAffected = cs.getUpdateCount();
				}
				results = cs.getMoreResults();
			}
			while (rs.next()) {
				employeeFullName.add(rs.getInt("cand_id") + "," + rs.getString("ssn").trim() + ","
						+ rs.getString("last_name") + "," + rs.getString("first_name") + ","
						+ rs.getString("middle_initial") + "," + rs.getString("address").replaceAll(",", "").trim() + "," + rs.getString("city")
						+ "," + rs.getString("state") + "," + rs.getString("zip_code") + "," + rs.getInt("div_id") + ","
						+ rs.getDate("hire_date") + "," + rs.getString("health_insurance_benifit_status").trim());

			}
			System.out.println(employeeFullName);
			con.close();
			System.out.println("Database Connection closed Successfully");
			WriteTextUtil.log("Database Connection closed Successfully");
		} catch (Exception e) {
			System.out.println("Exception found at NYHireData():" + e.getMessage());
			WriteTextUtil.log("Exception found at NYHireData(): " + e.getMessage());
			SendExceptionMailPage.sendEmail(ConstantsPage.from, ConstantsPage.to, ConstantsPage.subject,
					"Exception at NYHireData():" + e.getMessage() + "\n\n" + ConstantsPage.emailsignature);
		} finally {
			con.close();
			System.out.println("Database Connection Closed Successfully");
			WriteTextUtil.log("Database Connection Closed Successfully");
		}

		return employeeFullName;

	}

	public static void calendar() {
		LocalDate dt = LocalDate.now();
		LocalDate lastfriday = dt.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY));
		SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yyyy");
		Date date = Date.valueOf(lastfriday);
		String result = format1.format(date);
		System.out.println(result);
	}

	public static void NYHireDetailsUpdate(int Cand_id, String status, String statusmessage) throws SQLException {
		try {
			con = DriverManager.getConnection(dbUrl, username, password);
			System.out.println("Connected to database Successfully");
			WriteTextUtil.log("Connected to database Successfully");
			CallableStatement cs = con.prepareCall("{call uspInsert_CandNewHireFormStatus (?,?,?)}");
			System.out.println("Query executed successfully");
			WriteTextUtil.log("Query executed successfully");
			cs.setInt(1, Cand_id);

			cs.setString(2, status);

			cs.setString(3, statusmessage);
			cs.execute();
			con.close();
			System.out.println("Database Connection closed Successfully");
			WriteTextUtil.log("Database Connection closed Successfully");
		} catch (Exception e) {
			System.out.println("Exception found at NYHireDetailsUpdate():" + e.getMessage());
			WriteTextUtil.log("Exception found at NYHireDetailsUpdate(): " + e.getMessage());
			SendExceptionMailPage.sendEmail(ConstantsPage.from, ConstantsPage.to, ConstantsPage.subject,
					"Exception at NYHireDetailsUpdate():" + e.getMessage() + "\n\n" + ConstantsPage.emailsignature);
		}finally {
			con.close();
			System.out.println("Database Connection closed Successfully");
			WriteTextUtil.log("Database Connection closed Successfully");	
		}
		

	}

	public static ArrayList<String> GetloginDetails() throws SQLException {
		ArrayList<String> NYHireLogins = new ArrayList<String>();
		try {
			con = DriverManager.getConnection(dbUrl, username, password);
			System.out.println("Connected to database Successfully");
			WriteTextUtil.log("Connected to database Successfully");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("{call dbo.uspGet_NYNewHireWebLogin}");
			System.out.println("Connected to database Successfully");
			WriteTextUtil.log("Connected to database Successfully");
			while (rs.next()) {
				NYHireLogins.add(
						rs.getString("gp_company").trim() + "," + rs.getString("app_login") + "," + rs.getString("app_pwd").trim());
			}
			System.out.println(NYHireLogins);
			con.close();
			System.out.println("Database Connection closed Successfully");
			WriteTextUtil.log("Database Connection closed Successfully");
			
		} catch (Exception e) {
			System.out.println("Exception found at GetloginDetails():" + e.getMessage());
			WriteTextUtil.log("Exception found at GetloginDetails(): " + e.getMessage());
			SendExceptionMailPage.sendEmail(ConstantsPage.from, ConstantsPage.to, ConstantsPage.subject,
					"Exception at GetloginDetails():" + e.getMessage() + "\n\n" + ConstantsPage.emailsignature);
		} finally {
			con.close();
			System.out.println("Database Connection Closed Successfully");
			WriteTextUtil.log("Database Connection Closed Successfully");
		}

		return NYHireLogins;
	}
	public static void NewHireAuditDetails(String Message, String gp_company, String state) throws Exception {
		try {
			con = DriverManager.getConnection(dbUrl, username, password);
			System.out.println("Connected to database Successfully");
			WriteTextUtil.log("Connected to database Successfully");
			CallableStatement cs = con.prepareCall("{call uspInsert_CandNYNewHireScrappingAudit (?,?,?)}");
			System.out.println("Query executed successfully");
			WriteTextUtil.log("Query executed successfully");
			cs.setString(1, Message);

			cs.setString(2, gp_company);

			cs.setString(3, state);
			
			cs.execute();
			con.close();
			System.out.println("Database Connection closed Successfully");
			WriteTextUtil.log("Database Connection closed Successfully");
		} catch (Exception e) {
			System.out.println("Exception found at NewHireAuditDetails():" + e.getMessage());
			WriteTextUtil.log("Exception found at NewHireAuditDetails(): " + e.getMessage());
			SendExceptionMailPage.sendEmail(ConstantsPage.from, ConstantsPage.to, ConstantsPage.subject,
					"Exception at NewHireAuditDetails():" + e.getMessage() + "\n\n" + ConstantsPage.emailsignature);
		}finally {
			con.close();
			System.out.println("Database Connection closed Successfully");
			WriteTextUtil.log("Database Connection closed Successfully");	
		}
		

		
		
		
		
	}

}
