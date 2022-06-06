package tp1.impl.servers.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.glassfish.jersey.server.ResourceConfig;

import dropbox.CreateDirectory;
import dropbox.Delete;
import dropbox.ListDirectory;
import tp1.api.service.java.Files;
import tp1.impl.servers.rest.util.GenericExceptionMapper;
import util.Debug;
import util.Token;

public class DropboxFilesServer extends AbstractRestServer {
	public static final int PORT = 6789;
	private static final String PATH = "/files";
	
	private static Logger Log = Logger.getLogger(FilesRestServer.class.getName());

	
	DropboxFilesServer() {
		super(Log, Files.SERVICE_NAME, PORT);
	}
	
	@Override
	void registerResources(ResourceConfig config) {
		config.register( DropboxFilesResources.class ); 
		config.register( GenericExceptionMapper.class );
//		config.register( CustomLoggingFilter.class);
	}
	
	public static void main(String[] args) throws Exception {
		
		Debug.setLogLevel( Level.INFO, Debug.TP1);
		
		Token.set( args.length == 1 ? "" : args[1] );
		
		boolean clean = Boolean.parseBoolean(args[0]);
		
		new DropboxFilesServer().start();
		
		if (clean) {
			List<String> path = new ArrayList<String>();
			ListDirectory ld = new ListDirectory();
			path = ld.execute(PATH);
			if(!path.isEmpty()) {
				Delete del = new Delete();
				del.execute(PATH);
			}
			else {
				CreateDirectory cd = new CreateDirectory();
				cd.execute(PATH);
			}
		}
		
	}	
}
