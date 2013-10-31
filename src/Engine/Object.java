package Engine;

/**
 * Created with IntelliJ IDEA.
 * User: SED
 * Date: 31.10.13
 * Time: 23:16
 * To change this template use File | Settings | File Templates.
 */
public interface Object {
    //Основной метод в нём описывается логика Объекта
    public void start();
    //Получить части объекта
    public WChunk[] getParts();
}
