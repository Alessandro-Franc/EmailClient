package sample;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class UpdateTask implements Runnable{
    private ArrayList<EasyEmail> serverList;

    private Model model;

    public UpdateTask (Model m){
        this.model=m;
    }

    @Override
    public void run() {
        AggiornaLista();
    }

    public void AggiornaLista(){
        System.out.println("AggiornaLista");
        //resetto la lista
        if(this.model.geteMailList().size()>0){
            model.geteMailList().remove(0 , model.geteMailList().size());
        }
        //connessione al server
        try{
            String nomeHost = InetAddress.getLocalHost().getHostName();
            Socket s = new Socket(nomeHost, 8843);
            try{
                OutputStream out = new DataOutputStream(s.getOutputStream());
                //mando la richiesta di ricevere la lista
                out.write(0);
                //ricevo la lista
                ObjectInputStream in = new ObjectInputStream(s.getInputStream());
                //aggiorno la lista
                try{
                    serverList = ((ArrayList<EasyEmail>) in.readObject());
                    for(int i=0 ; i<serverList.size() ; i++){
                        String text = serverList.get(i).geteText();
                        String Dest = serverList.get(i).getDestination();
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
}
