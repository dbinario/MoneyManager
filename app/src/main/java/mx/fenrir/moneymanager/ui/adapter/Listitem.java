package mx.fenrir.moneymanager.ui.adapter;

public abstract class Listitem {

    public static  final int TYPE_HEADER=0;
    public static  final int TYPE_EVENT=1;

    abstract public int getType();

}
