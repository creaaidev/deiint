package pt.ulisboa.tecnico.rnl.dei.deiint.exceptions;

public enum ErrorMessage {

	NO_SUCH_CALL("Não existe nenhum concurso com o ID %s", 1001);

	private final String label;
	private final int code;

	ErrorMessage(String label, int code) {
		this.label = label;
		this.code = code;
	}

	public String getLabel() {
		return this.label;
	}

	public int getCode() {
		return this.code;
	}
}
