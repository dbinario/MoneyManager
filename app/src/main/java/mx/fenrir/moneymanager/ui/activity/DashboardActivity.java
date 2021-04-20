package mx.fenrir.moneymanager.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

import mx.fenrir.moneymanager.R;
import mx.fenrir.moneymanager.db.MoneyManagerViewModel;
import mx.fenrir.moneymanager.ui.fragments.GraficosFragment;
import mx.fenrir.moneymanager.ui.fragments.MovimientosFragment;
import mx.fenrir.moneymanager.ui.fragments.NuevoMovimientoDialogFragment;
import mx.fenrir.moneymanager.utils.Utils;


public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {

    FloatingActionButton fab;
    MoneyManagerViewModel managerViewModel;
    TextView tvIngresos,tvEgresos,tvTotal,tvMeses,tvAnoDashboard;
    TextView tvMes0,tvMes1,tvMes2,tvMes3,tvMes4,tvMes5,tvMes6,tvMes7,tvMes8,tvMes9,tvMes10,tvMes11;
    ImageView ivLeft,ivRight,ivOpciones;
    DecimalFormat decimalFormat=new DecimalFormat("$###,###,###,###.00");
    Calendar calendar=Calendar.getInstance();
    int Mes,Ano,EstadoTool=0;
    MaterialCardView materialCardViewSelMes;
    Date fecIniMes,fecFinMes;
    BottomNavigationView navigation;
    int opc=0;
    Fragment f;
    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //inicializamos el MobileAds

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });


        //cargamos el anuncio
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        //invocamos al metodo que vincula los elementos
        setElements();
        //generemos el viewmodel
        managerViewModel=ViewModelProviders.of(this).get(MoneyManagerViewModel.class);

        //hacemos invisible al materialtoolbar de los meses
        materialCardViewSelMes.setVisibility(View.GONE);
        //obtenemos la fecha actual y la vinculamos a las variables
        Mes=calendar.get(Calendar.MONTH);
        Ano=calendar.get(Calendar.YEAR);
        marcarMes();

        //obtenemos la fecha del inicio del mes y del fin del mes para invocar al fragment
        fecIniMes= Utils.FechaInicioMes(calendar.getTime());
        fecFinMes= Utils.FechaFinMes(calendar.getTime());


        //le ponemos el año al tv
        tvAnoDashboard.setText(Integer.toString(Ano));
        setFechaTV();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contenedor, MovimientosFragment.newInstance(fecIniMes,fecFinMes))
                .commit();


        setObservadores();


    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            f=null;

            switch (item.getItemId()) {
                case R.id.navigation_principal:
                    opc=0;
                    //f= MovimientosFragment.newInstance(fecIniMes,fecFinMes);
                    fab.show();
                    break;
                case R.id.navigation_graficos:
                    opc=1;
                    //f=GraficosFragment.newInstance(fecIniMes,fecFinMes);
                    fab.hide();
                    break;
            }

                setFragment();

                return true;

        }


        };

    private void setObservadores() {


        //se genera el observador de obtener ingresos
        managerViewModel.obtenerIngresos(fecIniMes,fecFinMes).observe(this, new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {

                if(aFloat!=null) {

                    tvIngresos.setText(decimalFormat.format(aFloat));

                }else{

                    tvIngresos.setText("0.0");

                }
            }
        });

        // se genera el observador de obtener egresos
        managerViewModel.obtenerEgresos(fecIniMes,fecFinMes).observe(this, new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {

                if(aFloat!=null){
                    tvEgresos.setText(decimalFormat.format(aFloat));
                }else{

                    tvEgresos.setText("0.0");

                }

            }
        });



        //se obtiene el observador para el total de saldo
        managerViewModel.obtenerSaldo(fecIniMes,fecFinMes).observe(this, new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {

                if(aFloat!=null){

                    if(aFloat>0.0){

                        tvTotal.setTextColor(getColor(R.color.colorIngreso));

                    }else if(aFloat<0.0){

                        tvTotal.setTextColor(getColor(R.color.colorEgreso));

                    }


                    tvTotal.setText(decimalFormat.format(aFloat));
                }else{

                    tvTotal.setText("0.0");

                }


            }
        });

    }

    private void marcarMes() {

        switch (Mes){
            case 0:
            tvMes0.setBackgroundColor(getColor(R.color.colorAccent));

            tvMes1.setBackgroundColor(getColor(R.color.colorGris));
            tvMes2.setBackgroundColor(getColor(R.color.colorGris));
            tvMes3.setBackgroundColor(getColor(R.color.colorGris));
            tvMes4.setBackgroundColor(getColor(R.color.colorGris));
            tvMes5.setBackgroundColor(getColor(R.color.colorGris));
            tvMes6.setBackgroundColor(getColor(R.color.colorGris));
            tvMes7.setBackgroundColor(getColor(R.color.colorGris));
            tvMes8.setBackgroundColor(getColor(R.color.colorGris));
            tvMes9.setBackgroundColor(getColor(R.color.colorGris));
            tvMes10.setBackgroundColor(getColor(R.color.colorGris));
            tvMes11.setBackgroundColor(getColor(R.color.colorGris));


                break;

            case 1:

            tvMes1.setBackgroundColor(getColor(R.color.colorAccent));

                tvMes0.setBackgroundColor(getColor(R.color.colorGris));
                tvMes2.setBackgroundColor(getColor(R.color.colorGris));
                tvMes3.setBackgroundColor(getColor(R.color.colorGris));
                tvMes4.setBackgroundColor(getColor(R.color.colorGris));
                tvMes5.setBackgroundColor(getColor(R.color.colorGris));
                tvMes6.setBackgroundColor(getColor(R.color.colorGris));
                tvMes7.setBackgroundColor(getColor(R.color.colorGris));
                tvMes8.setBackgroundColor(getColor(R.color.colorGris));
                tvMes9.setBackgroundColor(getColor(R.color.colorGris));
                tvMes10.setBackgroundColor(getColor(R.color.colorGris));
                tvMes11.setBackgroundColor(getColor(R.color.colorGris));


                break;

            case 2:

            tvMes2.setBackgroundColor(getColor(R.color.colorAccent));

                tvMes0.setBackgroundColor(getColor(R.color.colorGris));
                tvMes1.setBackgroundColor(getColor(R.color.colorGris));
                tvMes3.setBackgroundColor(getColor(R.color.colorGris));
                tvMes4.setBackgroundColor(getColor(R.color.colorGris));
                tvMes5.setBackgroundColor(getColor(R.color.colorGris));
                tvMes6.setBackgroundColor(getColor(R.color.colorGris));
                tvMes7.setBackgroundColor(getColor(R.color.colorGris));
                tvMes8.setBackgroundColor(getColor(R.color.colorGris));
                tvMes9.setBackgroundColor(getColor(R.color.colorGris));
                tvMes10.setBackgroundColor(getColor(R.color.colorGris));
                tvMes11.setBackgroundColor(getColor(R.color.colorGris));


                break;

            case 3:

            tvMes3.setBackgroundColor(getColor(R.color.colorAccent));

                tvMes0.setBackgroundColor(getColor(R.color.colorGris));
                tvMes1.setBackgroundColor(getColor(R.color.colorGris));
                tvMes2.setBackgroundColor(getColor(R.color.colorGris));
                tvMes4.setBackgroundColor(getColor(R.color.colorGris));
                tvMes5.setBackgroundColor(getColor(R.color.colorGris));
                tvMes6.setBackgroundColor(getColor(R.color.colorGris));
                tvMes7.setBackgroundColor(getColor(R.color.colorGris));
                tvMes8.setBackgroundColor(getColor(R.color.colorGris));
                tvMes9.setBackgroundColor(getColor(R.color.colorGris));
                tvMes10.setBackgroundColor(getColor(R.color.colorGris));
                tvMes11.setBackgroundColor(getColor(R.color.colorGris));

                break;

            case 4:

            tvMes4.setBackgroundColor(getColor(R.color.colorAccent));

                tvMes0.setBackgroundColor(getColor(R.color.colorGris));
                tvMes1.setBackgroundColor(getColor(R.color.colorGris));
                tvMes2.setBackgroundColor(getColor(R.color.colorGris));
                tvMes3.setBackgroundColor(getColor(R.color.colorGris));
                tvMes5.setBackgroundColor(getColor(R.color.colorGris));
                tvMes6.setBackgroundColor(getColor(R.color.colorGris));
                tvMes7.setBackgroundColor(getColor(R.color.colorGris));
                tvMes8.setBackgroundColor(getColor(R.color.colorGris));
                tvMes9.setBackgroundColor(getColor(R.color.colorGris));
                tvMes10.setBackgroundColor(getColor(R.color.colorGris));
                tvMes11.setBackgroundColor(getColor(R.color.colorGris));



                break;

            case 5:

            tvMes5.setBackgroundColor(getColor(R.color.colorAccent));


                tvMes0.setBackgroundColor(getColor(R.color.colorGris));
                tvMes1.setBackgroundColor(getColor(R.color.colorGris));
                tvMes2.setBackgroundColor(getColor(R.color.colorGris));
                tvMes3.setBackgroundColor(getColor(R.color.colorGris));
                tvMes4.setBackgroundColor(getColor(R.color.colorGris));
                tvMes6.setBackgroundColor(getColor(R.color.colorGris));
                tvMes7.setBackgroundColor(getColor(R.color.colorGris));
                tvMes8.setBackgroundColor(getColor(R.color.colorGris));
                tvMes9.setBackgroundColor(getColor(R.color.colorGris));
                tvMes10.setBackgroundColor(getColor(R.color.colorGris));
                tvMes11.setBackgroundColor(getColor(R.color.colorGris));

                break;

            case 6:

            tvMes6.setBackgroundColor(getColor(R.color.colorAccent));

                tvMes0.setBackgroundColor(getColor(R.color.colorGris));
                tvMes1.setBackgroundColor(getColor(R.color.colorGris));
                tvMes2.setBackgroundColor(getColor(R.color.colorGris));
                tvMes3.setBackgroundColor(getColor(R.color.colorGris));
                tvMes4.setBackgroundColor(getColor(R.color.colorGris));
                tvMes5.setBackgroundColor(getColor(R.color.colorGris));
                tvMes7.setBackgroundColor(getColor(R.color.colorGris));
                tvMes8.setBackgroundColor(getColor(R.color.colorGris));
                tvMes9.setBackgroundColor(getColor(R.color.colorGris));
                tvMes10.setBackgroundColor(getColor(R.color.colorGris));
                tvMes11.setBackgroundColor(getColor(R.color.colorGris));

                break;

            case 7:

            tvMes7.setBackgroundColor(getColor(R.color.colorAccent));

                tvMes0.setBackgroundColor(getColor(R.color.colorGris));
                tvMes1.setBackgroundColor(getColor(R.color.colorGris));
                tvMes2.setBackgroundColor(getColor(R.color.colorGris));
                tvMes3.setBackgroundColor(getColor(R.color.colorGris));
                tvMes4.setBackgroundColor(getColor(R.color.colorGris));
                tvMes5.setBackgroundColor(getColor(R.color.colorGris));
                tvMes6.setBackgroundColor(getColor(R.color.colorGris));
                tvMes8.setBackgroundColor(getColor(R.color.colorGris));
                tvMes9.setBackgroundColor(getColor(R.color.colorGris));
                tvMes10.setBackgroundColor(getColor(R.color.colorGris));
                tvMes11.setBackgroundColor(getColor(R.color.colorGris));


                break;

            case 8:

            tvMes8.setBackgroundColor(getColor(R.color.colorAccent));

                tvMes0.setBackgroundColor(getColor(R.color.colorGris));
                tvMes1.setBackgroundColor(getColor(R.color.colorGris));
                tvMes2.setBackgroundColor(getColor(R.color.colorGris));
                tvMes3.setBackgroundColor(getColor(R.color.colorGris));
                tvMes4.setBackgroundColor(getColor(R.color.colorGris));
                tvMes5.setBackgroundColor(getColor(R.color.colorGris));
                tvMes6.setBackgroundColor(getColor(R.color.colorGris));
                tvMes7.setBackgroundColor(getColor(R.color.colorGris));
                tvMes9.setBackgroundColor(getColor(R.color.colorGris));
                tvMes10.setBackgroundColor(getColor(R.color.colorGris));
                tvMes11.setBackgroundColor(getColor(R.color.colorGris));

            break;

            case 9:

            tvMes9.setBackgroundColor(getColor(R.color.colorAccent));

                tvMes0.setBackgroundColor(getColor(R.color.colorGris));
                tvMes1.setBackgroundColor(getColor(R.color.colorGris));
                tvMes2.setBackgroundColor(getColor(R.color.colorGris));
                tvMes3.setBackgroundColor(getColor(R.color.colorGris));
                tvMes4.setBackgroundColor(getColor(R.color.colorGris));
                tvMes5.setBackgroundColor(getColor(R.color.colorGris));
                tvMes6.setBackgroundColor(getColor(R.color.colorGris));
                tvMes7.setBackgroundColor(getColor(R.color.colorGris));
                tvMes8.setBackgroundColor(getColor(R.color.colorGris));
                tvMes10.setBackgroundColor(getColor(R.color.colorGris));
                tvMes11.setBackgroundColor(getColor(R.color.colorGris));

            break;

            case 10:


            tvMes10.setBackgroundColor(getColor(R.color.colorAccent));

                tvMes0.setBackgroundColor(getColor(R.color.colorGris));
                tvMes1.setBackgroundColor(getColor(R.color.colorGris));
                tvMes2.setBackgroundColor(getColor(R.color.colorGris));
                tvMes3.setBackgroundColor(getColor(R.color.colorGris));
                tvMes4.setBackgroundColor(getColor(R.color.colorGris));
                tvMes5.setBackgroundColor(getColor(R.color.colorGris));
                tvMes6.setBackgroundColor(getColor(R.color.colorGris));
                tvMes7.setBackgroundColor(getColor(R.color.colorGris));
                tvMes8.setBackgroundColor(getColor(R.color.colorGris));
                tvMes9.setBackgroundColor(getColor(R.color.colorGris));
                tvMes11.setBackgroundColor(getColor(R.color.colorGris));

            break;

            case 11:


            tvMes11.setBackgroundColor(getColor(R.color.colorAccent));

                tvMes0.setBackgroundColor(getColor(R.color.colorGris));
                tvMes1.setBackgroundColor(getColor(R.color.colorGris));
                tvMes2.setBackgroundColor(getColor(R.color.colorGris));
                tvMes3.setBackgroundColor(getColor(R.color.colorGris));
                tvMes4.setBackgroundColor(getColor(R.color.colorGris));
                tvMes5.setBackgroundColor(getColor(R.color.colorGris));
                tvMes6.setBackgroundColor(getColor(R.color.colorGris));
                tvMes7.setBackgroundColor(getColor(R.color.colorGris));
                tvMes8.setBackgroundColor(getColor(R.color.colorGris));
                tvMes9.setBackgroundColor(getColor(R.color.colorGris));
                tvMes10.setBackgroundColor(getColor(R.color.colorGris));

                break;


        }

    }

    private void setFechaTV() {

    String mesesArray[] =getResources().getStringArray(R.array.meses);

    String cadenaMostrar=mesesArray[Mes]+" - "+Ano;

        tvMeses.setText(cadenaMostrar);

        //marcamos el mes seleccionado
        marcarMes();
        //materialCardViewSelMes.setVisibility(View.GONE);
    }



    public void setElements(){

        //ocultamos la barra
        getSupportActionBar().hide();

        //vinculamos el fab
        fab=findViewById(R.id.fabDashboard);

        fab.setOnClickListener(this);

        tvIngresos=findViewById(R.id.tVIngresosTot);
        tvEgresos=findViewById(R.id.tVEgresosTot);
        tvTotal=findViewById(R.id.tVSaldoTot);
        tvMeses=findViewById(R.id.tVmesDashboard);
        tvMeses.setOnClickListener(this);
        materialCardViewSelMes=findViewById(R.id.materialCardViewSelMes);
        tvAnoDashboard=findViewById(R.id.tvAnoDashboard);

        //vinculamos los meses
        tvMes0=findViewById(R.id.tvMes0);
        tvMes0.setOnClickListener(this);

        tvMes1=findViewById(R.id.tvMes1);
        tvMes1.setOnClickListener(this);

        tvMes2=findViewById(R.id.tvMes2);
        tvMes2.setOnClickListener(this);

        tvMes3=findViewById(R.id.tvMes3);
        tvMes3.setOnClickListener(this);

        tvMes4=findViewById(R.id.tvMes4);
        tvMes4.setOnClickListener(this);

        tvMes5=findViewById(R.id.tvMes5);
        tvMes5.setOnClickListener(this);

        tvMes6=findViewById(R.id.tvMes6);
        tvMes6.setOnClickListener(this);

        tvMes7=findViewById(R.id.tvMes7);
        tvMes7.setOnClickListener(this);

        tvMes8=findViewById(R.id.tvMes8);
        tvMes8.setOnClickListener(this);

        tvMes9=findViewById(R.id.tvMes9);
        tvMes9.setOnClickListener(this);

        tvMes10=findViewById(R.id.tvMes10);
        tvMes10.setOnClickListener(this);

        tvMes11=findViewById(R.id.tvMes11);
        tvMes11.setOnClickListener(this);

        //vinculamos las imagenes
        ivLeft=findViewById(R.id.imageViewLeft);
        ivLeft.setOnClickListener(this);

        ivRight=findViewById(R.id.imageViewRight);
        ivRight.setOnClickListener(this);

        ivOpciones=findViewById(R.id.imageViewOpciones);
        ivOpciones.setOnClickListener(this);


        navigation = findViewById(R.id.nav_view);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.fabDashboard:

                NuevoMovimientoDialogFragment dialogFragment=new NuevoMovimientoDialogFragment();
                dialogFragment.show(getSupportFragmentManager(), "NuevoMovimientoDialogFragment");

                break;

            case R.id.tVmesDashboard:

                setToolMeses();

                break;

            case R.id.tvMes0:
                Mes=0;
                setFechaTV();
                setFechasIniFin();
                break;

            case R.id.tvMes1:
                Mes=1;
                setFechaTV();
                setFechasIniFin();
                break;

            case R.id.tvMes2:
                Mes=2;
                setFechaTV();
                setFechasIniFin();
                break;

            case R.id.tvMes3:
                Mes=3;
                setFechaTV();
                setFechasIniFin();
                break;

            case R.id.tvMes4:
                Mes=4;
                setFechaTV();
                setFechasIniFin();
                break;

            case R.id.tvMes5:
                Mes=5;
                setFechaTV();
                setFechasIniFin();
                break;

            case R.id.tvMes6:
                Mes=6;
                setFechaTV();
                setFechasIniFin();
                break;

            case R.id.tvMes7:
                Mes=7;
                setFechaTV();
                setFechasIniFin();
                break;

            case R.id.tvMes8:
                Mes=8;
                setFechaTV();
                setFechasIniFin();
                break;

            case R.id.tvMes9:
                Mes=9;
                setFechaTV();
                setFechasIniFin();
                break;

            case R.id.tvMes10:
                Mes=10;
                setFechaTV();
                setFechasIniFin();
                break;

            case R.id.tvMes11:
                Mes=11;
                setFechaTV();
                setFechasIniFin();
                break;

            case R.id.imageViewLeft:

                Ano=Ano-1;
                tvAnoDashboard.setText(Integer.toString(Ano));

                break;

            case R.id.imageViewRight:

                Ano=Ano+1;
                tvAnoDashboard.setText(Integer.toString(Ano));

                break;

            case R.id.imageViewOpciones:

                //Toast.makeText(this, "Proximamente", Toast.LENGTH_SHORT).show();

                Intent i=new Intent(this, OpcionesActivity.class);
                startActivity(i);

                break;

            default:

                Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show();

            break;

        }


    }

    private void setFechasIniFin() {

        //generamos las fechas de acuerdo al tv seleccionado
        calendar.set(Ano,Mes,1);
        fecIniMes=Utils.FechaInicioMes(calendar.getTime());
        fecFinMes=Utils.FechaFinMes(calendar.getTime());



        setFragment();
        setObservadores();

    }

    private void setFragment() {

        if(opc==0){

            f=MovimientosFragment.newInstance(fecIniMes,fecFinMes);


        }else if(opc==1){


            f= GraficosFragment.newInstance(fecIniMes,fecFinMes);

        }

        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,f).commit();

    }

    private void setToolMeses() {


        if(EstadoTool==0){

            EstadoTool=1;
            materialCardViewSelMes.setVisibility(View.VISIBLE);
            tvMeses.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_drop_up_black_24dp, 0);

        }else if(EstadoTool==1){

            EstadoTool=0;
            materialCardViewSelMes.setVisibility(View.GONE);
            tvMeses.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_drop_down_black_24dp, 0);

        }



    }

}
