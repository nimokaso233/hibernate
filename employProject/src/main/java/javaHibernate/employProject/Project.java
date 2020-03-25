package javaHibernate.employProject;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table (name="PROJECT", uniqueConstraints = {
        @UniqueConstraint(columnNames = "p_id")})
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY, region="employee")

public class Project {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="p_id",unique=true, nullable=false)
	private long id;
	
	
	@Column(name="Title",unique = true, nullable = false, length = 100)
	private String title;
	
	@ManyToMany(mappedBy="project")
	private List<Employee> employee;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public List<Employee> getEmp(){
		return employee;
	}
	
	public void setEmp(List<Employee> employee) {
		this.employee=employee;
		
	}
}
