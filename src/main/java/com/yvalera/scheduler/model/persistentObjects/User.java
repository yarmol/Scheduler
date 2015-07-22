package main.java.com.yvalera.scheduler.model.persistentObjects;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import main.java.com.yvalera.scheduler.model.persistentObjects.Task.Task;

/**
 * It represents users with their schedulled days and
 * tasks. Keeps in database.
 * 
 * @author Yakubovich Valeriy
 */
@Entity
public class User {
		
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="USR_ID")
	private  long id;

	// while username is unique in application
	@Column(unique=true)
	private String name;//username
	
	/*
	 * Tasks which user scheduled to do
	 */
	@OneToMany(fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
	@JoinColumn(name="USER_ID", referencedColumnName="USR_ID") 
	private Set<Task> tasks = new HashSet<Task>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Task> getTasks() {
		return tasks;
	}
}
