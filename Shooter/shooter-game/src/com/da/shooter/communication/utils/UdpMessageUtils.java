package com.da.shooter.communication.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.List;

import com.da.shooter.communication.Message;

/**
 * The Class UdpMessageUtils.
 */
public class UdpMessageUtils {

	/**
	 * Send message.
	 *
	 * @param msg the msg
	 * @param port the port
	 */
	public static void sendMessages(List<Message> msgs){
		for (Message msg : msgs) {
			sendMessage(msg);
		}
	}
	
	public static void sendMessage(Message msg){
		DatagramSocket serverSocket = null;
		try {
			serverSocket = new DatagramSocket();
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
			
			objectOutputStream.writeObject(msg);
			byte[] data = outputStream.toByteArray();
			for (InetAddress address : msg.getAddresses()) {
				DatagramPacket sendPacket = new DatagramPacket(data, data.length,address , msg.getPort());
				serverSocket.send(sendPacket);
				System.out.println("Msg out:"+address.getHostAddress()+" | "+msg.getPort()+"|"+msg.getType());
			}
			serverSocket.close();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(serverSocket!=null && !serverSocket.isClosed()){
			serverSocket.close();
		}
	}
	
	/**
	 * Receive message.
	 *
	 * @param port the port
	 * @param messageLength the message length
	 * @param timeout the timeout
	 * @return the udp message
	 */
	public static Message receiveMessage(int port, int messageLength, int timeout){
		DatagramSocket serverSocket = null;
		Message message = null;
		try {
//			serverSocket = new DatagramSocket(port);
			serverSocket = new DatagramSocket(null);
			serverSocket.setReuseAddress(true);
//			serverSocket.setBroadcast(true);
			serverSocket.bind(new InetSocketAddress(port));
			serverSocket.setSoTimeout(timeout);
			byte[] receiveData = new byte[messageLength];

			DatagramPacket receivePacket = new DatagramPacket(receiveData,	receiveData.length);
			serverSocket.receive(receivePacket);
			
			byte[] data = receivePacket.getData();
			ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
			ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
			message = (Message)objectInputStream.readObject();
			message.setAddress(receivePacket.getAddress());
			System.out.println("Msg in:"+receivePacket.getAddress().getHostAddress()+" | "+port+"|"+message.getType());
		}catch(SocketTimeoutException e){
			// Message timeout
		}catch (Exception e) {
			e.printStackTrace();
		} 
		if(serverSocket!=null && !serverSocket.isClosed()){
			serverSocket.close();
		}
		return message;
	}
}
