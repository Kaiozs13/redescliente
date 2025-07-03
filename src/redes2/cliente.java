package redes2;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class cliente {

    private static int PORTA;

    public static void main(String[] args) {
        Scanner c = new Scanner(System.in);

        try {
            System.out.print("Digite a porta a ser usada: ");
            PORTA = Integer.parseInt(c.nextLine());

            Socket socket = new Socket("localhost", PORTA);
            System.out.println("✅ Conectado ao servidor!");

            BufferedReader leitorServidor = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter escritorServidor = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader entradaCliente = new BufferedReader(new InputStreamReader(System.in));

            // Thread que recebe mensagens do servidor
            Thread recebedor = new Thread(() -> {
                String respostaServidor;
                try {
                    while ((respostaServidor = leitorServidor.readLine()) != null) {
                        System.out.println("\nServidor: " + respostaServidor);
                        System.out.print("Você (cliente): ");
                        System.out.flush();
                        if (respostaServidor.equalsIgnoreCase("sair")) {
                            System.out.println("⚠️ Conexão encerrada pelo servidor.");
                            break;
                        }
                    }
                } catch (IOException e) {
                    System.out.println("❌ Erro ao receber mensagem do servidor.");
                }
            });

            // Thread que envia mensagens
            Thread emissor = new Thread(() -> {
                String mensagem;
                try {
                    while (true) {
                        System.out.print("Você (cliente): ");
                        mensagem = entradaCliente.readLine();
                        escritorServidor.println(mensagem);
                        System.out.println("✔️ Mensagem enviada: " + mensagem);

                        if (mensagem.equalsIgnoreCase("sair")) {
                            System.out.println("👋 Você encerrou a conexão.");
                            break;
                        }
                    }
                    socket.close();
                } catch (IOException e) {
                    System.out.println("❌ Erro ao enviar mensagem.");
                }
            });

            recebedor.start();
            emissor.start();

            recebedor.join();
            emissor.join();

            System.out.println("🔌 Conexão encerrada.");
        } catch (Exception e) {
            System.out.println("Erro no cliente:");
            e.printStackTrace();
        }
    }
}
