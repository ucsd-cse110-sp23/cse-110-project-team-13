import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.io.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Body extends JPanel {
  Color backgroundColor = new Color(240, 248, 255);
  public JPanel prompt;
  public JPanel history;
  public DefaultListModel<String> model;
  public boolean micOpen;
  public Recording recording;
  public Question currQuestion;
  private JScrollPane scrollHistory;
  public String appEmail;

  Body(String username) {
    prompt = new JPanel();
    history = new JPanel();
    micOpen = false;
    recording = new Recording();
    appEmail = username;

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
    model.clear();
    this.repaint(); 
    this.revalidate();
    Delete.clearAll(appEmail);
  }
  

  //Loads Previous Questions from Database
  public void loadQuestions() {
    ArrayList<Question> questionList = new ArrayList<Question>();
    questionList = Read.readUserChatDataByEmail(appEmail);
    for (Question q : questionList) {
      history.add(q);
      q.qName.addMouseListener(
        new MouseAdapter(){
          @Override
          public void mousePressed(MouseEvent e){
            model.clear();
            model.addElement(q.qName.getText());
            model.addElement(q.answer);
            currQuestion = q;
          }
        }
      );
    }
  } 

  public void voiceCommands(String filePath) throws IOException, InterruptedException{
    if (micOpen == false){
      recording.openMicrophone();
      micOpen = true;
    }
    else{
      if (filePath == null) {
        recording.closeMicrophone();
        micOpen = false;
      }
      try {
        String transcript;
        if (filePath == null)
          transcript = TranscribeAudio.transcribeAudio("recording.wav").strip();
        else
          transcript = TranscribeAudio.transcribeAudio(filePath).strip();
        if (transcript.length() >= 10 && transcript.substring(0, 8).toLowerCase().equals("question")) {
          newQuestion(transcript.substring(9), false);
        }
        else if (transcript.length() >= 20 && 
        transcript.substring(0, 12).toLowerCase().equals("create email")) {
          newQuestion(transcript, true);
        }
        else if (transcript.length() >= 15 && 
        transcript.substring(0, 10).toLowerCase().equals("send email")) {
          sendEmail(transcript.substring(11));
        }
        else if (transcript.toLowerCase().equals("delete prompt") || transcript.toLowerCase().equals("delete prompt.")) {
          if (currQuestion == null){
            JOptionPane.showMessageDialog(null, "There is no prompt to delete", "Error", JOptionPane.INFORMATION_MESSAGE);
            return;
          }
          history.remove(currQuestion);
          model.clear();
          repaint();
          revalidate();
          Delete.clearOne(currQuestion.qName.getText(), appEmail);
        }
        else if (transcript.toLowerCase().equals("clear all") || transcript.toLowerCase().equals("clear all.")) {
          clearHistory();
          model.clear();
          repaint(); 
          revalidate();
          Delete.clearAll(appEmail);
        }
        else if (transcript.toLowerCase().equals("setup email") || transcript.toLowerCase().equals("set up email") || 
        transcript.toLowerCase().equals("set up email.") || transcript.toLowerCase().equals("setup email.")) {
          new SetupEmailFrame(appEmail);
        }
        else{
          JOptionPane.showMessageDialog(null, "Sorry, I couldn't understand you. Your message was:\n" + transcript, "Error", JOptionPane.INFORMATION_MESSAGE);
          return;
        }
      }
      catch (IOException | InterruptedException e){
        e.getStackTrace();
      }
    }
  }

  public void newQuestion(String transcript, Boolean makeEmail) throws IOException, InterruptedException{
    String question = transcript;
    model.clear();
    String generatedText = "";
    model.addElement(question);
    Question newQuestion = new Question();
    newQuestion.qName.setText(question);
    generatedText = ChatGPT.generateText(question, 2048);
    generatedText = generatedText.trim();
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
    model.addElement(generatedText);
    currQuestion = newQuestion;

    if (makeEmail == true){
      newQuestion.isEmail = true;
      Create.addEmail(question, generatedText, appEmail);
    }
    else{
      newQuestion.isEmail = false;
      Create.addQuestionAndAnswer(question, generatedText, appEmail);
    }
    
    this.revalidate();
  }

  public void sendEmail(String transcript){
    if (currQuestion.isEmail == false){
      JOptionPane.showMessageDialog(null, "Please select an email and not a prompt", "Error", JOptionPane.INFORMATION_MESSAGE);
      return;
    }

    // find email from transcript

    String[] emailInfo = new String[7];
    emailInfo = Read.sendEmailInfo(appEmail);
    if (emailInfo == null){
      JOptionPane.showMessageDialog(null, "Please setup your email first with the 'Setup Email' voice command", "Error", JOptionPane.INFORMATION_MESSAGE);
      return;
    }
    else {
      // Send email logic
    }
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