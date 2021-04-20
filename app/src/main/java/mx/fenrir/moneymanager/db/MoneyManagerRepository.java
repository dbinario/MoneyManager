package mx.fenrir.moneymanager.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import mx.fenrir.moneymanager.db.dao.MoneyManagerDAO;
import mx.fenrir.moneymanager.db.entity.MovimientosEntity;
import mx.fenrir.moneymanager.db.entity.TipoEntity;
import mx.fenrir.moneymanager.utils.fakeEntry;

public class MoneyManagerRepository {

    private MoneyManagerDAO moneyManagerDAO;
    private LiveData<List<MovimientosEntity>> movimientosMes;
    private LiveData<Float> ingresos,egresos,saldo;
    private LiveData<List<TipoEntity>> listaTipos;


    public MoneyManagerRepository(Application application){

            MoneyManagerDatabase moneyManagerDatabase=MoneyManagerDatabase.getDatabase(application);
            moneyManagerDAO=moneyManagerDatabase.moneyDAO();

    }


    public LiveData<List<MovimientosEntity>> obtenerMovimientosMes(Date fecIniMes,Date fecFinMes){

        movimientosMes=moneyManagerDAO.obtenerMovimientosPorMes(fecIniMes,fecFinMes);
        return movimientosMes;

    }

    public LiveData<List<TipoEntity>> obtenerListaTipos(int tipo){

        listaTipos=moneyManagerDAO.obtenerListaTipos(tipo);
        return listaTipos;
    }



    public List<String> obtenerTipos(int tipo){

        try {
            return new ObtenerTiposAsyn(moneyManagerDAO,tipo).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();

            List<String> ejemploLista = new ArrayList<String>();
            return ejemploLista;

        } catch (InterruptedException e) {
            e.printStackTrace();
            List<String> ejemploLista = new ArrayList<String>();
            return ejemploLista;
        }


    }

    public LiveData<Float> obtenerIngresos(Date fecIniMes, Date fecFinMes){

        ingresos=moneyManagerDAO.obtenerIngresos(fecIniMes,fecFinMes);
        return ingresos;

    }


    public LiveData<Float> obtenerEgresos(Date fecIniMes, Date fecFinMes){

        egresos=moneyManagerDAO.obtenerEgresos(fecIniMes,fecFinMes);
        return egresos;

    }

    public LiveData<Float> obtenerSaldo(Date fecIniMes, Date fecFinMes){

        saldo=moneyManagerDAO.obtenerSaldo(fecIniMes,fecFinMes);
        return saldo;

    }

    public void eliminarMovimientos(){

        new eliminarMovimientosAsyn(moneyManagerDAO).execute();

    }


    public void eliminarMovimiento(int id){

        new eliminarMovimientoAsyn(moneyManagerDAO,id).execute();

    }


    public void eliminarTipo(int id){

        new eliminarTipoAsyn(moneyManagerDAO,id).execute();

    }


    public void actualizarMovimiento(int id,String nombreTipo,float egreso,float ingreso,int tipo,Date fechaNueva){

        new actualizarMovimientoAsyn(moneyManagerDAO,id,nombreTipo,egreso,ingreso,tipo,fechaNueva).execute();


    }


    public void actualizarTipo(int id,String nombreTipo,int tipo){

        new actualizarTipoAsyn(moneyManagerDAO,id,nombreTipo,tipo).execute();

    }

    public List<fakeEntry> obtenerEgresosGraficos(Date fecIniMes, Date fecFinMes){

        try {
            return  new obtenerEgresosGraficosAsyn(moneyManagerDAO,fecIniMes,fecFinMes).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
            List<fakeEntry> ejemploLista = new ArrayList<>();
            return ejemploLista;
        } catch (InterruptedException e) {
            e.printStackTrace();
            List<fakeEntry> ejemploLista = new ArrayList<>();
            return ejemploLista;
        }

    }


    public List<fakeEntry> obtenerIngresosGraficos(Date fecIniMes, Date fecFinMes){

        try {
            return  new obtenerIngresosGraficosAsyn(moneyManagerDAO,fecIniMes,fecFinMes).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
            List<fakeEntry> ejemploLista = new ArrayList<>();
            return ejemploLista;
        } catch (InterruptedException e) {
            e.printStackTrace();
            List<fakeEntry> ejemploLista = new ArrayList<>();
            return ejemploLista;
        }

    }


    public void insertarTipoMovimiento(TipoEntity tipoEntity){


        new insertTipoMovimientoAsyn(moneyManagerDAO,tipoEntity).execute();


    }

    private class ObtenerTiposAsyn extends AsyncTask<Void,Void,List<String>> {

        private MoneyManagerDAO moneyManagerDAO;
        private int tipo;

        public ObtenerTiposAsyn(MoneyManagerDAO moneyManagerDAO, int tipo) {

            this.moneyManagerDAO=moneyManagerDAO;
            this.tipo=tipo;

        }

        @Override
        protected List<String> doInBackground(Void... voids) {

            return moneyManagerDAO.obtenerTipos(tipo);

        }

        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);
        }
    }


    public void insertarMovimiento(MovimientosEntity movimientosEntity){


        new InsertarMovimientoAsyn(moneyManagerDAO,movimientosEntity).execute();

    }


    private class eliminarMovimientosAsyn  extends AsyncTask<Void,Void,Void>{

        MoneyManagerDAO dao;

        public eliminarMovimientosAsyn(MoneyManagerDAO moneyManagerDAO) {

            dao=moneyManagerDAO;

        }

        @Override
        protected Void doInBackground(Void... voids) {

            dao.eliminarMovimientos();
            return null;
        }
    }



    private class InsertarMovimientoAsyn extends AsyncTask<Void,Void,Void>{

        MoneyManagerDAO dao;
        MovimientosEntity movimientosEntity;

        public InsertarMovimientoAsyn(MoneyManagerDAO moneyManagerDAO, MovimientosEntity movimientosEntity) {

            dao=moneyManagerDAO;
            this.movimientosEntity=movimientosEntity;

        }

        @Override
        protected Void doInBackground(Void... voids) {

            dao.insertMovimiento(movimientosEntity);

            return null;
        }
    }

    private class insertTipoMovimientoAsyn  extends AsyncTask<Void,Void,Void>{

        MoneyManagerDAO dao;
        TipoEntity tipoEntity;


        public insertTipoMovimientoAsyn(MoneyManagerDAO moneyManagerDAO,TipoEntity tipoEntity) {

            dao=moneyManagerDAO;
            this.tipoEntity=tipoEntity;


        }

        @Override
        protected Void doInBackground(Void... voids) {

            dao.insertTipoMovimiento(tipoEntity);
            return null;
        }
    }

    private class eliminarMovimientoAsyn extends AsyncTask<Void,Void,Void>{

        MoneyManagerDAO dao;
        int id;

        public eliminarMovimientoAsyn(MoneyManagerDAO moneyManagerDAO, int id) {

        dao=moneyManagerDAO;
        this.id=id;

        }

        @Override
        protected Void doInBackground(Void... voids) {

            dao.eliminarMovimiento(id);

            return null;
        }
    }

    private class eliminarTipoAsyn extends AsyncTask<Void,Void,Void>{

        MoneyManagerDAO dao;
        int id;

        public eliminarTipoAsyn(MoneyManagerDAO moneyManagerDAO, int id) {
            dao=moneyManagerDAO;
            this.id=id;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.eliminarTipo(id);
            return null;
        }
    }

    private class actualizarMovimientoAsyn extends AsyncTask<Void,Void,Void> {

        MoneyManagerDAO dao;
        int id,tipo;
        String nombreTipo;
        float ingreso,egreso;
        Date fechaNueva;

        public actualizarMovimientoAsyn(MoneyManagerDAO moneyManagerDAO, int id, String nombreTipo, float egreso, float ingreso, int tipo,Date fechaNueva) {

            dao=moneyManagerDAO;
            this.id=id;
            this.nombreTipo=nombreTipo;
            this.egreso=egreso;
            this.ingreso=ingreso;
            this.tipo=tipo;
            this.fechaNueva=fechaNueva;

        }

        @Override
        protected Void doInBackground(Void... voids) {

            dao.actualizarMovimiento(id,nombreTipo,egreso,ingreso,tipo,fechaNueva);
            return null;
        }
    }

    private class obtenerEgresosGraficosAsyn extends AsyncTask<Void,Void,List<fakeEntry>>{


        MoneyManagerDAO dao;
        Date fecIniMes,fecFinMes;


        public obtenerEgresosGraficosAsyn(MoneyManagerDAO moneyManagerDAO, Date fecIniMes, Date fecFinMes) {

        dao=moneyManagerDAO;
        this.fecIniMes=fecIniMes;
        this.fecFinMes=fecFinMes;

        }

        @Override
        protected List<fakeEntry> doInBackground(Void... voids) {
            return dao.obtenerEgresosGraficos(fecIniMes,fecFinMes);
        }

        @Override
        protected void onPostExecute(List<fakeEntry> fakeEntries) {
            super.onPostExecute(fakeEntries);
        }
    }

    private class obtenerIngresosGraficosAsyn extends AsyncTask<Void,Void,List<fakeEntry>> {

        MoneyManagerDAO dao;
        Date fecInimes,fecFinMes;

        public obtenerIngresosGraficosAsyn(MoneyManagerDAO moneyManagerDAO, Date fecIniMes, Date fecFinMes) {

            dao=moneyManagerDAO;
            this.fecInimes=fecIniMes;
            this.fecFinMes=fecFinMes;

        }

        @Override
        protected List<fakeEntry> doInBackground(Void... voids) {

            return dao.obtenerIngresosGraficos(fecInimes,fecFinMes);

        }

        @Override
        protected void onPostExecute(List<fakeEntry> fakeEntries) {
            super.onPostExecute(fakeEntries);
        }


    }

    private class actualizarTipoAsyn  extends AsyncTask<Void,Void,Void>{

        MoneyManagerDAO dao;
        int id,tipo;
        String nombreTipo;

        public actualizarTipoAsyn(MoneyManagerDAO moneyManagerDAO, int id, String nombreTipo, int tipo) {


            dao=moneyManagerDAO;
            this.id=id;
            this.nombreTipo=nombreTipo;
            this.tipo=tipo;

        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.actualizarTipo(tipo,nombreTipo,id);
            return null;
        }
    }
}
