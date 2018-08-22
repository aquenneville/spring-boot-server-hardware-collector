package serverhardwarecollector;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import serverhardwarecollector.exception.IllegalApplicationStateException;
import serverhardwarecollector.model.ServerHardwareData;
import serverhardwarecollector.model.ServerHardwareData.Disk;
import serverhardwarecollector.model.ServerHardwareResponse;

@RestController
@RequestMapping("/api")
public class RestServerHardwareCollectorController {

	Set<ServerHardwareData> networkConfig = new HashSet<ServerHardwareData>();
	ReentrantLock lock = new ReentrantLock();
	
	private static final Logger logger = LoggerFactory.getLogger(RestServerHardwareCollectorController.class);
	
	@RequestMapping("/add-config")
    public ServerHardwareResponse addConfig(HttpServletResponse httpResponse, @RequestBody @Valid ServerHardwareData request) {
		lock.lock();
		ObjectMapper mapper = new ObjectMapper()
		.registerModule(new Jdk8Module())
		   .registerModule(new JavaTimeModule());
		JavaType type = mapper.getTypeFactory().constructCollectionType(Set.class, ServerHardwareData.class);
		try {
			networkConfig = mapper.readValue(new File(Paths.get("hardware-config.json").toString()), type);
			logger.info("hardware-config.json values have been loaded");
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new IllegalApplicationStateException("Error: could not read value from the internal file: hardware-config.json. Please check it exists in the current path.");
		}
		
		try {
			networkConfig.add(request);
			
			// save in json to disk			
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			try {
				mapper.writeValue(new File(Paths.get("hardware-config.json").toString()), networkConfig);
				logger.info("config json data has been stored.");
			} catch (IOException e) {
				logger.error(e.getMessage());
				throw new IllegalApplicationStateException("Error: could not write json data to the internal file hardware-config.json in the current path.");
			}
		} finally {
			lock.unlock();
		}
		ServerHardwareResponse response = new ServerHardwareResponse();
		response.setResponseCode(httpResponse.getStatus());
	    return response; 
    }
	
	@RequestMapping("/get-config")
	public Set<ServerHardwareData> getConfig() {
		Set<ServerHardwareData> networkConfig = null;
		ObjectMapper mapper = new ObjectMapper()
		   .registerModule(new Jdk8Module())
		   .registerModule(new JavaTimeModule());
		JavaType type = mapper.getTypeFactory().constructCollectionType(Set.class, ServerHardwareData.class);
		try {
			networkConfig = mapper.readValue(new File(Paths.get("hardware-config.json").toString()), type);
			logger.info("hardware-config.json values have been loaded");
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new IllegalApplicationStateException("Error: could not read value from the internal file: hardware-config.json. Please check it exists in the current path.");
		}
		
		return networkConfig;
	}
	
	@RequestMapping("/test") 
	public ServerHardwareData test() {
		ServerHardwareData data = new ServerHardwareData();
		// cat /proc/cpuinfo | grep "model name" | head -n1
		data.setCpu("Intel "); 
		// ifconfig | grep -oP '([0-9a-f]{2}[:]){5}([0-9a-f]){2}'
		data.setMacAddress("0a:e2:56:e4:ec:a8");
		// hostname
		data.setHostname("hostname");
		//ifconfig | grep -oP '([0-9]{2,3}[.]){3}([0-9]){3}'
		data.setIp("172.32.12.243");
		// cat /proc/meminfo | grep MemTotal or free -h
		data.setMemory("7.8 G");		
		//lsb_release -a | grep -P "^(Description:)(.*)"
		data.setOsName("Ubuntu 16.04.3 LTS");
		//lshw or dmidecode or lspci
		data.setMotherboard("");
		data.setCollectionDate(LocalDate.now());
		
		List<Disk> disks = new ArrayList<Disk>();
		Disk disk = new ServerHardwareData.Disk();
		disk.setModel("");
		disk.setSize("20G");
		disk.setUsage("75%");
		disk.setMaster(true);
		disks.add(disk);
		data.setDisks(disks);
		
		networkConfig.add(data);
		ObjectMapper mapper = new ObjectMapper()
		.registerModule(new Jdk8Module())
		   .registerModule(new JavaTimeModule());
		// save in json to disk			
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		try {
			mapper.writeValue(new File(Paths.get("hardware-config.json").toString()), networkConfig);
			logger.info("config json data has been stored.");
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new IllegalApplicationStateException("Error: could not write json data to the internal file hardware-config.json in the current path.");
		}
		
		return data;
	}
}
