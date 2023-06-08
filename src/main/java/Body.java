import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.io.*;
import java.util.ArrayList;
import java.util.Currency;

public class Body extends JPanel {
  Color backgroundColor = new Color(240, 248, 255);
  public JPanel prompt;
  public JPanel history;
  public boolean micOpen;
  public Recording recording;
  public Question currQuestion;
  private JScrollPane scrollHistory;
  public String appEmail;
  public JTextArea questionPanel;
  public JTextArea answerPanel;
  private Boolean debug;

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

    questionPanel = new JTextArea();
    questionPanel.setLineWrap(true);
    questionPanel.setEditable(false);
    questionPanel.setBorder(BorderFactory.createCompoundBorder(
      questionPanel.getBorder(), 
      BorderFactory.createEmptyBorder(5, 5, 5, 5)));

    answerPanel = new JTextArea();
    answerPanel.setBackground(new Color(240, 240, 240));
    answerPanel.setLineWrap(true);
    answerPanel.setEditable(false);
    answerPanel.setBorder(BorderFactory.createCompoundBorder(
      answerPanel.getBorder(), 
      BorderFactory.createEmptyBorder(5, 5, 5, 5)));

    JPanel qnaPanel = new JPanel(new GridLayout(2, 1));
    qnaPanel.add(questionPanel);
    qnaPanel.add(answerPanel);

    JScrollPane qnaScroll = new JScrollPane(qnaPanel);
    qnaScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    prompt.setLayout(new BorderLayout());
    prompt.add(qnaScroll, BorderLayout.CENTER);
    
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
    clearPanels();
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
            clearPanels();
            if (q.isEmail){
              questionPanel.setText(q.qName.getText().substring(7));
            }
            else{
              questionPanel.setText(q.qName.getText().substring(10));
            }
            answerPanel.setText(q.answer);
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
          sendEmail(transcript);
        }
        else if (transcript.toLowerCase().equals("delete prompt") || transcript.toLowerCase().equals("delete prompt.")) {
          if (currQuestion == null){
            JOptionPane.showMessageDialog(null, "There is no prompt to delete", "Error", JOptionPane.INFORMATION_MESSAGE);
            return;
          }
          history.remove(currQuestion);
          clearPanels();
          repaint();
          revalidate();
          Delete.clearOne(currQuestion.qName.getText(), appEmail);
        }
        else if (transcript.toLowerCase().equals("clear all") || transcript.toLowerCase().equals("clear all.")) {
          clearHistory();
          clearPanels();
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
    String question = transcript.strip();
    clearPanels();
    String generatedText = "";
    
    questionPanel.setText(question);
    Question newQuestion = new Question();
    if (makeEmail){
      question = "Email: " + question;
    }
    else {
      question = "Question: " + question;
    }
    newQuestion.qName.setText(question);
    generatedText = ChatGPT.generateText(question, 2048);
    generatedText = generatedText.strip();
    if (makeEmail){
      String[] displayName = Read.sendEmailInfo(appEmail);
      if (displayName == null){
      }
      else {
        generatedText = generatedText.substring(0, generatedText.length() - 11);
        generatedText += displayName[2];
      }
    }
    newQuestion.answer = generatedText;

    newQuestion.qName.addMouseListener(
      new MouseAdapter(){
        @Override
        public void mousePressed(MouseEvent e){
          clearPanels();
          if (newQuestion.isEmail){
            questionPanel.setText(newQuestion.qName.getText().substring(7));
          }
          else{
            questionPanel.setText(newQuestion.qName.getText().substring(10));
          }
          answerPanel.setText(newQuestion.answer);
          currQuestion = newQuestion;
        }
      }
    );

    history.add(newQuestion);
    answerPanel.setText(generatedText);
    currQuestion = newQuestion;

    if (makeEmail){
      newQuestion.isEmail = true;
      Create.addEmail(question, generatedText, appEmail);
    }
    else{
      newQuestion.isEmail = false;
      Create.addQuestionAndAnswer(question, generatedText, appEmail);
    }
    
    this.revalidate();
  }

  public Boolean sendEmail(String transcript){
    if (currQuestion == null || currQuestion.isEmail == null || currQuestion.isEmail == false){
      JOptionPane.showMessageDialog(null, "Please select an email", "Error", JOptionPane.INFORMATION_MESSAGE);
      return false;
    }

    // find email from transcript
    String realEmail = "";
    int ctr = 0;
    for (int i = transcript.length() - 1; i > 0; i--){
      if (transcript.charAt(i) == ' '){
        if (ctr != 0)
          break;
        else {
          ctr++;
          continue;
        }
      }
      else if (transcript.charAt(i) == 't' && transcript.charAt(i-1) == 'a'){
        realEmail += '@';
        i--;i--;
        continue;
      }
      realEmail += transcript.charAt(i);
    }

    realEmail = new StringBuilder(realEmail).reverse().toString().toLowerCase();

    String[] emailInfo = new String[7];
    emailInfo = Read.sendEmailInfo(appEmail);
    if (emailInfo == null){
      if (!debug)
        JOptionPane.showMessageDialog(null, "Please setup your email first with the 'Setup Email' voice command", "Error", JOptionPane.INFORMATION_MESSAGE);
      return false;
    }
    else {
      Exception result = SendEmail.sendEmail(emailInfo[3], emailInfo[6], realEmail, emailInfo[4], emailInfo[5], currQuestion.answer);
      currQuestion = null;
      questionPanel.setText(transcript);
      if (result == null){
        answerPanel.setText("Your email has been successfully sent!");
        return true;
      }
      else {
        answerPanel.setText(result.toString());
        return false;
      }
    }
  }

  public void clearPanels(){
    questionPanel.setText("");
    answerPanel.setText("");
  }

  public void debugOn(){
    debug = true;
  }
}