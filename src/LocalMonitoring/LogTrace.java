package LocalMonitoring;

public class LogTrace {

	public static void writeTraceLog(String s) {
		System.out.println(s);
	}

	public static void writeExceptionLog(Exception x) {
		System.out.println(x.toString());
	}

	public static void writeScreenLog(String s) {
		System.out.println(s);
	}

}
