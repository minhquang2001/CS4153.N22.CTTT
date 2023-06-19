package com.minhquang.sensor.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;



import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.minhquang.sensor.R;

import java.util.ArrayList;
import java.util.List;

public class LinearFragment extends Fragment {

    private DatabaseReference databaseRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_linear, container, false);
        // Perform any additional setup or view initialization here

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseRef = database.getReference();

        // ScatterChart for Training Data
        ScatterChart trainingChart = view.findViewById(R.id.TrainingChart);
        retrieveAndPlotScatterData(databaseRef.child("x_train"), databaseRef.child("y_train"), trainingChart, Color.BLUE, "Dữ liệu huấn luyện");

        // ScatterChart for Testing Data
        ScatterChart testingChart = view.findViewById(R.id.TestingChart);
        retrieveAndPlotScatterData(databaseRef.child("x_test"), databaseRef.child("y_test"), testingChart, Color.RED, "Dữ liệu kiểm tra");

        // LineChart for Linear Regression
        LineChart linearChart = view.findViewById(R.id.LinearChart);
        retrieveAndPlotLineData(databaseRef.child("x_train"), databaseRef.child("y_train"), linearChart, Color.GREEN, "Linear Regression");

        // CombinedChart for Data Visualization and Linearity
        CombinedChart combinedChart = view.findViewById(R.id.DataChart);
        retrieveAndPlotCombinedData(databaseRef.child("x_train"), databaseRef.child("y_train"), databaseRef.child("x_test"), databaseRef.child("y_test"), combinedChart);
        return view;
    }

    private void retrieveAndPlotCombinedData(DatabaseReference xTrainRef, DatabaseReference yTrainRef, DatabaseReference xTestRef, DatabaseReference yTestRef, final CombinedChart chart) {
        xTrainRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Entry> trainingEntries = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String xKey = snapshot.getKey();
                    double x = Double.parseDouble(xKey.replace(",", ".")); // Replace comma with dot

                    double y = 0.0;
                    Object value = snapshot.getValue();
                    if (value instanceof Double) {
                        y = (Double) value;
                    } else if (value instanceof Long) {
                        y = ((Long) value).doubleValue();
                    } else if (value instanceof String) {
                        try {
                            y = Double.parseDouble((String) value);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }

                    trainingEntries.add(new Entry((float) x, (float) y));
                }

                ScatterDataSet trainingDataSet = new ScatterDataSet(trainingEntries, "Dữ liệu huấn luyện");
                trainingDataSet.setColor(Color.BLUE);

                xTestRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<Entry> testingEntries = new ArrayList<>();

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String xKey = snapshot.getKey();
                            double x = Double.parseDouble(xKey.replace(",", ".")); // Replace comma with dot

                            double y = 0.0;
                            Object value = snapshot.getValue();
                            if (value instanceof Double) {
                                y = (Double) value;
                            } else if (value instanceof Long) {
                                y = ((Long) value).doubleValue();
                            } else if (value instanceof String) {
                                try {
                                    y = Double.parseDouble((String) value);
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }
                            }

                            testingEntries.add(new Entry((float) x, (float) y));
                        }

                        ScatterDataSet testingDataSet = new ScatterDataSet(testingEntries, "Dữ liệu kiểm tra");
                        testingDataSet.setColor(Color.RED);

                        List<IScatterDataSet> scatterDataSets = new ArrayList<>();
                        scatterDataSets.add(trainingDataSet);
                        scatterDataSets.add(testingDataSet);

                        ScatterData scatterData = new ScatterData(scatterDataSets);

                        CombinedData combinedData = new CombinedData();
                        combinedData.setData(scatterData);

                        chart.setData(combinedData);
                        chart.invalidate();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle onCancelled event if needed
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle onCancelled event if needed
            }
        });
    }

    private void retrieveAndPlotScatterData(DatabaseReference xRef, DatabaseReference yRef, final ScatterChart chart, final int color, final String label) {
        xRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Entry> entries = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String xKey = snapshot.getKey();
                    double x = Double.parseDouble(xKey.replace(",", ".")); // Replace comma with dot

                    double y = 0.0;
                    Object value = snapshot.getValue();
                    if (value instanceof Double) {
                        y = (Double) value;
                    } else if (value instanceof Long) {
                        y = ((Long) value).doubleValue();
                    } else if (value instanceof String) {
                        try {
                            y = Double.parseDouble((String) value);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }

                    entries.add(new Entry((float) x, (float) y));
                }

                ScatterDataSet dataSet = new ScatterDataSet(entries, label);
                dataSet.setColor(color);

                ScatterData scatterData = new ScatterData(dataSet);

                chart.setData(scatterData);
                chart.invalidate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle onCancelled event if needed
            }
        });
    }

    private void retrieveAndPlotLineData(DatabaseReference xRef, DatabaseReference yRef, final LineChart chart, final int color, final String label) {
        xRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Entry> entries = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String xKey = snapshot.getKey();
                    double x = Double.parseDouble(xKey.replace(",", ".")); // Replace comma with dot

                    double y = 0.0;
                    Object value = snapshot.getValue();
                    if (value instanceof Double) {
                        y = (Double) value;
                    } else if (value instanceof Long) {
                        y = ((Long) value).doubleValue();
                    } else if (value instanceof String) {
                        try {
                            y = Double.parseDouble((String) value);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }

                    entries.add(new Entry((float) x, (float) y));
                }

                LineDataSet dataSet = new LineDataSet(entries, label);
                dataSet.setColor(color);

                List<ILineDataSet> lineDataSets = new ArrayList<>();
                lineDataSets.add(dataSet);

                LineData lineData = new LineData(lineDataSets);

                chart.setData(lineData);
                chart.invalidate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle onCancelled event if needed
            }
        });
    }
    private DatabaseReference databaseReference;

    public void FirebaseChartExample() {
        // Initialize Firebase
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void retrieveAndPlotData(final ScatterChart chart) {
        final List<Entry> trainingEntries = new ArrayList<>();
        final List<Entry> testingEntries = new ArrayList<>();

        // Retrieve x_train data
        databaseReference.child("x_train").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String xKey = snapshot.getKey();
                    double x = Double.parseDouble(xKey.replace(",", "."));

                    double y = snapshot.getValue(Double.class);

                    trainingEntries.add(new Entry((float) x, (float) y));
                }

                ScatterDataSet trainingDataSet = new ScatterDataSet(trainingEntries, "Training Data");
                trainingDataSet.setColor(Color.BLUE);

                // Retrieve x_test data
                databaseReference.child("x_test").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String xKey = snapshot.getKey();
                            double x = Double.parseDouble(xKey.replace(",", "."));

                            double y = snapshot.getValue(Double.class);

                            testingEntries.add(new Entry((float) x, (float) y));
                        }

                        ScatterDataSet testingDataSet = new ScatterDataSet(testingEntries, "Testing Data");
                        testingDataSet.setColor(Color.RED);

                        List<ScatterDataSet> scatterDataSets = new ArrayList<>();
                        scatterDataSets.add(trainingDataSet);
                        scatterDataSets.add(testingDataSet);

                        ScatterData scatterData = new ScatterData((IScatterDataSet) scatterDataSets);

                        chart.setData(scatterData);
                        chart.invalidate();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle onCancelled event if needed
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle onCancelled event if needed
            }
        });
    }

    public void configureChart(ScatterChart chart) {
        // Configure chart settings
        chart.getDescription().setEnabled(false);
        chart.setDrawGridBackground(false);
        chart.setDrawBorders(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter());

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setAxisMinimum(0f);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);
    }

    public static void main(String[] args) {
        FirebaseChartExample example = new FirebaseChartExample();

        ScatterChart chart = new ScatterChart(null); // Replace null with your chart instance

        example.configureChart(chart);
        example.retrieveAndPlotData(chart);
    }
}
