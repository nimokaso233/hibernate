package javaHibernate.employProject;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.stat.Statistics;

import javaHibernate.employProject.Project;
import javaHibernate.employProject.Employee;
import javaHibernate.employProject.HibernateUtil;;;

public class EHCacheMain {
	
public static void main(String[] args) {
		
		System.out.println("Temp Dir:"+System.getProperty("java.io.tmpdir"));
		
		//Initialize Sessions
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Statistics stats = sessionFactory.getStatistics();
		System.out.println("Stats enabled="+stats.isStatisticsEnabled());
		stats.setStatisticsEnabled(true);
		System.out.println("Stats enabled="+stats.isStatisticsEnabled());
		
		Session session = sessionFactory.openSession();
		Session otherSession = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		Transaction otherTransaction = otherSession.beginTransaction();
		
		printStats(stats, 0);
		
		Employee emp = (Employee) session.load(Employee.class, 1L);
		printData(emp, stats, 1);
		
		emp = (Employee) session.load(Employee.class, 1L);
		printData(emp, stats, 2);
		
		//clear first level cache, use second level cache 
		session.evict(emp);
		
		emp = (Employee) session.load(Employee.class, 1L);
		printData(emp, stats, 3);
		
		emp = (Employee) session.load(Employee.class, 3L);
		printData(emp, stats, 4);
		
		
		//Release resources
		transaction.commit();
		otherTransaction.commit();
		sessionFactory.close();
	}

	@SuppressWarnings("unused")
	private static void setupData() {
		
		Employee emp1 = new Employee();
		emp1.setId(1123);
		emp1.setName("Tom");
		emp1.setAge(21);
		emp1.setEmail("tom1123@gmail.com");
		
		Employee emp2 =new Employee();
		emp2.setId(1124);
		emp2.setName("Mark");
		emp2.setAge(29);
		emp2.setEmail("Mark1124@gmail.com");
		
		Employee emp3 =new Employee();
		emp3.setId(1127);
		emp3.setName("Bob");
		emp3.setAge(30);
		emp3.setEmail("Bob1127@gmail.com");
		
		Project proj1=new Project();
		proj1.setId(12);
		proj1.setTitle("webapp133");
		
		Project proj2=new Project();
		proj1.setId(15);
		proj1.setTitle("webapp137");
		
		Project proj3=new Project();
		proj1.setId(22);
		proj1.setTitle("mobile12");
		
		ArrayList<Employee> e1=new ArrayList<Employee>(); 
	    e1.add(emp1);
		e1.add(emp2);
		
		ArrayList<Employee> e2=new ArrayList<Employee>(); 
		e2.add(emp1);
		e2.add(emp3);
		
		ArrayList<Employee> e3=new ArrayList<Employee>(); 
		e3.add(emp1);
		e3.add(emp2);
		e3.add(emp3);
		
		ArrayList<Project> p1=new ArrayList<Project>(); 
		p1.add(proj1);
		
		ArrayList<Project> p2=new ArrayList<Project>();
		p2.add(proj1);
		p2.add(proj3);
		
		ArrayList<Project> p3=new ArrayList<Project>();
		p3.add(proj2);
		
		
		proj1.setEmp(e1);
		proj2.setEmp(e2);
		proj3.setEmp(e3);
		

	
	 
	        Session session = HibernateUtil.getSessionFactory().openSession();
	        session.beginTransaction();
	 
	        session.save(emp1);
	        session.save(emp2);
	        session.save(emp3);
	        
	        session.getTransaction().commit();
	        session.close();
	
		
	}
	
	private static void printStats(Statistics stats, int i) {
		System.out.println("***** " + i + " *****");
		System.out.println("Fetch Count="
				+ stats.getEntityFetchCount());
		System.out.println("Second Level Hit Count="
				+ stats.getSecondLevelCacheHitCount());
		System.out
				.println("Second Level Miss Count="
						+ stats
								.getSecondLevelCacheMissCount());
		System.out.println("Second Level Put Count="
				+ stats.getSecondLevelCachePutCount());
	}

	private static void printData(Employee emp, Statistics stats, int count) {
		System.out.println(count+":: Name="+emp.getName()+", Project="+emp.getProject());
		printStats(stats, count);
	}

}
