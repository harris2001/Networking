import javax.xml.crypto.Data;
import java.io.*;
import java.net.*;
import java.util.Random;

class UDPSender{
    public static void main(String [] args){
        try{
            InetAddress address =
                    InetAddress.getByName("127.0.0.1");
            DatagramSocket socket = new DatagramSocket();
//            Random random = new Random();
//            int a = random.nextInt(10);
            for(int i=0;i<10;i++){
                byte[] buf = String.valueOf(i+" Test"/*+a*/).getBytes();
                DatagramPacket packet =
                        new DatagramPacket(buf, buf.length, address, 4321);
                socket.send(packet);
                System.out.println("send DatagramPacket "
                        + new String(packet.getData()) + " "
                        + packet.getAddress() + ":"
                        + packet.getPort());
                buf = new byte[256];
                UDPSender sender = new UDPSender();
                DatagramPacket response = new DatagramPacket(buf,buf.length);
                socket.receive(response);
                while(sender.getResponse(socket,response,String.valueOf(i))){
                    response = new DatagramPacket(buf,buf.length);
                    socket.receive(response);
                };
                Thread.sleep(2000);
            }
        }catch(Exception e){System.out.println("error");}
    }

    private boolean getResponse(DatagramSocket socket,DatagramPacket packet,String id){
        byte[] buf = new byte[256];
        System.out.println("GOT RESPONSE");
        System.out.println((new String(packet.getData())).trim());
        if(packet.toString().split(":")[0]!="ACK"){
            return false;
        }
        String packetId = packet.toString().split(":")[1];
        System.out.println("receive DatagramPacket "
                + (new String(packet.getData())).trim() + " "
                + packet.getAddress() + ":"
                + packet.getPort());
        return id.equals(packetId);
    }
}
