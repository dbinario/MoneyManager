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
import java.util.List;

import mx.fenrir.moneymanager.ui.adapter.CategoriasRecyclerViewAdapter;
import mx.fenrir.moneymanager.R;
import mx.fenrir.moneymanager.db.MoneyManagerViewModel;
import mx.fenrir.moneymanager.db.entity.TipoEntity;

public class categoriasFragment extends Fragment {


    private static final String ARG_TIPO = "tipo";
    private int tipoF;
    private CategoriasRecyclerViewAdapter adapter;
    private List<TipoEntity> items=new ArrayList<>();

    private MoneyManagerViewModel moneyManagerViewModel;


    public categoriasFragment() {
    }

    public static categoriasFragment newInstance(int tipo) {
        categoriasFragment fragment = new categoriasFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TIPO, tipo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            tipoF = getArguments().getInt(ARG_TIPO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categorias_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            adapter=new CategoriasRecyclerViewAdapter(items,getActivity());
            recyclerView.setAdapter(adapter);
            lanzarViewModel();
        }
        return view;
    }

    private void lanzarViewModel() {

        moneyManagerViewModel= ViewModelProviders.of(getActivity()).get(MoneyManagerViewModel.class);
        moneyManagerViewModel.obtenerListaTipos(tipoF).observe(getActivity(), new Observer<List<TipoEntity>>() {
            @Override
            public void onChanged(List<TipoEntity> tipoEntities) {


                adapter.setNuevasCategorias(tipoEntities);


            }
        });

    }

}
