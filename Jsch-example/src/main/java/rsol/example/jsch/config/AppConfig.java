package rsol.example.jsch.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.boeing.onepdl.spm.autopilot.SvgAutoPilotTemplate;
import com.boeing.onepdl.spm.autopilot.session.DefaultAPSessionFactory;
import com.boeing.onepdl.spm.autopilot.session.SessionFactory;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.extern.log4j.Log4j2;

@Configuration
@Log4j2
public class AppConfig {

	@Value("${kafka.topic.name}") 
	private String topic;
	

	@Value("${rsol.host:}")
	private String host;
	
	private final int port = 22;
	
	@Value("${rsol.user:}")
	private String user;
	
	@Value("${rsol.secret:}")
	private String secret;
	
	@Value("${rsol.secretkey:}")
	private String secretKey;
	
	/**
	 * Method used for Controller to be exposed on Swaggers!
	 * 
	 * @return
	 */
	@Bean
	    public OpenAPI openAPI() {
		return new OpenAPI()
			.components(new Components())
			.info(new Info().title("SSH Example API"));
	    }
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	public SessionFactory sshSessionFactory(){
		log.info("Initializing session factory with {}@{}:{} and pk: {}", user, host, port, secretKey);
		DefaultSSHSessionFactory factory = new DefaultSSHSessionFactory();
		factory.setHost(host);
		factory.setPort(port);
		factory.setPassword(secret);
		factory.setUser(user);
		factory.setSecretKey(secretKey);
		return factory;
	}
	
	@Bean
	public SvgAutoPilotTemplate autopilotTemplate() {
		return new SSHCommandTemplate(sshSessionFactory());

	}	
    
}
