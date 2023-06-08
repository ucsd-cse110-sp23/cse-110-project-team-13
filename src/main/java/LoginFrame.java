/*Citation
Website: https://www.tutorialsfield.com/login-form-in-java-swing-with-source-code/
Title: Login Form in Java Swing with Source Code Tutorial
Data Accessed: 5/30/22
Source Use: Learning and Template
Initials: JS
*/

//importing packages
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//Login Frame Class
public class LoginFrame extends JFrame {

    Container container = getContentPane();

    //Labels for Login Page (Title, Username, Password)
    JLabel pageLabel = new JLabel("Sign In");
    JLabel userLabel = new JLabel("Username");
    JLabel passwordLabel = new JLabel("Password");

    //Textfields for user input (username, password)
    JTextField userTextField = new JTextField();
    JPasswordField passwordField = new JPasswordField();

    //Buttons for login page
    JButton loginButton = new JButton("Login");
    JButton registerButton = new JButton("Register");
    JCheckBox loginAutoButton = new JCheckBox("Login Automatically");

    //constructor for login page
    LoginFrame() {

        //setups the page
        this.setTitle("Sign In Page");
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
        this.setVisible(false);
        this.dispose();
    }

    //layout of page
    public void setLayoutManager() {
        container.setLayout(null);

        //set color of the page to match the SayIt App
        Color backgroundColor = new Color(240, 248, 255);
        container.setBackground(backgroundColor);
        loginAutoButton.setBackground(backgroundColor);
    }

    //sets the postions and sizes of each component in Login Page
    public void setLocationAndSize() {

        //page title
        pageLabel.setBounds(100, 50, 200, 30);
        pageLabel.setFont(new Font("Sans-serif", Font.BOLD, 20));
        pageLabel.setHorizontalAlignment(JLabel.CENTER); // Align the text to the center

        //user and password label
        userLabel.setBounds(50,100,100,30);
        passwordLabel.setBounds(50,150,100,30);

        //text fields for user and password
        userTextField.setBounds(150,100,150,30);
        passwordField.setBounds(150,150,150,30);

        //buttons
        loginAutoButton.setBounds(100,200,150,30);
        loginButton.setBounds(200,250,100,30);
        registerButton.setBounds(50,250,100,30);
    }

    //add each components to the Container
    public void addComponentsToContainer() {
        container.add(pageLabel);
        container.add(userLabel);
        container.add(passwordLabel);
        container.add(userTextField);
        container.add(passwordField);
        container.add(loginAutoButton);
        container.add(loginButton);
        container.add(registerButton);
    }

    public boolean login(String userText, String pwdText){
      if (Read.successfulLogin(userText, pwdText)){
        if (loginAutoButton.isSelected() == true) {
          Update.automaticallyLog(userText);
        }
        else {
          Update.manuallyLog(userText);
        }
        new AppFrame(userText);
        closeFrame();
        return true;
      }
      else {
        JOptionPane.showMessageDialog(null, "Wrong email or password.", "Error", JOptionPane.INFORMATION_MESSAGE);
        return false;
      }
    }

    //Testing Code Methods (Getter Methods)
    public Component getUserTextField() {
      return this.userTextField;
    }
    public Component getPasswordField() {
      return this.passwordField; 
    }
    public Component getAutomaticLogin() {
      return this.loginAutoButton; 
    }

    //add responses to the buttons
    public void addListeners() {
        loginButton.addMouseListener(
          new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //retrieve the input for user and password
                String userText = userTextField.getText().strip();
                String pwdText = passwordField.getText().strip();

                login(userText, pwdText);
            }
          }
        );

        registerButton.addMouseListener(
          new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                new RegisterFrame(); //opens Register Page
                closeFrame();        //close LoginFrame
            }
          }
        );
    }

    public static void main(String[] args) {
        new LoginFrame(); 
    } 
}
