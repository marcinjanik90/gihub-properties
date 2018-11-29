package pl.mjanik.github.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
class RestTemplateFactoryImpl implements RestTemplateFactory {

	private static final String API_VERSION_MEDIA_TYPE = "application/vnd.github.v3+json";

	@Override
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@Override
	public HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(MediaType.parseMediaTypes(API_VERSION_MEDIA_TYPE));
		return headers;
	}
}
