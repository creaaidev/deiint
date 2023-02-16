package pt.ulisboa.tecnico.rnl.dei.deiint.exceptions;

public enum ErrorMessage {

	NO_SUCH_CALL("Não existe nenhum concurso com o ID %s", 1001),

	NO_SUCH_INTERVIEW("Não existe nenhuma interview com o ID %s", 1004),
	NO_SUCH_CANDIDATE("Não existe nenhum candidato com o ID %s", 1002),
	NO_SUCH_ROOM("Não existe nenhuma sala com o ID %s", 1003),
	INVALID_SCORES("Os resultados introduzidos são inválidos", 1005),
	DUPLICATE_CALL("Já existe um concurso com o ID %s", 1006),
	DUPLICATE_INTERVIEW("O candidato %s já se encontra registado para o concurso %s", 1007),
	NO_SUCH_RATING("Não existe avaliação de entrevista com o ID %s", 1008),
	EXISTING_RATING("Já existe uma avaliação, para esta entrevista, do avaliador %s", 1009),
	INVALID_RATING("Avaliação inválida de valor %s", 1010);

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
