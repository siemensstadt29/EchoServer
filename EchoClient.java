import java.io.*;
import java.net.*;
import java.util.regex.Pattern;


public class EchoClient {
	
	public static void main(String[] args){
		
		// standard values for Client
		String host = "localhost";
		int port = 5678;
					
		try{
			if(args.length == 1){
				
				// argument committed by the command line is split at the colon sign and saved in a String array
				String[] splitArray = (args[0].split(Pattern.quote(":")));
				
				if(args[0].contains(":")){
					if(splitArray.length == 2){
						if(splitArray[0].isEmpty()){
							port = Integer.parseInt(splitArray[1]);
						}
						else{
							host = splitArray[0];
							port = Integer.parseInt(splitArray[1]);							
						}					
					}
				}
				else{
					host = splitArray[0];
				}
			}
			else if(args.length > 1){
				System.out.println("Fehlerhafte Eingabe! Starten des Clients mit java EchoClient [<host>]:[<port>]");	
				return;
			}
			
			// building new connection to committed host and port
			Socket client = new Socket(host, port);
			
			// out-/input stream of the Client and the standard system input stream
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			
			String userInput;
			
			// reading the messages committed in command line
			while ((userInput = stdIn.readLine()) != null) {
				
				// free resources after typing in "quit"
			    if(userInput.equals("quit")){
			    	in.close();
			    	stdIn.close();
			    	out.close();
			    	client.close();
			    	break;
			    }
			    
			    // prints the inserted message on the Client and then prints the same
			    // with "Echo:" at beginning
			    out.println(userInput);
			    System.out.println(in.readLine());
			    
			}

	    }
		catch(NumberFormatException e){
			System.err.println("Fehlerhafte Eingabe! Port muss eine ganze Zahl darstellen!\nStarten des Clients mit java EchoClient [<host>]:[<port>]");
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		
     }	
	
 }
