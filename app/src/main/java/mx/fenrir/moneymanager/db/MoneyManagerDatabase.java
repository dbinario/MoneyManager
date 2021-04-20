package mx.fenrir.moneymanager.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import mx.fenrir.moneymanager.R;
import mx.fenrir.moneymanager.db.dao.MoneyManagerDAO;
import mx.fenrir.moneymanager.db.entity.MovimientosEntity;
import mx.fenrir.moneymanager.db.entity.TipoEntity;
import mx.fenrir.moneymanager.db.utils.converters;


@Database(entities = {MovimientosEntity.class, TipoEntity.class},version = 1)
@TypeConverters({converters.class})

public abstract class MoneyManagerDatabase extends RoomDatabase {

    public abstract MoneyManagerDAO moneyDAO();
    public static Context ctx;
    private static  volatile MoneyManagerDatabase INSTANCE;

    public static MoneyManagerDatabase getDatabase(final Context context){

        ctx=context;

        if(INSTANCE==null){

            synchronized (MoneyManagerDatabase.class){

                if(INSTANCE==null){


                    INSTANCE=Room.databaseBuilder(context.getApplicationContext(),
                            MoneyManagerDatabase.class,"MoneyManagerDatabase")
                            .addCallback(sRoomDatabaseCallback)
                            .build();

                }
            }
        }

        return  INSTANCE;

    }



    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PoblarBaseTipo(INSTANCE).execute();
        }

    };



    private static class PoblarBaseTipo extends AsyncTask<Void,Void,Void> {

        private final MoneyManagerDAO managerDAO;

        PoblarBaseTipo(MoneyManagerDatabase db) {

            managerDAO=db.moneyDAO();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            managerDAO.eliminarMovimientos();

            managerDAO.insertTipoMovimiento(new TipoEntity(1, ctx.getString(R.string.Egresocomida)));
            managerDAO.insertTipoMovimiento(new TipoEntity(1, ctx.getString(R.string.EgresoTransporte)));
            managerDAO.insertTipoMovimiento(new TipoEntity(1, ctx.getString(R.string.EgresoRopa)));
            managerDAO.insertTipoMovimiento(new TipoEntity(1, ctx.getString(R.string.EgresoSalud)));
            managerDAO.insertTipoMovimiento(new TipoEntity(1, ctx.getString(R.string.EgresoCultura)));
            managerDAO.insertTipoMovimiento(new TipoEntity(1, ctx.getString(R.string.EgresoEntretenimiento)));
            managerDAO.insertTipoMovimiento(new TipoEntity(1, ctx.getString(R.string.EgresoRegalos)));
            managerDAO.insertTipoMovimiento(new TipoEntity(1, ctx.getString(R.string.EgresoGastosPropios)));
            managerDAO.insertTipoMovimiento(new TipoEntity(1, ctx.getString(R.string.EgresoEducacion)));
            managerDAO.insertTipoMovimiento(new TipoEntity(1, ctx.getString(R.string.EgresoRenta)));

            managerDAO.insertTipoMovimiento(new TipoEntity(0,ctx.getString(R.string.IngresoSalario)));
            managerDAO.insertTipoMovimiento(new TipoEntity(0,ctx.getString(R.string.IngresoPremios)));
            managerDAO.insertTipoMovimiento(new TipoEntity(0,ctx.getString(R.string.IngresoVenta)));
            managerDAO.insertTipoMovimiento(new TipoEntity(0,ctx.getString(R.string.IngresoOtros)));


            return null;
        }
    }
}
