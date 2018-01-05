import java.util.*;

class ExceptionLogger {
	

	static public String getExceptionDetails(Exception e) {
		StringBuffer sb = new StringBuffer(e.toString() + "\n");
		for (StackTraceElement message : e.getStackTrace()) {  
			sb.append(message.toString() + "\n");
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		try {
			List<Integer> data = new ArrayList<>();
			data.get(2);
		} catch (Exception e) {
			System.out.print(getExceptionDetails(e));
		}

	}
}