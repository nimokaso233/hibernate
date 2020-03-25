package javaHibernate.employProject;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "EMPLOYEE",uniqueConstraints = {
        @UniqueConstraint(columnNames = "emp_id")})
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY, region="employee")

public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "emp_id")
	private long id;

	@Column(name = "emp_name",unique = true, nullable = false, length = 100)
	private String name;

	@Column(name = "age",unique = true, nullable = false, length = 100)
	private int age;
	
	@Column(name = "email", unique = true, nullable = false, length = 100)
	private String email;
	
	
	@ManyToMany(cascade= {CascadeType.ALL})
	@JoinTable(name = "emp_project",   
            joinColumns = {@JoinColumn(referencedColumnName="emp_id") },   
            inverseJoinColumns = {@JoinColumn(referencedColumnName="p_id")})

	private List<Project> project;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	public List<Project> getProject(){
		return project;
	}
	
	public void setProject(List<Project> project) {
		this.project=project;
	}

}


