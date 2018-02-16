/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolfb;

import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author vunguyen
 */
public class Main  implements Runnable {
    private int numberThread = 1;
    public static String urlApi = "";
    public static List<Main> arrWoker  =   Collections.synchronizedList(new ArrayList<Main>());//new ArrayList<Main>();
    public static void main(String[] args){
        
        Properties prop = new Properties();
	InputStream input = null;

	try {

		input = new FileInputStream("config.properties");

		// load a properties file
		prop.load(input);

		// get the property value and print it out
		System.out.println(prop.getProperty("urlApi"));
                urlApi = prop.getProperty("urlApi");
	} catch (IOException ex) {
		ex.printStackTrace();
	} finally {
		if (input != null) {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
        
        System.setProperty("webdriver.chrome.driver", "C:\\webdriver\\chromedriver.exe");
       // WebDriver driver = new ChromeDriver();
        
        
        JFrame f = new JFrame("ToolFB");
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                 try {
                   Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
                } catch (IOException ex) {
                    
                }
                System.exit(0);
            }
        });
        
        TextArea  taAccount = new TextArea();
        taAccount.setBounds(20,20, 400,400);
        
        JLabel lblThread = new JLabel("Thread");  
        lblThread.setBounds(450,50, 100,30);  
        JTextField textFieldThread = new JTextField(20);
        textFieldThread.setBounds(450, 75, 95, 30);
        textFieldThread.setText("1");
        
        JButton btnStart = new JButton("Start");  
        btnStart.setBounds(450,120,95,30);  
        btnStart.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){
                Thread t1 = new Thread(new Runnable() {
                public void run()
                {
                    arrWoker.clear();
                    btnStart.setEnabled(false);
                    String[] arrAccount = taAccount.getText().split("\n");
                    //System.out.println(arrAccount[1]);
                    ExecutorService executor = Executors.newFixedThreadPool(Integer.parseInt(textFieldThread.getText()));
                    for (int i = 0; i < arrAccount.length ;i++) {
                        
                        String[] account = arrAccount[i].split("\\|");
                        if(account.length < 2) continue;
                        Main worker = new Main();
                        worker.email = account[0];
                        worker.pass = account[1];
                        
                        executor.execute(worker);//calling execute method of ExecutorService   
                    }

                    executor.shutdown();
                    while (!executor.isTerminated()) {  try {
                        Thread.sleep(100);
                        } catch (InterruptedException ex) {

                        }
                    }
                    btnStart.setEnabled(true);
                }});  
                t1.start();
                
            }  
        });
        
        JButton btnExport = new JButton("Export");  
        btnExport.setBounds(450,200,95,30);  
        btnExport.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){
                for (int i = arrWoker.size() - 1 ; i >= 0; i--) {
                    Main m = arrWoker.get(i) ;
                    if(m == null)
                        return;
                    m.authFB.exportCookie();   
                    System.out.println(m.authFB.cookieStr);
                    m.authFB.callCreateAccountApi();
                    m.driver.close();
                    m.driver.quit();
                    arrWoker.remove(i);
                    m.done = true;
                    m = null; 
                    taAccount.setText("");
                }
            }  
        });

        
        f.add(textFieldThread);
        f.add(lblThread);
        f.add(btnStart);
        f.add(btnExport);
        f.add(taAccount);
        f.setSize(720,480);  
        f.setLayout(null);  
        f.setVisible(true);
        
    }

    public String email = "";
    public String pass = "";
    public WebDriver driver;
    public AuthFB authFB;
    public boolean done = false;
    
    @Override
    public void run() {
        try {
            driver =  new ChromeDriver();
            this.authFB = new AuthFB(this.driver,"http://45.117.169.77:1337/accountsfb/create" ,this.email, this.pass);
            authFB.login();
            arrWoker.add(this);
            while (!this.done) {  try {
                    Thread.sleep(100);
                    } catch (InterruptedException ex) {
                       
                    }
            }
            
            // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    
}
