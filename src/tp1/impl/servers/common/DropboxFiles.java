package tp1.impl.servers.common;

import tp1.api.service.java.Files;
import tp1.api.service.java.Result;

public class DropboxFiles implements Files{

	@Override
	public Result<byte[]> getFile(String fileId, String token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result<Void> deleteFile(String fileId, String token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result<Void> writeFile(String fileId, byte[] data, String token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result<Void> deleteUserFiles(String userId, String token) {
		// TODO Auto-generated method stub
		return null;
	}

}
