import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.io.*;

class Question extends JPanel {

  JLabel index;         //might be needed
  JTextField qName;  //text of the question
  JButton doneButton;   //delete question
  String answer;

  Color gray = new Color(218, 229, 234);

  Question() {
    this.setPreferredSize(new Dimension(400, 20)); // set size of task
    this.setBackground(gray); // set background color of task
    this.setLayout(new BorderLayout()); // set layout of task

    answer = "";

    index = new JLabel(""); // create index label
    index.setPreferredSize(new Dimension(20, 20)); // set size of index label
    index.setHorizontalAlignment(JLabel.CENTER); // set alignment of index label
    this.add(index, BorderLayout.WEST); // add index label to task

    qName = new JTextField(""); // create task name text field
    qName.setBorder(BorderFactory.createEmptyBorder()); // remove border of text field
    qName.setBackground(gray); // set background color of text field
    qName.setEditable(false);
    this.add(qName, BorderLayout.CENTER);

    doneButton = new JButton("Delete");
    doneButton.setPreferredSize(new Dimension(120, 20));
    doneButton.setBorder(BorderFactory.createEmptyBorder());
    doneButton.setFocusPainted(false);
    this.add(doneButton, BorderLayout.EAST);
  }

  public JButton getDone() {
    return doneButton;
  }
}

class Footer extends JPanel {

  JButton addButton;

  Color backgroundColor = new Color(240, 248, 255);
  Border emptyBorder = BorderFactory.createEmptyBorder();

  Footer() {
    this.setPreferredSize(new Dimension(400, 60));
    this.setBackground(backgroundColor);
    this.setLayout(new GridLayout(1, 4));

    addButton = new JButton("New Question"); // add task button
    addButton.setFont(new Font("Sans-serif", Font.ITALIC, 8)); // set font
    this.add(addButton); // add to footer
  }

  public JButton getAddButton() {
    return addButton;
  }
}

class Header extends JPanel {
  Color backgroundColor = new Color(240, 248, 255);

  Header() {
    this.setPreferredSize(new Dimension(400, 60)); // Size of the header
    this.setBackground(backgroundColor);
    JLabel titleText = new JLabel("SayIt App"); // Text of the header
    titleText.setPreferredSize(new Dimension(200, 60));
    titleText.setFont(new Font("Sans-serif", Font.BOLD, 20));
    titleText.setHorizontalAlignment(JLabel.CENTER); // Align the text to the center
    this.add(titleText); // Add the text to the header
  }
}

class AppFrame extends JFrame {

  private Header header;
  private Footer footer;
  private Body body;

  private JButton addButton;
  private JButton clearButton;

  AppFrame(String username) {
    this.setSize(400, 600); // 400 width and 600 height
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close on exit
    this.setVisible(true); // Make visible

    header = new Header();
    footer = new Footer();
    body = new Body(username);

    this.add(header, BorderLayout.NORTH); // Add title bar on top of the screen
    this.add(footer, BorderLayout.SOUTH); // Add footer on bottom of the screen
    this.add(body, BorderLayout.CENTER); // Add body in middle of footer and title

    addButton = footer.getAddButton();

    addListeners();
    this.revalidate();
  }

  public void addListeners() {
    addButton.addMouseListener(
      new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
          try {
            body.voiceCommands();
          }
          catch (IOException | InterruptedException e2){
            System.out.println("Error");
          }
        }
      }
    );

    //clear all questions from history
    clearButton.addMouseListener(
      new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
          body.clearHistory();
        }
      }
    );
  }
}

public class SayIt {
  public static void main(String args[]) {
    //Launches App by first Logging In
    LoginFrame frame = new LoginFrame();
  }
} 