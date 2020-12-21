package uminho.dss.turmas3l.business.Transporte;

import java.util.Collection;
import java.util.TreeSet;

public class SubSistemaTransporte {
    private Collection<String> robotsIds;

    public SubSistemaTransporte(){
        robotsIds = new TreeSet<>();
    }

    public SubSistemaTransporte(Collection<String> ids){
        robotsIds = new TreeSet<>(ids);
    }

    public void setRobotsIds(Collection<String> ids){
        robotsIds.addAll(ids);
    }

    public void addRobotId(String id){
        robotsIds.add(id);
    }

    public String removeRobotId(String id){
        if(robotsIds.contains(id)) {
            robotsIds.remove(id);
            return id;
        }
        else return null;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SubSistemaTransporte{");
        sb.append("robotsIds=").append(robotsIds);
        sb.append('}');
        return sb.toString();
    }
}
