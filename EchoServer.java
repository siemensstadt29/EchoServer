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
			
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	       
	        String inputData;
			        
	        while((inputData = in.readLine()) != null){	        		        	
	        	       	
	        	if(debugOn == true){
	        		System.out.println(inputData); 
	        	}
	        	
	        	out.println("Echo: " + inputData);
	       }
	    }
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	
 public static void main(String[] args){
	 
	 boolean debugOn = false;
	 int port = 5678;
	 
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
	 
	 try{
		 ServerSocket server = new ServerSocket(port);
		 
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
