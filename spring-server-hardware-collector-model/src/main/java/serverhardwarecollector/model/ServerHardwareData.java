package serverhardwarecollector.model;

import java.util.ArrayList;
import java.util.List;


public class ServerHardwareData {
	
	private String cpu;
	private String motherboard;	
	private String hostname;
	private String memory;
	private String ip;
	private String osName;	
	private String macAddress;
	private static List<Disk> disks;
		
	public ServerHardwareData() {
		disks = new ArrayList<Disk>();
	}
	
	public String getCpu() {
		if (cpu == null) {
			cpu = "";
		}
		return cpu;
	}
	public void setCpu(String cpu) {
		this.cpu = cpu;
	}
	public String getMotherboard() {
		if (motherboard == null) {
			motherboard = "";
		}
		return motherboard;
	}
	public void setMotherboard(String motherboard) {
		this.motherboard = motherboard;
	}
	public String getHostname() {
		if (hostname == null) {
			hostname = "";
		}
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public String getMemory() {
		if (memory == null) {
			memory = "";
		}
		return memory;
	}
	public void setMemory(String memory) {
		this.memory = memory;
	}
	public String getIp() {
		if (ip == null) {
			ip = "";
		}
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public List<Disk> getDisks() {		
		return disks;
	}
	public void setDisks(List<Disk> disks) {
		ServerHardwareData.disks = disks;
	}
	public String getOsName() {
		if (osName == null) {
			osName = "";
		}
		return osName;
	}
	public void setOsName(String osName) {
		this.osName = osName;
	}
	
	public String getMacAddress() {
		if (macAddress == null) {
			macAddress = "";
		}
		return macAddress;
	}
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	
	public static class Disk {
		
		public Disk() {		
		}
		
		private String size;
		private String model;
		private String usage;
		private boolean isMaster;
		
		public String getSize() {
			if (size == null) {
				size = "";
			}
			return size;
		}
		public void setSize(String size) {
			this.size = size;
		}
		public String getModel() {
			if (model == null) {
				model = "";
			}
			return model;
		}
		public void setModel(String model) {
			this.model = model;
		}
		public String getUsage() {
			if (usage == null) {
				usage = "";
			}
			return usage;
		}
		public void setUsage(String usage) {
			this.usage = usage;
		}
		public boolean isMaster() {
			return isMaster;
		}
		public void setMaster(boolean isMaster) {
			this.isMaster = isMaster;
		}
		
	}

	public boolean equals(Object o) {
	      return (o instanceof ServerHardwareData) && ((ServerHardwareData)o).getMacAddress().equals(this.getMacAddress());
		  //return false;
	  }
	
	public int hashCode() {
	    return macAddress.hashCode();
		//return 31;
	}
	
}

