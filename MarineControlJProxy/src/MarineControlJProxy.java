import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.BufferOverflowException;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

public class MarineControlJProxy implements SerialPortEventListener {
	public static final String SET_ELAPSED_TIMER = "SET";
	public static final String SET_STOP_TIME = "SST";
	
	private SerialPort serialPort;
	
	private boolean readingFrame = false;
	
	
	/** The port we're normally going to use. */
	private static final String PORT_NAMES[] = { "/dev/tty.usbserial-A9007UX1", // Mac
																				// OS
																				// X
			"/dev/ttyACM0", // Raspberry Pi
//			"/dev/ttyACM4",
			"/dev/ttyUSB0", // Linux
			"COM3", // Windows
	};
	/**
	 * A BufferedReader which will be fed by a InputStreamReader converting the
	 * bytes into characters making the displayed results codepage independent
	 */
	
	//TODO: Make this a tree sorted by ID.
	private HashMap<Integer, MProxyInterface> attachedInterfaces;

	private InputStream serialInputStream;
	/** The output stream to the port */
	private OutputStream serialOutputStream;
	/** Buffer for protocol frame **/
	private ArrayList<Integer> frameBuffer;
	/** Milliseconds to block while waiting for port open */
	private static final int TIME_OUT = 2000;
	/** Default bits per second for COM port. */
	private static final int DATA_RATE = 9600;

	private static final int SOF = 0xFF;
	private static final int EOF = 0xFE;	
	private static final int ESCAPE = 0xF0;
	
	final static int TIMER = 0x10;
	final static int GYRO = 0x20;
    final static int ACCELEROMETER = 0x30;
    final static int MAGNETOMETER = 0x40; 
    final static int BAROMETRIC = 0x50;
    final static int HYGROMETHER = 0x60; 
    
    

	public void initialize() {
		// the next line is for Raspberry Pi and
		// gets us into the while loop and was suggested here was suggested
		// http://www.raspberrypi.org/phpBB3/viewtopic.php?f=81&t=32186
		System.setProperty("gnu.io.rxtx.SerialPorts", "/dev/ttyACM0");

		CommPortIdentifier portId = null;
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

		// First, Find an instance of serial port as set in PORT_NAMES.
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum
					.nextElement();
			for (String portName : PORT_NAMES) {
				if (currPortId.getName().equals(portName)) {
					portId = currPortId;
					break;
				}
			}
		}
		if (portId == null) {
			System.out.println("Could not find COM port.");
			return;
		}

		try {
			// open serial port, and use class name for the appName.
			serialPort = (SerialPort) portId.open(this.getClass().getName(),
					TIME_OUT);

			// set port parameters
			serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

			// open the streams

			serialInputStream = serialPort.getInputStream();

			serialOutputStream = serialPort.getOutputStream();

			// add event listeners
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
			
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	public MarineControlJProxy() {
		super();
		this.attachedInterfaces = new HashMap<Integer,MProxyInterface>();
		
	}

	/**
	 * This should be called when you stop using the port. This will prevent
	 * port locking on platforms like Linux.
	 */
	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	public void readFrameStream(MProxyInterface o) {

	}
	
	public void attachInterface(MProxyInterface mProxyInterface){
		this.attachedInterfaces.put(mProxyInterface.getId(), mProxyInterface);
		mProxyInterface.setReturnCaller(this);
	}
	
	public void processFrame(ArrayList<Integer> frame) {
		
		// Remove all escape characters in the array.
		for (int i = 0; i < frame.size(); i ++) {
			//System.out.println("Processing " + String.format("0b%8s",  Integer.toBinaryString((int) 0xFF & frame.get(i))).replace(' ', '0') + "");
			
			if (frame.get(i)==ESCAPE){
				System.out.println("Escaped");
				frame.remove(i);
				i--;
			}
		}
			
		int toAdress = (int) frame.get(0) & 0xFF; // To ID
		int fromAdress = (int) frame.get(1) & 0xFF; // From ID
		int messageType = (int) frame.get(2) & 0xFF; // Message type
		int dataLength =  frame.get(3) & 0xFF; // Data length
		
		
		
		  System.out.println("to: " + toAdress + 
						 " from " + fromAdress + 
						 " Binary " + Integer.toBinaryString(fromAdress) + 
						 " type:" + messageType + 
						 " DataLength " + dataLength);
						 
		// Update attached interfaces
		if (attachedInterfaces.containsKey(fromAdress)){
			attachedInterfaces.get(fromAdress).setData(frameBuffer.subList(4, 4+dataLength));
			attachedInterfaces.get(fromAdress).setReturnCaller(this);
			// System.out.println("Set timer " +  fromAdress);
		} else {
			 //System.out.println("Key " + fromAdress +" did not exist in the " + attachedInterfaces.size() + " keys");
			
		}
		
		if ((fromAdress & TIMER) > 0 ){
			 // System.out.println("From timer.");
		}
		
		if ((fromAdress & GYRO) > 0 ) {
			// System.out.println("From gyro.");
		}
	}

	
	public void zeroTimer(int timerId){		
		try {
			serialOutputStream.write((byte)timerId); //to ID
			serialOutputStream.write((byte)0x10); //from ID
			serialOutputStream.write((byte)0x02); // Message
			serialOutputStream.write((byte)0x03); // Payload size
			serialOutputStream.write((byte)0x01); // Payload
			serialOutputStream.write((byte)0x00); // Payload msb
			serialOutputStream.write((byte)0x00); // Payload lsb
			serialOutputStream.write((byte)0x2); //CRC
			serialOutputStream.write((byte)0x3); //CRC
			serialOutputStream.write(EOF);
			
			// System.out.println("Zeroed timer " + timerId);

		} catch (IOException e) {
		
			e.printStackTrace();
		}
	}
	
	
	
	public void setStopTime(int timerId, int stopTime){
		
		try {
			serialOutputStream.write((byte)timerId); //to ID
			serialOutputStream.write((byte)0x10); //from ID
			serialOutputStream.write((byte)0x02); // Message Update single value
			serialOutputStream.write((byte)0x03); // Payload size
			serialOutputStream.write((byte)0x00); // Payload
			serialOutputStream.write((byte)(stopTime >> 8) & 0xFF ); // Payload msb
			serialOutputStream.write((byte)stopTime & 0xFF); // Payload lsb
			serialOutputStream.write((byte)0x2); //CRC
			serialOutputStream.write((byte)0x3); //CRC
			serialOutputStream.write(EOF);

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Handle an event on the serial port. Read the data and print it.
	 */
	public synchronized void serialEvent(SerialPortEvent oEvent) {
		// ByteBuffer b = ByteBuffer.allocate(2);
		byte[] data = new byte[4];
		frameBuffer = new ArrayList<Integer>();
		
		
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				int in;
				while ((in = serialInputStream.read(data)) > 0) {
					try {
						
					} catch (BufferOverflowException e) {
						e.printStackTrace();
						System.out.println("Value: " + in);
					}

					/*
					 * Wait Buffer Write
					 */
					// If first in new buffer is EOF and last previous is not
					// escape
					
					// System.out.println(data);

						for (int i = 0; i < in; i++) {
							
							// System.out.println("Reading " + String.format("0x%8s",  Integer.toHexString(0xFF & data[i])).replace(' ', '0'));



							//if (!readingFrame && data[i]==SOF) {}
							// Handle end of frame situations.
							// If EOF found, process frame.
							if (!readingFrame && 
									((int) data[i] & 0xFF) == SOF ) {
								System.out.println("New frame. Starting with "
										+ i + 1 + "th byte in buffer");
								readingFrame = true;
								
							} else if (readingFrame) {
								
								
								if (									
									( i == 0
										    && (((int) data[i] & 0xFF) == EOF)
											)
									|| (
								i > 0
								&& (((int) data[i] & 0xFF) == EOF)
								&& (((int) data[i - 1] & 0xFF) != ESCAPE)
								) 
								
									) {
								
								/*
								System.out.println("New frame. Starting with "
										+ i + 1 + "th byte in buffer");
									*/

								if (frameBuffer.size() > 0){
										/*
										for (int j = 0 ; j < frameBuffer.size(); j ++) {
											System.out.print((char)(int)frameBuffer.get(j));
										}
										*/
									//System.out.println();
									
										processFrame(frameBuffer);
										frameBuffer.clear();
										readingFrame = false;
									}
								
								} else 
										if (data[i]!=ESCAPE){
											// Add byte as int to avoid making it force signed (thnx Java)
											System.out.println("Appending " + String.format("0x%8s",  Integer.toHexString(0xFF & data[i])).replace(' ', '0'));
											frameBuffer.add((int)data[i] & 0xFF);
								} else {
									System.out.println("Escaped");
								}
							
						}
						}
				}
				//System.out.println("End");
			} catch (Exception e) {
				System.err.println(e.toString());

			}
		}
		// Ignore all the other eventTypes, but you should consider the other
		// ones.
	}
	
	public static int intArrayToInt(int[] b) 
	{
		int num = 0;
		int ret =
				 
				( (b[0] & 0xFF) |
	    		( (b[1] & 0xFF) << 8)  | 
	    		( (b[2] & 0xFF ) << 16) +
	    		( (b[3]  & 0xFF) << 24));
		//1 1111 0100
	
	
		System.out.println("Reassembling " + String.format("0b%8s",  Integer.toBinaryString((int) 0xFF & b[0])).replace(' ', '0') + "");
		System.out.println("Reassembling " + String.format("0b%8s",  Integer.toBinaryString((int)(((0xFF & b[1]) << 8 ) ))).replace(' ', '0'));
		System.out.println("Reassembling " + String.format("0b%8s",  Integer.toBinaryString((int)(((0xFF & b[2]) << 16)   ))).replace(' ', '0'));
		System.out.println("Reassembling " + String.format("0b%8s",  Integer.toBinaryString((int)(((0xFF & b[3]) << 24)  ))).replace(' ', '0'));
		System.out.println("Result: "+ ret);
		System.out.println("Result bin: " + String.format("0b%8s",  Integer.toBinaryString(ret).replace(' ', '0')));
	
	    
		return ret;
	}
	
	public static float intArrayToFloat(int[] b) 
	{
	    return b[0] & 0xFF | ( b[1] & 0xFF) << 8 | (b[2] & 0xFF) << 16 | ( b[3] & 0xFF) << 24;
	}
	
}