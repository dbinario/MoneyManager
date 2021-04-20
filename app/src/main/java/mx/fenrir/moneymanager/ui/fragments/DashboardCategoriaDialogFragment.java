package mx.fenrir.moneymanager.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import mx.fenrir.moneymanager.R;

public class DashboardCategoriaDialogFragment extends DialogFragment implements View.OnClickListener {


    ImageView ivClose;
    Spinner spSelTipo;
    int tipo='0';
    Button ButtonNuevaCategoria;

    public DashboardCategoriaDialogFragment(){

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view=inflater.inflate(R.layout.categoria_dialog,container,false);

        ivClose=view.findViewById(R.id.iVcloseedit);
        ivClose.setOnClickListener(this);
        spSelTipo=view.findViewById(R.id.spSelTipo);
        ButtonNuevaCategoria =view.findViewById(R.id.buttonNuevaCategoria);
        ButtonNuevaCategoria.setOnClickListener(this);

        String [] mov={getString(R.string.ingreso),getString(R.string.egreso)};
        ArrayAdapter<String> adapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,mov);

        spSelTipo.setAdapter(adapter);


        cargarFragment();

        spSelTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selspMov=spSelTipo.getSelectedItem().toString();

                if(selspMov.equals(getString(R.string.ingreso))){

                tipo=0;

                }else if(selspMov.equals(getString(R.string.egreso))){

                tipo=1;

                }

                cargarFragment();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        return view;
    }

    private void cargarFragment() {


        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.contenedorCategorias, categoriasFragment.newInstance(tipo))
                .commit();


    }

    @Override
    public void onClick(View v) {


        switch (v.getId()){

            case R.id.iVcloseedit:

                getDialog().dismiss();

                break;

            case R.id.buttonNuevaCategoria:

                EditarCategoriasFragment dialogFragment=new EditarCategoriasFragment();
                dialogFragment.show(getChildFragmentManager(), "EditarCategoriasFragment");

                break;



        }

    }
}
