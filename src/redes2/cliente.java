package redes2;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class cliente {
    public static void main(String[] args){
        
        try{
            Socket socket = new Socket("Localhost", 3455);
            BufferedReader leitor = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter escritor = new PrintWriter(socket.getOutputStream(), true);
            
            escritor.println("O cliente esteve aqui");
            
            String mensagemRecebida = leitor.readLine();
            System.out.println(mensagemRecebida);
            
            socket.close();
            
        }catch(IOException exception){
            exception.printStackTrace();
        }
    }
    
}
