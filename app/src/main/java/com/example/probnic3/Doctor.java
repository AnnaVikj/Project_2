package com.example.probnic3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;

import com.example.probnic3.data.PatientBase;
import com.example.probnic3.databinding.ActivityMain2Binding;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Geometry;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.search.Response;
import com.yandex.mapkit.search.SearchFactory;
import com.yandex.mapkit.search.SearchManager;
import com.yandex.mapkit.search.SearchManagerType;
import com.yandex.mapkit.search.SearchOptions;
import com.yandex.mapkit.search.Session;
import com.yandex.mapkit.search.ToponymObjectMetadata;
import com.yandex.runtime.Error;

public class Doctor extends AppCompatActivity {
    ActivityMain2Binding binding2;
    private final String MAPKIT_API_KEY = "bfdfc8c7-bb74-4c23-aba9-28b5a05bc800";
    private final Point TARGET_LOCATION = new Point(55.0459, 82.9069);
    private MapView mapView;
    private PatientBase room;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        String street_patient = getIntent().getStringExtra("markup");
        MapKitFactory.setApiKey(MAPKIT_API_KEY);
        MapKitFactory.initialize(this);
        room = Room.databaseBuilder(this, PatientBase.class, "roompatientbase").allowMainThreadQueries().build();
        binding2 = ActivityMain2Binding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding2.getRoot());

        mapView = (MapView) findViewById(R.id.mapview);
        mapView.getMap().move(
                new CameraPosition(TARGET_LOCATION, 14.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 5),
                null);
        Point mappoint2 = new Point(55.045965, 82.906955);
        mapView.getMap().getMapObjects().addPlacemark(mappoint2);

        //ManagerPoint();
        //onMapTap
        /*mapView.getMap().addInputListener(new InputListener() {
           @Override
           public void onMapTap(@NonNull com.yandex.mapkit.map.Map map, @NonNull Point point2) {
               //Toast toast = Toast.makeText(getApplicationContext(), point2.getLatitude() + " " + point2.getLongitude(), Toast.LENGTH_LONG);
               for (int i = 0; i < pointObjectLatitude.size(); i++) {
                   if((pointObjectLatitude.get(i) - 0.0002 <= point2.getLatitude()
                           && point2.getLatitude() <= pointObjectLatitude.get(i) + 0.0002)
                           && (pointObjectLongitude.get(i) - 0.0002 <= point2.getLongitude()
                           && point2.getLongitude() <= pointObjectLongitude.get(i) + 0.0002)){
                       Alert(i);
                   }
               }
               //toast.show();
           }

           @Override
           public void onMapLongTap(@NonNull com.yandex.mapkit.map.Map map, @NonNull Point point) {

           }
       });
        invalidateOptionsMenu();*/
    }
    @Override
    protected void onStop () {
        // Вызов onStop нужно передавать инстансам MapView и MapKit.
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    @Override
    protected void onStart () {
        // Вызов onStart нужно передавать инстансам MapView и MapKit.
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
    }
    public void ManagerPoint(){
        SearchManager searchManager = SearchFactory.getInstance().createSearchManager(SearchManagerType.ONLINE);
        Geometry point = Geometry.fromPoint(new Point(55.045965, 82.906955));
        for (Patient i: room.patientDao().getAll()) {
            searchManager.submit(String.valueOf(i.getStreet()), point, new SearchOptions(), new Session.SearchListener() {
                @Override
                public void onSearchResponse(@NonNull Response response) {
                    Point pointBySearch = response.getCollection()
                            .getChildren().get(0).getObj()
                            .getMetadataContainer().getItem(ToponymObjectMetadata.class)
                            .getBalloonPoint();
                    /*pointObjectLatitude.add(pointBySearch.getLatitude());
                    pointObjectLongitude.add(pointBySearch.getLongitude());*/
                    mapView.getMap().getMapObjects().addPlacemark(pointBySearch);
                }

                @Override
                public void onSearchError(@NonNull Error error) {

                }
            });
        }
    }
    //AlertDialog
    /*public void Alert(int i){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(room.patientDao().getAll().get(i).getStreet());
        builder.setMessage(room.patientDao().getAll().get(i).getDescription() + "\n"
                + room.patientDao().getAll().get(i).getFio() + "\n" + "\n" + "\n"
                + "Принять?");
        builder.setNeutralButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                DeletedPatient(i, room.patientDao().getAll().get(i).getStreet());
            }
        });
        builder.create().show();
    }

    public void DeletedPatient(int i, String street){
        AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
        builder2.setTitle("Удаление точки на карте");
        builder2.setMessage("Хорошо, теперь это ваш пациент" + "\n"
                + "Запомните или запишите улицу (" + street +
                "), так как точка сейчас удалиться и не будет видна на карте");
        builder2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                room.patientDao().delete(room.patientDao().getAll().get(i));
                ManagerPoint();
                dialog.dismiss();
            }
        });
        builder2.create().show();
    }*/
}
