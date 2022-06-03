package tp1.impl.servers.common;

import static tp1.api.service.java.Result.error;
import static tp1.api.service.java.Result.ok;
import static tp1.api.service.java.Result.ErrorCode.INTERNAL_ERROR;
import static tp1.api.service.java.Result.ErrorCode.NOT_FOUND;

import dropbox.*;
import tp1.api.service.java.Files;
import tp1.api.service.java.Result;

public class DropboxFiles implements Files{

	static final String DELIMITER = "$$$";
	private static final String ROOT = "/files/";
	
	@Override
	public Result<byte[]> getFile(String fileId, String token) {
		fileId = fileId.replace( DELIMITER, "/");
		GetFile gf = new GetFile();
		byte[] data;
		try {
			data = gf.execute(ROOT + fileId);
		} catch (Exception e) {
			e.printStackTrace();
			return error(NOT_FOUND);
		}
		return ok(data);
	}

	@Override
	public Result<Void> deleteFile(String fileId, String token) {
		fileId = fileId.replace( DELIMITER, "/");
		Delete del = new Delete();
		try {
			del.execute(ROOT + fileId);
		} catch (Exception e) {
			e.printStackTrace();
			return error(NOT_FOUND);
		}
		return ok();
	}

	@Override
	public Result<Void> writeFile(String fileId, byte[] data, String token) {
		fileId = fileId.replace( DELIMITER, "/");
		WriteFile wf = new WriteFile();
		try {
			wf.execute(ROOT + fileId, data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ok();
	}

	@Override
	public Result<Void> deleteUserFiles(String userId, String token) {
		Delete del = new Delete();
		try {
			del.execute(ROOT + userId);
		} catch (Exception e) {
			e.printStackTrace();
			return error(INTERNAL_ERROR);
		}
		return ok();
	}

}
