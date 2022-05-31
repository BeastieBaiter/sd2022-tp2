package dropbox;

import org.pac4j.scribe.builder.api.DropboxApi20;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.google.gson.Gson;

import dropbox.msgs.DeleteV2Args;

public class Delete {
	
	private static final String apiKey = "f9ocr6b102r29o6";
	private static final String apiSecret = "qybbze8lgf720bg";
	private static final String accessTokenStr = "sl.BIl9Sk9OboR97JdtGeeHeZZX4Xpb-WrVitKrmMw-a-MN0Tas2Nyb1gS2NMcWsSh2fdDqjGoksz6BvNzW6ueJ1i2bFMSD4NPD6JBlz5tGT1VMtrhtuBeHmLOTBLtXOD_RWqRNzvQ";
	
	private static final String DELETE_V2_URL = "https://api.dropboxapi.com/2/files/delete";
	
	private static final int HTTP_SUCCESS = 200;
	private static final String CONTENT_TYPE_HDR = "Content-Type";
	private static final String JSON_CONTENT_TYPE = "application/json; charset=utf-8";
	
	private final Gson json;
	private final OAuth20Service service;
	private final OAuth2AccessToken accessToken;
		
	public Delete() {
		json = new Gson();
		accessToken = new OAuth2AccessToken(accessTokenStr);
		service = new ServiceBuilder(apiKey).apiSecret(apiSecret).build(DropboxApi20.INSTANCE);
	}
	
	public void execute( String path ) throws Exception {
		
		var delete = new OAuthRequest(Verb.POST, DELETE_V2_URL);
		delete.addHeader(CONTENT_TYPE_HDR, JSON_CONTENT_TYPE);

		delete.setPayload(json.toJson(new DeleteV2Args(path)));

		service.signRequest(accessToken, delete);
		
		Response r = service.execute(delete);
		if (r.getCode() != HTTP_SUCCESS) 
			throw new RuntimeException(String.format("Failed to delete file: %s, Status: %d, \nReason: %s\n", path, r.getCode(), r.getBody()));
	}
	
}
