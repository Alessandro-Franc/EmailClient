package sample;

import com.google.gson.Gson;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class UpdateTask implements Runnable{
    private ArrayList<ArrayList<EasyEmail>> serverList;

    private Model model;

    public UpdateTask (Model m){
        this.model=m;
    }

    @Override
    public void run() {
        AggiornaLista();
    }

    public void AggiornaLista() {
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
                        for(int i=0 ; i<serverList.get(0).size() ; i++){
                            String text = serverList.get(0).get(i).geteText();
                            String[] Dest = serverList.get(0).get(i).getDestination();
                            String obj = serverList.get(0).get(i).getObject();
                            String Mitt = serverList.get(0).get(i).getMitt();
                            String data = serverList.get(0).get(i).getData();
                            model.getReMailList().add(i ,new Email(Dest , Mitt, obj , text, data));
                        }
                        //prendo le email inviate
                        for(int i=0 ; i<serverList.get(1).size() ; i++){
                            String text = serverList.get(1).get(i).geteText();
                            String[] Dest = serverList.get(1).get(i).getDestination();
                            String obj = serverList.get(1).get(i).getObject();
                            String Mitt = serverList.get(1).get(i).getMitt();
                            String data = serverList.get(1).get(i).getData();
                            model.getIeMailList().add(i ,new Email(Dest , Mitt, obj , text, data));
                        }
                        if(model.check(model.getReMailList().size())) {
                            new PopUpController("Avete nuove mail").start();
                        }
                        switch(model.getEmailVisual()){
                            case 0:
                                model.seteMaillistI();
                                model.seteMaillistR();
                                break;
                            case 1:
                                model.seteMaillistR();
                                model.seteMaillistI();
                                break;
                        }
                    }catch(ClassNotFoundException e){
                        System.out.println(e.getMessage());
                    }
                }finally{
                    s.close();
                }
            }catch(Exception e){
                System.out.println("Connessione al server assente");
            }
    }
}
