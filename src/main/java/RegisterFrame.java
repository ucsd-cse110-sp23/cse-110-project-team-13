/*
Register Page of SayItApp
Users have to input an email and password in order to register for an account on SayIt App.
Same structure as LoginFrame.java
*/

//importing packages
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;

//Register Frame Class
public class RegisterFrame extends JFrame {

    Container container = getContentPane();

    //Labels for Register Page (Title, Username, Password)
    JLabel pageLabel = new JLabel("Create Account");
    JLabel emailLabel = new JLabel("Email");
    JLabel passwordLabel = new JLabel("Password");
    JLabel verifyPasswordLabel = new JLabel(" Verify Password");

    //Textfields for user input (username, password)
    JTextField emailTextField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JPasswordField verifyPasswordField = new JPasswordField();

    //Buttons for login page
    JButton createAccountButton = new JButton("Create Account");
    JButton cancelButton = new JButton("Cancel");

    //constructor for login page
    RegisterFrame() {

        //setups the page
        this.setTitle("Register Page");
        this.setVisible(true);
        this.setBounds(10, 10, 400, 400);

        this.addListeners();
        this.revalidate();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        //calling methods
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
    }

    //close the frame
    public void closeFrame() {
        //Close the Login Page
        this.setVisible(false);
        this.dispose();
    }

    //layout of page
    public void setLayoutManager() {
        container.setLayout(null);

        //set color of the page to match the SayIt App
        Color backgroundColor = new Color(240, 248, 255);
        container.setBackground(backgroundColor);
    }

    //sets the postions and sizes of each component in Login Page
    public void setLocationAndSize() {

        //page title
        pageLabel.setBounds(100, 50, 200, 30);
        pageLabel.setFont(new Font("Sans-serif", Font.BOLD, 20));
        pageLabel.setHorizontalAlignment(JLabel.CENTER); // Align the text to the center

        //email, password, and verify password label
        emailLabel.setBounds(50,100,100,30);
        passwordLabel.setBounds(50,150,100,30);
        verifyPasswordLabel.setBounds(50, 200, 100, 30);

        //text fields for email, password, and verify password
        emailTextField.setBounds(150,100,150,30);
        passwordField.setBounds(150,150,150,30);
        verifyPasswordField.setBounds(150, 200, 150, 30);

        //buttons
        createAccountButton.setBounds(50,250,130,30);
        cancelButton.setBounds(200,250,100,30);
    }

    //add each components to the Container
    public void addComponentsToContainer() {
        container.add(pageLabel);
        container.add(emailLabel);
        container.add(verifyPasswordLabel);
        container.add(passwordLabel);
        container.add(emailTextField);
        container.add(verifyPasswordField);
        container.add(passwordField);
        container.add(cancelButton);
        container.add(createAccountButton);
    }

    //add responses to the buttons
    public void addListeners() {
        createAccountButton.addMouseListener(
          new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //retrieve the input for email, password, verify password
                String emailText = emailTextField.getText();
                String pwdText = passwordField.getText();
                String verifypwdText = verifyPasswordField.getText();

                //check that the verify password matches the password text
                if (pwdText.equals(verifypwdText)){

                    //FIX ME: Add code for registering account to database

                    new AppFrame();     //opens SayIt App after registering account
                    closeFrame();       //close screen and return back to login page

                }
            }
          }
        );

        cancelButton.addMouseListener(
          new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                new LoginFrame();   //open new LoginFrame();
                closeFrame();       //close screen and return back to login page
            }
          }
        );

    }
}

