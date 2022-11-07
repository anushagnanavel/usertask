package io.manju.vertx.crudapi.entity;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import io.vertx.core.json.JsonObject;
@Entity
@Table(name = "Task")
public class Task implements Serializable {
	
		@Id	  
		@GenericGenerator(name="task_id", strategy = "task_id")
		 private String task_id;
		
	    @Column(name = "title", nullable=false)
	    private String title;
	   
	    @Column(name ="description", nullable=false)
	    private String description;
	   
	    @Column(name ="status", nullable=false)
	    private String status;
	    @Column(name = "timeline", nullable=false)
	    private String timeline;
	    @Column(name = "assignto", nullable=false)
	    private String  assignto;

	   
	    public String getTitle() {
	        return title;
	    }
	    public void setTitle(String title ) {
	        this.title = title ;
	    }
	   
	    public String getDescription() {
	        return description;
	    }
	    public void setDescription(String description ) {
	        this.description = description ;
	       
	    }
	    public String getStatus() {
	        return status;
	    }
	    public void setStatus(String status ) {
	        this.status = status ;
	    }
	   
	   
	   
	    public String getTimeline() {
	        return timeline;
	    }
	    public void setTimeline(String timeline ) {
	        this.timeline = timeline ;
	    }
	   
	   
	    public String getAssignto() {
	        return assignto;
	    }
	    public void setAssignto(String assignto ) {
	        this.assignto = assignto ;
	    }
	   
	   
	   
	    public String getTask_id() {
	        return task_id;
	    }
	    public void setTask_id(String task_id ) {
	        this.task_id = task_id ;
	    }
	   
	   
	 
	    public String toJsonString(){
            return String.valueOf(JsonObject.mapFrom(this));
        }
}