import javax.swing.JFrame;
import javax.swing.JPanel;

public class ProxyRunner {
public static void main(String[] args) throws Exception {
		
		MarineControlJProxy main = new MarineControlJProxy();
		main.initialize();
		TimerDisplay timerDisplay0 = new TimerDisplay(0x10);
		TimerDisplay timerDisplay1 = new TimerDisplay(0x11);
		TimerDisplay timerDisplay2 = new TimerDisplay(0x12);
		TimerDisplay timerDisplay3 = new TimerDisplay(0x13);
		TimerDisplay timerDisplay4 = new TimerDisplay(0x14);
		TimerDisplay timerDisplay5 = new TimerDisplay(0x15);
		TimerDisplay timerDisplay6 = new TimerDisplay(0x16);
		TimerDisplay timerDisplay7 = new TimerDisplay(0x17);
		TimerDisplay timerDisplay8 = new TimerDisplay(0x18);
		TimerDisplay timerDisplay9 = new TimerDisplay(0x19);
		TimerDisplay timerDisplayA = new TimerDisplay(0x1A);
		TimerDisplay timerDisplayB = new TimerDisplay(0x1B);
		TimerDisplay timerDisplayC = new TimerDisplay(0x1C);
		TimerDisplay timerDisplayD = new TimerDisplay(0x1D);
		TimerDisplay timerDisplayE = new TimerDisplay(0x1E);
		TimerDisplay timerDisplayF = new TimerDisplay(0x1F);
		
		
		main.attachInterface(timerDisplay0);
		main.attachInterface(timerDisplay1);
		main.attachInterface(timerDisplay2);
		main.attachInterface(timerDisplay3);
		main.attachInterface(timerDisplay4);
		main.attachInterface(timerDisplay5);
		main.attachInterface(timerDisplay6);
		main.attachInterface(timerDisplay7);
		main.attachInterface(timerDisplay8);
		main.attachInterface(timerDisplay9);
		main.attachInterface(timerDisplayA);
		main.attachInterface(timerDisplayB);
		main.attachInterface(timerDisplayC);
		main.attachInterface(timerDisplayD);
		main.attachInterface(timerDisplayE);
		main.attachInterface(timerDisplayF);
		
		
		
		Thread t = new Thread() {
			public void run() {
				//the following line will keep this app alive for 1000 seconds,
				//waiting for events to occur and responding to them (printing incoming messages to console).
				try {Thread.sleep(1000000);} catch (InterruptedException ie) {}
			}
		};
		t.start();
		//System.out.println("Started");
        JFrame guiFrame = new JFrame();
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setTitle("Example GUI");
        guiFrame.setSize(300,250);
        final JPanel comboPanel = new JPanel();
        guiFrame.add(comboPanel);
        comboPanel.add(timerDisplay0);
        comboPanel.add(timerDisplay1);
        comboPanel.add(timerDisplay2);
        comboPanel.add(timerDisplay3);
        comboPanel.add(timerDisplay4);
        comboPanel.add(timerDisplay5);
        comboPanel.add(timerDisplay6);
        comboPanel.add(timerDisplay7);
        comboPanel.add(timerDisplay8);
        comboPanel.add(timerDisplay9);
        comboPanel.add(timerDisplayA);
        comboPanel.add(timerDisplayB);
        comboPanel.add(timerDisplayC);
        comboPanel.add(timerDisplayD);
        comboPanel.add(timerDisplayE);
        comboPanel.add(timerDisplayF);
        
        
        guiFrame.setVisible(true);
	}
}