Instructions for running SayIt Assistant.
  1. Download the project source code.
  2. Run the SayIt.java file.
  3. Click the "Ask Question" button and begin speaking your question.
  4. Click "Ask Question" again to stop recording your question.
  5. View the SayIt Assistant's response, and click "Save Question" to save the current question in the prompt history.
  6. Click "Load Question" to view past saved questions and their answers.
  7. Click "Clear Saved Questions" to clear all saved questions from your prompt history.

Command Line Instructions for compiling and running:
cd src/main/java

javac -cp ../../../lib/json-20230227.jar -d ../../../bin/main/ ChatGPT.java ConnectionSetup.java WriteToOutput.java CreateRequest.java ResponseHandler.java Recording.java TranscribeAudio.java SayIt.java

cd ../../../bin/main

java -cp ../../lib/json-20230227.jar;. SayIt