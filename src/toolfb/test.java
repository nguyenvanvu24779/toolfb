/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolfb;

import java.util.List;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author vunguyen
 */
public class test {
    public static String getStrings(String test_str,String text_begin,String text_end) {
      int start_pos = test_str.indexOf(text_begin);
      if (start_pos < 0) {
         return "";
      }
      start_pos += text_begin.length();
      int end_pos = test_str.indexOf(text_end, start_pos);
      String text_to_get = test_str.substring(start_pos, end_pos);
      return text_to_get;
    }
    public static void main(String[] args) throws InterruptedException{
        System.setProperty("webdriver.chrome.driver", "C:\\webdriver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        
        driver.get("https://facebook.com");
        
        Thread.sleep(5000); 
        
        WebElement emailEle =  driver.findElement(By.id("email"));
        WebElement passEle =  driver.findElement(By.id("pass"));
        WebElement loginBtnEle = driver.findElement(By.cssSelector("input[type='submit']"));// driver.findElement(By.id("u_0_a"));
        
        emailEle.sendKeys("100011230868716.0vgupa@mko.nz");
        Thread.sleep(2000); 
        passEle.sendKeys("ndtdtk");
        Thread.sleep(2000); 
        loginBtnEle.click();
        
        Thread.sleep(5000);  // Let the user actually see something!
        Set<Cookie> lstCookie = driver.manage().getCookies();
        String cookieStr = "";
        for(Cookie cook : lstCookie){
            if(cook.getName().equals("c_user")) cookieStr += "c_user=" + cook.getValue() + ";";
            if(cook.getName().equals("datr")) cookieStr += "datr=" + cook.getValue() + ";";
            if(cook.getName().equals("xs")) cookieStr += "xs=" + cook.getValue() + ";";
            if(cook.getName().equals("fr")) cookieStr += "fr=" + cook.getValue() + ";";
            

        }
        System.out.println(cookieStr);
        
        JavascriptExecutor js = (JavascriptExecutor)driver;
        String jsScript = "function getStrings(test_str, text_begin, text_end) {\n" +
        "      var start_pos = test_str.indexOf(text_begin);\n" +
        "      if (start_pos < 0) {\n" +
        "         return '';\n" +
        "      }\n" +
        "      start_pos += text_begin.length;\n" +
        "      var end_pos = test_str.indexOf(text_end, start_pos);\n" +
        "      var text_to_get = test_str.substring(start_pos, end_pos);\n" +
        "      return text_to_get;\n" +
        "};\n" +
        "getStrings(document.documentElement.innerHTML, '{\"token\":\"', '\"')";
    
        String sText =  js.executeScript("return document.documentElement.innerHTML").toString();
       // System.out.println();
        String fb_dtsg = getStrings(sText, "{\"token\":\"", "\"" );
        String jazoest = "";
        for (int i = 0; i < fb_dtsg.length(); i++) 
            jazoest += (int)fb_dtsg.charAt(i);
        
        System.out.println(jazoest);
        
        //driver.get("https://www.facebook.com/100019217749037/groups");
        driver.quit();
        
        
        
        
        
    }
}
