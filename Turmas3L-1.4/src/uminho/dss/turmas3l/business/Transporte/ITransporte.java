package uminho.dss.turmas3l.business.Transporte;

import uminho.dss.turmas3l.business.Gestao.Palete;

public interface ITransporte {
    void comunicarTransporte(String id, Palete p, Percurso per);
    Robot getRobotDisponivel();
}
