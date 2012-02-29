/**
 * 
 */
package org.akka.labs.ws.server.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;

import javax.activation.DataHandler;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.validation.constraints.NotNull;

import org.akka.labs.ws.server.TestService;
import org.akka.labs.ws.server.WtfException;

import com.google.common.base.Preconditions;
import com.google.common.io.Closeables;
import com.google.common.io.Files;
import com.google.common.io.OutputSupplier;

/**
 * The Class TestLiteImpl.
 */
@Stateless
@WebService(targetNamespace = "http://org.akka.labs.ws.client/v1/", name = "TestManagement", portName = "TestManagementPort", serviceName = "TestManagementService")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class TestImpl implements TestService {

	/** The file. */
	private File file;

	/**
	 * Configure.
	 * 
	 * @throws IOException
	 */
	@PostConstruct
	protected void configure() throws IOException {
		this.file = File.createTempFile("tmp", "ws");
		Files.touch(this.file);
	}

	/**
	 * @see org.akka.labs.ws.server.TestService#testCard(java.lang.String, java.lang.String)
	 * 
	 * @param mandatory
	 * @param optional
	 */

	@Override
	@WebMethod
	public void testCard(@NotNull @WebParam(name = "mandatory") String mandatory, @WebParam(name = "optional") String optional) {
		Preconditions.checkNotNull(mandatory, "Mandatory parameter is null");
		// Do Nothing
	}

	/**
	 * @see org.akka.labs.ws.server.TestService#readData()
	 * 
	 * @return
	 */
	@Override
	@WebMethod
	@WebResult(name = "dataHandler")
	public DataHandler readData() {
		DataHandler handler = null;
		try {
			handler = new DataHandler(this.file.toURI().toURL());
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
		return handler;
	}

	/**
	 * @see org.akka.labs.ws.server.TestService#writeData(javax.activation.DataHandler)
	 * 
	 * @param dataHandler
	 */
	@Override
	@WebMethod
	public void writeData(@WebParam(name = "dataHandler") DataHandler dataHandler) {
		OutputSupplier<FileOutputStream> out = Files.newOutputStreamSupplier(this.file);
		OutputStream fos = null;
		try {
			fos = out.getOutput();
			dataHandler.writeTo(fos);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			Closeables.closeQuietly(fos);
		}
	}

	/**
	 * @see org.akka.labs.ws.server.TestService#shouldFail()
	 * 
	 * @throws WtfException
	 */
	@Override
	@WebMethod
	public void shouldFail() throws WtfException {
		//		if(new Random().nextBoolean()) {
		throw new WtfException("Ooops !");
		//		} else {
		//			throw new NotImplementedException();
		//		}
	}
}
