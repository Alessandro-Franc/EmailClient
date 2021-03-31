package sample;

import com.google.gson.Gson;
//prova
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class ConnectionController{
    private ArrayList<EasyEmail> serverList;

    private Model model;

    public void start(Model model){
        this.model = model;
    }

    public synchronized void AggiornaLista(){
        System.out.println("AggiornaLista");
        //resetto la lista
        if(this.model.geteMailList().size()>0){
            model.geteMailList().remove(0 , model.geteMailList().size());
        }
        //connessione al server
        try{
            String nomeHost = InetAddress.getLocalHost().getHostName();
            Socket s = new Socket(nomeHost, 8082);
            try{
                Request r = new Request(0, model.getId());//senza id static non andava
                DataOutputStream task = new DataOutputStream(s.getOutputStream());
                task.writeUTF(new Gson().toJson(r));
                //mando la richiesta di ricevere la lista

                //ricevo la lista
                ObjectInputStream in = new ObjectInputStream(s.getInputStream());
                //aggiorno la lista
                try{
                    serverList = ((ArrayList<EasyEmail>) in.readObject());
                    for(int i=0 ; i<serverList.size() ; i++){
                        String text = serverList.get(i).geteText();
                        String[] Dest = serverList.get(i).getDestination();
                        String obj = serverList.get(i).getObject();
                        String Mitt = serverList.get(i).getMitt();
                        model.geteMailList().add(i ,new Email(Dest , Mitt, obj , text));
                    }
                }catch(ClassNotFoundException e){
                    System.out.println(e.getMessage());
                }
            }finally{
                s.close();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public synchronized void sendEmail(EasyEmail m){
        try{
            String nomeHost = InetAddress.getLocalHost().getHostName();
            Socket s = new Socket(nomeHost, 8082);
            System.out.println("Ho aperto il socket verso il server");
            try{
                //avviso che sto mandando una mail
                String send = new Gson().toJson(new SendMail(Model.getId(), m));
                DataOutputStream emailOut = new DataOutputStream(s.getOutputStream());
                //mando l'oggetto con la mail
                emailOut.writeUTF(send);
                System.out.println("email mandata: "+send);
            }finally{
                s.close();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
