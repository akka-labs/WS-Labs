package org.akka.labs.ws.server;

/**
 * 
 */


import javax.activation.DataHandler;


/**
 * The Interface Test.
 */
public interface TestService {

	/**
	 * Test card.
	 *
	 * @param mandatory the mandatory
	 * @param optional the optional
	 */
	void testCard(String mandatory, String optional);


	/**
	 * Read data.
	 * 
	 * @return the data handler
	 */
	DataHandler readData();

	/**
	 * Write data.
	 * 
	 * @param dataHandler
	 *        the data handler
	 */
	void writeData(DataHandler dataHandler);

	/**
	 * May fail.
	 * 
	 * @throws WtfException
	 *         the wtf exception
	 */
	void shouldFail() throws WtfException;


}
