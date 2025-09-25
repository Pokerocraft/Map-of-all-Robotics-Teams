package org.example;
//Yeah, for whatever reason, jxmapviewer2 doesn't have any documentation in code, so you'll have to likely check
// https://javadoc.io/doc/org.jxmapviewer/jxmapviewer2/latest/index.html
import org.jxmapviewer.*;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.painter.AbstractPainter;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;

import com.google.gson.*;

/**
 * The primary map viewer, called Main due to this being the main class
 */
@SuppressWarnings("ALL")
public class Main {
    static JXMapViewer mapViewer = new JXMapViewer();
    static Set<ButtonWaypoint> buttonWaypoints = new HashSet<>();
    static ButtonWaypointRenderer renderer = new ButtonWaypointRenderer(mapViewer);

    public static void main(String[] args) throws FileNotFoundException, URISyntaxException {

        TileFactoryInfo info = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP);
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        mapViewer.setTileFactory(tileFactory);
        Color deepGreen = new Color(0, 105, 30);
        URI uri = new URI("https://www.thebluealliance.com/");
        Desktop desktop = Desktop.getDesktop();

        mapViewer.setLayout(null);
        //Centers
        GeoPosition holtMichiganUsa = new GeoPosition(42.640176, -84.523455);
        GeoPosition masonMichiganUsa = new GeoPosition(42.579713, -84.442283);
        GeoPosition masonMichiganUsa2 = new GeoPosition(42.578682, -84.442278);
        GeoPosition newportMichiganUsa = new GeoPosition(42.0022672, -83.3085419);

        //Multitudes of ButtonWaypoints
        ButtonWaypoint holtMichiganUsaButton = new ButtonWaypoint("RoboRams", holtMichiganUsa, 6078);
        ButtonWaypoint masonMichiganUsaButton = new ButtonWaypoint("Tractor Technicians", masonMichiganUsa, 3655);
        ButtonWaypoint masonMichiganUsaButton2 = new ButtonWaypoint("Tractor Technicians Next Gen", masonMichiganUsa2, 8424);
        ButtonWaypoint newportMichiganUsaButton = new ButtonWaypoint("TEMPEST", newportMichiganUsa, 240);

        //This'll be even longer
        buttonWaypoints = Set.of(holtMichiganUsaButton, masonMichiganUsaButton, masonMichiganUsaButton2, newportMichiganUsaButton);

        renderer.setWaypoints(buttonWaypoints);

        List<List<GeoPosition>> michiganUsaCitiesPolygonPoints = processGeoJsonFolder("src/main/geojsons/USA/Michigan");
        System.out.println(michiganUsaCitiesPolygonPoints.size());
        List<PolygonalPainter> painters = createNewPainter(michiganUsaCitiesPolygonPoints);
        System.out.println(painters.size());


        //Creates a Compound Painter that utilizes JXMapViewer
        CompoundPainter<JXMapViewer> compoundPainter = createCompoundPainter(painters, renderer);
        mapViewer.setOverlayPainter(compoundPainter);

        JTextPane textPane = new JTextPane();
        textPane.setText("It appears that AbstractTileFactory is experiencing some sort of bug, so the default until that is fixed is Virtual Earth instead of Open Street Map");
        textPane.setEditable(false);

        //Options for the map
        String[] choices = {"Virtual Earth", "Open Street Map (Bugged)", "Hybrid Between Virtual Earth and Satellite", "Satellite",};
        JComboBox comboBox = new JComboBox(choices);
        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TileFactoryInfo info;
                int index = comboBox.getSelectedIndex();
                if (index == 0) {
                    info = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP);
                } else if (index == 1) {

                    info = new OSMTileFactoryInfo();
                } else if (index == 2) {
                    info = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.HYBRID);
                } else {
                    info = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.SATELLITE);
                }
                DefaultTileFactory tileFactory = new DefaultTileFactory(info);
                mapViewer.setTileFactory(tileFactory);
                tileFactory.setThreadPoolSize(8);
            }
        });
        JButton linkedButton = new JButton("Go to The Blue Alliance");
        linkedButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    desktop.browse(uri);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


        GeoPosition wilsonTalentCenter = new GeoPosition(42.640123, -84.523664);
        //Setting the Zoom and address to WTC
        mapViewer.setZoom(5);
        mapViewer.setAddressLocation(wilsonTalentCenter);
        //Frame
        JFrame frame = new JFrame("A Map");
        frame.getContentPane().add(mapViewer, BorderLayout.CENTER);
        frame.getContentPane().add(comboBox, BorderLayout.NORTH);
        frame.getContentPane().add(textPane, BorderLayout.SOUTH);
        frame.getContentPane().add(linkedButton, BorderLayout.EAST);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        //Mouse Input for making the map work
        MouseInputListener mouseListener = new PanMouseInputListener(mapViewer);
        mapViewer.addMouseListener(mouseListener);
        mapViewer.addMouseMotionListener(mouseListener);
        mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCenter(mapViewer));
    }


    /**
     * Parses the GeoJSON files so that the map highlights the location, make sure to make these [[[[ ]]]] into these [[ ]]
     *
     * @param file the location of the file, primarily src/main/geojsons/cityState.geojson
     * @throws FileNotFoundException In the event there is no file there
     */
    static List<GeoPosition> parseGeoJsonFile(File file) throws FileNotFoundException {
        ArrayList<GeoPosition> list = new ArrayList<GeoPosition>();
        Scanner scanner = new Scanner(file);
        String geoJsonString = scanner.nextLine();
        Gson gson = new Gson();
        try {
            GeoJSON geoJSON = gson.fromJson(geoJsonString, GeoJSON.class);
            if (geoJSON != null) {
                List<List<Double>> coordinates = geoJSON.getCoordinates();
                //System.out.println("Coordinates");
                for (List<Double> coordinate : coordinates) {
                    //for (Double place:  coordinate) {
                    list.add(new GeoPosition(coordinate.get(1), coordinate.get(0)));
                    //System.out.println("Longitude: " + coordinate.get(0) + " Latitude: " + coordinate.get(1));
                    // }
                }
            }
        } catch (Exception e) {
            System.err.println("Welp, you likely forgot that the code has to have 2 of these [] not 4 at the beginning and end");
            System.exit(-1);
        }
        return list;
    }

    /**
     * Processes an entire folder worth of GeoJSON files
     * @param directoryPath The place where the folder where your files are found
     * @return Returns a List of Lists, with each list holding {@link GeoPosition GeoPositions}
     */
    static List<List<GeoPosition>> processGeoJsonFolder(String directoryPath) {
        Path startPath = Paths.get(directoryPath);
        List<List<GeoPosition>> list = new ArrayList<>();

        try (Stream<Path> paths = Files.walk(startPath)) {
            paths.filter(Files::isRegularFile).filter(path -> path.toString().endsWith(".geojson")).forEach(path -> {
                try {
                    List<GeoPosition> geopositions = parseGeoJsonFile(path.toFile());
                    list.add(geopositions);
                } catch (FileNotFoundException e) {
                    System.err.println("Oh no 😢");
                }
            });
        } catch (IOException e){
            System.err.println("Whoops, an error 😢");
        }

        return list;
    }

    /**
     * Makes a List of Painters
     * @param locations Pretty much like, the state + the country you live
     * @return Returns the list of painters
     */
    static List<PolygonalPainter> createNewPainter(List<List<GeoPosition>> locations) {
        List<PolygonalPainter> painters = new ArrayList<>();
        for (List<GeoPosition> location : locations) {
            PolygonalPainter painter = new PolygonalPainter(
                    location, Color.red, Color.red
            );
            painters.add(painter);
        }
        return painters;
    }

    static CompoundPainter<JXMapViewer> createCompoundPainter(List<PolygonalPainter> polygonPainters, ButtonWaypointRenderer renderer) {
        List<AbstractPainter<JXMapViewer>> painters = new ArrayList<>();
        painters.addAll(polygonPainters);
        painters.add(renderer);
        return new CompoundPainter<JXMapViewer>(painters);
    }
}