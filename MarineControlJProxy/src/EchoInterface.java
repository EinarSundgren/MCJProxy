import java.util.ArrayList;
import java.util.List;

public class EchoInterface implements MProxyInterface{
	private int id;
	private int echoedInt;
	private float echoedFloat;
	
	public EchoInterface() {
	super();
	this.id = 48;
	}
	
	@Override
	public void setReturnCaller(MarineControlJProxy marineControlJProxy) {
		// TODO Auto-generated method stub
		
	}

	// Id of the particular interface
	@Override
	public void setId(int id) {
		this.id=id;
		
	}

	@Override
	public void setData(List<Integer> data) {
		int[] elapsedTimeByteArray = new int[4];
		
	}

	@Override
	public int getId() {

		return this.id;
	}

	@Override
	public ArrayList<Integer> getData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setZero() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setStoptime(int stopTime) {
		// TODO Auto-generated method stub
		
	}

}
