package edu.poc.gradle.springboot.quickstart.topic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.poc.gradle.springboot.quickstart.topic.bean.Topic;
import edu.poc.gradle.springboot.quickstart.topic.service.TopicTransientService;

@RestController
public class TopicController {

	@Autowired
	private TopicTransientService topicService;
	
	@RequestMapping("/topics")
	public List<Topic> getAllTopics() {
		return topicService.getAllTopics();
	}
	
	@RequestMapping("/topics/{id}")
	public Topic getTopicById(@PathVariable String id) {
		return topicService.getTopicById(id);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/addTopic")
	public boolean addTopic(@RequestBody Topic topic) {
		return topicService.addTopic(topic);
	}
}
