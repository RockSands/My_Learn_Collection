package log.log4j.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 指定日志包<log.log4j.com>
 * @author chenkw
 *
 */
public class AppointLogPackageMain {

	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(AppointLogPackageMain.class);
		logger.debug("===>AppointLogPackageMain Debug===");
		logger.info("===>AppointLogPackageMain Info<===");
		logger.warn("===>AppointLogPackageMain WARN<===");
		logger.error("===>AppointLogPackageMain Error<===");
	}

}
