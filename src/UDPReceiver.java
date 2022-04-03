import java.io.IOException;
import java.net.*;
class UDPReceiver{
    public static void main(String [] args){
        try{
            DatagramSocket socket = new DatagramSocket(4321);
            byte[] buf = new byte[256];
            while (true){
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                String packetId = new String(packet.getData()).trim().split(" ")[0];
                System.out.println("receive DatagramPacket "
                        + (new String(packet.getData())).trim() + " "
                        + packet.getAddress() + ":"
                        + packet.getPort());
                UDPReceiver receiver = new UDPReceiver();
                /**
                 * By uncommenting it you expect UDP server to send an acknowledgement
                 * after receving a message
                 */
                //receiver.sendResponse(socket,packetId,packet.getAddress(),packet.getPort());

            }
        } catch(Exception e){System.out.println("error "+e);}
    }

    /**
     * Used to send acknowledgements after receiving a packet
     * => If a packet is lost => the connection is dropped
     * @param socket is the used socket opened for the communication
     * @param packetId is the id of the packet we send and acknowledgement for
     * @param address the connected address (localhost)
     * @param port the port used for the communication
     */
    private void sendResponse(DatagramSocket socket, String packetId,InetAddress address, int port){
        try {
            System.out.println("PacketId: "+packetId);
            byte[] buf = String.valueOf("ACK:"+packetId).getBytes();
            DatagramPacket packet = new DatagramPacket(buf,buf.length, address,port);
            socket.send(packet);
            System.out.println("Sent response for packet with id: "+packetId+" on port "+port);

        } catch (UnknownHostException e) {
            System.out.println("Can't reach localhost, respond "+packetId+" not send");
        } catch (IOException e) {
            System.out.println("Can't send response for packet "+packetId);
        }
    }
}