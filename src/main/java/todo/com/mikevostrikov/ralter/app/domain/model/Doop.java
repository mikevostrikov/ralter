package todo.com.mikevostrikov.ralter.app.domain.model;
// Generated 28.03.2009 2:16:48 by Hibernate Tools 3.2.1.GA

/**
 * Doop generated by hbm2java
 */
public class Doop  implements java.io.Serializable {


     private Long doopId;
     private Documents documents;
     private Operations operations;

    public Doop() {
    }

	
    public Doop(Long doopId) {
        this.doopId = doopId;
    }
    public Doop(Long doopId, Documents documents, Operations operations) {
       this.doopId = doopId;
       this.documents = documents;
       this.operations = operations;
    }
   
    public Long getDoopId() {
        return this.doopId;
    }
    
    public void setDoopId(Long doopId) {
        this.doopId = doopId;
    }
    public Documents getDocuments() {
        return this.documents;
    }
    
    public void setDocuments(Documents documents) {
        this.documents = documents;
    }
    public Operations getOperations() {
        return this.operations;
    }
    
    public void setOperations(Operations operations) {
        this.operations = operations;
    }




}


