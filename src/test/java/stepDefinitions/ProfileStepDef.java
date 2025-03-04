package stepDefinitions;

import static org.junit.Assert.*;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import factory.Base;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.LoginPageObj;
import pageObjects.ProfilePageObj;

public class ProfileStepDef {

	WebDriver driver;
	LoginPageObj lp;
	ProfilePageObj pp;
	WebDriverWait wait = new WebDriverWait(Base.getDriver(), Duration.ofSeconds(15));

	@Given("I navigate to Naukri Application")
	public void i_navigate_to_naukri_application() {
		lp = new LoginPageObj(Base.getDriver());
		lp.selectLogin();
	}

	@Given("enter the credentials")
	public void enter_the_credentials() {
		lp.enterCredentials("abhayjoshi23146@gmail.com", "Abhayaj@621");
		lp.selectLoginButton();
	}

	@Given("I naviagte to my profile")
	public void i_naviagte_to_my_profile() {
		pp = new ProfilePageObj(Base.getDriver());
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("View")));
		pp.clickViewProfile();
	}

	@Given("Click on carrer profile")
	public void click_on_carrer_profile() throws InterruptedException {
		pp.clickCarrerProfile();
		Thread.sleep(3000);
		pp.editCarrerProfile();
		boolean res = pp.verifyFormIsPresent();
		assertTrue(res);
	}

	@When("I click on {string}, select\\/deslect a {string}")
	public void i_click_on_select_deslect_a(String string, String string2) throws InterruptedException {
		Thread.sleep(4000);
		pp.clickForLocation();
		boolean isSelected = pp.isLocationSelected();
		if (isSelected) {
			pp.addLocation();
			System.out.print("Added Chennai to location prefernce");
		} else {
			pp.addLocation();
			System.out.print("Removed Chennai to location prefernce");
		}
		pp.clickLocationLabel();
	}

	@When("clcik on save button")
	public void clcik_on_save_button() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("saveDesiredProfile")));
		pp.clickcpSaveButton();
	}

	@Then("I should see profile updated as {string}")
	public void i_should_see_message(String exp_text) {
		String msg = pp.verifyProfileUpdateStatus();
		assertTrue(msg.contains(exp_text));
	}

	@When("Click on {string} in resume")
	public void click_on_in_resume(String string) {
		pp.clickUpdateLink();
	}

	@When("I click on resume that has to be uploaded")
	public void i_click_on_resume_that_has_to_be_uploaded() {
		pp.uploadResume();
	}

	@Then("Resume should br uploaded successfully")
	public void resume_should_br_uploaded_successfully() throws InterruptedException {
	    Thread.sleep(5000); // Wait for upload to complete (5 seconds)
	    Base.getDriver().navigate().refresh(); // Force refresh the page
	    
	    Thread.sleep(5000); // Wait for refresh
	    String name = pp.validateResumeName(); // Fetch updated resume name
	    
	    System.out.println("Uploaded File Name: " + name);
	    
	    if (name != null) {
	        assertTrue("Uploaded file name does not match!", name.contains("Abhay_Joshi.pdf"));
	        System.out.println("✅ Resume Uploaded Successfully");
	    } else {
	        System.out.println("❌ No File Name Found");
	        fail("File Name is null");
	    }
	}


}
