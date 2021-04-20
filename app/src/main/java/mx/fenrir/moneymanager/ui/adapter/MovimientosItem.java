package mx.fenrir.moneymanager.ui.adapter;

import mx.fenrir.moneymanager.db.entity.MovimientosEntity;

public class MovimientosItem extends Listitem {


    private MovimientosEntity movimientosEntity;

    public MovimientosItem(MovimientosEntity movimientosEntity) {
        this.movimientosEntity = movimientosEntity;
    }

    public MovimientosEntity getMovimientosEntity() {
        return movimientosEntity;
    }

    @Override
    public int getType() {
        return TYPE_EVENT;
    }
}
