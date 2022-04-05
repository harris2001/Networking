import java.io.*;
import java.net.*;
class TCPSender{
    public static void main(String [] args){
        try{Socket socket = new Socket("127.0.0.1",4322);
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            for(int i=0;i<10;i++){
                out.println("TCP message "+i); out.flush();
                System.out.println("TCP message "+i+" sent");
                //Thread.sleep(1000);
                TCPSender sender = new TCPSender();
                while(sender.getResponse(socket)==false);
            }
        }catch(Exception e){System.out.println("error"+e);}
    }
    private boolean getResponse(Socket socket){
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            if(in.readLine().equals("ACK")){
                System.out.println("Received acknowledgement");
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}