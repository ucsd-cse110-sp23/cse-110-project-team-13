/*
 Email Page of SayIt.java
 Users can construct an email recipient by inputting information in the following fields. 
 Uses same structure as LoginFrame.java
 */

//importing packages
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SetupEmailFrame extends JFrame{

    Container container = getContentPane();

    //title label
    JLabel titleLabel = new JLabel("Email"); 

    //Labels for SetupEmail Page (Name, Display, Email, SMTP Host, Email Password, etc.)
    JLabel firstNameLabel = new JLabel("First Name:");
    JLabel lastNameLabel = new JLabel("Last Name:");
    JLabel displayLabel = new JLabel("Display:");
    JLabel emailLabel = new JLabel("Email:");
    JLabel SMTPhostLabel = new JLabel("SMTP Host:");
    JLabel TLSPortLabel = new JLabel("TLS Port:");
    JLabel EmailPasswordLabel = new JLabel("Email Password:");

    //Textfields for user input (Name, Display, Email, etc.)
    JTextField firstNameField = new JTextField();
    JTextField lastNameField = new JTextField();
    JTextField displayField = new JTextField();
    JTextField emailField = new JTextField();
    JTextField SMTPhostField = new JTextField();
    JTextField TLSPortField = new JTextField();
    JPasswordField EmailPasswordField = new JPasswordField();

    //Buttons for login page
    JButton cancelButton = new JButton("Cancel");
    JButton setupButton = new JButton("Setup");

    String email;

    //constructor for login page
    SetupEmailFrame(String email) {
        this.email = email;

        //setups the page
        this.setTitle("Setup Email Page");
        this.setVisible(true);
        this.setBounds(10, 10, 400, 600);

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
        titleLabel.setBounds(100, 50, 200, 30);
        titleLabel.setFont(new Font("Sans-serif", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(JLabel.CENTER); // Align the text to the center

        //labels
        firstNameLabel.setBounds(50,100,100,30);
        lastNameLabel.setBounds(50,150,100,30);
        displayLabel.setBounds(50,200,100,30);
        emailLabel.setBounds(50,250,100,30);
        SMTPhostLabel.setBounds(50,300,100,30);
        TLSPortLabel.setBounds(50,350,100,30);
        EmailPasswordLabel.setBounds(50,400,100,30);
       

        //text fields 
        firstNameField.setBounds(150,100,150,30);
        lastNameField.setBounds(150,150,150,30);
        displayField.setBounds(150,200,150,30);
        emailField.setBounds(150,250,150,30);
        SMTPhostField.setBounds(150,300,150,30);
        TLSPortField.setBounds(150,350,150,30);
        EmailPasswordField.setBounds(150,400,150,30);


        //buttons
        cancelButton.setBounds(50,500,100,30);
        setupButton.setBounds(250,500,100,30);
    }

    //add each components to the Container
    public void addComponentsToContainer() {

        //adding labels
        container.add(titleLabel);
        container.add(firstNameLabel);
        container.add(lastNameLabel);
        container.add(displayLabel);
        container.add(emailLabel);
        container.add(SMTPhostLabel);
        container.add(TLSPortLabel);
        container.add(EmailPasswordLabel);

        //addings textfields
        container.add(firstNameField);
        container.add(lastNameField);
        container.add(displayField);
        container.add(emailField);
        container.add(SMTPhostField);
        container.add(TLSPortField);
        container.add(EmailPasswordField);
       
        //add buttons
        container.add(cancelButton);
        container.add(setupButton); 

    }

    //add responses to the buttons
    public void addListeners() {
        setupButton.addMouseListener(
          new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //retrieve the input from all textfields
                String firstNameText = firstNameField.getText().strip();
                String lastNameText = lastNameField.getText().strip();
                String displayText = displayField.getText().strip();
                String emailText = emailField.getText().strip();
                String SMTPhostText = SMTPhostField.getText().strip();
                String TLSPortText = TLSPortField.getText().strip();
                String EmailPasswordText = EmailPasswordField.getText().strip();

                Boolean checkFields = firstNameText == "" || lastNameText == "" || displayText == "" || emailText == "" || SMTPhostText == "" || TLSPortText == "" || EmailPasswordText == "";
                //FIX ME: Add code for setup email using the given input 
                if (checkFields){
                  JOptionPane.showMessageDialog(null, "Please fill out all fields", "Error", JOptionPane.INFORMATION_MESSAGE);
                }
                else{
                  Update.updateSetupEmailInfo(email, firstNameText, lastNameText, displayText, emailText, SMTPhostText, TLSPortText, EmailPasswordText);
                  JOptionPane.showMessageDialog(null, "Your email information has been successfully created/updated!", "Success", JOptionPane.INFORMATION_MESSAGE);
                  closeFrame();
                }
            }
          }
        );


        cancelButton.addMouseListener(
          new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                closeFrame();        //close LoginFrame
            }
          }
        );
    }
}
