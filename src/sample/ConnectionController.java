package sample;

import com.google.gson.Gson;
//prova
import java.io.*;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class ConnectionController{
    private ArrayList<ArrayList<EasyEmail>> serverList;

    private Model model;

    public void start(Model model){
        this.model = model;
    }

    public synchronized void AggiornaLista() {
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
                Request r = new Request(0, model.getId());
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
                    if(model.check(model.getReMailList().size())) {
                        new PopUpController("Avete nuove mail").start();
                    }
                    model.seteMaillistI();
                    model.seteMaillistR();

                }catch(ClassNotFoundException e){
                    System.out.println(e.getMessage());
                }
            }finally{
                s.close();
            }
        }catch(Exception e){
            new PopUpController("Connessione al server assente, attendere di ricollegarsi").start();
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
            }finally{
                s.close();
            }
        }catch(Exception e){
            new PopUpController("Connessione al server assente, attendere di ricollegarsi").start();
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
                if(model.getEmailVisual()==0 && !response.equals("email da eliminare non trovata")) {
                    model.sizemin();
                }
                System.out.println(send+"\n"+response);
                new PopUpController(response).start();
            }finally{
                s.close();
            }
    }catch (ConnectException e) {
            new PopUpController("Connessione al server assente, attendere di ricollegarsi").start();
    } catch (NullPointerException e) {
            new PopUpController("Nessuna email da eliminare selezionata").start();
    } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
