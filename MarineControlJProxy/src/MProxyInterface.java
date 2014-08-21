import java.util.ArrayList;
import java.util.List;

public interface MProxyInterface{

	public void setReturnCaller(MarineControlJProxy marineControlJProxy);
	
	public void setId(int id);
	public void setData(List<Integer> data);
	public int getId();
	public ArrayList<Integer> getData();
	public void setZero();
	public void setStoptime(int stopTime);
}
