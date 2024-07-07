package com.studyprogramming;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConvergeShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConvergeShopApplication.class, args);
//		createDirectory("src/main/resources/media/images/user");
//		createDirectory("src/main/resources/media/images/product");
	}
//	private static void createDirectory(String path) {
//		File directory = new File(path);
//		if (!directory.exists()) {
//			if (directory.mkdirs()) {
//				System.out.println("Directory created successfully: " + path);
//			} else {
//				System.out.println("Failed to create directory: " + path);
//			}
//		} else {
//			System.out.println("Directory already exists: " + path);
//		}
//	}

}
