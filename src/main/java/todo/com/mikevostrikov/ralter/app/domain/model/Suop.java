package todo.com.mikevostrikov.ralter.app.domain.model;
// Generated 28.03.2009 2:16:48 by Hibernate Tools 3.2.1.GA


/**
 * Suop generated by hbm2java
 */
public class Suop  implements java.io.Serializable {


     private Long suopId;
     private Operations operations;
     private Supplies supplies;
     private Double suopQuantity;

    public Suop() {
    }

    public Suop(Operations operations, Supplies supplies, Double suopQuantity) {
       this.supplies = supplies;
       this.operations = operations;
       this.suopQuantity = suopQuantity;
    }
   
    public Long getSuopId() {
        return this.suopId;
    }
    
    public void setSuopId(Long suopId) {
        this.suopId = suopId;
    }
    public Supplies getSupplies() {
        return this.supplies;
    }
    
    public void setSupplies(Supplies supplies) {
        this.supplies = supplies;
    }
    public Operations getOperations() {
        return this.operations;
    }
    
    public void setOperations(Operations operations) {
        this.operations = operations;
    }
    public Double getSuopQuantity() {
        return this.suopQuantity;
    }
    
    public void setSuopQuantity(Double suopQuantity) {
        this.suopQuantity = suopQuantity;
    }




}


