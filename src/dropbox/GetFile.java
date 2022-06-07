package dropbox;

import org.pac4j.scribe.builder.api.DropboxApi20;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.google.gson.Gson;

import dropbox.msgs.DownloadFileV2Args;

public class GetFile {
	
	private static String apiKey;
	private static String apiSecret;
	private static String accessTokenStr;
	
	private static final String DOWNLOAD_V2_URL = "https://content.dropboxapi.com/2/files/download";
	
	private static final int HTTP_SUCCESS = 200;
	private static final String CONTENT_TYPE_HDR = "Content-Type";
	private static final String DROPBOX_API_ARG = "Dropbox-API-Arg";
	private static final String OCTET_CONTENT_TYPE = "application/octet-stream";
	
	private final Gson json;
	private final OAuth20Service service;
	private final OAuth2AccessToken accessToken;
		
	public GetFile() {
		json = new Gson();
		accessToken = new OAuth2AccessToken(accessTokenStr);
		service = new ServiceBuilder(apiKey).apiSecret(apiSecret).build(DropboxApi20.INSTANCE);
	}
	
	public byte[] execute( String fileId ) throws Exception {
		
		var getFile = new OAuthRequest(Verb.POST, DOWNLOAD_V2_URL);
		getFile.addHeader(DROPBOX_API_ARG, json.toJson(new DownloadFileV2Args(fileId)));
		getFile.addHeader(CONTENT_TYPE_HDR, OCTET_CONTENT_TYPE);

		service.signRequest(accessToken, getFile);
		
		Response r = service.execute(getFile);
		var in = r.getStream();
		byte[] result = in.readAllBytes();
		if (r.getCode() != HTTP_SUCCESS) 
			throw new RuntimeException(String.format("Failed to get file: %s, Status: %d, \nReason: %s\n", fileId, r.getCode(), r.getBody()));
		else return result;
	}
	
	public static void setApiKey(String key) {
		apiKey = key;
	}
	
	public static void setApiSecret(String secret) {
		apiSecret = secret;
	}
	
	public static void setAccessTokenStr(String str) {
		accessTokenStr = str;
	}
	
}
