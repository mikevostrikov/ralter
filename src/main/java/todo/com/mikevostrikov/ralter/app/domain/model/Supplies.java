package todo.com.mikevostrikov.ralter.app.domain.model;
// Generated 28.03.2009 2:16:48 by Hibernate Tools 3.2.1.GA


import java.util.HashSet;
import java.util.Set;

/**
 * Supplies generated by hbm2java
 */
public class Supplies  implements java.io.Serializable {


     private Long suId;
     private String suName;
     private Double suQuantity;
     private String suMeasure;
     private String suComment;
     private String suManufacturer;
     private Double suPrice;
     private Set suops = new HashSet(0);

    public Supplies() {
    }

	
    public Supplies(Long suId) {
        this.suId = suId;
    }
    public Supplies(Long suId, String suName, Double suQuantity, String suMeasure, String suComment, String suManufacturer, Double suPrice, Set suops) {
       this.suId = suId;
       this.suName = suName;
       this.suQuantity = suQuantity;
       this.suMeasure = suMeasure;
       this.suComment = suComment;
       this.suManufacturer = suManufacturer;
       this.suPrice = suPrice;
       this.suops = suops;
    }
   
    public Long getSuId() {
        return this.suId;
    }
    
    public void setSuId(Long suId) {
        this.suId = suId;
    }
    public String getSuName() {
        return this.suName;
    }
    
    public void setSuName(String suName) {
        this.suName = suName;
    }
    public Double getSuQuantity() {
        return this.suQuantity;
    }
    
    public void setSuQuantity(Double suQuantity) {
        this.suQuantity = suQuantity;
    }
    public String getSuMeasure() {
        return this.suMeasure;
    }
    
    public void setSuMeasure(String suMeasure) {
        this.suMeasure = suMeasure;
    }
    public String getSuComment() {
        return this.suComment;
    }
    
    public void setSuComment(String suComment) {
        this.suComment = suComment;
    }
    public String getSuManufacturer() {
        return this.suManufacturer;
    }
    
    public void setSuManufacturer(String suManufacturer) {
        this.suManufacturer = suManufacturer;
    }
    public Double getSuPrice() {
        return this.suPrice;
    }
    
    public void setSuPrice(Double suPrice) {
        this.suPrice = suPrice;
    }
    public Set getSuops() {
        return this.suops;
    }
    
    public void setSuops(Set suops) {
        this.suops = suops;
    }

    public void trimStrings(){
       if (suName != null) suName = suName.trim();
       if (suMeasure != null) suMeasure = suMeasure.trim();
       if (suComment != null) suComment = suComment.trim();
       if (suManufacturer != null) suManufacturer = suManufacturer.trim();
    }

    public void cutNulls(){
       if (suName == null) suName = "-";
       if (suMeasure == null) suMeasure = "-";
       if (suComment == null) suComment = "-";
       if (suManufacturer == null) suManufacturer = "-";
       if (suPrice == null) suPrice = new Double(0);
       if (suQuantity == null) suQuantity = new Double(0);
    }


}

