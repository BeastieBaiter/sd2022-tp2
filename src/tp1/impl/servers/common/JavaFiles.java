package tp1.impl.servers.common;

import static tp1.api.service.java.Result.error;
import static tp1.api.service.java.Result.ok;
import static tp1.api.service.java.Result.ErrorCode.INTERNAL_ERROR;
import static tp1.api.service.java.Result.ErrorCode.NOT_FOUND;
import static tp1.api.service.java.Result.ErrorCode.FORBIDDEN;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Comparator;

import tp1.api.service.java.Files;
import tp1.api.service.java.Result;
import util.Hash;
import util.IO;
import util.Token;

public class JavaFiles implements Files {

	static final String DELIMITER = "$$$";
	private static final String ROOT = "/tmp/";
	
	public JavaFiles() {
		new File( ROOT ).mkdirs();
	}

	@Override
	public Result<byte[]> getFile(String fileId, String token) {
		if (token == null) {
			return error(FORBIDDEN);
		}
		String[] tokenSpliced = token.split(JavaDirectory.DELIMITER);
		long time = Long.parseLong(tokenSpliced[1]);
		long currenTime = System.currentTimeMillis();
		String fileToken = Hash.of(fileId, time, Token.get());
		if (!(tokenSpliced[0].equals(fileToken)) || currenTime - time > 10000) {
			return error(FORBIDDEN);
		}
		fileId = fileId.replace( DELIMITER, "/");
		byte[] data = IO.read( new File( ROOT + fileId ));
		return data != null ? ok( data) : error( NOT_FOUND );
	}

	@Override
	public Result<Void> deleteFile(String fileId, String token) {
		if (token == null) {
			return error(FORBIDDEN);
		}
		String[] tokenSpliced = token.split(JavaDirectory.DELIMITER);
		long time = Long.parseLong(tokenSpliced[1]);
		long currenTime = System.currentTimeMillis();
		String fileToken = Hash.of(fileId, time, Token.get()); 
		if (!(tokenSpliced[0].equals(fileToken)) || currenTime - time > 10000) {
			return error(FORBIDDEN);
		}
		fileId = fileId.replace( DELIMITER, "/");
		boolean res = IO.delete( new File( ROOT + fileId ));	
		return res ? ok() : error( NOT_FOUND );
	}

	@Override
	public Result<Void> writeFile(String fileId, byte[] data, String token) {
		if (token == null) {
			return error(FORBIDDEN);
		}
		String[] tokenSpliced = token.split(JavaDirectory.DELIMITER);
		long time = Long.parseLong(tokenSpliced[1]);
		long currenTime = System.currentTimeMillis();
		String fileToken = Hash.of(fileId, time, Token.get()); 
		if (!(tokenSpliced[0].equals(fileToken)) || currenTime - time > 10000) {
			return error(FORBIDDEN);
		}
		
		fileId = fileId.replace( DELIMITER, "/");
		File file = new File(ROOT + fileId);
		file.getParentFile().mkdirs();
		IO.write( file, data);
		return ok();
	}

	@Override
	public Result<Void> deleteUserFiles(String userId, String token) {
		if (token == null) {
			return error(FORBIDDEN);
		}
		File file = new File(ROOT + userId);
		String[] tokenSpliced = token.split(JavaDirectory.DELIMITER);
		long time = Long.parseLong(tokenSpliced[1]);
		long currenTime = System.currentTimeMillis();
		String fileToken = Hash.of(userId, time, Token.get()); 
		if (!(tokenSpliced[0].equals(fileToken)) || currenTime - time > 10000) {
			return error(FORBIDDEN);
		}
		
		try {
			java.nio.file.Files.walk(file.toPath())
			.sorted(Comparator.reverseOrder())
			.map(Path::toFile)
			.forEach(File::delete);
		} catch (IOException e) {
			e.printStackTrace();
			return error(INTERNAL_ERROR);
		}
		return ok();
	}

	public static String fileId(String filename, String userId) {
		return userId + JavaFiles.DELIMITER + filename;
	}
}
