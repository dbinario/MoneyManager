package mx.fenrir.moneymanager.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "movimientos")
public class MovimientosEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public Date fecha;
    public float egreso;
    public float ingreso;
    public int tipo;
    public String nombreTipo;

    public MovimientosEntity(Date fecha, float egreso, float ingreso, int tipo, String nombreTipo) {
        this.fecha = fecha;
        this.egreso = egreso;
        this.ingreso = ingreso;
        this.tipo = tipo;
        this.nombreTipo = nombreTipo;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }


    public float getEgreso() {
        return egreso;
    }


    public float getIngreso() {
        return ingreso;
    }


    public int getTipo() {
        return tipo;
    }

    public String getNombreTipo() {
        return nombreTipo;
    }


}
