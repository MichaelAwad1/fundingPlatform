package testing;

import org.junit.Before;
import org.junit.Test;
import net.sourceforge.jwebunit.junit.WebTester;

/**
 * This class performs a system test on the StarterGUI using JWebUnit.
 
 */
public class StarterGUIWebTestCase {

	private WebTester tester;

	/**
	 * Create a new WebTester object that performs the test.
	 */
	@Before
	public void prepare() {
		tester = new WebTester();
		tester.setBaseUrl("http://localhost:8080/FP/");
	}

	@Test
	public void testEnterFundingRequest() {
		// Start testing for guestgui
		tester.beginAt("StarterGui");

		// Check all components of the search form
		tester.assertTitleEquals("FundingPlatform - Enter Request");
		tester.assertFormPresent();
		tester.assertTextPresent("Required Information");
		tester.assertTextPresent("Project Name");
		tester.assertFormElementPresent("projectName");
		tester.assertTextPresent("Description");
		tester.assertFormElementPresent("description");
		tester.assertTextPresent("Funding limit");
		tester.assertFormElementPresent("fundingLimit");
		tester.assertTextPresent("End Date");
		tester.assertFormElementPresent("endDate");
		tester.assertTextPresent("Amount");
		tester.assertFormElementPresent("amount");
		tester.assertTextPresent("Reward");
		tester.assertFormElementPresent("reward");
		tester.assertTextPresent("E-Mail");
		tester.assertFormElementPresent("email");
		tester.assertTextPresent("IBAN");
		tester.assertFormElementPresent("IBAN");
		tester.assertButtonPresent("submit");

		// Submit the form with given parameters
		tester.setTextField("projectName", "JUnitTestProject");
		tester.setTextField("description", "Test Description");
		tester.setTextField("fundingLimit", "100");
		tester.setTextField("endDate", "05/05/2022");
		tester.setTextField("reward", "List of Rewards Test");
		tester.setTextField("amount", "10");
		tester.setTextField("email", "testemail@mail.com");
		tester.setTextField("IBAN", "DE4589891234793839493");

		tester.clickButton("submit");

		// Check the representation of the table for an empty result
		tester.assertTextPresent("New Funding Request successfully stored in the database.");

	
	}

}


