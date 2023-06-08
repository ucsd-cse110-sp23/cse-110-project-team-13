import java.io.IOException;
import javax.swing.JTextField;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


//Tests for Logging Into SayIt App
/*
 * Tests
 * User tries to login without inputting anything.
 * User tries to login with an invalid (not registered) account.
 * User logins wth wrong password, but valid account. 
 * User logins with valid email and password.
 * User logins and clicks (want to use) automatic login.
 * User logins and unclicks (doesn't want to use) automatic login. 
 */
public class LoginTest {

    LoginFrame testLoginFrame;
    String testUser;
    String testPassword; 

    //User inputs nothing and attempts to login. 
    @Test
    public void testBlankFields() throws Exception {
        //test blank input
        testUser = "";
        testPassword = "";

        //create instance of login frame
        this.testLoginFrame = new LoginFrame();
        testLoginFrame.debugOn();

        //login returns false since user is not able to login due to no input
        assertFalse(testLoginFrame.login(testUser, testPassword));

        //close the frame
        this.testLoginFrame.closeFrame();
    }

    //User tries to login with an invalid account.
    @Test
    public void testInvalidEmail() throws Exception {
        //make a user that is not registered
        testUser = "TestNewUser";
        testPassword = "TestNewPassword"; 

        //test that the account does not login
        this.testLoginFrame = new LoginFrame();
        testLoginFrame.debugOn();

        //login returns false since user's account doesn't exist
        assertFalse(testLoginFrame.login(testUser, testPassword)); 

        //checks database that there is no record of account
        assertFalse(Read.userExists(testUser)); 

        //close the frame
        this.testLoginFrame.closeFrame();
    } 

    //User logins with valid email but incorrect password. 
    @Test
    public void testWrongPassword() throws Exception {
        //make a user 
        testUser = "TestNewUser";
        testPassword = "TestNewPassword";

        //create the account
        Create.addLoginInfo(testUser, testPassword); 

        //test that the account successfully logins
        this.testLoginFrame = new LoginFrame();
        testLoginFrame.debugOn();

        //login function returns false since password is incorrrect
        assertFalse(testLoginFrame.login(testUser, "WrongPassword")); 

        //checks that account is in database
        assertTrue(Read.userExists(testUser));

        //checks that account is not able to login through database due to wrong password
        assertFalse(Read.successfulLogin(testUser, "WrongPassword"));

        //close the frame
        this.testLoginFrame.closeFrame();

        //clear the database
        Delete.clearDatabase();
    }

    //User finally logins with a valid email and password. 
    @Test
    public void testValidEmail() throws Exception {
        //make a user
        testUser = "TestNewUser";
        testPassword = "TestNewPassword";

        //register the account
        Create.addLoginInfo(testUser, testPassword); 

        //test that the account successfully logins
        this.testLoginFrame = new LoginFrame();
        testLoginFrame.debugOn();

        //login returns true indicating that user is able to login
        assertTrue(testLoginFrame.login(testUser, testPassword)); 

        //checks that account is in database
        assertTrue(Read.userExists(testUser));

        //checks that user is able to successfully login
        assertTrue(Read.successfulLogin(testUser, testPassword)); 

        //close the frame
        this.testLoginFrame.closeFrame();

        //clear the database
        Delete.clearDatabase();
    } 

    
    
    //User wants to remain automatically logged into SayIt App. 
    @Test
    public void testYesAutomaticLogin() throws Exception {
        //make a user
        testUser = "TestNewUser";
        testPassword = "TestNewPassword";

        //register the account
        Create.addLoginInfo(testUser, testPassword);
        
        //test that the account successfully logins
        this.testLoginFrame = new LoginFrame();
        JCheckBox checkBoxTest = (JCheckBox)testLoginFrame.getAutomaticLogin(); 
        checkBoxTest.setSelected(true);

        //check login works and also inputs true textbox
        assertTrue(testLoginFrame.login(testUser, testPassword)); 

        //String returned is user email or null if user didn't check auto-login
        String returnAuto = Read.checkAutomaticLogin(); 

        //read.java checks if user has selected automatic login
        assertEquals(returnAuto, testUser); 

        //Update.manuallyLog(testUser);

        //clear the database
        Delete.clearDatabase();

        

    }

    //method to ensure that the user switches back to logout auto before running each test
    @After
    public void after(){
        Update.manuallyLog(testUser);
    }


    //User does not want to remain automatically logged into SayIt App. 
    @Test
    public void testNoAutomaticLogin() throws Exception {

        //make a user
        testUser = "TestNewUser";
        testPassword = "TestNewPassword";

        //register the account
        Create.addLoginInfo(testUser, testPassword);
        
        //test that the account successfully logins
        this.testLoginFrame = new LoginFrame();
        JCheckBox checkBoxTest = (JCheckBox)testLoginFrame.getAutomaticLogin(); 
        checkBoxTest.setSelected(false);

        //check login works and also inputs true textbox
        assertTrue(testLoginFrame.login(testUser, testPassword)); 

        //String returned is user email or null if user didn't check auto-login
        String returnAuto = Read.checkAutomaticLogin(); 

        //read.java checks if user has selected automatic login
        assertNull(returnAuto); 

        //clear the database
        Delete.clearDatabase();

    } 
    

}