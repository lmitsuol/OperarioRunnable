
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Principal {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Digite o host (ex: www.google.com.br): ");
        String host = sc.nextLine().trim();
        if (host.isEmpty()) {
            System.out.println("Host vazio. Encerrando.");
            sc.close();
            return;
        }

        System.out.print("Digite a porta (enter para usar 80): ");
        String portaStr = sc.nextLine().trim();
        int port = 80;
        if (!portaStr.isEmpty()) {
            try {
                port = Integer.parseInt(portaStr);
            } catch (NumberFormatException e) {
                System.out.println("Porta inválida. Usando 80.");
                port = 80;
            }
        }

        // tenta conectar e enviar pedido HTTP simples
        try (Socket sock = new Socket(host, port); PrintWriter out = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()), true); BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()))) {

            // Envia uma requisição HTTP bem formada (terminadores CRLF) e Host header
            out.print("GET / HTTP/1.0\r\n");
            out.print("Host: " + host + "\r\n");
            out.print("\r\n");
            out.flush();

            String linha;
            while ((linha = in.readLine()) != null) {
                System.out.println(linha);
            }

        } catch (UnknownHostException e) {
            System.err.println("Host desconhecido: " + host);
        } catch (IOException e) {
            System.err.println("Problemas de IO: " + e.getMessage());
        } finally {
            sc.close();
        }
    }
}
