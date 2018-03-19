package serverhardwarecollector.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("spring.command")
@Component
public class ServerHardwareCommandConfiguration {
	
	private String cpu = "";
	private String motherboard = "";
	private String diskSerial = "";
	private String diskSize = "";
	private String diskUsage = "";
	private String hostname = "";
	private String memory = "";
	private String ip = "";
	private String macAddress = "";
	private String os = "";
	public String getCpu() {
		return cpu;
	}
	public void setCpu(String cpu) {
		this.cpu = cpu;
	}
	public String getMotherboard() {
		return motherboard;
	}
	public void setMotherboard(String motherboard) {
		this.motherboard = motherboard;
	}
	public String getDiskSerial() {
		return diskSerial;
	}
	public void setDiskSerial(String diskSerial) {
		this.diskSerial = diskSerial;
	}
	public String getDiskSize() {
		return diskSize;
	}
	public void setDiskSize(String diskSize) {
		this.diskSize = diskSize;
	}
	public String getDiskUsage() {
		return diskUsage;
	}
	public void setDiskUsage(String diskUsage) {
		this.diskUsage = diskUsage;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public String getMemory() {
		return memory;
	}
	public void setMemory(String memory) {
		this.memory = memory;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	
}
