import java.util.*;
import jssc.*;

public class Test {

	static SerialPort serialPort;

	public static void main(String[] args)  {
		serialPort = new SerialPort("COM3"); 
		try 
		{
			serialPort.openPort();//Open port
			serialPort.setParams(9600, 8, 1, 0);//Set params
			serialPort.addEventListener(new SerialPortReader());//Add SerialPortEventListener
		}
		catch (SerialPortException ex) {
			System.out.println(ex);
		}
	}

	static class SerialPortReader implements SerialPortEventListener 
	
	{

		public String[] serialEvent(SerialPortEvent event) 
		{
				String p = null;
				String[] valueBunch = new String[8];
				String[] values = new String[8];
				String valueString = null;
				
				String RV;
				String RE;
				String RH;
				String one;
				String two;
				String three;
					
					try
					{
						p = serialPort.readString();
					}
					catch (SerialPortException ex) 
					{
						System.out.println(ex);
					}
					
					if(p != null)
					{
						valueBunch = null;
						valueBunch = p.split("\\n");
						valueString = valueBunch[1];
						values = valueString.split(",");
						RV = values[4];
						RE = values[5];
						RH = values[3];
						one = values[1];
						two = values[0];
						three = values[2];
						System.out.println(RE);
					}
					
					
				return values;
		}
	}
}

