import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

final public class ObjectSerialize {
	
	public static void writeObjectToFile(Object object, String fileName) {
		ObjectOutputStream objectOutputStream = null;
		try {
			objectOutputStream = new ObjectOutputStream(
					new FileOutputStream(fileName));
			objectOutputStream.writeObject(object);
		} catch (Exception e) {
			e.printStackTrace(System.err);
		} finally {
			if (objectOutputStream != null) {
				try {
					objectOutputStream.close();
				} catch (Exception e2) {
					e2.printStackTrace(System.err);
				}
			}
		}
	}
	
	public static Object readSerObject(String fileName) {
		ObjectInputStream objectInputStream = null;
		Object res = null;
		try {
			objectInputStream = new ObjectInputStream(
					new FileInputStream(fileName));
			res = objectInputStream.readObject();
		} catch (Exception e) {
			e.printStackTrace(System.err);
		} finally {
			try {
				objectInputStream.close();
			} catch (Exception e2) {
				e2.printStackTrace(System.err);
			}
		}
		return res;
	}
}
