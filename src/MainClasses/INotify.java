package MainClasses;

/**
 * Created by Hodei Eceiza
 * Date: 11/24/2020
 * Time: 18:13
 * Project: QuizkampenKopia
 * Copyright: MIT
 */
public interface INotify {
    void addObserver(IObserve o);
    void notifyObserver(String str);
}
