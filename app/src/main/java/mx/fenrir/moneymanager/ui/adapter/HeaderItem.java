package mx.fenrir.moneymanager.ui.adapter;

import java.util.Date;

import mx.fenrir.moneymanager.ui.adapter.Listitem;

public class HeaderItem extends Listitem {

    private Date date;


    public HeaderItem(Date date) {
        this.date = date;
    }


    public Date getDate() {
        return date;
    }

    @Override
    public int getType() {
        return TYPE_HEADER;
    }


}
