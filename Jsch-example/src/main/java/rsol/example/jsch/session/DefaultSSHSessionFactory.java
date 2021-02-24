package rsol.example.jsch.config;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

import com.boeing.onepdl.spm.autopilot.session.APSession;
import com.boeing.onepdl.spm.autopilot.session.SessionFactory;
import com.google.common.io.CharStreams;
import com.jcraft.jsch.ChannelExec;

import lombok.extern.log4j.Log4j2;



@Log4J2
public class SSHCommandTemplate {
	protected final SessionFactory sessionFactory;
	private APSession session;
	private final String ap_command_template = "/usr/bin/nohup %s >/dev/null 2>&1 & echo $!";

	
	public SSHCommandTemplate(SessionFactory sessionFactory) {
		Assert.notNull(sessionFactory, "sessionFactory must not be null");
		this.sessionFactory = sessionFactory;
	}
	
	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}

	private APSession getSession() {

		if (session != null && session.isOpen()) {
			return session;
		}

		session = this.sessionFactory.getSession();
		return session;
	}
		
	/**
	 * execute command
	 * @param path command path
	 * @return List<String> returnCodes for each command
	 */
	public List<String> execCommands(String path, String[] commands) throws Exception {	
		List<String> returnCodes = new ArrayList<String>();
		for(String command : commands) {
			returnCodes.add(execCommand(path, command));	
		}
		return returnCodes;
	}
	
	/**
	 * execute command
	 * @param path command path
	 * @return returnCode for command
	 */
	public String execCommand(String path, String command) throws Exception {
		
		try(APSession session = getSession()){
			return exec(path, command, (ChannelExec) session.getChannel());
		}	
	}

	private String exec(String path, String command, ChannelExec channel) throws Exception {

		Assert.notNull(path, " command path must not be null");
		
		command = path + "/" + command;
		command = String.format(ap_command_template, command);
		try {

			log.info("Executing command [{}] on {}@{}:{} ", command, session.getUserName(), session.getHost(),
					session.getPort());

			channel.setCommand(command);
			channel.setPty(false);
			channel.setInputStream(null);
			channel.setErrStream(System.err);
			InputStream in = channel.getInputStream();
			channel.connect();

			String result = CharStreams.toString(new InputStreamReader(in));
			log.info("Result of command {} : {}", command, result);

			in.close();
			
			return result;
		} catch (Exception e) {
			throw new APCommandFailedException("Failed to execute command", command, e);
		}
	}

}
