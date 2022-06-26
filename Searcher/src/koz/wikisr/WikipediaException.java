package koz.wikisr;

/** Wikipedia выдала ошибку **/
public class WikipediaException extends Exception {
    public WikipediaException(){
        super("Wikipedia выдала ошибку");
    }
}
