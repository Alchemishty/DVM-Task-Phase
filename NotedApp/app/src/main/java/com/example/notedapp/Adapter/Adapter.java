package com.example.notedapp.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notedapp.AddNewNote;
import com.example.notedapp.MainActivity;
import com.example.notedapp.Model.NotesModel;
import com.example.notedapp.R;
import com.example.notedapp.Utils.DataBaseHelper;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private List<NotesModel> mList;
    private MainActivity activity;
    private DataBaseHelper myDB;

    public Adapter(DataBaseHelper myDB , MainActivity activity){
        this.activity = activity;
        this.myDB = myDB;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_layout, parent , false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final NotesModel item = mList.get(position);
        holder.mCheckBox.setText(item.getNote());
    }

    public Context getContext(){
        return activity;
    }

    public void setNotes(List<NotesModel> mList){
        this.mList = mList;
        notifyDataSetChanged();
    }

    public void deleteNote(int position){
        NotesModel item = mList.get(position);
        myDB.deleteNote(item.getId());
        mList.remove(position);
        notifyItemRemoved(position);
    }

    public void editItem(int position){
        NotesModel item = mList.get(position);

        Bundle bundle = new Bundle();
        bundle.putInt("id" , item.getId());
        bundle.putString("task" , item.getNote());

        AddNewNote note = new AddNewNote();
        note.setArguments(bundle);
        note.show(activity.getSupportFragmentManager() , note.getTag());


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView mCheckBox;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mCheckBox = itemView.findViewById(R.id.mcheckbox);
        }
    }
}