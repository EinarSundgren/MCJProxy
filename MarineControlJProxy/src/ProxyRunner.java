import javax.swing.JFrame;
import javax.swing.JPanel;

public class ProxyRunner {
public static void main(String[] args) throws Exception {
		
		MarineControlJProxy main = new MarineControlJProxy();
		main.initialize();
		TimerDisplay timerDisplay = new TimerDisplay(0x10);
		TimerDisplay timerDisplay2 = new TimerDisplay(0x11);
		
		main.attachInterface(timerDisplay);
		main.attachInterface(timerDisplay2);
		
		
		
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
        comboPanel.add(timerDisplay);
        //comboPanel.add(reset1Button);
        
        comboPanel.add(timerDisplay2);
        //comboPanel.add(reset2Button);
        guiFrame.setVisible(true);
	}
}