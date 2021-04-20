package mx.fenrir.moneymanager.ui.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import mx.fenrir.moneymanager.R;
import mx.fenrir.moneymanager.db.MoneyManagerViewModel;
import mx.fenrir.moneymanager.db.entity.MovimientosEntity;
import mx.fenrir.moneymanager.ui.adapter.HeaderItem;
import mx.fenrir.moneymanager.ui.adapter.Listitem;
import mx.fenrir.moneymanager.ui.adapter.MovimientosItem;
import mx.fenrir.moneymanager.ui.adapter.MyMovimientosRecyclerViewAdapter;
import mx.fenrir.moneymanager.utils.Utils;


public class MovimientosFragment extends Fragment {

    private static final String ARG_FEC_INI = "FEC1";
    private static final String ARG_FEC_FIN = "FEC2";

    private MoneyManagerViewModel moneyManagerViewModel;
    private MyMovimientosRecyclerViewAdapter adapter;

    private List<Listitem> items=new ArrayList<>();

    private Date fecIniMes,fecFinMes;



    public MovimientosFragment() {
    }

    public static MovimientosFragment newInstance(Date fecIniMes,Date fecFinMes) {
        MovimientosFragment fragment = new MovimientosFragment();

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movimientos_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            adapter=new MyMovimientosRecyclerViewAdapter(items,getActivity());
            recyclerView.setAdapter(adapter);
            lanzarViewModel();
        }
        return view;
    }

    private void lanzarViewModel() {

        moneyManagerViewModel= ViewModelProviders.of(getActivity()).get(MoneyManagerViewModel.class);
        moneyManagerViewModel.obtenetMovimientosMes(fecIniMes,fecFinMes).observe(getActivity(), new Observer<List<MovimientosEntity>>() {
            @Override
            public void onChanged(List<MovimientosEntity> movimientosEntities) {

                items=new ArrayList<>();

                Map<Date, List<MovimientosEntity>> events = toMap(movimientosEntities);

                for (Date date : events.keySet()) {
                    HeaderItem header = new HeaderItem(date);
                    items.add(header);
                    for (MovimientosEntity movimientosEntity : events.get(date)) {
                        MovimientosItem item = new MovimientosItem(movimientosEntity);
                        items.add(item);
                    }
                }
                //enviamos los items para segmentar
                adapter.setNuevosMovimientosItems(items);


            }
        });


    }

    private Map<Date, List<MovimientosEntity>> toMap(List<MovimientosEntity> movimientosEntities) {


        Map<Date, List<MovimientosEntity>> map = new TreeMap<>(java.util.Collections.reverseOrder());

        for (MovimientosEntity movimientosEntity : movimientosEntities) {

            List<MovimientosEntity> value = map.get(Utils.FechaInicioDia(movimientosEntity.getFecha()));

            if (value == null) {
                value = new ArrayList<>();
                map.put(Utils.FechaInicioDia(movimientosEntity.getFecha()), value);
            }

            value.add(movimientosEntity);

        }

        return map;
    }

}
