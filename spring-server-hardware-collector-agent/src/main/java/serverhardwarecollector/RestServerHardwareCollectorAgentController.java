package serverhardwarecollector;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import serverhardwarecollector.configuration.ServerHardwareCommandConfiguration;
import serverhardwarecollector.exception.IllegalApplicationStateException;
import serverhardwarecollector.model.ServerHardwareData;
import serverhardwarecollector.model.ServerHardwareData.Disk;



@RestController
@RequestMapping("/api")
public class RestServerHardwareCollectorAgentController {

	private static final Logger logger = LoggerFactory.getLogger(RestServerHardwareCollectorAgentController.class);
	
	// --server.hardware.collector.port=9390 --server.hardware.collector.ip=1.1.1.1
	@Value("${server.hardware.collector.ip}")
	private String serverIp;

	@Value("${server.hardware.collector.port}")
	private String serverPort;

	@Autowired
	private ServerHardwareCommandConfiguration commandConfig;
	
	@RequestMapping("/test-agent") 
	public ServerHardwareData test() {
		return collectConfigurationData(commandConfig);
	}
	
	@RequestMapping("/send-config")
	@Scheduled(cron = "0 0 23 * * ?")
	public String sendConfig() {
		
		ServerHardwareData data = collectConfigurationData(commandConfig);
		return sendConfigurationData(data);
	}
	
	public ServerHardwareData collectConfigurationData(ServerHardwareCommandConfiguration commandConfig) {
		ServerHardwareData data = new ServerHardwareData();
		String[] diskModels = null;		
		String[] disksUsage = null;

		if (SystemUtils.IS_OS_WINDOWS) {
			data.setOsName("Windows");
			String[] networkConfigurationTokens = collectExternalProcessData("ipconfig /all").split("\n");
			for (String token: networkConfigurationTokens) {
				if (token.contains("Host Name")) {
					if ("".equals(data.getHostname())) {
						data.setHostname(token.split(" : ")[1]);
					}					
				}
				
				if (token.contains("Physical Address")) {
					if ("".equals(data.getMacAddress())) {
						data.setMacAddress(token.split(" : ")[1]);
					}					
				}
				
				if (token.contains("IPv4 Address")) {
					if ("".equals(data.getIp())) {
						data.setIp(token.split(" : ")[1]);
					}					
				}
			}
			data.setCpu("unknown");
			data.setMemory("unknown");
			data.setMotherboard("unknown");
			Disk disk = new ServerHardwareData.Disk();
			disk.setModel("unknown");
			disk.setSize("unknown");
			disk.setUsage("unknown");
			disk.setMaster(true);
			List<Disk> disks = new ArrayList<Disk>();
			disks.add(disk);
			data.setDisks(disks);
			data.setCollectionDate(LocalDate.now());
			
		} else if (SystemUtils.IS_OS_MAC_OSX) {			
			data.setHostname("unknown");
			data.setMacAddress("unknown");
			data.setCpu("unknown");
			data.setMemory("unknown");
			data.setMotherboard("unknown");
			Disk disk = new ServerHardwareData.Disk();
			disk.setModel("unknown");
			disk.setSize("unknown");
			disk.setUsage("unknown");
			disk.setMaster(true);
			List<Disk> disks = new ArrayList<Disk>();
			disks.add(disk);
			data.setDisks(disks);
			data.setCollectionDate(LocalDate.now());
			
		} else	{
			data.setCollectionDate(LocalDate.now());
					
			data.setCpu(collectExternalProcessData(commandConfig.getCpu()));
			
			data.setHostname(collectExternalProcessData(commandConfig.getHostname()));
			data.setMemory(collectExternalProcessData(commandConfig.getMemory()));
			data.setIp(collectExternalProcessData(commandConfig.getIp()));
			data.setOsName(collectExternalProcessData(commandConfig.getOs()));
			data.setMotherboard(collectExternalProcessData(commandConfig.getMotherboard()));
			data.setMacAddress(collectExternalProcessData(commandConfig.getMacAddress()));
		
			List<Disk> disks = new ArrayList<Disk>();
			diskModels = collectExternalProcessData(commandConfig.getDiskSerial()).split(" ");
			disksUsage = collectExternalProcessData(commandConfig.getDiskUsage()).split(" ");
			Disk disk = null;
			int index = 0;
			for (String model: diskModels) {
				disk = new ServerHardwareData.Disk();
				disk.setModel(model);
				disk.setSize(collectExternalProcessData(commandConfig.getDiskSize()));
				disk.setUsage(disksUsage[index]);
				if (index == 0) {
					disk.setMaster(true);
				}
				index ++;
				disks.add(disk);
			}
			data.setDisks(disks);
			
		}
		return data;
	}
	
	public String collectExternalProcessData(String command) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		String line = command;
		logger.info("executing: /bin/bash -c '"+command+"'");
		CommandLine cmdLine = new CommandLine("/bin/bash"); 
		String[] cmd = {"-c", line }; 
		cmdLine.addArguments(cmd, false);
		
		DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
		ExecuteWatchdog watchdog = new ExecuteWatchdog(60000);
		DefaultExecutor executor = new DefaultExecutor();
		executor.setExitValue(1);
		executor.setWatchdog(watchdog);
		PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
		executor.setStreamHandler(streamHandler);
		
		try {
			executor.execute(cmdLine, resultHandler);
			Thread.sleep(1000);
			
		} catch (ExecuteException e) {
			logger.error(e.getMessage());
			throw new IllegalApplicationStateException("Error: hardware collection of the data could not complete.");
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new IllegalApplicationStateException("Error: hardware collection of the data could not complete.");
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
			throw new IllegalApplicationStateException("Error: hardware collection of the data was interrupted.");
		}
		return new String(outputStream.toString().replaceAll("\\n", "").trim());
	}

	public String sendConfigurationData(ServerHardwareData configData) {
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		//headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);		
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		ObjectMapper mapper = new ObjectMapper()
			.registerModule(new Jdk8Module())
		   	.registerModule(new JavaTimeModule());
		HttpEntity<String> httpEntity = null;
		try {
			httpEntity = new HttpEntity <String> (mapper.writeValueAsString(configData), headers);
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage());
			throw new IllegalApplicationStateException("Error: hardware collection of the json data could not process before the transfer.");
		}
		System.out.println(serverIp+":"+serverPort);
		String response = restTemplate.postForObject("http://"+serverIp+":"+serverPort+"/api/add-config", httpEntity, String.class);
		System.out.println(response);

		return response;
	}
	

}
