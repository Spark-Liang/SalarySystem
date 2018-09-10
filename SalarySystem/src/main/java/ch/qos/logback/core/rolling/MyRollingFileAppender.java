package ch.qos.logback.core.rolling;

import java.util.List;
import java.util.Vector;

public class MyRollingFileAppender<E> extends RollingFileAppender<E> {
	public static List<MyRollingFileAppender<?>> instances = new Vector<>();
	
	public MyRollingFileAppender() {
		instances.add(this);
	}
}
