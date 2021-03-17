import java.util.concurrent.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Network class
 *
 * @author Kerly Titus
 */
public class Network extends Thread {

	private static int maxNbPackets; /* Maximum number of simultaneous transactions handled by the network buffer */
	private static int inputIndexClient, inputIndexServer, outputIndexServer,
			outputIndexClient; /*
								 * Network buffer indices for accessing the input buffer (inputIndexClient,
								 * outputIndexServer) and output buffer (inputIndexServer, outputIndexClient)
								 */
	private static String clientIP; /* IP number of the client application */
	private static String serverIP; /* IP number of the server application */
	private static int portID; /* Port ID of the client application */
	private static String clientConnectionStatus; /* Client connection status - connected, disconnected, idle */
	private static String serverConnectionStatus; /* Server connection status - connected, disconnected, idle */
	private static Transactions inComingPacket[]; /* Incoming network buffer */
	private static Transactions outGoingPacket[]; /* Outgoing network buffer */
	private static String inBufferStatus,
			outBufferStatus; /* Current status of the network buffers - normal, full, empty */
	private static String networkStatus; /* Network status - active, inactive */

	private static Semaphore mutex1;
	private static Semaphore mutex2;

	private static Semaphore full1;
	private static Semaphore full2;
	private static Semaphore empty1;
	private static Semaphore empty2;

	/**
	 * Constructor of the Network class
	 *
	 * @return
	 * @param
	 */
	Network() {
		int i;

		System.out.println("\n Activating the network ...");
		clientIP = "192.168.2.0";
		serverIP = "216.120.40.10";
		clientConnectionStatus = "idle";
		serverConnectionStatus = "idle";
		portID = 0;
		maxNbPackets = 10;
		inComingPacket = new Transactions[maxNbPackets];
		outGoingPacket = new Transactions[maxNbPackets];

		mutex1 = new Semaphore(1);
		mutex2 = new Semaphore(1);

		full1 = new Semaphore(0);
		full2 = new Semaphore(0);

		empty1 = new Semaphore(maxNbPackets);
		empty2 = new Semaphore(maxNbPackets);

		for (i = 0; i < maxNbPackets; i++) {
			inComingPacket[i] = new Transactions();
			outGoingPacket[i] = new Transactions();
		}
		inBufferStatus = "empty";
		outBufferStatus = "empty";
		inputIndexClient = 0;
		inputIndexServer = 0;
		outputIndexServer = 0;
		outputIndexClient = 0;

		networkStatus = "active";
	}

	/**
	 * Accessor method of Network class
	 *
	 * @return clientIP
	 * @param
	 */
	public static String getClientIP() {
		return clientIP;
	}

	/**
	 * Mutator method of Network class
	 *
	 * @return
	 * @param cip
	 */
	public static void setClientIP(String cip) {
		clientIP = cip;
	}

	/**
	 * Accessor method of Network class
	 *
	 * @return serverIP
	 * @param
	 */
	public static String getServerIP() {
		return serverIP;
	}

	/**
	 * Mutator method of Network class
	 *
	 * @return
	 * @param sip
	 */
	public static void setServerIP(String sip) {
		serverIP = sip;
	}

	/**
	 * Accessor method of Network class
	 *
	 * @return clientConnectionStatus
	 * @param
	 */
	public /* synchronized */ static String getClientConnectionStatus() {
		return clientConnectionStatus;
	}

	/**
	 * Mutator method of Network class
	 *
	 * @return
	 * @param connectStatus
	 */
	public static void setClientConnectionStatus(String connectStatus) {
		clientConnectionStatus = connectStatus;
	}

	/**
	 * Accessor method of Network class
	 *
	 * @return serverConnectionStatus
	 * @param
	 */
	public static String getServerConnectionStatus() {
		return serverConnectionStatus;
	}

	/**
	 * Mutator method of Network class
	 *
	 * @return
	 * @param connectStatus
	 */
	public static void setServerConnectionStatus(String connectStatus) {
		serverConnectionStatus = connectStatus;
	}

	/**
	 * Accessor method of Network class
	 *
	 * @return portID
	 * @param
	 */
	public static int getPortID() {
		return portID;
	}

	/**
	 * Mutator method of Network class
	 *
	 * @return
	 * @param pid
	 */
	public static void setPortID(int pid) {
		portID = pid;
	}

	/**
	 * Accessor method of Netowrk class
	 *
	 * @return inBufferStatus
	 * @param
	 */
	public /* synchronized */ static String getInBufferStatus()// added synchronized
	{
		return inBufferStatus;
	}

	/**
	 * Mutator method of Network class
	 *
	 * @return
	 * @param inBufStatus
	 */
	public /* synchronized */ static void setInBufferStatus(String inBufStatus)// added synchronized
	{
		inBufferStatus = inBufStatus;
	}

	/**
	 * Accessor method of Netowrk class
	 *
	 * @return outBufferStatus
	 * @param
	 */
	public /* synchronized */ static String getOutBufferStatus() // added synchronized
	{
		return outBufferStatus;
	}

	/**
	 * Mutator method of Network class
	 *
	 * @return
	 * @param outBufStatus
	 */
	public /* synchronized */ static void setOutBufferStatus(String outBufStatus) // added sync
	{
		outBufferStatus = outBufStatus;
	}

	/**
	 * Accessor method of Netowrk class
	 *
	 * @return networkStatus
	 * @param
	 */
	public /* synchronized */ static String getNetworkStatus() // added synchronized
	{
		return networkStatus;
	}

	/**
	 * Mutator method of Network class
	 *
	 * @return
	 * @param netStatus
	 */
	public static void setNetworkStatus(String netStatus) {
		networkStatus = netStatus;
	}

	/**
	 * Accessor method of Netowrk class
	 *
	 * @return inputIndexClient
	 * @param
	 */
	public /* synchronized */ static int getinputIndexClient() {
		return inputIndexClient;
	}

	/**
	 * Mutator method of Network class
	 *
	 * @return
	 * @param i1
	 */
	public /* synchronized */ static void setinputIndexClient(int i1)// added synchronized
	{

		inputIndexClient = i1;
	}

	/**
	 * Accessor method of Netowrk class
	 *
	 * @return inputIndexServer
	 * @param
	 */
	public /* synchronized */ static int getinputIndexServer()// added synchronized
	{
		return inputIndexServer;
	}

	/**
	 * Mutator method of Network class
	 *
	 * @return
	 * @param i2
	 */
	public /* synchronized */ static void setinputIndexServer(int i2)// added synchronized
	{
		inputIndexServer = i2;
	}

	/**
	 * Accessor method of Netowrk class
	 *
	 * @return outputIndexServer
	 * @param
	 */
	public /* synchronized */ static int getoutputIndexServer()// added synchronized
	{
		return outputIndexServer;
	}

	/**
	 * Mutator method of Network class
	 *
	 * @return
	 * @param o1
	 */
	public /* synchronized */ static void setoutputIndexServer(int o1)// added synchronized
	{

		outputIndexServer = o1;
	}

	/**
	 * Accessor method of Netowrk class
	 *
	 * @return outputIndexClient
	 * @param
	 */
	public /* synchronized */ static int getoutputIndexClient()// added synchronized
	{
		return outputIndexClient;
	}

	/**
	 * Mutator method of Network class
	 *
	 * @return
	 * @param o2
	 */
	public /* synchronized */ static void setoutputIndexClient(int o2)// added synchronized
	{
		outputIndexClient = o2;
	}

	/**
	 * Accessor method of Netowrk class
	 *
	 * @return maxNbPackets
	 * @param
	 */
	public static int getMaxNbPackets() {
		return maxNbPackets;
	}

	/**
	 * Mutator method of Network class
	 *
	 * @return
	 * @param maxPackets
	 */
	public static void setMaxNbPackets(int maxPackets) {
		maxNbPackets = maxPackets;
	}

	/**
	 * Transmitting the transactions from the client to the server through the
	 * network
	 *
	 * @return
	 * @param inPacket transaction transferred from the client
	 *
	 */
	public /* synchronized */ static boolean send(Transactions inPacket) {
		try {
			empty1.acquire();
			mutex1.acquire();
			// System.out.println("debug-send call"); debug
			inComingPacket[inputIndexClient].setAccountNumber(inPacket.getAccountNumber());
			inComingPacket[inputIndexClient].setOperationType(inPacket.getOperationType());
			inComingPacket[inputIndexClient].setTransactionAmount(inPacket.getTransactionAmount());
			inComingPacket[inputIndexClient].setTransactionBalance(inPacket.getTransactionBalance());
			inComingPacket[inputIndexClient].setTransactionError(inPacket.getTransactionError());
			inComingPacket[inputIndexClient].setTransactionStatus("transferred");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		/*
		 * System.out.println("\n DEBUG : Network.send() - index inputIndexClient " +
		 * inputIndexClient);
		 */
		/*
		 * System.out.println("\n DEBUG : Network.send() - account number " +
		 * inComingPacket[inputIndexClient].getAccountNumber());
		 */
		finally {
			// if incomingpacket is empty print error message , wrong account
			if (inComingPacket[inputIndexClient].getAccountNumber().equals(""))
				System.out.println("Error");

			setinputIndexClient(((getinputIndexClient() + 1)
					% getMaxNbPackets())); /* Increment the input buffer index for the client */
			/* Check if input buffer is full */
			if (getinputIndexClient() == getoutputIndexServer()) {
				setInBufferStatus("full");

				/*
				 * System.out.println("\n DEBUG : Network.send() - inComingBuffer status " +
				 * getInBufferStatus());
				 */
			} else {
				setInBufferStatus("normal");
			}

			mutex1.release();
			full1.release();
		}
		return true;

	}

	/**
	 * Transmitting the transactions from the server to the client through the
	 * network
	 * 
	 * @return
	 * @param outPacket updated transaction received by the client
	 *
	 */
	public /* synchronized */ static boolean receive(Transactions outPacket) {

		try {
			empty2.acquire();
			mutex2.acquire();
			outPacket.setAccountNumber(outGoingPacket[outputIndexClient].getAccountNumber());
			outPacket.setOperationType(outGoingPacket[outputIndexClient].getOperationType());
			outPacket.setTransactionAmount(outGoingPacket[outputIndexClient].getTransactionAmount());
			outPacket.setTransactionBalance(outGoingPacket[outputIndexClient].getTransactionBalance());
			outPacket.setTransactionError(outGoingPacket[outputIndexClient].getTransactionError());
			outPacket.setTransactionStatus("done");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		/*
		 * System.out.println("\n DEBUG : Network.receive() - index outputIndexClient "
		 * + outputIndexClient);
		 */
		/*
		 * System.out.println("\n DEBUG : Network.receive() - account number " +
		 * outPacket.getAccountNumber());
		 */
		finally {
			setoutputIndexClient(((getoutputIndexClient() + 1)
					% getMaxNbPackets())); /* Increment the output buffer index for the client */
			/* Check if output buffer is empty */
			if (getoutputIndexClient() == getinputIndexServer()) {
				setOutBufferStatus("empty");

				/*
				 * System.out.println("\n DEBUG : Network.receive() - outGoingBuffer status " +
				 * getOutBufferStatus());
				 */
			} else {
				setOutBufferStatus("normal");
			}
			mutex2.release();
			full2.release();
		}
		return true;
	}

	/**
	 * Transferring the completed transactions from the server to the network buffer
	 *
	 * @return
	 * @param outPacket updated transaction transferred by the server to the network
	 *                  output buffer
	 *
	 */
	public /* synchronized */ static boolean transferOut(Transactions outPacket) {
		try {
			full2.acquire();
			mutex2.acquire();
			// System.out.println("Call TransferOut method ->Input index server: " +
			// inputIndexServer); debug
			outGoingPacket[inputIndexServer].setAccountNumber(outPacket.getAccountNumber());
			outGoingPacket[inputIndexServer].setOperationType(outPacket.getOperationType());
			outGoingPacket[inputIndexServer].setTransactionAmount(outPacket.getTransactionAmount());
			outGoingPacket[inputIndexServer].setTransactionBalance(outPacket.getTransactionBalance());
			outGoingPacket[inputIndexServer].setTransactionError(outPacket.getTransactionError());
			outGoingPacket[inputIndexServer].setTransactionStatus("transferred");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			// if outGoingPacket is empty print error message, wrong account
			if (outGoingPacket[inputIndexServer].getAccountNumber().equals(""))
				System.out.println("Error");

			/*
			 * System.out.
			 * println("\n DEBUG : Network.transferOut() - index inputIndexServer " +
			 * inputIndexServer);
			 */
			/*
			 * System.out.println("\n DEBUG : Network.transferOut() - account number " +
			 * outGoingPacket[inputIndexServer].getAccountNumber());
			 */
			setinputIndexServer(((getinputIndexServer() + 1)
					% getMaxNbPackets())); /* Increment the output buffer index for the server */
			/* Check if output buffer is full */
			if (getinputIndexServer() == getoutputIndexClient()) {
				setOutBufferStatus("full");

				/*
				 * System.out.
				 * println("\n DEBUG : Network.transferOut() - outGoingBuffer status " +
				 * getOutBufferStatus());
				 */
			} else {
				setOutBufferStatus("normal");
			}
			mutex2.release();
			empty2.release();
		}
		return true;
	}

	/**
	 * Transferring the transactions from the network buffer to the server
	 * 
	 * @return
	 * @param inPacket transaction transferred from the input buffer to the server
	 *
	 */
	public /* synchronized */ static boolean transferIn(Transactions inPacket) // added synchronized
	{
		try {
			full1.acquire();
			mutex1.acquire();
			// System.out.println("Call TransferIn method -> Output Index server: " +
			// outputIndexServer); debug
			inPacket.setAccountNumber(inComingPacket[outputIndexServer].getAccountNumber());
			inPacket.setOperationType(inComingPacket[outputIndexServer].getOperationType());
			inPacket.setTransactionAmount(inComingPacket[outputIndexServer].getTransactionAmount());
			inPacket.setTransactionBalance(inComingPacket[outputIndexServer].getTransactionBalance());
			inPacket.setTransactionError(inComingPacket[outputIndexServer].getTransactionError());
			inPacket.setTransactionStatus("received");
		}
		/*
		 * System.out.
		 * println("\n DEBUG : Network.transferIn() - index outputIndexServer " +
		 * outputIndexServer);
		 */
		/*
		 * System.out.println("\n DEBUG : Network.transferIn() - account number " +
		 * inPacket.getAccountNumber());
		 */
		catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			if (inPacket.getAccountNumber().equals("") || inPacket.getAccountNumber().equals(" "))
				System.out.println("Invalid account!");

			setoutputIndexServer(((getoutputIndexServer() + 1)
					% getMaxNbPackets())); /* Increment the input buffer index for the server */
			/* Check if input buffer is empty */
			if (getoutputIndexServer() == getinputIndexClient()) {
				setInBufferStatus("empty");

				/*
				 * System.out.println("\n DEBUG : Network.transferIn() - inComingBuffer status "
				 * + getInBufferStatus());
				 */
			} else {
				setInBufferStatus("normal");
			}
			mutex1.release();
			empty1.release();
		}
		return true;
	}

	/**
	 * Handling of connection requests through the network
	 *
	 * @return valid connection
	 * @param IP
	 *
	 */
	public static boolean connect(String IP) {
		if (getNetworkStatus().equals("active")) {
			if (getClientIP().equals(IP)) {
				setClientConnectionStatus("connected");
				setPortID(0);
			} else if (getServerIP().equals(IP)) {
				setServerConnectionStatus("connected");
			}
			return true;
		} else
			return false;
	}

	/**
	 * Handling of disconnection requests through the network
	 * 
	 * @return valid disconnection
	 * @param IP
	 *
	 */
	public static boolean disconnect(String IP) {
		if (getNetworkStatus().equals("active")) {
			if (getClientIP().equals(IP)) {
				setClientConnectionStatus("disconnected");
			} else if (getServerIP().equals(IP)) {
				setServerConnectionStatus("disconnected");
			}
			return true;
		} else
			return false;
	}

	/**
	 * Create a String representation based on the Network Object
	 *
	 * @return String representation
	 */
	public String toString() {
		return ("\n Network status " + getNetworkStatus() + "Input buffer " + getInBufferStatus() + "Output buffer "
				+ getOutBufferStatus());
	}

	/**
	 * Code for the run method
	 *
	 * @return
	 * @param
	 */
	public void run() {

		/*
		 * while server or client is connected or idling, thread will yield
		 * while(getServerConnectionStatus().equals("connected") ||
		 * getClientConnectionStatus().equals("connected") ||
		 * getServerConnectionStatus().equals("idle") ||
		 * getClientConnectionStatus().equals("idle")) { Thread.yield(); }
		 */

		System.out.println("\n Terminating network thread - Client disconnected Server disconnected");
		// this.interrupt();
	}
}