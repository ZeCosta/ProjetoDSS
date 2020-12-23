package uminho.dss.turmas3l.business;

import uminho.dss.turmas3l.business.Gestao.MateriaPrima;
import uminho.dss.turmas3l.business.Gestao.Palete;


import java.util.Scanner;

public class QRCode {
    private String qrcode;

    public QRCode(String qr){
        this.qrcode=qr;
    }

    public Palete criarPalete(){
        try {
            //transformar qrcode em palete e em materiais

            String[] linhaPartida;
            linhaPartida = this.qrcode.split(":", 2);

            String[] linhas1;
            linhas1=linhaPartida[0].split(";",2);

            String[] linhas;
            linhas=linhaPartida[1].split(";",4);


            return new Palete(linhas1[0],Double.parseDouble(linhas1[1]), new MateriaPrima(linhas[0],linhas[1],Double.parseDouble(linhas[2]),Integer.parseInt(linhas[3])));
        }catch (Exception e){
            return null;
        }
    }

}
