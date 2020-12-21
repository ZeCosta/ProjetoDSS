package uminho.dss.turmas3l.business.Gestao;

import java.util.ArrayList;
import java.util.Collection;

public class ZonaArmazenamento {
    private Collection<String> pratsIds;

    public ZonaArmazenamento(){
        pratsIds = new ArrayList<>();
    }

    public ZonaArmazenamento(Collection<String> ids){
        pratsIds = new ArrayList<>();
        pratsIds.addAll(ids);
    }
}
