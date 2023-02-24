package entity;

import java.io.Serializable;

/**
 * a class representing a subject of study
 */
public class Subject implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/** The subject ID*/
	private String id;
	
	/** The subject name */
	private String name;
	
	/**
	 * @param id the subject ID
	 * @param name the subject name
	 */
	public Subject(String id, String name) {
		super();
		this.id = id;
		this.name = name;
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

	@Override
	public String toString() {
		return "Subject [id=" + id + ", name=" + name + "]";
	}
}
