import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by jonahss on 8/6/14.
 */
public class Main {
  public static void main(String [ ] args) throws MalformedURLException, InterruptedException {

    DesiredCapabilities caps = new DesiredCapabilities();
    File app = new File("/Users/jonahss/Workspace/appium/submodules/ApiDemos/bin/ApiDemos-debug.apk");
    caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
    caps.setCapability(MobileCapabilityType.BROWSER_NAME, "");
    caps.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
    caps.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());

    AppiumDriver driver = new AppiumDriver(new URL("http://127.0.0.1:4723/wd/hub"), caps);

    //activate japanese keyboard
    WebDriver.ImeHandler ime = driver.manage().ime();
    ime.getAvailableEngines();
    for (String engine : ime.getAvailableEngines()) {
      System.out.println(engine);
    }
    ime.activateEngine("jp.co.omronsoft.openwnn/.OpenWnnJAJP");

    driver.complexFind("something craaaazy");

    //get to a view with a text box
    driver.scrollTo("Views");
    driver.findElementByAccessibilityId("Views").click();
    driver.findElementByAccessibilityId("Controls").click();
    driver.findElementByAccessibilityId("1. Light Theme").click();
    MobileElement button = (MobileElement) driver.findElementByClassName("android.widget.EditText");

    Thread.sleep(6000);

    //put japanese keyboard into hiragana mode
    //(I'm looking for a better way to do this, but for now, we have to do a manual screen touch)
    TouchAction modeTap = new TouchAction(driver);
    modeTap.tap(30, 750);
    driver.performTouchAction(modeTap);
    modeTap = new TouchAction(driver);
    modeTap.tap(30, 750);
    driver.performTouchAction(modeTap);

    //LOOK! We are sending romaji keys, but it comes in as hiragana
    button.sendKeys("romaji");

    Thread.sleep(4000);

    //let's click one of the Input Candidates (there must be a way to get these. I'm working on it. You can see them using the Android Device Monitor)
    TouchAction candidateTap = new TouchAction(driver);
    //260 400?
    candidateTap.tap(100, 450);
    driver.performTouchAction(candidateTap);



  }
}
