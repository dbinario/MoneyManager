package mx.fenrir.moneymanager.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import mx.fenrir.moneymanager.R;
import mx.fenrir.moneymanager.db.MoneyManagerViewModel;
import mx.fenrir.moneymanager.db.entity.TipoEntity;


public class EditarCategoriasFragment extends DialogFragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private MoneyManagerViewModel moneyManagerViewModel;

    private TipoEntity tipoEntity;

    ImageView ivClose;
    Button buttonGuardar;
    EditText ettitulo;
    RadioGroup radioGroup;


    public EditarCategoriasFragment() {
        // Required empty public constructor
    }


    public EditarCategoriasFragment(TipoEntity tipoEntity) {

        this.tipoEntity=tipoEntity;

    }

    public static EditarCategoriasFragment newInstance(String param1, String param2) {
        EditarCategoriasFragment fragment = new EditarCategoriasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle);

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_editar_cat, container, false);

        moneyManagerViewModel= ViewModelProviders.of(getActivity()).get(MoneyManagerViewModel.class);


        ivClose=view.findViewById(R.id.iVCloseEditCat);
        ivClose.setOnClickListener(this);

        buttonGuardar=view.findViewById(R.id.buttonGuardarEditCat);
        buttonGuardar.setOnClickListener(this);
        ettitulo=view.findViewById(R.id.editTextTituloEdit);

        radioGroup=view.findViewById(R.id.radioGroupEdit);


        if(tipoEntity!=null){

            setTipo();

        }


        return view;
    }

    private void setTipo() {


        buttonGuardar.setText(getText(R.string.buttonActualizarMov));
        ettitulo.setText(tipoEntity.getNombreTipo());

        radioGroup.clearCheck();

        if(tipoEntity.getTipo()==1){

            radioGroup.check(R.id.radioButtonEgreso);

        }else{

            radioGroup.check(R.id.radioButtonIngreso);

        }



    }


    @Override
    public void onClick(View v) {


        switch (v.getId()){

            case R.id.iVCloseEditCat:

                getDialog().dismiss();

                break;

            case R.id.buttonGuardarEditCat:



                String titulo=ettitulo.getText().toString().trim();
                int tipo=0;

                switch (radioGroup.getCheckedRadioButtonId()){
                    case R.id.radioButtonIngreso:
                        tipo=0;
                        break;
                    case R.id.radioButtonEgreso:
                        tipo=1;
                        break;
                }


                if(!titulo.isEmpty()){


                    if(tipoEntity==null){

                        moneyManagerViewModel.insertarTipoMovimiento(new TipoEntity(tipo,titulo));
                        Toast.makeText(getActivity(),getString(R.string.categoriaNueva),Toast.LENGTH_SHORT).show();

                    }else{

                        moneyManagerViewModel.actualizarTipo(tipoEntity.getId(),tipo,titulo);
                        Toast.makeText(getActivity(),getString(R.string.categoriaActualizada),Toast.LENGTH_SHORT).show();

                    }


                    getDialog().dismiss();


                }else{


                    Toast.makeText(getActivity(),getString(R.string.tituloVacio),Toast.LENGTH_SHORT).show();


                }


                break;



        }


    }
}
