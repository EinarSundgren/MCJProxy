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
		MProxyInterface echoInterface;
		echoInterface = new EchoInterface();
		proxy.attachInterface(echoInterface);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Requesting echo");
		
		// Arrays defining what values are to be tested.
		int[] intValuesForTest = {0xFFFFFFFF, 0x0000000, 
				0x000F, 0x00FF, 0x00FF, 0x0F00};
		float[] floatValuesForTest = {Float.MAX_VALUE, 
									Float.MIN_VALUE, Float.MIN_NORMAL, 
									Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY,
									Float.MAX_EXPONENT, Float.MIN_EXPONENT, 
									12.312f, 234234.2f, 234423423.4234234234f};
		float delta = 0.0001f;
		int largestArraySize;
		int intToTest;
		float floatToTest;
		
		// Assess max size of test
		if (floatValuesForTest.length > intValuesForTest.length) {
			largestArraySize = floatValuesForTest.length;
		} else {
			largestArraySize = intValuesForTest.length;
		}
		
		for (int i = 0; i < largestArraySize ; i ++){
			
			if (i<intValuesForTest.length){
			intToTest = intValuesForTest[i];} else {
				intToTest = 0;
			}
			
			if (i<floatValuesForTest.length){
			floatToTest = floatValuesForTest[i];}
			else {
				floatToTest = 0;
			}
			
			proxy.requestEcho(intToTest, floatToTest);
		
			while(!((EchoInterface) proxy.getInterface(48)).isUpdated()){			
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
				// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			((EchoInterface) proxy.getInterface(48)).setUpdated(false);
			
			System.out.println("Echo Updated");
			System.out.println("Echoed int: " + ((EchoInterface) proxy.getInterface(48)).getEchoedInt());
			System.out.println("Echoed float: " + ((EchoInterface) proxy.getInterface(48)).getEchoedFloat());
			
		//fail("Not yet implemented");
			//Testing the int value
			assertEquals("Failed sending int: " + intToTest + 
					" Received: " + ((EchoInterface) proxy.getInterface(48)).getEchoedInt(), 
					intToTest,  
					((EchoInterface) proxy.getInterface(48)).getEchoedInt());
			// Testing the float value
			assertEquals("Failed sending float: " + floatToTest + 
					" Received: " + ((EchoInterface) proxy.getInterface(48)).getEchoedFloat(), 
					floatToTest,  
					((EchoInterface) proxy.getInterface(48)).getEchoedFloat(), delta);
		}
	}

}
