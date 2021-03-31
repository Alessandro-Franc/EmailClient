package sample;

import com.google.gson.Gson;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class SendEmailTask implements Runnable{
    private Model model;

    private EasyEmail email;

   public SendEmailTask (Model m){
       this.model = m;
   }

    @Override
    public void run() {
        sendEmail();
    }

    public void SetEmail(EasyEmail m){
        this.email = m;
    }
    public synchronized void sendEmail(){
        try{
            String nomeHost = InetAddress.getLocalHost().getHostName();
            Socket s = new Socket(nomeHost, 8843);
            System.out.println("Ho aperto il socket verso il server");
            try{
                String sa = new Gson().toJson(this.email);
                OutputStream out = new DataOutputStream(s.getOutputStream());
                //avviso che sto mandando una mail
                out.write(1);
                ObjectOutputStream emailOut = new ObjectOutputStream(s.getOutputStream());
                //mando l'oggetto con la mail
                emailOut.writeUTF(sa);
                System.out.println("email mandata");
            }finally{
                s.close();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
