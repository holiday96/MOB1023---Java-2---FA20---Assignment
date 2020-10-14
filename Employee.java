package Assignment;

import java.io.Serializable;

public class Employee implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7608405808412227102L;
	
	private String id;
	private String name;
	private int age;
	private String email;
	private double salary;
	
	public Employee() {
		super();
	}

	public Employee(String id, String name, int age, String email, double salary) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.email = email;
		this.salary = salary;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}
}
