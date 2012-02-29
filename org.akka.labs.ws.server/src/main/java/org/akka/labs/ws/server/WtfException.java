package org.akka.labs.ws.server;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.ws.WebFault;

/**
 * The Class WtfException.
 */

@WebFault(name = "WtfPayload", targetNamespace = "http://org.galaxy.test/v1/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class WtfException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7982697237373200768L;

	/** The payload. */
	@XmlElement(required = true, nillable = false)
	private final long timestamp;

	/**
	 * Instantiates a new wtf exception.
	 * 
	 * @param message
	 *        the message
	 */
	public WtfException(String message) {
		super(message);
		this.timestamp = System.currentTimeMillis();
	}


	/**
	 * @return the timestamp
	 */
	public long getTimestamp() {
		return timestamp;
	}

}
