import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class TimerDisplay extends JPanel implements MProxyInterface, ActionListener, ChangeListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private JProgressBar progressMeter; 
	private JButton resetButton;
	private JButton setStopButton;
	private JSpinner stopTimeSpinner;
	private JLabel elapsedOf;
	
	private MarineControlJProxy returnProxy;
	
	private boolean autoUpdateStoptime = true;
	
	public TimerDisplay(int id) {
		super();
		this.id = id;
		elapsedOf = new JLabel();
		progressMeter = new JProgressBar();
		resetButton = new JButton("Reset");
		setStopButton = new JButton("Set stoptime");
		stopTimeSpinner = new JSpinner();
		stopTimeSpinner.setModel(new SpinnerNumberModel(0, 0, 20000, 60));
		resetButton.setActionCommand(MarineControlJProxy.SET_ELAPSED_TIMER);
		setStopButton.setActionCommand(MarineControlJProxy.SET_STOP_TIME);
		
		resetButton.addActionListener(this);
		setStopButton.addActionListener(this);
		
		stopTimeSpinner.addChangeListener(this);
		
		System.out.println("Timer interface created with adress: " + id);
		this.add(progressMeter);
		this.add(resetButton);
		this.add(stopTimeSpinner);
		this.add(setStopButton);
		this.add(elapsedOf);
	}

	@Override
	public void setId(int id) {
		// TODO Auto-generated method stub
		this.id = id;
	}

	@Override
	public void setData(List<Integer> data) {
		// System.out.println ("Setting display: " + this.id + " with data");
			int[] elapsedTimeByteArray = new int[4];
			int[] stopTimeByteArray  = new int[4];
			
			for (int i = 0; i < 4; i ++){	
				elapsedTimeByteArray[i]=data.get(i);
				//System.out.println((byte)(int)data.get(i));
			}
			for (int i = 4; i <8; i ++){	
				stopTimeByteArray[i-4]=data.get(i);
				//System.out.println((byte)(int)data.get(i));
			}
			

			System.out.print ("Stoptime: "  + MarineControlJProxy.intArrayToInt(stopTimeByteArray));
			System.out.println(" Elapsedtime: "  + MarineControlJProxy.intArrayToInt(elapsedTimeByteArray));
			
			int maxVal = MarineControlJProxy.intArrayToInt(stopTimeByteArray);
			int elapsedVal = MarineControlJProxy.intArrayToInt(elapsedTimeByteArray);
			//System.out.println((float)elapsedVal/maxVal);
			if (elapsedVal>=maxVal) {this.setForeground(Color.RED);}
			else if ((float)elapsedVal/maxVal > 0.7) {
				progressMeter.setForeground(Color.YELLOW);
			}
			else {
				progressMeter.setForeground(Color.GREEN);}
			
			progressMeter.setMaximum(maxVal);
			progressMeter.setValue(elapsedVal);
			if (autoUpdateStoptime){
				stopTimeSpinner.setValue(maxVal);
			}
			elapsedOf.setText(""+elapsedVal +"/" + maxVal + "minutes." + (float)elapsedVal/60 + "/" + (float) maxVal/60 + " hours");
	}

	@Override
	public ArrayList<Integer> getData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	@Override
	public void setReturnCaller(MarineControlJProxy marineControlJProxy) {
		// TODO Auto-generated method stub
		this.returnProxy = marineControlJProxy;
		
	}

	@Override
	public void setZero() {
		if (returnProxy!=null){
			returnProxy.zeroTimer(this.id);
			
		} else {
			System.out.println("Return proxy not set.");
		}
	}
	
	
	@Override
	public void setStoptime(int stopTime) {
		if (returnProxy!=null){
			System.out.println("Setting stoptime...");
			returnProxy.setStopTime(this.id, stopTime);
			
		} else {
			System.out.println("Return proxy not set.");
		}
		
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(MarineControlJProxy.SET_ELAPSED_TIMER)){
			System.out.println("Reset button pressed");
			setZero();
		} else if (e.getActionCommand().equals(MarineControlJProxy.SET_STOP_TIME)) {
			System.out.println("Stop time change");
			setStoptime((int) stopTimeSpinner.getValue());
			autoUpdateStoptime = true;
				
			}
		}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Changed");
		autoUpdateStoptime = false;
	}







	}
	
	



//TODO: Beräkna cheksum. Rata felaktig.
//TODO: Bygg hårdvaran och testa på båten.
//TODO: Skriv regressionstester i C och Java