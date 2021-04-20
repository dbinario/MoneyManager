package mx.fenrir.moneymanager.db;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import java.util.Date;
import java.util.List;

import mx.fenrir.moneymanager.db.entity.MovimientosEntity;
import mx.fenrir.moneymanager.db.entity.TipoEntity;
import mx.fenrir.moneymanager.utils.fakeEntry;

public class MoneyManagerViewModel extends AndroidViewModel {

    private MoneyManagerRepository moneyManagerRepository;
    private List<String> listaTipos;
    private LiveData<List<MovimientosEntity>> movimientosMes;
    private LiveData<List<TipoEntity>> listaTiposLD;
    private LiveData<Float> ingresos,egresos,saldo;
    private List<fakeEntry> egresosGraficos,ingresosGraficos;



    public MoneyManagerViewModel(@NonNull Application application) {
        super(application);
        moneyManagerRepository=new MoneyManagerRepository(application);
    }

    public List<String> obtenerTipos(int tipo){

        listaTipos=moneyManagerRepository.obtenerTipos(tipo);
        return listaTipos;

    }


    public LiveData<List<MovimientosEntity>> obtenetMovimientosMes(Date fecIniMes, Date fecFinMes){

        movimientosMes=moneyManagerRepository.obtenerMovimientosMes(fecIniMes,fecFinMes);
        return movimientosMes;

    }

    public void insertMovimiento(MovimientosEntity movimientosEntity){

        moneyManagerRepository.insertarMovimiento(movimientosEntity);

    }


    public LiveData<Float> obtenerIngresos(Date fecIniMes, Date fecFinMes){

        ingresos=moneyManagerRepository.obtenerIngresos(fecIniMes,fecFinMes);
        return ingresos;
    }

    public LiveData<Float> obtenerEgresos(Date fecIniMes, Date fecFinMes){

        egresos=moneyManagerRepository.obtenerEgresos(fecIniMes,fecFinMes);
        return egresos;
    }


    public LiveData<Float> obtenerSaldo(Date fecIniMes, Date fecFinMes){

        saldo=moneyManagerRepository.obtenerSaldo(fecIniMes,fecFinMes);
        return saldo;

    }

    public LiveData<List<TipoEntity>> obtenerListaTipos(int tipo){

        listaTiposLD=moneyManagerRepository.obtenerListaTipos(tipo);
        return listaTiposLD;

    }


    public void eliminarMovimientos(){

        moneyManagerRepository.eliminarMovimientos();

    }

    public void eliminarMovimiento(int id){

        moneyManagerRepository.eliminarMovimiento(id);

    }

    public void eliminarTipo(int id){

        moneyManagerRepository.eliminarTipo(id);

    }

    public void actualizarMovimiento(int id,String nombreTipo,float egreso,float ingreso,int tipo,Date fechaNueva){

        moneyManagerRepository.actualizarMovimiento(id,nombreTipo,egreso,ingreso,tipo,fechaNueva);

    }



    public List<fakeEntry> obtenerEgresosGraficos(Date fecIniMes, Date fecFinMes){

        egresosGraficos=moneyManagerRepository.obtenerEgresosGraficos(fecIniMes,fecFinMes);
        return egresosGraficos;

    }

    public List<fakeEntry> obtenerIngresosGraficos(Date fecIniMes, Date fecFinMes){

        ingresosGraficos=moneyManagerRepository.obtenerIngresosGraficos(fecIniMes,fecFinMes);
        return ingresosGraficos;

    }


    public void insertarTipoMovimiento(TipoEntity tipoEntity){

        moneyManagerRepository.insertarTipoMovimiento(tipoEntity);

    }


    public void actualizarTipo(int id,int tipo,String nombreTipo){

        moneyManagerRepository.actualizarTipo(id,nombreTipo,tipo);

    }

}
