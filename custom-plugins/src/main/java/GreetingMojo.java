
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Sagt "Hallo" zu dem Benutzer.
 * 
 * @author p.schoenfeld
 *
 */

@Mojo( name="say-something")
public class GreetingMojo extends AbstractMojo {
	
	@Parameter(property = "msg", defaultValue = "from maven")
	private String msg;

	public void execute() throws MojoExecutionException {
		getLog().info("Hello, " + msg);
	}

	
	
	
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
