import java.io.*;

public class cardReader {
    
    private String cleanOut(String out){
        out = (out.substring(out.indexOf(":") + 1));
        
        for(int i = 0; i < out.length(); i++){
            if(out.charAt(i) == ' '){
                out = out.substring(0, i);
                i = out.length();
            }
        }
        
        return out;
        
    }
    
    public String[] read(int cardNumber) {
        String[] output = new String[16];
        int index = 0;
        
        String cardString = "" + cardNumber;
        if(cardNumber < 10){
            cardString = "0" + cardString;
        }
        
        // The name of the file to open.
        String fileName = "Card" + cardString + ".txt";

        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);
            
            //System.out.println("Reading " + fileName);
            while((line = bufferedReader.readLine()) != null) {
                //System.out.println(line);
                line = cleanOut(line);
                //System.out.println(line);
                output[index] = line;
                index++;
            }   

            // Always close files.
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
			try {  //if it's not found here, try pointing specifically to this directory
				// FileReader reads text files in the default encoding.
				fileName = "./ODSYredux/" + fileName;
				
				FileReader fileReader = 
					new FileReader(fileName);

				// Always wrap FileReader in BufferedReader.
				BufferedReader bufferedReader = 
					new BufferedReader(fileReader);
				
				//System.out.println("Reading " + fileName);
				while((line = bufferedReader.readLine()) != null) {
					//System.out.println(line);
					line = cleanOut(line);
					//System.out.println(line);
					output[index] = line;
					index++;
				}   

				// Always close files.
				bufferedReader.close();         
			}
			catch(FileNotFoundException no) {
				System.out.println(
					"Unable to open file '" + 
					fileName + "'");                
			}           
			catch(IOException iox) {
				System.out.println(
					"Error reading file '" 
					+ fileName + "'");                  
				// Or we could just do this: 
				// ex.printStackTrace();
			}			
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }
        
        return output;
    }
}