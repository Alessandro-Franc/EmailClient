package sample;

import com.google.gson.Gson;
//prova
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class ConnectionController{
    private ArrayList<ArrayList<EasyEmail>> serverList;

    private Model model;

    public void start(Model model){
        this.model = model;
    }

    public synchronized void AggiornaLista(){
        System.out.println("AggiornaLista");
        //resetto la lista email Ricevute
        if(model.getReMailList().size()>0){
            model.getReMailList().clear();
        }
        //resetto la lista email Inviate
        if(this.model.getIeMailList().size()>0){
            model.getIeMailList().clear();
        }
        //connessione al server
        try{
            String nomeHost = InetAddress.getLocalHost().getHostName();
            Socket s = new Socket(nomeHost, 8082);
            try{
                Request r = new Request(0, model.getId());//senza id static non andava
                DataOutputStream task = new DataOutputStream(s.getOutputStream());
                //mando la richiesta di ricevere la lista
                task.writeUTF(new Gson().toJson(r));
                //ricevo la lista
                ObjectInputStream in = new ObjectInputStream(s.getInputStream());
                //aggiorno la lista
                try{
                    serverList = ((ArrayList<ArrayList<EasyEmail>>) in.readObject());
                    //prendo le email ricevute
                    for (int i = 0; i < serverList.get(0).size(); i++) {
                        String text = serverList.get(0).get(i).geteText();
                        String[] Dest = serverList.get(0).get(i).getDestination();
                        String obj = serverList.get(0).get(i).getObject();
                        String Mitt = serverList.get(0).get(i).getMitt();
                        model.getReMailList().add(i, new Email(Dest, Mitt, obj, text));
                    }
                    //prendo le email inviate
                    for (int i = 0; i < serverList.get(1).size(); i++) {
                        String text = serverList.get(1).get(i).geteText();
                        String[] Dest = serverList.get(1).get(i).getDestination();
                        String obj = serverList.get(1).get(i).getObject();
                        String Mitt = serverList.get(1).get(i).getMitt();
                        model.getIeMailList().add(i, new Email(Dest, Mitt, obj, text));
                    }
                    model.seteMaillistI();
                    model.seteMaillistR();

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
                String send = new Gson().toJson(new SendMail(model.getId(), m));
                DataOutputStream emailOut = new DataOutputStream(s.getOutputStream());
                //mando l'oggetto con la mail
                emailOut.writeUTF(send);
                DataInputStream in =  new DataInputStream(s.getInputStream());
                String response = in.readUTF();
                System.out.println(send + "\n" + response);
               new PopUpController(response).start();
                //chiamare qui metodo per popup dove stampare response
            }finally{
                s.close();
            }
        }catch(IOException | InterruptedException e){
            e.printStackTrace();
        }
    }

    public synchronized void deleteEmail(Email m){
        try{
            EasyEmail ee = m.toEasyMail();
            String nomeHost = InetAddress.getLocalHost().getHostName();
            Socket s = new Socket(nomeHost, 8082);
            System.out.println("Ho aperto il socket verso il server");
            try{
                //avviso che sto mandando una richiesta di eliminazione mail
                String send = new Gson().toJson(new DeleteMail(model.getId(), ee, model.getEmailVisual()));
                DataOutputStream emailOut = new DataOutputStream(s.getOutputStream());
                //mando l'oggetto con la mail
                emailOut.writeUTF(send);
                DataInputStream in =  new DataInputStream(s.getInputStream());
                String response = in.readUTF();
                System.out.println(send+"\n"+response);
                //chiamare qui popup per stampare response
            }finally{
                s.close();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
