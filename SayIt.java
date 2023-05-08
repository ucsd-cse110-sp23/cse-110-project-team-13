import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

//required for icons if needed
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;

//required for scrolling
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


class Question extends JPanel {

  JLabel index;         //might be needed
  JTextField taskName;  //text of the question
  JButton doneButton;   //delete question
  JButton answerButton; //ask the question again

  Color gray = new Color(218, 229, 234);
  Color green = new Color(188, 226, 158);

  private boolean markedDone;

  Question() {
    this.setPreferredSize(new Dimension(400, 20)); // set size of task
    this.setBackground(gray); // set background color of task

    this.setLayout(new BorderLayout()); // set layout of task

    markedDone = false;

    index = new JLabel(""); // create index label
    index.setPreferredSize(new Dimension(20, 20)); // set size of index label
    index.setHorizontalAlignment(JLabel.CENTER); // set alignment of index label
    this.add(index, BorderLayout.WEST); // add index label to task

    taskName = new JTextField(""); // create task name text field
    taskName.setBorder(BorderFactory.createEmptyBorder()); // remove border of text field
    taskName.setBackground(gray); // set background color of text field

    this.add(taskName, BorderLayout.CENTER);

    doneButton = new JButton("Delete Question");
    doneButton.setPreferredSize(new Dimension(120, 20));
    doneButton.setBorder(BorderFactory.createEmptyBorder());
    doneButton.setFocusPainted(false);

    //TODO: Implement answer button 
    //answerButton = new JButton(); 
    
    this.add(doneButton, BorderLayout.EAST);
  }

  //Methods from Lab 5 used for Project (may be removed)
  public void changeIndex(int num) {
    this.index.setText(num + ""); // num to String
    this.revalidate(); // refresh
  }

  public JButton getDone() {
    return doneButton;
  }

  public boolean getState() {
    return markedDone;
  }

  public void changeState() {
    if (markedDone == true) {
      this.setBackground(gray);
      taskName.setBackground(gray);
      markedDone = false;
    }
    else {
      this.setBackground(green);
      taskName.setBackground(green);
      markedDone = true;
    }
    revalidate();
  }
}


class Body extends JPanel {

  Color backgroundColor = new Color(240, 248, 255);
  private JPanel prompt;
  public JPanel history; //used to be private

  //Scroll Panes for history 
  public JScrollPane scrollHistory;

  Body() {
    prompt = new JPanel();
    history = new JPanel();

    // Set the layout manager of this JPanel to GridBagLayout
    this.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    
    // Set the background color of the prompt and history panels
    prompt.setBackground(Color.WHITE);
    history.setBackground(Color.WHITE);
    
    // Set the preferred sizes of the prompt and history panels
    prompt.setPreferredSize(new Dimension(400, 370));
    history.setPreferredSize(new Dimension(400, 1000));
    
    // Add the prompt panel to this JPanel, taking up 2/3 of the available space
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 1;
    gbc.weighty = 0.666;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.insets.set(10, 10, 5, 10);
    this.add(prompt, gbc);
    

    //Adding scrolling features
    scrollHistory = new JScrollPane(this.history);
    scrollHistory.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    scrollHistory.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    //scrollHistory.setPreferredSize(new Dimension(100, 10000));
    
    // Add the history panel to this JPanel, taking up 1/3 of the available space
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weightx = 1;
    gbc.weighty = 0.333;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.insets.set(5, 10, 10, 10);
    this.add(scrollHistory, gbc);
  
    this.setPreferredSize(new Dimension(400, 960));
    this.setBackground(backgroundColor);
  }

  //Methods from Lab 5 used for Project (may be removed)
  //updates number in question history
  public void updateNumbers() {
    Component[] listItems = this.getComponents();

    for (int i = 0; i < listItems.length; i++) {
      if (listItems[i] instanceof Question) {
        ((Question) listItems[i]).changeIndex(i + 1);
      }
    }
  }

  //removes question(s) from question history 
  public void removeQuestionHistory() {
    for (Component c : history.getComponents()) {
      if (c instanceof Question) {
        history.remove(c); // remove the component
      }
    }
  }
  

  //Loads Previous Questions from Text File
  public ArrayList<Question> loadQuestions() {
    ArrayList<Question> questionList = new ArrayList<Question>();

    try{
      FileReader reader = new FileReader("questions.txt");
      BufferedReader buffer = new BufferedReader(reader);
      String line = "";

      while (line != null){
        line = buffer.readLine();
        if (line == null) {
          break; 
        }
        Question question = new Question();
        question.taskName.setText(line);
        questionList.add(question);
      }

      buffer.close();
      reader.close();

      this.updateNumbers();
      this.revalidate();
    }
    catch(Exception e){
      e.getStackTrace();
    }

    return questionList; 
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
  private Body list;
  private ArrayList<Question> taskList;

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
    list = new Body();

    this.add(header, BorderLayout.NORTH); // Add title bar on top of the screen
    this.add(footer, BorderLayout.SOUTH); // Add footer on bottom of the screen
    this.add(list, BorderLayout.CENTER); // Add list in middle of footer and title

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
        @override
        public void mousePressed(MouseEvent e) {
          
        }
      }
    );

    //clear all questions from history
    clearButton.addMouseListener(
      new MouseAdapter() {
        @override
        public void mousePressed(MouseEvent e) {
          list.removeQuestionHistory();
          repaint(); 
          revalidate();
        }
      }
    );


    //load previous questions
    loadButton.addMouseListener(
      new MouseAdapter() {
        @override
        public void mousePressed(MouseEvent e){
          ArrayList<Question> questionList = new ArrayList<Question>();
          questionList = list.loadQuestions();
          for (int i = 0; i  < questionList.size(); i++) {
            Question newQuestion = questionList.get(i);
            list.history.add(newQuestion); 

            //Delete question from history
            JButton doneButton = newQuestion.getDone(); 
            doneButton.addMouseListener(
              new MouseAdapter(){
                @override
                public void mousePressed(MouseEvent e2){
                  list.history.remove(newQuestion);
                  list.repaint(); 
                  revalidate(); 
                }
              }
            );
          }
          
        }
      }
    );

    saveButton.addMouseListener(
      new MouseAdapter() {
        @override
        public void mousePressed(MouseEvent e){
          
        }
      }
    );
  }
}

public class SayIt {

  public static void main(String args[]) {
    new AppFrame(); // Create the frame
  }
}

@interface override {
}