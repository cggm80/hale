package eu.esdihumboldt.web;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;

import junit.framework.Assert;

import org.apache.commons.logging.impl.Log4JLogger;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.geotools.util.logging.Log4JLoggerFactory;
import org.geotools.util.logging.Logging;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.esdihumboldt.cst.CstFunction;
import eu.esdihumboldt.cst.iobridge.IoBridgeFactory;
import eu.esdihumboldt.cst.iobridge.IoBridgeFactory.BridgeType;
import eu.esdihumboldt.cst.iobridge.impl.DefaultCstServiceBridge;
import eu.esdihumboldt.cst.transformer.service.CstFunctionFactory;
import eu.esdihumboldt.hale.gmlparser.GmlHelper.ConfigurationType;

/**
 * Test IOBridge in CST-WPS environment.
 * @author jezekjan
 *
 */
public class TestBridge {

	
	@BeforeClass 
	public static void initialize(){
		addCST();
		// configure logging
		Logging.ALL.setLoggerFactory(Log4JLoggerFactory.getInstance());
		Logger.getLogger(Log4JLogger.class).setLevel(Level.WARN);
		Logger.getRootLogger().setLevel(Level.WARN);
	}
	
	@Test
	public void testCstGetRegisteredTransfomers(){
	
		CstFunctionFactory tf = CstFunctionFactory.getInstance();
		tf.registerCstPackage("eu.esdihumboldt.cst.corefunctions");
		Map<String, Class<? extends CstFunction>> functions = tf
				.getRegisteredFunctions();
		//functions.clear();
		functions = tf.getRegisteredFunctions();
		Assert.assertTrue(functions.size() > 2);
	}
	
	
	@Test
	public void testTransformItaly_TC() {	
		CstFunctionFactory tf = CstFunctionFactory.getInstance();
		tf.registerCstPackage("eu.esdihumboldt.cst.corefunctions");
		try {
	
			URL omlURL =TestBridge.class.getResource(    "./it/HALE_CST_Italy_TC.xml.goml");
			URL gmlURL = TestBridge.class.getResource("./it/SPECCHI_ACQUA_07.gml");			
			URL xsd =  TestBridge.class.getResource(  "./it/TCS_final_mdv.xsd");		
			String out = TestBridge.class.getResource("").toExternalForm() + "out.gml";				//String out = IoBridgeIntegrationTest.class.getResource("").toExternalForm() + "HALE_CST_Italy_TC/lakes/SPECCHI_ACQUA_07_generated.gml";		
			DefaultCstServiceBridge csb = (DefaultCstServiceBridge)IoBridgeFactory.getIoBridge(BridgeType.preloaded);
			System.out.println(xsd.toURI().toString());
			System.out.println(omlURL.toURI().toString());
			System.out.println(gmlURL.toURI().toString());
			
			
			String result = csb.transform(
					xsd.toURI().toString(),
					omlURL.toURI().toString(), 
			        gmlURL.toURI().toString(),
					out, null, null);
						
			System.out.println(result);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * @param args
	 */
	@Test
	public void testBridge() throws Exception{
		
		CstFunctionFactory tf = CstFunctionFactory.getInstance();
		tf.registerCstPackage("eu.esdihumboldt.cst.corefunctions");
		
			URL omlURL = TestBridge.class.getResource("test.oml");
			URL gmlURL = TestBridge.class.getResource("test_gs.xml");			
			URL xsd =  TestBridge.class.getResource("test_gs_target.xsd");		
	
		System.out.println(xsd.toURI());
		DefaultCstServiceBridge csb = (DefaultCstServiceBridge)IoBridgeFactory.getIoBridge(BridgeType.preloaded);

			
		String out = TestBridge.class.getResource("").toExternalForm() + "out.gml";				
		System.out.println(xsd.toURI().toString());
		System.out.println(omlURL.toURI().toString());
		System.out.println(gmlURL.toURI().toString());
		
		
		String result = csb.transform(
				xsd.toURI().toString(),
				omlURL.toURI().toString(), 
		        gmlURL.toURI().toString(),
				out, null, null);
		
		 ConfigurationType.valueOf("GML2");

	}
	
	public static void addCST() {
		Class<?>[] parameters = new Class[]{URL.class};
		URL functions = (new TestBridge()).getClass().getResource("corefunctions-1.0.3-SNAPSHOT.jar");		
		URLClassLoader sysloader = (URLClassLoader) ClassLoader.getSystemClassLoader();
	      Class<?> sysclass = URLClassLoader.class;

	      try {
	         Method method = sysclass.getDeclaredMethod("addURL", parameters);
	         method.setAccessible(true);
	         method.invoke(sysloader, new Object[]{functions});
	      } catch (Throwable t) {
	         t.printStackTrace();
	         //throw new IOException("Error, could not add URL to system classloader");
	      }

	}

}