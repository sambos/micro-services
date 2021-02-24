package rsol.example.jsch.config;

import java.io.File;

import org.springframework.util.StringUtils;

import com.jcraft.jsch.JSch;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class DefaultSSHSessionFactory implements SessionFactory {
	
	private final JSch jsch;
	
	private String host;
	private int port;
	private String user;
	private String secretKey;
	private String secret;
	
	
	public DefaultAPSessionFactory() {
		jsch = new JSch();
	}

	public void setHost(String host) {
		this.host = host;
	}
	
	public void setPort(int port) {
		this.port = port;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
	public void setPassword(String password) {
		this.secret = password;
	}
	
	public void setSecretKey(String key) {
		this.secretKey = key;
	}
	
	@Override
	public APSession getSession() {
		return initJschSession();
	}

	private APSession initJschSession() {
		APSession session=null;
		try {
			com.jcraft.jsch.Session jschSession = this.jsch.getSession(this.user, this.host, this.port);
			
			if(!StringUtils.isEmpty(this.secretKey) && new File(this.secretKey).exists()) {
				log.info("Auth key specified.. : " + this.secretKey);
				this.jsch.addIdentity(this.secretKey);
			}else {
				log.info("pwd specified..");
				jschSession.setPassword(this.secret);
			}
			
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			jschSession.setConfig(config);
			session = new APSession(jschSession);
			session.connect();
			return session;
		} catch (Exception e) {
			try {
				if (session != null) {
					session.close();
				}
			} catch (Exception e1) {
				throw new IllegalStateException("failed to close JSch Session", e1);
			}
			throw new IllegalStateException("failed to create JSch Session", e);
		}
	}
}
