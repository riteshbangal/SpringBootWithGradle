package edu.poc.gradle.springboot.quickstart.topic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
