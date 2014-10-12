import java.util.ArrayList;
import java.util.List;

public class EchoInterface implements MProxyInterface{
	private int id;
	private int echoedInt;
	private float echoedFloat;
	private boolean updated;
	
	public boolean isUpdated() {
		return updated;
	}

	public void setUpdated(boolean updated) {
		this.updated = updated;
	}

	public EchoInterface() {
	super();
	this.id = 48;
	this.updated = false;
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

	public int getEchoedInt() {
		return echoedInt;
	}

	public void setEchoedInt(int echoedInt) {
		this.echoedInt = echoedInt;
	}

	public float getEchoedFloat() {
		return echoedFloat;
	}

	public void setEchoedFloat(float echoedFloat) {
		this.echoedFloat = echoedFloat;
	}

	@Override
	public void setData(List<Integer> data) {
		int[] intByteArray = new int[4];
		int[] floatByteArray = new int[4];
		
		for (int i = 0; i < 4; i ++){	
			intByteArray[i]=data.get(i);
			//System.out.println((byte)(int)data.get(i));
		}
		for (int i = 4; i <8; i ++){	
			floatByteArray[i-4]=data.get(i);
			//System.out.println((byte)(int)data.get(i));
		}
		this.echoedInt = MarineControlJProxy.intArrayToInt(intByteArray);
		this.echoedFloat = MarineControlJProxy.intArrayToFloat(floatByteArray);
		this.updated=true;
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
