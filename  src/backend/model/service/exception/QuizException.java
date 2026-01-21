package backend.exception;

/**
 * Exceção genérica para erros do sistema de quiz
 * 
 * @author  [Helberth Renan Gomes de sousa]
 * @version 1.0
 */
public class QuizException extends Exception {
    public QuizException(String mensagem) {
        super(mensagem);
    }
    
    public QuizException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}