package dropbox;

import org.pac4j.scribe.builder.api.DropboxApi20;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.google.gson.Gson;

import dropbox.msgs.UploadFileV2Args;

public class WriteFile {

	private static final String apiKey = "f9ocr6b102r29o6";
	private static final String apiSecret = "qybbze8lgf720bg";
	private static final String accessTokenStr = "sl.BI7IC0YOsRx6ILmE-n2W_lv439yP0sM1Uqr_7YHipfPRbP7KksJU6fPRtaNqTadfuJik3P_DTKIZ8rhSn7Ay6BqEejkh-bKTrxUycGDpUB1EAvhdLNoWDs14vsLV61dZbpalwYY";
	
	private static final String UPLOAD_FILE_V2_URL = "https://content.dropboxapi.com/2/files/upload";
	
	private static final int HTTP_SUCCESS = 200;
	private static final String CONTENT_TYPE_HDR = "Content-Type";
	private static final String DROPBOX_API_ARG = "Dropbox-API-Arg";
	private static final String OCTET_CONTENT_TYPE = "application/octet-stream";
	
	private final Gson json;
	private final OAuth20Service service;
	private final OAuth2AccessToken accessToken;
		
	public WriteFile() {
		json = new Gson();
		accessToken = new OAuth2AccessToken(accessTokenStr);
		service = new ServiceBuilder(apiKey).apiSecret(apiSecret).build(DropboxApi20.INSTANCE);
	}
	
	public void execute( String directoryName, byte[] file ) throws Exception {
		
		var writeFile = new OAuthRequest(Verb.POST, UPLOAD_FILE_V2_URL);
		writeFile.addHeader(DROPBOX_API_ARG, json.toJson(new UploadFileV2Args(directoryName, false, "overwrite", false, false)));
		writeFile.addHeader(CONTENT_TYPE_HDR, OCTET_CONTENT_TYPE);
		
		writeFile.setPayload(file);

		service.signRequest(accessToken, writeFile);
		
		Response r = service.execute(writeFile);
		if (r.getCode() != HTTP_SUCCESS) 
			throw new RuntimeException(String.format("Failed to upload file: %s, Status: %d, \nReason: %s\n", file, r.getCode(), r.getBody()));
	}

	
}
