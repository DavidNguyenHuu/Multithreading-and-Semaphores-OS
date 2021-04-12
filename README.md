# Multithreading-and-Semaphores-OS

In a client-server system, the client application sends requests to a server application
through a network connection. In such system, the user interface is implemented in the
client and the database is stored in the server. 

The following diagram illustrates the classes for the client-server banking 
application. 

![Capture](https://user-images.githubusercontent.com/37845640/114465761-8d7b8380-9bb5-11eb-97d5-678d4588faed.JPG)

 
4 threads are implemented so that the client, the server and the network can all run concurrently. The 
client has 2 threads, one for sending the transactions and another for receiving 
the completed transactions. 

Essentially it is a <b>consumer-producer</b> problem:

![Capture](https://user-images.githubusercontent.com/37845640/114466099-0084fa00-9bb6-11eb-9622-872a5f3144e8.JPG)
