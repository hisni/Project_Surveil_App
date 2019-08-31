package com.aslam.co321_project;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class graph extends AppCompatActivity {


    FirebaseDatabase database;
    DatabaseReference reference;
    GraphView graphView;
    LineGraphSeries series;

//    @Override
    protected void onDRAW() {
//        super.onStart();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataPoint [] dp =new DataPoint[(int)dataSnapshot.getChildrenCount()];
                int index=0;

                for(DataSnapshot myDataSnapshot: dataSnapshot.getChildren()){
                    GraphPoints graphPoints = myDataSnapshot.getValue(GraphPoints.class);

                    dp[index] = new DataPoint(graphPoints.getPressure(), graphPoints.getPressure());
                    index++;
                }
                for(int i=0; i<dp.length; i++){
                    Toast.makeText(graph.this, dp[i].toString(), Toast.LENGTH_SHORT).show();
                }
                series.resetData(dp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        graphView = findViewById(R.id.graph);
        series = new LineGraphSeries();
        graphView.addSeries(series);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Readings").child("rn1001").child("en1001");
        onDRAW();
    }
}