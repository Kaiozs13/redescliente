package redes2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.IOException;
import java.util.Scanner;


public class cliente {
    private static int PORTA;
    public static void main(String[] args) {
        Scanner c = new Scanner(System.in);
        
        System.out.println("Digite a porta a ser usada: ");
            PORTA = c.nextInt();
        
        try (Socket socket = new Socket("localhost", PORTA);
             BufferedReader leitor = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter escritor = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Conectado ao servidor. Digite mensagens para enviar.");
            System.out.println("Para sair, digite 'sair'.");

            String mensagemParaEnviar;
            String resposta;

            while (true) {
                System.out.print("Você: ");
                mensagemParaEnviar = teclado.readLine();
                escritor.println(mensagemParaEnviar);

                resposta = leitor.readLine();
                System.out.println("" + resposta);

                if ("sair".equalsIgnoreCase(mensagemParaEnviar)) {
                    System.out.println("Você encerrou a conexão.");
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
