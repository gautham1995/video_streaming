package com.example.videostream;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Showvideo extends AppCompatActivity {

    DatabaseReference databaseReference;
    FirebaseDatabase database;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showvideo);

        recyclerView = findViewById(R.id.recyclerview_ShowVideo);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Video");

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Member> options =
                new FirebaseRecyclerOptions.Builder<Member>()
                .setQuery(databaseReference, Member.class)
                .build();

       FirebaseRecyclerAdapter<Member,ViewHolder> firebaseRecyclerAdapter =
               new FirebaseRecyclerAdapter<Member, ViewHolder>(options) {
                   @Override
                   protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Member model) {
                       holder.setExoplayer(getApplication(),model.getName(),model.getVideourl());

                   }

                   @NonNull
                   @Override
                   public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                       View view = LayoutInflater.from(parent.getContext())
                               .inflate(R.layout.item, parent, false);
                       return new ViewHolder(view);
                   }
               };

       firebaseRecyclerAdapter.startListening();
       recyclerView.setAdapter(firebaseRecyclerAdapter);


    }
}