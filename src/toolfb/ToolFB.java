/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolfb;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
/**
 *
 * @author vunguyen
 */
public class ToolFB {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException, IOException {
        // TODO code application logic here
        System.setProperty("webdriver.chrome.driver", "C:\\webdriver\\chromedriver.exe");
         
        WebDriver driver = new ChromeDriver();
        driver.get("https://facebook.com");
        Thread.sleep(5000);  // Let the user actually see something!
        Cookie cookie = new Cookie.Builder("c_user", "100019217749037").domain(".facebook.com").build();
        driver.manage().addCookie(cookie);
        Cookie cookie1 = new Cookie.Builder("xs", "24:7ECVCGXSuite1Q:2:1510674101:-1:-1").domain(".facebook.com").build();
        driver.manage().addCookie(cookie1);
        Cookie cookie2 = new Cookie.Builder("fr", "0ybzpFX5vOWdEOFbx.AWW-IIN3sjZ4bYfVM4Jxd1Fi76Y.BaCw61..AAA.0.0.BaCw61.AWXihaB9").domain(".facebook.com").build();
        driver.manage().addCookie(cookie2);
        Cookie cookie3 = new Cookie.Builder("datr", "tQ4LWpenlM9MDoLwfA94eJ9P").domain(".facebook.com").build();
        driver.manage().addCookie(cookie3);
        Thread.sleep(5000);  // Let the user actually see something!
        driver.get("https://www.facebook.com/100019217749037/groups");
        driver.quit();
    
   //  testShare();
    /*    postShare2Group("1547184938658515",
                "test phantomjs post",
                "100019217749037",
                "24:7ECVCGXSuite1Q:2:1510674101:-1:-1",
                "0ybzpFX5vOWdEOFbx.AWW-IIN3sjZ4bYfVM4Jxd1Fi76Y.BaCw61..AAA.0.0.BaCw61.AWXihaB9",
                "tQ4LWpenlM9MDoLwfA94eJ9P",
                "7AgNe-4amaxx2u6Xolg9pE9XG8GEW8xdLFwxx-6EeAq2i5U4e1oy9UvHyorxuE9pHxWU4St0h9VobohxOu2O7EO2S1Dxa2m4o9Ef8oC-Q3y7Uc9Ebo4Kum5Ueo6uqUogc828zUcovy8nyES3m2GdzEmx22613xzm6uaCyo8Ja6VQp0",
                "AQH1xkvYhklR%3AAQEfcKtZ9tuz",
                "26581724912010711889104107108825865816910299751169057116117122");*/
    }
    
    public static List<String> getListGroupJoined(
            String c_user,
            String xs,
            String fr,
            String datr,
            String __dyn,
            String fb_dtsg,
            String jazoest) throws MalformedURLException, MalformedURLException, IOException{
        List<String> lstGroup = new ArrayList<String>();
        
        String httpsURL = "https://www.facebook.com/" + c_user + "/groups";
        URL myUrl = new URL(httpsURL);
        HttpsURLConnection conn = (HttpsURLConnection)myUrl.openConnection();
        String cookie = String.format("c_user=%s;xs=%s;fr=%s;datr=%s", c_user, xs,fr, datr);
        conn.setRequestProperty("Cookie", cookie);
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");
        
        return lstGroup;
    }
    

    public static void postShare2Group(String groupId, 
            String message, 
            String c_user,
            String xs,
            String fr,
            String datr,
            String __dyn,
            String fb_dtsg,
            String jazoest) throws MalformedURLException, IOException{
        //String message = "test request Java code";
        //String groupId = "1547184938658515";
        //String c_user = "100019217749037";
        //String xs = "24:7ECVCGXSuite1Q:2:1510674101:-1:-1";
        //String fr = "0ybzpFX5vOWdEOFbx.AWW-IIN3sjZ4bYfVM4Jxd1Fi76Y.BaCw61..AAA.0.0.BaCw61.AWXihaB9";
        //String datr = "tQ4LWpenlM9MDoLwfA94eJ9P";
        //String __dyn = "7AgNe-4amaxx2u6aZGeFxqeCwDKEKEW8zKC-C267Uqzob4q2i5U4e1Fx-K9xK5WwIK7HwjpQ3uaVVobrzogUCu545K78O5UlwQwOxa2m4o9Ef8oC-Ujyorx-EuzFEbUmwODBxu3Cq1ex6WK6ooxu6Uao4afwNx-8xuazodopDy4czEhWx2fx613xzm6uaCyo8J1W8BU";
        //String fb_dtsg = "AQH1i2v3Sh7M:AQEUu0rhjq7Q";
        //String jazoest = "26581724910550118518310455775865816985117481141041061135581";
        String httpsURL = "https://www.facebook.com/ajax/updatestatus.php?av=" + c_user +  "&dpr=1";
        URL myUrl = new URL(httpsURL);
        HttpsURLConnection conn = (HttpsURLConnection)myUrl.openConnection();
        String cookie = String.format("c_user=%s;xs=%s;fr=%s;datr=%s", c_user, xs,fr, datr);
        conn.setRequestProperty("Cookie", cookie);
        //add reuqest header
        conn.setRequestMethod("POST");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");
        //conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        conn.addRequestProperty("Content-type", "application/x-www-form-urlencoded");
        
        // Send post request
        String urlParameters = 
                "composer_entry_time=7"+
                "&composer_session_id=de9f2c9a-8d7e-4bc4-87bd-6c988817e04d"+
                "&composer_session_duration=2774"+
                "&composer_source_surface=group"+
                "&hide_object_attachment=false"+
                "&num_keystrokes=16"+
                "&num_pastes=0"+
                "&privacyx&ref=group"+
                "&xc_sticker_id=0"+
                "&target_type=group"+
                "&xhpc_message="+  URLEncoder.encode(message, "utf-8")  +
                "&xhpc_message_text="+ URLEncoder.encode(message, "utf-8")  +
                "&is_react=true"+
                "&xhpc_composerid=rc.u_jsonp_4_r"+
                "&xhpc_targetid=" + groupId +
                "&xhpc_context=profile"+
                "&xhpc_timeline=false"+
                "&xhpc_finch=false"+
                "&xhpc_aggregated_story_composer=false"+
                "&xhpc_publish_type=1"+
                "&xhpc_fundraiser_page=false"+
                "&__user=" + c_user +
                "&__a=1"+
                "&__dyn="+ __dyn + 
                "&__req=49"+
                "&__be=1"+
                "&__pc=EXP1%3Ahome_page_pkg"+
                "&__rev=3453879"+
                "&fb_dtsg="+ fb_dtsg + 
                "&jazoest="+ jazoest + 
                "&__spin_r=3453879"+
                "&__spin_b=trunk"+
                "&__spin_t=1510641759";
        
        conn.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();
                
        int responseCode = conn.getResponseCode();
        System.out.println("Sending 'POST' request to URL : " + httpsURL);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

    }    
}
