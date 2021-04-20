package mx.fenrir.moneymanager.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.fenrir.moneymanager.R;
import mx.fenrir.moneymanager.db.MoneyManagerViewModel;
import mx.fenrir.moneymanager.utils.fakeEntry;


public class GraficosFragment extends Fragment {

    private static final String ARG_FEC_INI = "FEC1";
    private static final String ARG_FEC_FIN = "FEC2";
    PieChart pieChart;
    BarChart barChart;
    private MoneyManagerViewModel moneyManagerViewModel;
    private List<fakeEntry> egresosGraficos,ingresosGraficos;
    private List<PieEntry> egresosGraficosPE=new ArrayList<>(),ingresosGraficosPE=new ArrayList<>();
    private List<BarEntry> ingresosEgresosBE=new ArrayList<>();
    private Date fecIniMes,fecFinMes;
    private int grafico=0;
    Spinner spSelGrafico;

    final float[] egresos = {0};
    final float[] ingresos = {0};

    public GraficosFragment() {

    }

    public static GraficosFragment newInstance(Date fecIniMes, Date fecFinMes) {
        GraficosFragment fragment = new GraficosFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_FEC_INI,fecIniMes);
        args.putSerializable(ARG_FEC_FIN,fecFinMes);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            fecIniMes=(Date) getArguments().getSerializable(ARG_FEC_INI);
            fecFinMes=(Date) getArguments().getSerializable(ARG_FEC_FIN);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_graficos, container, false);
        pieChart=view.findViewById(R.id.piechart);
        barChart=view.findViewById(R.id.barchart);
        barChart.setVisibility(View.GONE);
        spSelGrafico=view.findViewById(R.id.spSelGrafico);
        String [] cs={getString(R.string.egreso),getString(R.string.ingreso),getString(R.string.ingresos_vs_egresos)};
        ArrayAdapter<String> adapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,cs);


        moneyManagerViewModel= ViewModelProviders.of(getActivity()).get(MoneyManagerViewModel.class);

        //metodo para cargar los datos en las variables
        cargarDatos();

        cargarGrafico();

        spSelGrafico.setAdapter(adapter);
        spSelGrafico.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String selspGraf=spSelGrafico.getSelectedItem().toString();

                if(selspGraf.equals(getString(R.string.ingreso))){

                    grafico=1;
                    cargarGrafico();


                }else if(selspGraf.equals(getString(R.string.egreso))){

                    grafico=0;
                    cargarGrafico();

                }else if(selspGraf.equals(getString(R.string.ingresos_vs_egresos))){

                    grafico=2;
                    cargarGrafico();

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    return view;
    }

    private void cargarDatos() {



        egresosGraficos=moneyManagerViewModel.obtenerEgresosGraficos(fecIniMes,fecFinMes);
        for(fakeEntry fe : egresosGraficos)
        {

            egresosGraficosPE.add(new PieEntry(fe.getDato(),fe.getNombre()));

        }

        ingresosGraficos=moneyManagerViewModel.obtenerIngresosGraficos(fecIniMes,fecFinMes);
        for(fakeEntry feing : ingresosGraficos)
        {

            ingresosGraficosPE.add(new PieEntry(feing.getDato(),feing.getNombre()));


        }

        moneyManagerViewModel.obtenerEgresos(fecIniMes,fecFinMes).observe(this, new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {

                if(aFloat!=null){

                    egresos[0] =aFloat;

                }else{

                    egresos[0] ='0';

                }



            }
        });

        moneyManagerViewModel.obtenerIngresos(fecIniMes,fecFinMes).observe(this, new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {

                if(aFloat!=null){

                    ingresos[0] =aFloat;

                }else{

                    ingresos[0] ='0';

                }



            }
        });

        //ingresosEgresosBE.add(new BarEntry(ingresos[0]+egresos[0],'3'));


    }

    private void cargarGrafico() {


        if(grafico==0){
        pieChart.setVisibility(View.VISIBLE);
        barChart.setVisibility(View.GONE);

        PieDataSet set = new PieDataSet(egresosGraficosPE, getActivity().getString(R.string.egresos));

        set.setColors(ColorTemplate.COLORFUL_COLORS);
        set.setValueTextSize(11f);
        PieData data = new PieData(set);
        data.setValueFormatter(new PercentFormatter(pieChart));
        pieChart.setData(data);
        pieChart.setUsePercentValues(true);
        pieChart.animateY(1400, Easing.EaseInOutQuad);
        pieChart.setCenterText(getActivity().getString(R.string.egresos));
        pieChart.setEntryLabelTextSize(12f);
        Description description = pieChart.getDescription();
        description.setText(getActivity().getString(R.string.egresos));
        pieChart.invalidate(); // refresh
        }else if(grafico==1){

        pieChart.setVisibility(View.VISIBLE);
        barChart.setVisibility(View.GONE);
        PieDataSet set = new PieDataSet(ingresosGraficosPE, getActivity().getString(R.string.ingresos));
        set.setColors(ColorTemplate.COLORFUL_COLORS);
        set.setValueTextSize(11f);
        PieData data = new PieData(set);
        data.setValueFormatter(new PercentFormatter(pieChart));
        pieChart.setData(data);
        pieChart.setUsePercentValues(true);
        pieChart.animateY(1400, Easing.EaseInOutQuad);
        pieChart.setCenterText(getActivity().getString(R.string.ingresos));
        pieChart.setEntryLabelTextSize(12f);
        Description description = pieChart.getDescription();
        description.setText(getActivity().getString(R.string.ingresos));
        pieChart.invalidate(); // refresh
        }else if(grafico==2){

         pieChart.setVisibility(View.GONE);

         //hacemos visible el grafico de barras
         barChart.setVisibility(View.VISIBLE);

            ingresosEgresosBE.add(new BarEntry('0',egresos[0]));
            ingresosEgresosBE.add(new BarEntry('1',ingresos[0]));

         BarDataSet dataSet=new BarDataSet(ingresosEgresosBE,getString(R.string.ingresos_vs_egresos));
         dataSet.setColors(new int[] { R.color.colorEgreso,R.color.colorIngreso}, getContext());
         BarData data=new BarData(dataSet);
         barChart.setData(data);

         barChart.getData().notifyDataChanged();

         barChart.getLegend().setEnabled(false);

         barChart.getAxisLeft().setDrawGridLines(false);

         barChart.setPinchZoom(false);
         barChart.setAutoScaleMinMaxEnabled(true);
         barChart.setDrawValueAboveBar(true);
         barChart.setMaxVisibleValueCount(2);
         barChart.setDrawBarShadow(false);
         barChart.setDrawGridBackground(false);

         barChart.setFitBars(true);
         barChart.invalidate();

        }

    }


}
