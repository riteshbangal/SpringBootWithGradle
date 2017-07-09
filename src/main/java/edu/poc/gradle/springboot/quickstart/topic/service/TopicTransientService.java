package edu.poc.gradle.springboot.quickstart.topic.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.poc.gradle.springboot.quickstart.topic.bean.Topic;

@Service
public class TopicTransientService {

	List<Topic> topics = new ArrayList<>(Arrays.asList(
			new Topic("100", "Java", "This is a Java Course"),
			new Topic("101", "Spring", "This is a Spring Course"),
			new Topic("102", "JavaScript", "This is a JavaScript Course"),
			new Topic("103", "Shell Script", "This is a Shell Script Course")
		));

	public List<Topic> getAllTopics() {
		return topics;
	}
	
	public Topic getTopicById(String id) {
		return topics.stream()
				.filter(t -> t.getId().equalsIgnoreCase(id))
				.findFirst().get();
	}
	
	@SuppressWarnings("unchecked")
	public List<Topic> getTopicsByIds(List<String> ids) {
		return (List<Topic>) topics.stream()
				.filter(t -> ids.contains(t.getId()));
	}

	public boolean addTopic(Topic topic) {
		return topics.add(topic);
	}

	public boolean deleteTopic(String id) {
		return topics.removeIf(t -> t.getId().equalsIgnoreCase(id));
	}
	
	public boolean updateTopic(String id, Topic topic) {
		int index = topics.indexOf(getTopicById(id));
		return null != topics.set(index, topic);
	}
}
