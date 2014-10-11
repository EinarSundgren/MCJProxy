import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class testMarineControlJproxyEchoUART {

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void test() {
		MarineControlJProxy proxy = new MarineControlJProxy();
		proxy.initialize();
		//ProxyInterface echoInterface;
		//proxy.attachInterface();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Requesting echo");
		proxy.requestEcho(100, 100);
		
		while(true){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//fail("Not yet implemented");
	}

}
