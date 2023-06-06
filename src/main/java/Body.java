import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.io.*;
import java.util.ArrayList;

public class Body extends JPanel {
  Color backgroundColor = new Color(240, 248, 255);
  public JPanel prompt;
  public JPanel history;
  public DefaultListModel<String> model;
  public boolean micOpen;
  public ArrayList<Question> questions;
  public Recording recording;
  public Question currQuestion;
  private JScrollPane scrollHistory;

  Body(String username) {
    prompt = new JPanel();
    history = new JPanel();
    micOpen = false;
    questions = new ArrayList<Question>();
    recording = new Recording();

    // Set the layout manager of this JPanel to GridBagLayout
    this.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    
    // Set the background color of the prompt and history panels
    prompt.setBackground(Color.WHITE);
    history.setBackground(Color.WHITE);
    
    // Set the preferred sizes of the prompt and history panels
    prompt.setPreferredSize(new Dimension(400, 370));
    history.setPreferredSize(new Dimension(400, 1000));

    model = new DefaultListModel<String>();
    JList<String> qnaList = new JList<String>(model);
    qnaList.setVisibleRowCount(2);
    qnaList.setCellRenderer(new AlternatingRowRenderer());

    JPanel qnaPanel = new JPanel(new BorderLayout());
    JScrollPane qnaScroll = new JScrollPane(qnaList);
    qnaScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    qnaPanel.add(qnaScroll, BorderLayout.CENTER);

    prompt.setLayout(new BorderLayout());
    prompt.add(qnaPanel, BorderLayout.CENTER);
    
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

    loadQuestions();
  }

  //removes question(s) from question history 
  public void clearHistory() {
    for (Component c : history.getComponents()) {
      if (c instanceof Question) {
        history.remove(c); // remove the component
      }
    }
    questions.clear();
    model.clear();
    this.repaint(); 
    this.revalidate();
  }
  

  //Loads Previous Questions from Database
  public ArrayList<Question> loadQuestions() {

  } 

  public void voiceCommands() throws IOException, InterruptedException{
    if (micOpen == false){
      recording.openMicrophone();
      micOpen = true;
    }
    else{
      recording.closeMicrophone();
      micOpen = false;
      try {
        String transcript = TranscribeAudio.transcribeAudio("recording.wav");
        if (transcript.length() >= 10 && transcript.substring(0, 10).toLowerCase() == "question.") {
          model.clear();
          newQuestion(transcript.substring(10));
        }
        else if (transcript.toLowerCase() == "delete prompt") {
          history.remove(currQuestion);
          questions.remove(currQuestion);
          repaint();
          revalidate();
        }
        else if (transcript.toLowerCase() == "clear all") {
          clearHistory();
          model.clear();
          repaint(); 
          revalidate();
        }
      }
      catch (IOException | InterruptedException e){
        e.getStackTrace();
      }
    }
  }

  public void newQuestion(String transcript) throws IOException, InterruptedException{
    String question = transcript;
    model.clear();
    String generatedText = "";
    model.addElement(question);
    Question newQuestion = new Question();
    newQuestion.qName.setText(question);
    JButton doneButton = newQuestion.getDone(); 
    doneButton.addMouseListener(
      new MouseAdapter(){
        @Override
        public void mousePressed(MouseEvent e){
          history.remove(newQuestion);
          questions.remove(newQuestion);
          repaint(); 
          revalidate(); 
        }
      }
    );

    generatedText = ChatGPT.generateText(question, 2048);
    generatedText = generatedText.replace("\n", "");
    newQuestion.answer = generatedText;
    newQuestion.qName.addMouseListener(
      new MouseAdapter(){
        @Override
        public void mousePressed(MouseEvent e){
          model.clear();
          model.addElement(newQuestion.qName.getText());
          model.addElement(newQuestion.answer);
          currQuestion = newQuestion;
        }
      }
    );

    history.add(newQuestion);
    questions.add(newQuestion);
    model.addElement(generatedText);
    currQuestion = newQuestion;

    Create.addQuestionAndAnswer(question, generatedText);
    this.revalidate();
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