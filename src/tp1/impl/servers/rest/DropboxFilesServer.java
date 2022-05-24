package tp1.impl.servers.rest;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.glassfish.jersey.server.ResourceConfig;

import dropbox.CreateDirectory;
import dropbox.Delete;
import tp1.api.service.java.Files;
import tp1.impl.servers.rest.util.GenericExceptionMapper;
import util.Debug;
import util.Token;

public class DropboxFilesServer extends AbstractRestServer {
	public static final int PORT = 6789;
	
	private static Logger Log = Logger.getLogger(FilesRestServer.class.getName());

	
	DropboxFilesServer( int port ) {
		super(Log, Files.SERVICE_NAME, port);
	}
	
	@Override
	void registerResources(ResourceConfig config) {
		config.register( DropboxFilesResources.class ); 
		config.register( GenericExceptionMapper.class );
//		config.register( CustomLoggingFilter.class);
	}
	
	public static void main(String[] args) throws Exception {

		for (int i = 0; i < args.length; i++) {
			System.out.println("arg " + i + " :" + args[i]);
		}
		
		Debug.setLogLevel( Level.INFO, Debug.TP1);
		
		Token.set( args.length == 1 ? "" : args[1] );
		
		new DropboxFilesServer(PORT).start();
		
		boolean overwrite = Boolean.parseBoolean(args[0]);
		
		if (overwrite) {
			Delete del = new Delete();
			del.execute("/files");
		}

		CreateDirectory cd = new CreateDirectory();
		cd.execute("/files");
		
		
	}	
}
