package serverhardwarecollector;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import serverhardwarecollector.ServerHardwareCollectorAgentApplication;
import serverhardwarecollector.configuration.ServerHardwareCommandConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=ServerHardwareCollectorAgentApplication.class)
public class ServerHardwareCollectorCommandConfigurationTests {

	@Autowired
	ServerHardwareCommandConfiguration commandConfig;

	@Test
	public void testCpuCommandTest() {
		assertEquals("lspcu|grep -oP \"(Model name:).*\"|grep -oP \"[^(Model name:)].*\"", commandConfig.getCpu());			
	}
	
	@Test
	public void testCommandDiskSizeTest() {
		assertEquals("lsblk -l -d -n | grep -P \"^(sd|hd).*\"", commandConfig.getDiskSize());			
	}
	
	@Test
	public void testCommandCommandDiskUsage() {
		System.out.println(commandConfig.getDiskUsage());
		assertEquals("df -h -T -t ext4", commandConfig.getDiskUsage());
	}
	
	@Test
	public void testCommandDiskSerial() {
		assertEquals("/sbin/udevadm info --query=property --name=sda | grep \"ID_SERIAL=.*\" | grep -o \"[^(ID_SERIAL=)].*\"", commandConfig.getDiskSerial());
	}
	
	@Test
	public void testCommandMotherboard() {
		assertEquals("cat /sys/devices/virtual/dmi/id/board_name && cat /sys/devices/virtual/dmi/id/board_vendor", commandConfig.getMotherboard());		
	}

	@Test
	public void testCommandMemory() {
		assertEquals("vmstat -s -S M | grep \"total memory\" | grep -oP \"\\d+ M\"", commandConfig.getMemory());		
	}
	
	@Test
	public void testCommandHostname() {
		assertEquals("hostname", commandConfig.getHostname());		
	}
	
	@Test
	public void testCommandIp() {
		assertEquals("ifconfig | grep -oP \".*inet \\d+.\\d+.\\d+.\\d+\" | grep \"[^(inet 127\\.0\\D.0\\.1)].*\" | grep -oP \"[^(inet )].*\"", commandConfig.getIp());
	}
	
	@Test
	public void testCommandMacAddress() {
		assertEquals("ls /sys/class/net/ | xargs -n1 bash -c 'echo $0 $(cat /sys/class/net/$0/address)'", commandConfig.getMacAddress());
	}
	
	@Test
	public void testCommandOS() {
		assertEquals("lsb_release -a", commandConfig.getOs());
	}

	
}
