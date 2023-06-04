// import java.awt.BorderLayout;
// import java.awt.Color;
// import java.awt.Component;
// import java.awt.Dimension;
// import java.awt.Font;
// import java.awt.GridBagConstraints;
// import java.awt.GridBagLayout;
// import java.awt.GridLayout;
// import java.awt.event.MouseAdapter;
// import java.awt.event.MouseEvent;
// import java.io.BufferedReader;
// import java.io.FileReader;
// import java.io.FileWriter;
// import java.io.IOException;
// import java.util.ArrayList;
// import javax.swing.BorderFactory;
// import javax.swing.JButton;
// import javax.swing.JFrame;
// import javax.swing.JLabel;
// import javax.swing.JList;
// import javax.swing.JPanel;
// import javax.swing.JTextField;
// import javax.swing.SwingConstants;
// import javax.swing.DefaultListModel;
// import javax.swing.ListCellRenderer;
// import javax.swing.border.Border;
// import javax.swing.border.EmptyBorder;
// import javax.swing.Box;
// import javax.swing.Icon;
// import javax.swing.ImageIcon;
// import javax.swing.JScrollPane;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.io.*;
import java.util.ArrayList;

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
  JButton clearButton;
  JButton loadButton;
  JButton saveButton;

  Color backgroundColor = new Color(240, 248, 255);
  Border emptyBorder = BorderFactory.createEmptyBorder();

  Footer() {
    this.setPreferredSize(new Dimension(400, 60));
    this.setBackground(backgroundColor);
    this.setLayout(new GridLayout(1, 4));

    addButton = new JButton("New Question"); // add task button
    addButton.setFont(new Font("Sans-serif", Font.ITALIC, 8)); // set font
    this.add(addButton); // add to footer

    clearButton = new JButton("Clear All"); // clear button
    clearButton.setFont(new Font("Sans-serif", Font.ITALIC, 8)); // set font
    this.add(clearButton); // add to footer

    loadButton = new JButton("Load Questions");
    loadButton.setFont(new Font("Sans-serif", Font.ITALIC, 8)); // set font
    this.add(loadButton);

    saveButton = new JButton("Save Questions");
    saveButton.setFont(new Font("Sans-serif", Font.ITALIC, 8)); // set font
    this.add(saveButton);
  }

  public JButton getAddButton() {
    return addButton;
  }

  public JButton getClearButton() {
    return clearButton;
  }

  public JButton getLoadButton() {
    return loadButton;
  }

  public JButton getSaveButton() {
    return saveButton;
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
  private JButton loadButton;
  private JButton saveButton;

  AppFrame() {
    this.setSize(400, 600); // 400 width and 600 height
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close on exit
    this.setVisible(true); // Make visible

    header = new Header();
    footer = new Footer();
    body = new Body();

    this.add(header, BorderLayout.NORTH); // Add title bar on top of the screen
    this.add(footer, BorderLayout.SOUTH); // Add footer on bottom of the screen
    this.add(body, BorderLayout.CENTER); // Add body in middle of footer and title

    addButton = footer.getAddButton();
    clearButton = footer.getClearButton();
    loadButton = footer.getLoadButton();
    saveButton = footer.getSaveButton();

    addListeners();
    this.revalidate();
  }

  public void addListeners() {
    addButton.addMouseListener(
      new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
          try {
            body.newQuestion();
          }
          catch (IOException | InterruptedException e2){
            e2.getStackTrace();
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


    //load previous questions
    loadButton.addMouseListener(
      new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e){
          ArrayList<Question> questionList = new ArrayList<Question>();
          questionList = body.loadQuestions();
          for (int i = 0; i  < questionList.size(); i++) {
            Question newQuestion = questionList.get(i);
            body.history.add(newQuestion); 
            body.questions.add(newQuestion);

            //Delete question from history
            JButton doneButton = newQuestion.getDone(); 
            doneButton.addMouseListener(
              new MouseAdapter(){
                @Override
                public void mousePressed(MouseEvent e2){
                  body.history.remove(newQuestion);
                  body.questions.remove(newQuestion);
                  body.repaint(); 
                  revalidate(); 
                }
              }
            );
            newQuestion.qName.addMouseListener(
              new MouseAdapter(){
                @Override
                public void mousePressed(MouseEvent e){
                  body.model.clear();
                  body.model.addElement(newQuestion.qName.getText());
                  body.model.addElement(newQuestion.answer);
                }
              }
            );
          }
        }
      }
    );

    saveButton.addMouseListener(
      new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e){
          body.saveQuestions();
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