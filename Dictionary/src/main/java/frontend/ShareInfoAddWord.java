package frontend;

interface Listener {
    void onNewWordChange();
}

public class ShareInfoAddWord {
    private static String newWord = "";
    private static Listener listener = null;
    public static void setNewWord(String nw) {
        newWord = nw;
        listenerReact();
    }
    public static String getNewWord() {
        return newWord;
    }
    public static void setListener(Listener otherListener) {
        listener = otherListener;
    }
    private static void listenerReact() {
        if(listener != null) {
            listener.onNewWordChange();
        }
    }

}
