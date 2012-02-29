/**
 * 
 */
package org.akka.labs.ws.client;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Date;

import javax.xml.ws.BindingProvider;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.base.Charsets;

/**
 * The Class TestWS.
 */
public class TestWS {

	/** The test ws. */
	private static TestManagement testWS;

	/**
	 * Retrieve web service.
	 * 
	 * @throws Exception
	 *         the exception
	 */
	@BeforeClass
	public static void retrieveWebService() throws Exception {
		//TestManagementService testService = new TestManagementService(new URL("http://localhost:8080/test-ws/TestManagementService/TestManagement?wsdl"));
		TestManagementService testService = new TestManagementService(new File("src/main/wsdl/TestManagementService.wsdl").toURI().toURL());
		testWS = testService.getTestManagementPort();
		((BindingProvider)testWS).getRequestContext().put("schema-validation-enabled", "true");
	}

	/**
	 * Test data handler.
	 * 
	 * @throws Exception
	 *         the exception
	 */
	@Test
	public void testDataHandler() throws Exception {
		Charset utf8 = Charsets.UTF_8;
		String msg = "Hello World at " + (new Date());

		// Write
		byte[] data = msg.getBytes(utf8);
		testWS.writeData(data);

		// Read
		data = testWS.readData();
		String res = new String(data, utf8);

		// Test
		Assert.assertEquals(msg, res);
	}

	/**
	 * Test cardinality.
	 */
	@Test
	public void testCardinality() {
		testWS.testCard("plop", "Plaf");
		testWS.testCard("plop", null);
		try {
			testWS.testCard(null, "Plaf");
			Assert.fail("Expected an exception");
		} catch (Exception e) {
			// OK expected an exception
			e.printStackTrace();
		}
	}

	/**
	 * Test exception.
	 */
	@Test
	public void testException() {
		try {
			testWS.shouldFail();
			Assert.fail("Expected an WtfException");
		} catch (WtfException e) {
			WtfPayload faultInfo = e.getFaultInfo();
			Assert.assertNotNull(faultInfo);
			Assert.assertNotNull(faultInfo.getTimestamp());
			Assert.assertTrue(faultInfo.getTimestamp() < System.currentTimeMillis());
			// expected error
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Not Expected Exception: " + e);
		}
	}

}
