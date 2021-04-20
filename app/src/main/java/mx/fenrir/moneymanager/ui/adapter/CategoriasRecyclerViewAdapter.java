package mx.fenrir.moneymanager.ui.adapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import mx.fenrir.moneymanager.R;
import mx.fenrir.moneymanager.db.MoneyManagerViewModel;
import mx.fenrir.moneymanager.db.entity.TipoEntity;
import mx.fenrir.moneymanager.ui.fragments.EditarCategoriasFragment;


public class CategoriasRecyclerViewAdapter extends RecyclerView.Adapter<CategoriasRecyclerViewAdapter.ViewHolder> {

    private List<TipoEntity> mValues;
    private Context ctx;
    private MoneyManagerViewModel moneyManagerViewModel;

    public CategoriasRecyclerViewAdapter(List<TipoEntity> items, Context context) {
        mValues = items;
        ctx = context;
        moneyManagerViewModel= ViewModelProviders.of((AppCompatActivity)ctx).get(MoneyManagerViewModel.class);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_categorias, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.tvtipo.setText(holder.mItem.getNombreTipo());
        holder.ivEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditarCategoriasFragment dialogFragment=new EditarCategoriasFragment(holder.mItem);
                dialogFragment.show(((AppCompatActivity)ctx).getSupportFragmentManager(), "EditarCategoriasFragment");

            }
        });
        holder.ivEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle(R.string.mensaje_eliminar_categoria);
                builder.setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        moneyManagerViewModel.eliminarTipo(holder.mItem.getId());
                        Toast.makeText(ctx,ctx.getText(R.string.mov_eliminar_confirmacion_categoria),Toast.LENGTH_LONG).show();

                    }
                });
                builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();


            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void setNuevasCategorias(List<TipoEntity> tipoEntities) {

        this.mValues=tipoEntities;
        notifyDataSetChanged();

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tvtipo;
        public final ImageView ivEliminar;
        public final ImageView ivEditar;


        public TipoEntity mItem;

        public ViewHolder(View view) {

            super(view);
            mView = view;
            tvtipo =view.findViewById(R.id.tvTipoCF);
            ivEliminar=view.findViewById(R.id.imageViewEliminar);
            ivEditar=view.findViewById(R.id.imageViewEditar);

        }

    }
}
