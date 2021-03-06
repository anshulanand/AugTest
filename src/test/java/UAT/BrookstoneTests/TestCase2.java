package UAT.BrookstoneTests;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pageObjects.BkstAccountsLogin;
import pageObjects.BkstChkoutBillingPage;
import pageObjects.BkstChkoutOptionstPage;
import pageObjects.BkstChkoutShippingPage;
import pageObjects.BkstMainpage;
import pageObjects.BkstProduct;
import pageObjects.BkstProductsOutline;
import pageObjects.BkstShoppingCart;
import pageObjects.BkstSubmitOrder;
import resources.base;

public class TestCase2 extends base {

	public static Logger log = LogManager.getLogger(base.class.getName());

	@BeforeTest(groups = { "Smoke" })
	public void initialize() throws IOException {

		driver = initializeDriver();
		// log.info("Driver is initialized");

		// 1. Go to Brookstone.com.
		String env = prop.getProperty("environment");
		driver.get(prop.getProperty(env));
		// log.info("Navigated to Home page");

	}

	@Test(groups = { "Smoke" })
	public void TC2() throws InterruptedException {

		// 2. Navigate to any product detail page, select any applicable product options
		// (color, size, etc.), leave the default quantity at 1, and click to buy the
		// product.
		BkstMainpage rd = new BkstMainpage(driver);
		rd.closeButton().click();

		rd.Top1().click();

		BkstProductsOutline ol = new BkstProductsOutline(driver);
		ol.Prod1().click();

		// 3. In the upper right corner of the page, in the mini cart, click the button
		// to go directly to checkout.
		BkstProduct pd = new BkstProduct(driver);
		pd.Add_to_cart().click();

		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(pd.Go_to_chkout()));
		pd.Go_to_chkout().click();

		// 4. At the Secure Checkout landing page, click the button to check out as a
		// guest.
		BkstChkoutOptionstPage ck = new BkstChkoutOptionstPage(driver);
		ck.Guest_chkout().click();

		// 5. At the Shipping page of checkout, in the page header, click the Brookstone
		// logo/link.
		BkstChkoutShippingPage sh = new BkstChkoutShippingPage(driver);
		sh.logo().click();

		Assert.assertEquals(rd.title(), "Gift Ideas, Cool Gadgets, Unique Gifts for Him and Her at Brookstone");
		log.info("TC2 Verified 6: Verify that you are taken to the site home page");

		// 7. In the upper right corner of the page, click either the "Shopping Cart"
		// icon in the page header or the "View Cart" button in the mini cart.
		rd.Cart().click();

		// 8. Back at the Shopping Cart page, click the button to check out.
		BkstShoppingCart sc = new BkstShoppingCart(driver);
		sc.Checkout().click();

		// 9. At the Secure Checkout landing page, click the button to check out as a
		// guest.
		ck.Guest_chkout().click();

		// 10. Again at the Shipping page, in the page header, click the "Back to Cart"
		// link.
		sh.BacktoCart().click();

		Assert.assertEquals(sc.title(), "Brookstone Cart");
		log.info("TC2 Verified 11: Verify that you are taken back to the Shopping Cart page.");

		// 12. At the Shopping Cart page, click the button to check out.
		sc.Checkout().click();

		// 13. At the Secure Checkout landing page, click the button to check out as a
		// guest.
		ck.Guest_chkout().click();

		// 14. Again at the Shipping page, in the page header, click the "Log In" link.
		sh.login().click();

		//
		BkstAccountsLogin ac = new BkstAccountsLogin(driver);
		Assert.assertEquals(ac.title(), "Brookstone Account Login");
		log.info("TC2 Verified 15. Verify that you are taken to the My Account Login page.");

		// 16. In the upper right corner of the page, click either the "Shopping Cart"
		// icon in the page header or the "View Cart" button in the mini cart.
		Actions action = new Actions(driver);
		action.moveToElement(ac.Cart()).click().perform();
		// ac.Cart().click();

		// driver.findElement(By.xpath("//img[@class='cart']")).click();

		// 17. At the Shopping Cart page, click the button to check out.
		sc.Checkout().click();

		// 18. At the Secure Checkout landing page, click the button to check out as a
		// guest.
		ck.Guest_chkout().click();

		// 19. Again at the Shipping page, in the page header, hover your mouse pointer
		// over the Customer Care phone number to confirm it is not a link.
		// to be done

		// Thread.sleep(5000);
		sh.First_Name().sendKeys(prop.getProperty("FirstName"));
		sh.Last_Name().sendKeys(prop.getProperty("LastName"));
		sh.Address1().sendKeys(prop.getProperty("STAddress1"));

		// Thread.sleep(2000);
		sh.City().sendKeys(prop.getProperty("STCity"));
		sh.Select_State().click();
		sh.SsState().click();
		sh.Phone().sendKeys(prop.getProperty("Phone"));
		sh.Zipcode().sendKeys(prop.getProperty("STZipcode"));

		sh.Continue().click();

		// Thread.sleep(10000);
		BkstChkoutBillingPage bl = new BkstChkoutBillingPage(driver);
		// Assert.assertEquals(bl.title(), "Brookstone Checkout Billing");
		log.info("TC2 Verified 15. Verify that you are taken to the My Account Login page.");

		bl.Email().sendKeys(prop.getProperty("Email"));

		bl.PromoCode().sendKeys(prop.getProperty("Offer2"));
		bl.Apply().click();

		String FullName = prop.getProperty("FirstName") + " " + prop.getProperty("LastName");
		bl.CardName().sendKeys(FullName);
		bl.CardNumber().sendKeys(prop.getProperty("DiscCardNo"));
		bl.CardCVV().sendKeys(prop.getProperty("DiscCardCvv"));
		bl.dropDown().click();
		bl.CardExpYear().click();

		bl.ContinueChkout().click();

		BkstSubmitOrder sb = new BkstSubmitOrder(driver);
		sb.submitOrder().click();
		sb.customerSurvey().click();
		sb.backToBrookstone().click();

		Assert.assertEquals(rd.title(), "Gift Ideas, Cool Gadgets, Unique Gifts for Him and Her at Brookstone");
		log.info("TC2 Verified 23. Verify that you are taken to the site home page.");

	}

	@AfterTest(groups = { "Smoke" })
	public void teardown() {

		driver.close();
		driver = null;

	}

}
