package com.issoft.training;

import com.issoft.training.pages.LoginPage;
import com.issoft.training.pages.MailPage;
import com.issoft.training.pages.TutByPage;
import com.issoft.training.pages.YandexPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class LoginToTutByWithPageFactoryPatternTest {

	private final static String TYT_BY_URL = "https://www.tut.by/";
	private final static String LOGIN = "seleniumtests10";
	private final static String PASSWORD = "060788avavav";
	public static final String USERNAME = "AlexSlap";
	public static final String ACCESS_KEY = "4e02e29d-3ffa-4f97-beba-eb4975a2f379";
	public static final String URL = "https://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:443/wd/hub";
	private WebDriver driver;
	private DesiredCapabilities caps;

	@BeforeClass
	public void setUp() throws MalformedURLException {
		caps = DesiredCapabilities.edge();
		caps.setCapability("platform", "Windows 10");
		caps.setCapability("version", "16.16299");

		driver = new RemoteWebDriver(new URL(URL), caps);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		driver.get(TYT_BY_URL);
	}

	@AfterClass
	public void tearDown() {
		driver.close();
	}

	@Test
	public void loginTutByTest() {
		TutByPage tutByPage = new TutByPage(driver);
		LoginPage loginPage = tutByPage.clickMailLink();
		MailPage mailPage = loginPage.login(LOGIN, PASSWORD);
		Assert.assertTrue(mailPage.isFormPresent(), "Form isn't present");
	}

	@Test(dependsOnMethods = {"loginTutByTest"})
	public void logOutTutByTest() {
		MailPage mailPage = new MailPage(driver);
		mailPage.logout();
		YandexPage yandexPage = new YandexPage(driver);
		Assert.assertTrue(yandexPage.isInputFieldIsPresent(), "Input field isn't on Yandex page");
	}
}
