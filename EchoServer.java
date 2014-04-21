import java.io.*; 
import java.net.*;

public class EchoServer extends Thread{	
	
	private Socket socket;
	private boolean debugOn;
		
	public EchoServer(Socket socket, boolean debugOn) throws IOException{
		this.socket = socket;
		this.debugOn = debugOn;
	}
	
	@Override
	public void run(){
		
		try{
			
			// out-/input stream of the Client
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	        
			String inputData;
			
			// reads the typed-in messages from the Client until the Client is closed by inserting "quit"
	        while((inputData = in.readLine()) != null){	        		        	
	        	
	        	// prints the typed-in message on the server screen when "-debug" is set
	        	if(debugOn == true){
	        		System.out.println(inputData); 
	        	}
	        	
	        	// sends the typed-in message back to the Client
	        	out.println("Echo: " + inputData);
	       }
	    }
		catch(IOException e){
			e.printStackTrace();
		}
	}
	// end of thread
	
	
 public static void main(String[] args){
	 
	 // standard values for debug an port
	 boolean debugOn = false;
	 int port = 5678;
	 
	 try{
		 
	 if(args.length == 1){
		 if(args[0].equals("-debug")){
			 debugOn = true;
		 }
		 else{
			 port = Integer.parseInt(args[0]);
		 }
	 }
	 
	 if(args.length == 2){
		 if(args[0].equals("-debug")){
			 debugOn = true;
			 port = Integer.parseInt(args[1]);
		 }
		 else{
			 port = Integer.parseInt(args[1]);
		 }
	 }
	 }
	 catch(NumberFormatException e){
		 System.err.print("Fehlerhafte Eingabe! Port muss eine ganze Zahl darstellen!\nStarten des Servers mit java EchoServer [-debug] [<port>]");
	 }
	 
	 try{
		 
		 // starts new Server
		 ServerSocket server = new ServerSocket(port);
		 
		 // waiting continuously for new connections from the Client to the Server and starting new thread for each Client
		 while(true){
			 Socket client = server.accept();
			 (new EchoServer(client, debugOn)).start();
		 }
	 }
	 catch(IOException e){
		 e.printStackTrace();
	 }
	 
	 
	 
 }
} 
