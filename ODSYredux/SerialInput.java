import java.util.*;
import jssc.*;

public class SerialInput 
{
	static SerialPort com3 = new SerialPort("COM4");
	static String p = null;
	static String[] valueBunch = new String[8];
	static String valueString = null;
	static String[] values = new String[8];
		
	public static String[] getInput()
	{
		//PORT OPENED
		try 
		{
			com3.openPort();
			com3.setParams(9600, 8, 1, 0);
		}
		catch (SerialPortException ex) 
		{
			System.out.println(ex);
		}
		
		while(com3.isOpened())
		{			
			try
			{
				//Input string from controller
				p = com3.readString();  //DOES THIS BLOCK?
			}
			catch (SerialPortException ex) 
			{
				System.out.println(ex);
			}
				
			if(p != null)
			{
				/*
					Due to some lines of input, usually 1, 3, and
					the last one, being garbage the whole input cannot be used.
					Instead the most stable line of input, line 2, is used.
				*/
				valueBunch = p.split("\\n");
				valueString = valueBunch[1];
				values = valueString.split(",");
				//PORT CLOSED
				try 
				{
					com3.closePort();
				}
				catch (SerialPortException ex) 
				{
					System.out.println(ex);
				}
			}
		}
		//Returns an array of size 8 containing each knob's input
		return values;
	}

}
//END MODIFICATION//





