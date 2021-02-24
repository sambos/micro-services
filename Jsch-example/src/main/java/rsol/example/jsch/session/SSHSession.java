package rsol.example.jsch.config;

import java.io.Closeable;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;

import lombok.extern.log4j.Log4j2;

/*
 * SSH Session
 */
@Log4j2
public class SSHSession implements Closeable {
	
	private com.jcraft.jsch.Session jschSession;
	private ChannelExec channel;
	private boolean closed;

	public APSession(com.jcraft.jsch.Session session) {
		this.jschSession = session;
	}
	
	@Override
	public void close() {
		this.closed = true;
		if (this.channel != null) {
			this.channel.disconnect();
		} 
		if (this.jschSession.isConnected()) {
			this.jschSession.disconnect();
		}
		log.info("SSH Session closed.");
	}

	public boolean isOpen() {
		return !this.closed && this.jschSession.isConnected();
	}
	
	public ChannelExec getChannel() {
		return this.channel;
	}
	
	void connect() {
		try {
			if (!this.jschSession.isConnected()) {
				this.jschSession.connect();
				log.info("connected to {}@{}", this.getUserName(), this.getHost());
			}
			
			this.channel = (ChannelExec) this.jschSession.openChannel("exec");

		} catch (JSchException e) {
			this.close();
			throw new IllegalStateException("failed to connect", e);
		}
	}

	public String getHost() {
		return jschSession.getHost();
	}

	public String getUserName() {
		return jschSession.getUserName();
	}

	public int getPort() {
		return jschSession.getPort();
	}
}
