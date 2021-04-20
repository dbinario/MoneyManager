package mx.fenrir.moneymanager.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tipo")
public class TipoEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public int tipo;
    public String nombreTipo;

    //0 ingreso
    //1 egreso


    public TipoEntity(int tipo, String nombreTipo) {
        this.tipo = tipo;
        this.nombreTipo = nombreTipo;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getNombreTipo() {
        return nombreTipo;
    }

    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }
}
