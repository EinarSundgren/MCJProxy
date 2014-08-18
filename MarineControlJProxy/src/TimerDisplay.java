import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JProgressBar;


public class TimerDisplay extends JProgressBar implements MProxyInterface, ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	
	private MarineControlJProxy returnProxy;
	
	public TimerDisplay(int id) {
		super();
		this.id = id;
		System.out.println("Timer interface created with adress: " + id);
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
				this.setForeground(Color.YELLOW);
			}
			else {
					this.setForeground(Color.GREEN);}
			
			this.setMaximum(maxVal);
			this.setValue(elapsedVal);
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
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(MarineControlJProxy.SET_ELAPSED_TIMER)){
			System.out.println("Reset button pressed");
			setZero();
		}

	}
	
	


}
//TODO: Skicka kontroll till den inbyggda för nollställning.
//TODO: Skicka kontroll till den inbyggda för justering av slutvärde.

//TODO: Beräkna cheksum. Rata felaktig.

//TODO: Få timerna att gå på interrupt och inte delayfunktion.

//TODO: Bygg hårdvaran och testa på båten.
//TODO: Skriv regressionstester i C och Java