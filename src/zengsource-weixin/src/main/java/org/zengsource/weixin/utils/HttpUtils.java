/**
 * 
 */
package org.zengsource.weixin.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public abstract class HttpUtils {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	static Log logger = LogFactory.getLog(HttpUtils.class);

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	public static String get(String uri, NameValuePair... prarms) {
		try {
			return execute(RequestBuilder.get().setUri(//
					new URI(uri)).setHeader("Content-Type", "text/html;charset=UTF-8")
					.addParameters(prarms).build());
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String post(String uri, NameValuePair... prarms) {
		try {
			return execute( //
			RequestBuilder.post().setUri( //
					new URI(uri)).setHeader("Content-Type", "text/html;charset=UTF-8")
					.addParameters(prarms).build());
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String execute(HttpUriRequest request) throws IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
			@Override
			public String handleResponse(HttpResponse response) throws ClientProtocolException,
					IOException {
				int status = response.getStatusLine().getStatusCode();
				if (status >= 200 && status < 300) {
					HttpEntity entity = response.getEntity();
					return entity != null ? EntityUtils.toString(entity, "utf-8") : null;
				} else {
					throw new ClientProtocolException("Unexpected response status: " + status);
				}
			}
		};
		String responseBody = null;
		try {
			responseBody = httpClient.execute(request, responseHandler);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpClient.close();
		}
		return responseBody;

	}

	public static void postJson(String url, String paramName, String paramValue, String json) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put(paramName, paramValue);
		postJson(url, paramMap, json);
	}

	public static void postJson(String url, Map<String, String> params, String json) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			URIBuilder builder = new URIBuilder(url);
			for (String name : params.keySet()) {
				builder.setParameter(name, params.get(name));
			}
			HttpPost httpPost = new HttpPost(builder.build());
			httpPost.addHeader("Content-type", "application/json");
			StringEntity entity = new StringEntity(json);
			entity.setContentType("application/json");
			// entity.setContentEncoding("utf-8");
			httpPost.setEntity(entity);
			logger.info("==> Http Post Url : " + url);
			logger.info("==> Http Post Json : " + json);
			HttpResponse response = httpClient.execute(httpPost);
			logger.info("==> Http Post Status : " + response.getStatusLine());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
