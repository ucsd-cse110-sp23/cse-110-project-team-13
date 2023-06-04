import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.DefaultListModel;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
//required for icons if needed
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;

//required for scrolling
import javax.swing.JScrollPane;

class Question extends JPanel {

  JLabel index;         //might be needed
  JTextField taskName;  //text of the question
  JButton doneButton;   //delete question
  String answer;

  Color gray = new Color(218, 229, 234);
  Color green = new Color(188, 226, 158);

  Question() {
    this.setPreferredSize(new Dimension(400, 20)); // set size of task
    this.setBackground(gray); // set background color of task

    this.setLayout(new BorderLayout()); // set layout of task

    answer = "";

    index = new JLabel(""); // create index label
    index.setPreferredSize(new Dimension(20, 20)); // set size of index label
    index.setHorizontalAlignment(JLabel.CENTER); // set alignment of index label
    this.add(index, BorderLayout.WEST); // add index label to task

    taskName = new JTextField(""); // create task name text field
    taskName.setBorder(BorderFactory.createEmptyBorder()); // remove border of text field
    taskName.setBackground(gray); // set background color of text field
    taskName.setEditable(false);
    this.add(taskName, BorderLayout.CENTER);

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
  private Body list;

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
        @Override
        public void mousePressed(MouseEvent e) {
          if (list.micOpen == false){
            list.recording.openMicrophone();
            list.micOpen = true;
          }
          else{
            list.recording.closeMicrophone();
            list.micOpen = false;
            try {
              String transcript = TranscribeAudio.transcribeAudio("recording.wav");
              if (transcript.length() >= 10 && transcript.substring(0, 10).toLowerCase() == "question.") {
                list.model.clear();
                list.newQuestion(transcript.substring(10));
              }
              else if (transcript.toLowerCase() == "delete prompt") {
                list.history.remove(list.currQuestion);
                list.questions.remove(list.currQuestion);
                repaint();
                revalidate();
              }
              else if (transcript.toLowerCase() == "clear all") {
                list.removeQuestionHistory();
                list.model.clear();
                repaint(); 
                revalidate();
              }
            }
            catch (IOException | InterruptedException e2){
              System.out.println("Error");
            }
          }
        }
      }
    );

    //clear all questions from history
    clearButton.addMouseListener(
      new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
          list.removeQuestionHistory();
          list.model.clear();
          repaint(); 
          revalidate();
        }
      }
    );


    //load previous questions
    loadButton.addMouseListener(
      new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e){
          ArrayList<Question> questionList = new ArrayList<Question>();
          questionList = list.loadQuestions();
          for (int i = 0; i  < questionList.size(); i++) {
            Question newQuestion = questionList.get(i);
            list.history.add(newQuestion); 
            list.questions.add(newQuestion);

            //Delete question from history
            JButton doneButton = newQuestion.getDone(); 
            doneButton.addMouseListener(
              new MouseAdapter(){
                @Override
                public void mousePressed(MouseEvent e2){
                  list.history.remove(newQuestion);
                  list.questions.remove(newQuestion);
                  list.repaint(); 
                  revalidate(); 
                }
              }
            );
            newQuestion.taskName.addMouseListener(
              new MouseAdapter(){
                @Override
                public void mousePressed(MouseEvent e){
                  list.model.clear();
                  list.model.addElement(newQuestion.taskName.getText());
                  list.model.addElement(newQuestion.answer);
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
          list.saveQuestions();
        }
      }
    );
  }
}

class AlternatingRowRenderer extends JLabel implements ListCellRenderer<String> {
  private static final Color EVEN_ROW_COLOR = Color.WHITE;
  private static final Color ODD_ROW_COLOR = new Color(240, 240, 240);

  AlternatingRowRenderer() {
    setOpaque(true);
    setBorder(new EmptyBorder(5, 10, 5, 10));
  }

  @Override
  public Component getListCellRendererComponent(JList<? extends String> list, String value, int index, boolean isSelected, boolean cellHasFocus) {
    setText(value);
    if (index % 2 == 0) {
      setBackground(EVEN_ROW_COLOR);
      setHorizontalAlignment(SwingConstants.LEFT);
    } 
    else {
      setBackground(ODD_ROW_COLOR);
      setHorizontalAlignment(SwingConstants.RIGHT);
    }
    return this;
  }
}

public class SayIt {

  public static void main(String args[]) {
    new AppFrame(); // Create the frame
  }
}