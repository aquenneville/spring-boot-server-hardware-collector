package serverhardwarecollector;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.PumpStreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import serverhardwarecollector.exception.IllegalApplicationStateException;

public class Process {

	private static final Logger logger = LoggerFactory.getLogger(Process.class);
	
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
			logger.error(e.getMessage());
			throw new IllegalApplicationStateException("Error: hardware collection of the data could not complete.");
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new IllegalApplicationStateException("Error: hardware collection of the data could not complete.");
		} 		
		return(outputStream.toString().replaceAll("[\\r\\n]", ""));
	}
}
