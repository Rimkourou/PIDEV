package entitie.hazem.share;

import com.teamdev.jxmaps.*;
import com.teamdev.jxmaps.swing.MapView;

import javax.swing.*;
import java.awt.*;

public class Mapa extends MapView {
    private Map map;
    private double lat;
    private double lon;
    public Mapa (String nName, double lat, double lon){
        JFrame frame = new JFrame(nName);
        setOnMapReadyHandler(new MapReadyHandler() {
            @Override
            public void onMapReady(MapStatus status) {
                if (status == MapStatus.MAP_STATUS_OK) {
                    map = getMap();
                    MapOptions mapOptions = new MapOptions();
                    MapTypeControlOptions controlOptions = new MapTypeControlOptions();
                    mapOptions.setMapTypeControlOptions(controlOptions);
                    map.setOptions(mapOptions);

                    map.setCenter(new LatLng(lon, lat));
                    map.setZoom(13.0);
                    Marker marker = new Marker(map);
                    marker.setPosition(map.getCenter());

                    Circle circle = new Circle(map);
                    circle.setCenter(map.getCenter());
                    circle.setRadius(400);

                    CircleOptions circleOptions = new CircleOptions();
                    circleOptions.setFillColor("#FF0");
                    circleOptions.setFillOpacity(0.35);
                    circle.setOptions(circleOptions);

                }
            }
        });
        frame.add(this, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setVisible(true);
    }
}
