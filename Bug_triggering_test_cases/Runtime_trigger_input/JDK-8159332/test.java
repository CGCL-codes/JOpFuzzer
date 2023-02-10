
package Comparador;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.PacketReceiver;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;
import jpcap.packet.UDPPacket;

public class Comparador {

    static NetworkInterface[] array;
    static JpcapCaptor subFluxo_pCap;
    static JpcapCaptor fluxoCompletos_pCap;
    static ArrayList<String> saida = new ArrayList<String>();
    static final String CLASSE = "http";
    //pasta onde estam os fluxos TCP completos
    static String url_completa = "D:\\Capturas\\Fluxos TCP Completo\\" + CLASSE;
    
    //static String url_completa = "D:\\Capturas\\teste\\alvo";
    
    
    //pasta onde estao os subfluxos a serem comparadosub
    static String url_subfluxos = "D:\\Capturas\\Subfluxos\\" + CLASSE;
    //static String url_subfluxos = "D:\\Capturas\\teste\\origem";
    
    static String url_fluxos_rotulados = "D:\\Capturas\\Fluxo Rotulado\\" + CLASSE;
   //  static String url_fluxos_rotulados = "D:\\Capturas\\teste\\rotulado";
  
    public static void main(String[] args) {
        try {
            
            
           
            for (int i = 1017; i < 1273; i++) {
                
                
                subFluxo_pCap = JpcapCaptor.openFile(url_subfluxos + "\\Subfluxo (" + i + ").pcap");
                Packet pacote = primeiroPacote(subFluxo_pCap);
                if(pacote  instanceof TCPPacket){
                TCPPacket pOrigem = (TCPPacket) pacote;
                System.out.println("Comparando o pacote do subfluxo:" + i);
                
                //int contadorFluxoCompleto = 1;
                for (int ix = 1; ix < 5159; ix++) {
                   // System.out.println("ix:" +ix);
                    fluxoCompletos_pCap = JpcapCaptor.openFile(url_completa + "\\Fluxo (" + ix + ").pcap");
                    //System.out.println("..comparando com fluxo: " + contadorFluxoCompleto++);
                    //System.out.println("...fluxo alvo:" + fluxoCompletos_pCap.getPacket().toString());
                     
                    boolean iguais = false;
                    boolean iguaisIvertido = false;
                    Packet pacoteAlvo = primeiroPacote(fluxoCompletos_pCap);
                    if (pacoteAlvo instanceof TCPPacket) {
                         TCPPacket pAlvo = (TCPPacket) pacoteAlvo;
                         iguais = comparafluxos(pOrigem, pAlvo);
                         iguaisIvertido = comparafluxosIvertido(pOrigem, pAlvo);
                     }
                   
                    if (iguais || iguaisIvertido) {
                        //salvar na pasta e excluir o fluxo completo e sair do loop interno
                        File fSalvar = new File(url_completa + "\\Fluxo (" + ix + ").pcap");
                        File fSalvarComo = new File(url_fluxos_rotulados + "\\Fluxo (" + ix + ").pcap");
                        copyFile(fSalvar, fSalvarComo);
                        System.out.println("...Fluxo Encontrado:"+ix);
                        break;
                    }else{
                     // System.out.println("...Diferente:" + ix);
                    }
                    
                    fluxoCompletos_pCap.close();
                    fluxoCompletos_pCap =null;
                    
                }
                
                
                         
           
         }
                 subFluxo_pCap.close();  
                 subFluxo_pCap = null;
            }
        } catch (IOException ex) {
            Logger.getLogger(Comparador.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    static private boolean comparafluxos(TCPPacket pOrigem, TCPPacket pAlvo) {

       
           // System.out.println("... Detalhes do Pacote");
            // System.out.println(".... Origem");
         //    System.out.println("..... IP-Origem:" + pOrigem.src_ip.getHostAddress());
           //  System.out.println("..... IP-Destino:" + pOrigem.dst_ip.getHostAddress().toString());
         //    System.out.println("..... Porta-Origem:" + pOrigem.src_port);
         //    System.out.println("..... Porta-Destino:" + pOrigem.dst_port);
             
         //    System.out.println(".... Destino");
         //    System.out.println("..... IP-Origem:" + pAlvo.src_ip.getHostAddress());
         //    System.out.println("..... IP-Destino:" + pAlvo.dst_ip.getHostAddress().toString());
         //    System.out.println("..... Porta-Origem:" + pAlvo.src_port);
         //    System.out.println("..... Porta-Destino:" + pAlvo.dst_port);
            
            
              //System.out.println(pOrigem.src_ip.getHostAddress() + ":" +pAlvo.dst_ip.getHostAddress());
            if (pOrigem.src_ip.getHostAddress().toString().equals(pAlvo.src_ip.getHostAddress().toString())) { //compara ip fonte
                if (pOrigem.dst_ip.getHostAddress().toString().equals(pAlvo.dst_ip.getHostAddress().toString())) { //compara ip destino
                    if (pOrigem.src_port == pAlvo.src_port) {
                        return (pOrigem.dst_port == pAlvo.dst_port);
                    } else {
                        return false;
                    }

                } else {
                    return false;
                }

            }

        
        return false;



    }
    
     static private boolean comparafluxosIvertido(TCPPacket pOrigem, TCPPacket pAlvo) {

       
           // System.out.println("... Detalhes do Pacote");
                             
           // System.out.println(pOrigem.src_ip.getHostAddress() + ":" +pAlvo.dst_ip.getHostAddress());
            if (pOrigem.src_ip.getHostAddress().equals(pAlvo.dst_ip.getHostAddress())) { //compara ip fonte
                 if (pOrigem.src_port == pAlvo.dst_port) {
                        return true;
                    } else {
                        return false;
                    }

             

            }

        
        return false;



    }

    public static void copyFile(File source, File destination) throws IOException {
        if (destination.exists()) {
            destination.delete();
        }
        FileChannel sourceChannel = null;
        FileChannel destinationChannel = null;
        try {
            sourceChannel = new FileInputStream(source).getChannel();
            destinationChannel = new FileOutputStream(destination).getChannel();
            sourceChannel.transferTo(0, sourceChannel.size(),
                    destinationChannel);
        } finally {
            if (sourceChannel != null && sourceChannel.isOpen()) {
                sourceChannel.close();
            }
            if (destinationChannel != null && destinationChannel.isOpen()) {
                destinationChannel.close();
            }
        }
    }

    private static int reserveID() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public static Packet primeiroPacote(JpcapCaptor pcaptor){
      final List<Packet> pacotes = new ArrayList<>();
                
            pcaptor.loopPacket(-1, new PacketReceiver() {
                @Override
                public void receivePacket(Packet packet) {
                    if (packet instanceof IPPacket) {
                       pacotes.add(packet);

                    }
                    
                }
            });
     return pacotes.get(0);
    
    
    }
}

