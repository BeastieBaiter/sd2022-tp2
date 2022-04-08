package tp1.api.service.java;

import java.util.List;

import tp1.api.FileInfo;
import tp1.api.service.java.Result.ErrorCode;

public interface Directory {

	Result<FileInfo> writeFile(String filename, byte []data, String userId, String password);

	Result<Void> deleteFile(String filename, String userId, String password);

	Result<Void> shareFile(String filename, String userId, String userIdShare, String password);

	Result<Void> unshareFile(String filename, String userId, String userIdShare, String password);

	Result<byte[]> getFile(String filename,  String userId, String accUserId, String password);

	Result<List<FileInfo>> lsFile(String userId, String password);
	
	default Result<FileInfo> getFileInfo(String filename,  String userId, String accUserId, String password) {
		return Result.error( ErrorCode.NOT_IMPLEMENTED);
	}
	
	Result<Void> deleteUserFiles(String userId, String password, String token);
}
