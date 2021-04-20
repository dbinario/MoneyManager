package mx.fenrir.moneymanager.utils;

import java.util.Calendar;
import java.util.Date;

public class Utils {


    //funcion para ponder la fecha al inicio del día
    public static Date FechaInicioDia(Date fec){

        int Hora=0,Minuto=0,Segundo=0,Milisegundos=0;

        Date FecInicioDia;
        Calendar cal=Calendar.getInstance();
        cal.setTime(fec);

        //obtenemos los datos que se deben de restar
        Hora=cal.get(Calendar.HOUR_OF_DAY);
        Minuto=cal.get(Calendar.MINUTE);
        Segundo=cal.get(Calendar.SECOND);
        Milisegundos=cal.get(Calendar.MILLISECOND);

        //restamos los datos que acabamos de obtener
        cal.add(Calendar.HOUR_OF_DAY,-Hora);
        cal.add(Calendar.MINUTE,-Minuto);
        cal.add(Calendar.SECOND,-Segundo);
        cal.add(Calendar.MILLISECOND,-Milisegundos);

        //Obtenemos la hora del inicio del dia
        FecInicioDia=cal.getTime();

        return  FecInicioDia;

    }

    //funcion para ponder la fecha al fin del día
    public static Date FechaFinDia(Date fec){

        int Hora=0,Minuto=0,Segundo=0;

        Date FecFinDia;
        Calendar cal=Calendar.getInstance();
        cal.setTime(fec);

        //obtenemos los datos que se deben de restar
        Hora=cal.get(Calendar.HOUR_OF_DAY);
        Minuto=cal.get(Calendar.MINUTE);
        Segundo=cal.get(Calendar.SECOND);

        //restamos los datos que acabamos de obtener
        cal.add(Calendar.HOUR_OF_DAY,-Hora);
        cal.add(Calendar.MINUTE,-Minuto);
        cal.add(Calendar.SECOND,-Segundo);


        cal.add(Calendar.HOUR_OF_DAY,23);
        cal.add(Calendar.MINUTE,59);
        cal.add(Calendar.SECOND,59);

        //Obtenemos la hora del inicio del dia
        FecFinDia=cal.getTime();

        return  FecFinDia;

    }


    public static Date FechaInicioMes(Date fec){

            int Hora,Minuto,Segundo,Milisegundos;


            Calendar cal=Calendar.getInstance();
            cal.setTime(fec);
            cal.set(Calendar.DAY_OF_MONTH,1);

            //obtenemos los datos que se deben de restar
            Hora=cal.get(Calendar.HOUR_OF_DAY);
            Minuto=cal.get(Calendar.MINUTE);
            Segundo=cal.get(Calendar.SECOND);
            Milisegundos=cal.get(Calendar.MILLISECOND);

            //restamos los datos que acabamos de obtener
            cal.add(Calendar.HOUR_OF_DAY,-Hora);
            cal.add(Calendar.MINUTE,-Minuto);
            cal.add(Calendar.SECOND,-Segundo);
            cal.add(Calendar.MILLISECOND,-Milisegundos);


            fec=cal.getTime();


        return fec;

    }


    public static Date FechaFinMes(Date fec){

        int Hora,Minuto,Segundo,Milisegundos;

        Calendar cal=Calendar.getInstance();
        cal.setTime(fec);
        cal.set(Calendar.DAY_OF_MONTH,1);
        cal.add(Calendar.MONTH,1);
        cal.add(Calendar.DAY_OF_YEAR,-1);

        //obtenemos los datos que se deben de restar
        Hora=cal.get(Calendar.HOUR_OF_DAY);
        Minuto=cal.get(Calendar.MINUTE);
        Segundo=cal.get(Calendar.SECOND);
        Milisegundos=cal.get(Calendar.MILLISECOND);

        //restamos los datos que acabamos de obtener
        cal.add(Calendar.HOUR_OF_DAY,-Hora);
        cal.add(Calendar.MINUTE,-Minuto);
        cal.add(Calendar.SECOND,-Segundo);
        cal.add(Calendar.MILLISECOND,-Milisegundos);

        //restamos los datos que acabamos de obtener
        cal.add(Calendar.HOUR_OF_DAY,23);
        cal.add(Calendar.MINUTE,59);
        cal.add(Calendar.SECOND,59);
        cal.add(Calendar.MILLISECOND,999);

        fec=cal.getTime();

        return fec;

    }



}
