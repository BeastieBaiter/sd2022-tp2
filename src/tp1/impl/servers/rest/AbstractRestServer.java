package tp1.impl.servers.rest;

import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

import javax.net.ssl.SSLContext;

import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import tp1.impl.discovery.Discovery;
import tp1.impl.servers.common.AbstractServer;
import util.IP;

public abstract class AbstractRestServer extends AbstractServer {
	protected final String SERVER_URI_FMT = "https://%s:%s/rest";
	
	protected AbstractRestServer(Logger log, String service, int port) {
		super(log, service, port);
	}


	protected void start() {
		var config = new ResourceConfig();
		config.register(UsersResources.class);

		String ip = null;
		URI serverURI = null;
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
			serverURI = URI.create(String.format(SERVER_URI_FMT, ip, port));
			JdkHttpServerFactory.createHttpServer( serverURI, config, SSLContext.getDefault());
		} catch (Exception e) {
			e.printStackTrace();
		}

		Log.info(String.format("%s Server ready @ %s\n",  service, serverURI));
		
		Discovery.getInstance().announce(service, serverURI.toString());
	}
	
	abstract void registerResources( ResourceConfig config );
}
