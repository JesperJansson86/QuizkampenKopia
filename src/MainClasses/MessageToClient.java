package MainClasses;

/**
 * Created by Hodei Eceiza
 * Date: 11/25/2020
 * Time: 12:41
 * Project: QuizkampenKopia
 * Copyright: MIT
 */
public class MessageToClient implements IObserve{
    public static String message;

    public MessageToClient(){}
    @Override
    public void update(String message) {
        this.message = message;
    }
}
