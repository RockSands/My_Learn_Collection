package log.slf4j.main;
import org.apache.log4j.chainsaw.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SLF4JMain {

	public static void main(String[] args) {
		String name = "CKW";
		Logger logger = LoggerFactory.getLogger(Main.class);
		logger.debug("Hello World");
		logger.info("hello {}", name);

	}

}
