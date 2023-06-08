import java.io.IOException;
import javax.swing.JTextField;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//Tests for Registering for a SayIt Account
/*
 * Tests
 * User tries to register without inputting anything.
 * User tries to register with incorrect password verification.
 * User registers with a valid email and password input. 
 * User tries to register with an account that is already registered.
 */
public class RegisterTest {

    RegisterFrame testRegisterFrame;
    String testEmail;
    String testPassword; 
    String testVerifyPassword; 

    //User inputs tothing into the text fields. 
    @Test
    public void testBlankFields() throws Exception {
        //test blank input
        testEmail = "";
        testPassword = "";
        testVerifyPassword = "";

        //create instance of login frame
        testRegisterFrame = new RegisterFrame();
        testRegisterFrame.debugOn();

        //checks register returns false indicating incorrect input
        assertFalse(testRegisterFrame.register(testEmail, testPassword, testVerifyPassword));

        //close the frame
        this.testRegisterFrame.closeFrame();
    }

    //User inputs different password for verify password field. 
    @Test
    public void testDifferentPasswords() throws Exception {
        //test blank input
        testEmail = "JohnDoe@ucsd.edu";
        testPassword = "Password";
        testVerifyPassword = "WrongPassword";

        //create instance of login frame
        this.testRegisterFrame = new RegisterFrame();
        testRegisterFrame.debugOn();

        //checks register returns false indicating incorrect input
        assertFalse(testRegisterFrame.register(testEmail, testPassword, testVerifyPassword));

        //close the frame
        this.testRegisterFrame.closeFrame();
    }

    
    //User Registers for SayIt App
    @Test
    public void testValidEmail() throws Exception {
        //make a user
        testEmail = "JohnDoe@ucsd.edu";
        testPassword = "Password";
        testVerifyPassword = "Password";

        //test that the account successfully logins
        this.testRegisterFrame = new RegisterFrame();
        testRegisterFrame.debugOn();

        //checks that register was successful
        assertTrue(testRegisterFrame.register(testEmail, testPassword, testVerifyPassword )); 

        //checks that user is in database
        assertTrue(Read.userExists(testEmail)); 

        //close the frame
        this.testRegisterFrame.closeFrame();

        //clear database
        Delete.clearDatabase();
    } 

    //User tries to register for SayIt App using an already registered account
    @Test
    public void testAlreadyRegisteredEmail() throws Exception {
        //make a user that is not registered
        testEmail = "JohnDoe@ucsd.edu";
        testPassword = "Password";
        testVerifyPassword = "Password";

        //register the account
        Create.addLoginInfo(testEmail, testPassword); 

        //test that the account does not login
        this.testRegisterFrame = new RegisterFrame();
        testRegisterFrame.debugOn();

        //checks register returns false since account is already registered
        assertFalse(testRegisterFrame.register(testEmail, testPassword, testVerifyPassword)); 

        //checks user exists in the database
        assertTrue(Read.userExists(testEmail)); 

        //close the frame
        this.testRegisterFrame.closeFrame();

        //clear database
        Delete.clearDatabase();
    } 

}