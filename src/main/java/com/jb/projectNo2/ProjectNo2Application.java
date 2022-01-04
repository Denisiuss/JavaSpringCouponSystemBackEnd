package com.jb.projectNo2;

import com.jb.projectNo2.Utils.ArtUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class ProjectNo2Application {

	public static void main(String[] args) {

		SpringApplication.run(ProjectNo2Application.class, args);
		System.out.println(ArtUtils.art);
	}

}
