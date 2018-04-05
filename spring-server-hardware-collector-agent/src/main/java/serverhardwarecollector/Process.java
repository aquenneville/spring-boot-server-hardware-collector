package serverhardwarecollector;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.PumpStreamHandler;

public class Process {

	public static String collectExternalProcessData(String command) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		String line = command;
		CommandLine cmdLine = CommandLine.parse(line);
		DefaultExecutor executor = new DefaultExecutor();
		PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
		executor.setStreamHandler(streamHandler);

		try {
			executor.execute(cmdLine);
		} catch (ExecuteException e) {
			System.out.println("Something went wrong during the execution of the command.");
			return("hardware discovery failed");
		} catch (IOException e) {
			System.out.println("Something went wrong during the IO execution of the command.");
			return("hardware discovery failed");
		} 		
		return(outputStream.toString().replaceAll("[\\r\\n]", ""));
	}
}
