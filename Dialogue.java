
public class Dialogue {
    private Message[] messages;
    private int delay;
    public class Message {
        private String message;
        private int typewriterDelay;

        // Constructor to initialize message and typewriter delay
        public Message(String message, int typewriterDelay) {
            this.message = message;
            this.typewriterDelay = typewriterDelay;
        }

        public void show(boolean toggleDelay) {
            try {
                for (int i = 0; i < this.message.length(); i++) {
                    System.out.print(this.message.charAt(i));
                    if (toggleDelay == true) {Thread.sleep(this.typewriterDelay);}
                }
            } catch (InterruptedException e) {
                System.err.println("Thread interrupted: " + e.getMessage());
            }
        }
    }

    // Modified constructor that accepts a 2D array of Objects
    public Dialogue(Object[][] messagesData, int messageDelay) {
        Message[] messages = new Message[messagesData.length];

        // Loop through the 2D array to create Message objects
        for (int i = 0; i < messagesData.length; i++) {
            String messageText = (String) messagesData[i][0]; 
            int typewriterDelay = (int) messagesData[i][1];
            messages[i] = new Message(messageText, typewriterDelay);
        }
        
        Dialogue(messages, messageDelay);
    }

    public void Dialogue(Message[] messages, int delay) {
        this.messages = messages;
        this.delay = delay;
    }

    public void show(boolean toggleDelay) {
        try {
            System.out.print("\u001B[30m");
            for (int i = 0; i < messages.length; i++) {
                messages[i].show(toggleDelay);
                System.out.print("\n");
                if (toggleDelay == true) {
                    Thread.sleep(delay);
                }  
                // Wait between messages
            }
            System.out.print("\u001B[0m");
        } catch (InterruptedException e) {
            System.err.println("Thread interrupted: " + e.getMessage());
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Message message : messages) {
            sb.append(message.message).append("\n");
        }
        return sb.toString();
    }
}
