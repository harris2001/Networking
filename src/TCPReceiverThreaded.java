import java.io.*;
import java.net.*;
class TCPReceiverThreadedClass{
    public static void main(String [] args){
        try{
            ServerSocket ss = new ServerSocket(4322);
            for(;;){
                try{Socket client = ss.accept();
                    new Thread(new ServiceThread(client)).start();
                }catch(Exception e){System.out.println("error "+e);}
            }
        }catch(Exception e){System.out.println("error "+e);}
    }

    /**
     * Creates a new thread for each client it connects to the receiver
     */
    static class ServiceThread implements Runnable{
        Socket client;
        ServiceThread(Socket c){client=c;}
        public void run(){try{
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(client.getInputStream()));
            String line;
            while((line = in.readLine()) != null) {
                System.out.println(line + " received from " + client.getInetAddress());
                PrintWriter out = new PrintWriter(client.getOutputStream());
                Thread.sleep(2000);
                out.println("ACK");
                out.flush();
            }
            client.close(); }catch(Exception e){}
        }
    }
}