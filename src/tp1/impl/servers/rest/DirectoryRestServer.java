package tp1.impl.servers.rest;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.glassfish.jersey.server.ResourceConfig;

import dropbox.CreateDirectory;
import dropbox.Delete;
import dropbox.GetFile;
import dropbox.ListDirectory;
import dropbox.WriteFile;
import tp1.api.service.java.Directory;
import tp1.impl.servers.rest.util.GenericExceptionMapper;
import util.Debug;
import util.Token;

public class DirectoryRestServer extends AbstractRestServer {
	
	public static final int PORT = 4567;
	
	private static Logger Log = Logger.getLogger(DirectoryRestServer.class.getName());

	DirectoryRestServer() {
		super(Log, Directory.SERVICE_NAME, PORT);
	}
	
	@Override
	void registerResources(ResourceConfig config) {
		config.register( DirectoryResources.class ); 
		config.register( GenericExceptionMapper.class );		
//		config.register( CustomLoggingFilter.class);
	}
	
	public static void main(String[] args) throws Exception {

		Debug.setLogLevel( Level.INFO, Debug.TP1);
		
		String[] argsSpliced = args[0].split("###");

		Token.set( args.length > 0 ? argsSpliced[0] : "");
		
		CreateDirectory.setApiKey(argsSpliced[1]);
		CreateDirectory.setApiSecret(argsSpliced[2]);
		CreateDirectory.setAccessTokenStr(argsSpliced[3]);
		
		Delete.setApiKey(argsSpliced[1]);
		Delete.setApiSecret(argsSpliced[2]);
		Delete.setAccessTokenStr(argsSpliced[3]);
		
		GetFile.setApiKey(argsSpliced[1]);
		GetFile.setApiSecret(argsSpliced[2]);
		GetFile.setAccessTokenStr(argsSpliced[3]);
		
		ListDirectory.setApiKey(argsSpliced[1]);
		ListDirectory.setApiSecret(argsSpliced[2]);
		ListDirectory.setAccessTokenStr(argsSpliced[3]);
		
		WriteFile.setApiKey(argsSpliced[1]);
		WriteFile.setApiSecret(argsSpliced[2]);
		WriteFile.setAccessTokenStr(argsSpliced[3]);

		new DirectoryRestServer().start();
	}	
}