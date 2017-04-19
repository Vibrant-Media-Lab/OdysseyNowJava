import java.util.*;
import jssc.*;

public class SerialInput 
{

	static SerialPort com3;
	
	public static String[] getInput()
	{
		com3 = new SerialPort("COM3"); 
		try 
		{
			com3.openPort();
			com3.setParams(9600, 8, 1, 0);
		}
		catch (SerialPortException ex) 
		{
			System.out.println(ex);
		}
		
		String p = null;
		String[] valueBunch = new String[8];
		String valueString = null;
		String[] values = new String[8];
		
		while(com3.isOpened())
		{			
		
			try
			{
				p = com3.readString();
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
				try 
				{
					com3.closePort();
				}
				catch (SerialPortException ex) {
					System.out.println(ex);
				}
			}
		}
		return values;
	}

}



