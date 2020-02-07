package testing;

import org.junit.Before;
import org.junit.Test;

import net.sourceforge.jwebunit.junit.WebTester;

/**
 * This class performs a system test on the SupportGUI using JWebUnit.

 */
public class SupporterGUIWebTestCase {

	private WebTester tester;
	//private String[][] tableHeadings;

	/**
	 * Create a new WebTester object that performs the test.
	 */
	@Before
	public void prepare() {
		tester = new WebTester();
		tester.setBaseUrl("http://localhost:8080/FP/");
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testSearch() {
		// Start testing for guestgui
		tester.beginAt("supportergui");

		// Check all components of the search form
		tester.assertTitleEquals("FundingPlatform - Search Projects");
		tester.assertFormPresent();
		tester.assertTextPresent("Select Project Status");
		tester.assertTextPresent("Select the status of the Project");
		
		tester.assertFormElementPresent("Status");
		
	//tester.assertSelectedOptionEquals("Open", "Status");
//		tester.assertSelectedOptionEquals("Successful", "Status");
//		tester.assertSelectedOptionEquals("Failed", "Status");
		
		// Submit the form with given parameters
	tester.assertButtonPresent("Submit");
		//tester.setTextField("Submit", "Open");		
		
// 		tester.setFormElement("form", "Open");
 		
		
		tester.clickButton("Submit");
//
//		// Check the representation of the table for an empty result
		tester.assertTablePresent("availableHOs");
//		tableHeadings ;
//	tableHeadings[0][0]="#";
//	tableHeadings[0][1]="Name";
//	tableHeadings[0][2]="Description";
//	tableHeadings[0][3]="Funding limit";
//	tableHeadings[0][4]="End date";
	tester.assertTablePresent("availableHOs");
//	String[][] tableHeadings = {{ "#", "Name", "Description", "Funding limit", "End date" } };
//	tester.assertTableEquals("availableHOs", tableHeadings);

//	{ { "#", "Name", "Description", "Funding limit", "End date" } };
	//	tester.assertTableRowCountEquals("availableHOs", 14);// number of rows currentl in the database table
		
//	
	
	tester.assertLinkPresent("Make Donation");
	tester.clickLink("Make Donation");
	
	
	//tester.assertHeaderPresent("Selected Project Details");
	tester.assertTitleEquals("FundingPlatform - Project Details");
	tester.assertTextPresent("Project Name");
	tester.assertTextPresent("Description");
	tester.assertTextPresent("Funding Limit");
	tester.assertTextPresent("End Date");
	tester.assertTextPresent("For this amount");
	tester.assertTextPresent("Reward is");
	tester.assertTextPresent("Total donations");
	tester.assertTextPresent("Email");
	tester.assertTextPresent("IBAN");
	//tester.assertHeaderPresent("Click on the button if you wish to donate ");
//	tester.assertButtonPresent("viewDetails");
//	tester.clickButton("viewDetails");

	tester.assertLinkPresent("viewDetails");
	tester.clickLink("viewDetails");
	
	tester.assertTitleEquals("FundingPlatform - Donate");
	
	//tester.assertHeaderPresent("Welcome to our demonstration on the FP Webapp");
	tester.assertTextPresent("Required Information");
	tester.assertFormPresent();
	tester.assertTextPresent("Email");
	tester.assertFormElementPresent("email");
	tester.assertTextPresent("IBAN");
	tester.assertFormElementPresent("iban");
	tester.assertTextPresent("Donation Amount");
	tester.assertFormElementPresent("amount");
	tester.setTextField("email", "abc@xyz.com");
	tester.setTextField("iban", "1232435446768797673");
	tester.setTextField("amount", "200");
	tester.assertButtonPresent("submit");
	tester.clickButton("submit");

	tester.assertTextPresent("New Donation successful stored in the database.");
	
	}

}


