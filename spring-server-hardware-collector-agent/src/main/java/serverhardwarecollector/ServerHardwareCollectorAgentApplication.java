package serverhardwarecollector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import serverhardwarecollector.configuration.ServerHardwareCommandConfiguration;

@SpringBootApplication
public class ServerHardwareCollectorAgentApplication {

	@Autowired
	static
	ServerHardwareCommandConfiguration commandConfig;
	
	public static void main(String[] args) {
		SpringApplication.run(ServerHardwareCollectorAgentApplication.class, args);
	}

}
