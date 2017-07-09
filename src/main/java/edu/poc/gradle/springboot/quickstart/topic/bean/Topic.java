package edu.poc.gradle.springboot.quickstart.topic.bean;

public class Topic {

	private String id;
	private String name;
	private String description;
	
	public Topic() {
		// Default constructor stub
	}
	
	public Topic(String id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}
	
	@Override
	public String toString() {
		StringBuilder topic = new StringBuilder();
		topic.append("Topic: [")
			.append("id: ").append(id)
			.append(", name: ").append(name)
			.append(", description: ").append(description)
			.append("]");
		return topic.toString();
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
