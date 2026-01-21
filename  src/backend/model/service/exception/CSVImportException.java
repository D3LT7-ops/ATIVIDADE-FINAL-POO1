package backend.exception;

/**
 * Exceção específica para erros na importação de CSV
 * 
 * @author  [Helberth Renan Gomes de sousa]
 * @version 1.0
 */
public class CSVImportException extends QuizException {
    public CSVImportException(String mensagem) {
        super(mensagem);
    }
    
    public CSVImportException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}