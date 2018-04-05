package serverhardwarecollector;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ProcessTest {
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCollectExternalProcessData() {
		System.out.println("%"+Process.collectExternalProcessData("hostname")+"%");
	}

}
