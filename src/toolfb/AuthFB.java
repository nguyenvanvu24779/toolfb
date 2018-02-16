/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolfb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *
 * @author vunguyen
 */
public class AuthFB {
    private String email = "";
    private String pass = "";
    public String cookieStr = "";
    public String fb_dtsg = "";
    public String jazoest = "";
    public String __user = "";
    public String urlApi = "";
    public boolean checkpoint = false;
    private WebDriver driver;
    public AuthFB(WebDriver driver ,String urlApi ,String email,String pass){
        this.email = email;
        this.pass = pass;
        this.driver = driver;
        this.urlApi = urlApi;
    }
    public  String getStrings(String test_str,String text_begin,String text_end) {
        int start_pos = test_str.indexOf(text_begin);
        if (start_pos < 0) {
           return "";
        }
        start_pos += text_begin.length();
        int end_pos = test_str.indexOf(text_end, start_pos);
        String text_to_get = test_str.substring(start_pos, end_pos);
        return text_to_get;
    }
    
    public void login() throws InterruptedException{
        driver.get("https://facebook.com");
        
        Thread.sleep(5000); 
        
        WebElement emailEle =  driver.findElement(By.id("email"));
        WebElement passEle =  driver.findElement(By.id("pass"));
        WebElement loginBtnEle = driver.findElement(By.cssSelector("input[type='submit']"));// driver.findElement(By.id("u_0_a"));
        
        emailEle.sendKeys(this.email);
        Thread.sleep(2000); 
        passEle.sendKeys(this.pass);
        Thread.sleep(2000); 
        loginBtnEle.click();
        
        //Thread.sleep(5000);  // Let the user actually see something!
        
    }
    
    public boolean exportCookie(){
        Set<Cookie> lstCookie = driver.manage().getCookies();
        boolean isValid = false;
        for(Cookie cook : lstCookie){
            if(cook.getName().equals("c_user")) {
                this.cookieStr += "c_user=" + cook.getValue() + ";";
                this.__user = cook.getValue();
                isValid = true; 
            }
            if(cook.getName().equals("datr")) this.cookieStr += "datr=" + cook.getValue() + ";";
            if(cook.getName().equals("xs")) this.cookieStr += "xs=" + cook.getValue() + ";";
            if(cook.getName().equals("fr")) this.cookieStr += "fr=" + cook.getValue() + ";";
            if(cook.getName().equals("checkpoint")) this.checkpoint = true;
        }
        
        JavascriptExecutor js = (JavascriptExecutor)driver;
        String sText =  js.executeScript("return document.documentElement.innerHTML").toString();
        fb_dtsg = this.getStrings(sText, "{\"token\":\"", "\"" );
        for (int i = 0; i < fb_dtsg.length(); i++) 
            jazoest += (int)fb_dtsg.charAt(i);
        return isValid;
    }
    
    public void callCreateAccountApi (){
          try {
                URL url;
                if(this.__user.length() == 0){
                    url = new URL(this.urlApi + String.format("?username=%s&password=%s&status=%s",
                            this.email,
                            this.pass,
                            "ADD_FAIL") 
                    );
                }
                else {
                    url = new URL(this.urlApi + String.format("?__user=%s&cookie=%s&fb_dtsg=%s&jazoest=%s&username=%s&password=%s&status=%s",
                            this.__user,
                            this.cookieStr,
                            this.fb_dtsg,
                            this.jazoest,
                            this.email,
                            this.pass,
                            "ADD_NEW")
                    );
                }
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");

		if (conn.getResponseCode() != 200) {
			return;
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));

		String output;
		System.out.println("Output from Server .... \n");
		while (( output = br.readLine()) != null) {
			System.out.println(output);
		}

		conn.disconnect();

	  } catch (MalformedURLException e) {

		e.printStackTrace();

	  } catch (IOException e) {

		e.printStackTrace();

	  }
    }
}
