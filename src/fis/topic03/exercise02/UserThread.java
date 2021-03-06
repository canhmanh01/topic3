package fis.topic03.exercise02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class UserThread extends Thread{
	private Socket socket;
    private ChatServer server;
    private PrintWriter writer;
 
    public UserThread(Socket socket, ChatServer server) {
        this.socket = socket;
        this.server = server;
    }
 

	public void run() {
        try {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input)); 
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true); 
            String clientMessage;
            String serverMessage;
            QuanLyCauHoi ql=new QuanLyCauHoi();
            ql.addCauHoi(new CauHoi(1,"a b c","Trl1"));
            ql.addCauHoi(new CauHoi(2,"a b d","Trl2"));
            ql.addCauHoi(new CauHoi(3,"a b e","Trl3"));
            ql.addCauHoi(new CauHoi(4,"a b f","Trl4"));
            ql.addCauHoi(new CauHoi(5,"a c f","Trl5"));
            ql.addCauHoi(new CauHoi(6,"a c f","Trl6"));
            ql.addCauHoi(new CauHoi(7,"a d f","Trl7"));
            List<CauHoi> list=new ArrayList<CauHoi>();
            do {
                clientMessage = reader.readLine();
                list=ql.checkTuongDong(clientMessage);
                StringBuilder s=new StringBuilder("Nhung Cau Hoi Co The Giong Cau Hoi Cua Ban : ");
                for (int i = 0; i < list.size(); i++) {
					s.append("/"+(i+1)+". "+list.get(i).getCauHoi());
				}
              System.out.println(s);
              writer.println(s);
              int viTri=Integer.parseInt(reader.readLine());
              System.out.println(viTri+"aaaaaa");
              if(list.size()<viTri) serverMessage="Khong Co Cau Hoi Nay";
              else {
              	serverMessage="Cau Tra Loi :"+list.get(viTri-1).getCauTraLoi();
              }
              System.out.println(serverMessage);
              writer.println(serverMessage);
            } while (!clientMessage.equals("bye"));
            socket.close();
        } catch (IOException ex) {
            System.out.println("Error in UserThread: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
