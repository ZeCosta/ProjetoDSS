package uminho.dss.turmas3l.business;

import uminho.dss.turmas3l.business.Gestao.Palete;

public class QRCode {
    private String qrcode;

    public QRCode(String qr){
        this.qrcode=qr;
    }

    public Palete criarPalete(){
        //transformar qrcode em palete e em materiais

        String[] linhaPartida;
        linhaPartida = this.qrcode.split(":", 1);

        String idpalete;


        idpalete=linhaPartida[0];

        String[] linhas;
        linhas=linhaPartida[1].split(";",4);
        //MateriaPrima materia = new MateriaPrima(linhas[0],linhas[1],Double.parseDouble(linhas[2]),Integer.parseInt(linhas[3]));



        return new Palete(idpalete,0);
    }
}
