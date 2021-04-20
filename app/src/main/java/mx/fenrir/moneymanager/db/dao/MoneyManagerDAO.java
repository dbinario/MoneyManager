package mx.fenrir.moneymanager.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.Date;
import java.util.List;

import mx.fenrir.moneymanager.db.entity.MovimientosEntity;
import mx.fenrir.moneymanager.db.entity.TipoEntity;
import mx.fenrir.moneymanager.utils.fakeEntry;

@Dao
public interface MoneyManagerDAO {

   @Insert
   void insertMovimiento(MovimientosEntity movimientosEntity);

   @Insert
   void insertTipoMovimiento(TipoEntity tipoEntity);

   @Query("SELECT nombreTipo FROM tipo WHERE tipo =:tipo ")
   List<String> obtenerTipos(int tipo);

   @Query("SELECT * FROM tipo WHERE tipo =:tipo")
   LiveData<List<TipoEntity>> obtenerListaTipos(int tipo);

   @Query("SELECT * FROM movimientos WHERE fecha BETWEEN :fecIniMes AND :fecFinMes ORDER BY fecha DESC")
   LiveData<List<MovimientosEntity>> obtenerMovimientosPorMes(Date fecIniMes, Date fecFinMes);

   @Query("SELECT SUM(egreso) FROM movimientos WHERE fecha BETWEEN :fecIniMes AND :fecFinMes")
   LiveData<Float> obtenerEgresos(Date fecIniMes, Date fecFinMes);

   @Query("SELECT SUM(ingreso) FROM movimientos WHERE fecha BETWEEN :fecIniMes AND :fecFinMes")
   LiveData<Float> obtenerIngresos(Date fecIniMes, Date fecFinMes);

   @Query("SELECT (SUM(ingreso)-SUM(egreso)) as 'SALDO' FROM movimientos WHERE fecha BETWEEN :fecIniMes AND :fecFinMes")
   LiveData<Float> obtenerSaldo(Date fecIniMes, Date fecFinMes);

   @Query("DELETE FROM movimientos")
   void eliminarMovimientos();

   @Query("DELETE FROM movimientos WHERE id=:id")
   void eliminarMovimiento(int id);

   @Query("DELETE FROM tipo WHERE id=:id")
   void eliminarTipo(int id);

   @Query("UPDATE tipo SET tipo=:tipo,nombreTipo=:nombreTipo WHERE id=:id")
   void actualizarTipo(int tipo,String nombreTipo,int id);

   @Query("UPDATE movimientos SET tipo=:tipo,nombreTipo=:nombreTipo,egreso=:egreso,ingreso=:ingreso,fecha=:fechaNueva WHERE id=:id")
   void actualizarMovimiento(int id,String nombreTipo,float egreso,float ingreso,int tipo,Date fechaNueva);

   @Query("SELECT sum(egreso) as dato,nombreTipo as nombre FROM movimientos WHERE fecha BETWEEN :fecIniMes AND :fecFinMes  AND tipo=0 GROUP BY nombreTipo")
   List<fakeEntry> obtenerEgresosGraficos(Date fecIniMes, Date fecFinMes);

   @Query("SELECT sum(ingreso) as dato,nombreTipo as nombre FROM movimientos WHERE fecha BETWEEN :fecIniMes AND :fecFinMes  AND tipo=1 GROUP BY nombreTipo")
   List<fakeEntry> obtenerIngresosGraficos(Date fecIniMes, Date fecFinMes);

}
