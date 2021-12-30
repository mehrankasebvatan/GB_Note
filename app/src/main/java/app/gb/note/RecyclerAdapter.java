package app.gb.note;

import android.app.Activity;
import android.content.Context;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import app.gb.note.activities.EditActivity;
import app.gb.note.activities.MainActivity;
import app.gb.note.database.DataBaseHelper;
import app.gb.note.database.DataNote;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.myViewHolder> {

    Context context;
    List<DataNote> dataNotes;
    DataBaseHelper myDb;

    public RecyclerAdapter(Context context, List<DataNote> dataNotes) {
        this.context = context;
        this.dataNotes = dataNotes;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recycler_view, viewGroup, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        DataNote dataNote = dataNotes.get(position);
        holder.title.setText(dataNote.title);
        holder.text.setText(dataNote.text);
        holder.content.setOnClickListener(view -> {

            Intent in = new Intent(context, EditActivity.class);
            in.putExtra("idKey", dataNote.id);
            in.putExtra("titleKey", dataNote.title);
            in.putExtra("textKey", dataNote.text);
            context.startActivity(in);
            ((Activity) context).finish();
        });

        holder.delete.setOnClickListener(view -> {

            myDb = new DataBaseHelper(context);

            AlertDialog alertDialog = new AlertDialog.Builder(view.getContext(), R.style.dialog).create();
            alertDialog.setTitle(dataNote.title);
            alertDialog.getWindow().getDecorView().setLayoutDirection(view.LAYOUT_DIRECTION_RTL);
            alertDialog.setMessage(context.getResources().getString(R.string.delete_message));
            alertDialog.setButton(context.getResources().getString(R.string.delete), (dialogInterface, i) -> {
                String id = dataNote.id;
                myDb.deleteSingleNote(id);
                restartApp();
            });
            alertDialog.show();



        });



    }

    @Override
    public int getItemCount() {
        return dataNotes.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        TextView title, text;
        ImageView delete;
        LinearLayoutCompat content;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.txt_title);
            text = itemView.findViewById(R.id.txt_text);
            delete = itemView.findViewById(R.id.img_delete);
            content = itemView.findViewById(R.id.texts);


        }
    }

    public void restartApp() {
        Intent res = new Intent(context, MainActivity.class);
        context.startActivity(res);
        ((Activity) context).finish();

    }


}
