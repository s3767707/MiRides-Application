package exception;

public class CorruptedFileException extends Exception {
	public CorruptedFileException() {
		super("File is corrupte");
	}

	public CorruptedFileException(String message) {
		super(message);
	}

}
