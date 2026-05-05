package org.example;
/*Yeah, for whatever reason, jxmapviewer2 doesn't have any documentation in code, so you'll have to likely check
https://javadoc.io/doc/org.jxmapviewer/jxmapviewer2/latest/index.html for any documentation with jxmapviewer2's code*/
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
import java.beans.PropertyChangeListener;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.gson.*;

/**
 * The primary map viewer, called Main due to this being the main class
 */
@SuppressWarnings("ALL")
public class Main {
    static JXMapViewer mapViewer = new JXMapViewer();
    //static Set<ButtonWaypoint> buttonWaypoints = new HashSet<>();
    static ButtonWaypointRenderer renderer = new ButtonWaypointRenderer(mapViewer);
    static ButtonWaypointRendererAlt rendererAlt = new ButtonWaypointRendererAlt(mapViewer);
    static Set<ButtonWaypoint> buttonWaypointstemp=new HashSet<>();
    /**
     * The Primary thing making this run
     *
     * @param args
     * @throws FileNotFoundException
     * @throws URISyntaxException
     */
    public static void main(String[] args) throws FileNotFoundException, URISyntaxException {

        TileFactoryInfo info = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP);
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        mapViewer.setTileFactory(tileFactory);
//        Color deepGreen = new Color(0, 105, 30);
//        URI uri = new URI("https://www.thebluealliance.com/");
//        Desktop desktop = Desktop.getDesktop();

        mapViewer.setLayout(null);
        //Centers
        List<List<ButtonWaypoint>> buttonsList = processfrcDataFolder();
        Set<ButtonWaypoint> buttonWaypoints;
        ButtonWaypointAlt eventWaypoint = parseEJsonFile( new File("src/main/specialPlaces/worlds.ejson"));
        buttonWaypoints = buttonsList.stream().flatMap(List::stream).collect(Collectors.toSet());

        Set<ButtonWaypointAlt> eventWaypoints = Collections.singleton(eventWaypoint);
        rendererAlt.setWaypoints(eventWaypoints);


        //Cities and Painters
        Map<String, List<GeoPosition>> cityPolygonPoints = new HashMap<>();
        List<PolygonalPainter> painters = new ArrayList<>();

        cityPolygonPoints = processGeojsonsFolder("src/main/geojsons");
        painters = createNewPainters(cityPolygonPoints);

        //Wrap the painters in a list, for simplicity sake
        List<List<PolygonalPainter>> nestedPainers = new ArrayList<>();
        nestedPainers.add(painters);

        //Creates a Compound Painter that utilizes JXMapViewer
        CompoundPainter<JXMapViewer> compoundPainter = createCompoundPainter(nestedPainers);
        compoundPainter.addPainter(renderer);
        compoundPainter.addPainter(rendererAlt);

        mapViewer.setOverlayPainter(compoundPainter);
        //Text Pane at the bottom of the screen
        JTextPane textPane = new JTextPane();
        textPane.setText("Zoom: " + mapViewer.getZoom());
        textPane.setEditable(false);

        //Options for the map
        String[] choices = {"Virtual Earth", "Hybrid Between Virtual Earth and Satellite", "Satellite",};
        JComboBox comboBox = new JComboBox(choices);
        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TileFactoryInfo info;
                int index = comboBox.getSelectedIndex();
                if (index == 0) {
                    info = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP);
                } else if (index == 1) {
                    info = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.HYBRID);
                } else {
                    info = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.SATELLITE);
                }
                DefaultTileFactory tileFactory = new DefaultTileFactory(info);
                mapViewer.setTileFactory(tileFactory);
                tileFactory.setThreadPoolSize(8);
            }
        });

        JTextField searchField = new JTextField(20);
        searchField.setToolTipText("Enter Team Number or City Name");

        JPanel headerPanel = new JPanel(new  BorderLayout());
        JPanel searchSection = new  JPanel(new FlowLayout(FlowLayout.LEFT));
        searchSection.add(new JLabel("Search:  "));
        searchSection.add(searchField);
        JPanel settingsSection = new   JPanel(new FlowLayout(FlowLayout.RIGHT));
        settingsSection.add(new JLabel("View:  "));
        settingsSection.add(comboBox);
        headerPanel.add(searchSection, BorderLayout.WEST);
        headerPanel.add(settingsSection, BorderLayout.EAST);

        GeoPosition someSpace = new GeoPosition(0, 0);
        //Setting the Zoom and address to WTC
        mapViewer.setZoom(17);
        textPane.setText("Zoom: " + mapViewer.getZoom());
        mapViewer.setAddressLocation(someSpace);
        //Frame
        JFrame frame = new JFrame("A Map");
        frame.getContentPane().add(mapViewer, BorderLayout.CENTER);
        frame.getContentPane().add(headerPanel, BorderLayout.NORTH);
        frame.getContentPane().add(textPane, BorderLayout.SOUTH);
//        frame.getContentPane().add(linkedButton, BorderLayout.EAST);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        //Mouse Input for making the map work
        MouseInputListener mouseListener = new PanMouseInputListener(mapViewer);
        mapViewer.addMouseListener(mouseListener);
        mapViewer.addMouseMotionListener(mouseListener);
        mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCenter(mapViewer));
        PropertyChangeListener zoomListener = evt -> {
            if ("zoom".equals(evt.getPropertyName())) {
                int newZoom = (Integer) evt.getNewValue();
                textPane.setText("Zoom: " + newZoom);

                boolean shouldShow = (newZoom <= 8);
                for (ButtonWaypoint wp : buttonWaypoints){
                    JButton btn = wp.getButton();
                    if (btn != null) {
                        btn.setVisible(shouldShow);
                    }
                }
            }
            if (mapViewer.getZoom() >= 9) {
                compoundPainter.removePainter(renderer);
                renderer.setWaypoints(buttonWaypointstemp);
                compoundPainter.addPainter(renderer);
                mapViewer.repaint();
            } else {
                compoundPainter.removePainter(renderer);
                renderer.setWaypoints(buttonWaypoints);
                compoundPainter.addPainter(renderer);
                mapViewer.repaint();
            }
        };
        mapViewer.addPropertyChangeListener(zoomListener);
    }


    /**
     * Parses the GeoJSON files so that the map highlights the location, make sure to make these [[[[ ]]]] into these [[ ]]
     *
     * @param file the location of the file, primarily src/main/geojsons/cityState.geojson
     * @throws FileNotFoundException In the event there is no file there
     */
    static List<GeoPosition> parseGeoJsonFile(File file) throws FileNotFoundException {
        ArrayList<GeoPosition> list = new ArrayList<>();
        String content = new Scanner(file).useDelimiter("\\Z").next();
        JsonObject json = new JsonParser().parse(content).getAsJsonObject();
        try {
            JsonArray allCoordinates = json.get("coordinates").getAsJsonArray();
            processNestedCoordinates(allCoordinates, list);
        } catch (Exception e) {
            System.err.println("Skipped " + file.getName()  + ": " + e.getMessage());
        }
        return list;
    }

    /**
     * Helper method to try flattening stuff
     * @param array
     * @param list
     */
    private static void processNestedCoordinates(JsonArray array, List<GeoPosition> list) {
        for (JsonElement element : array) {
            if (element.isJsonArray()) {
                JsonArray subArray = element.getAsJsonArray();
                if (subArray.size() == 2 && subArray.get(0).isJsonPrimitive()) {
                    list.add(new GeoPosition(subArray.get(1).getAsDouble(), subArray.get(0).getAsDouble()));
                } else {
                    processNestedCoordinates(subArray, list);
                }
            }
        }
    }

    /**
     * Helper method for {@link #parseGeoJsonFile} to effectively peel the brakcets until we reach the actual coordinates
     * @param array Effectively a json array
     * @return
     */
    private static JsonArray findDeepestArray(JsonArray array) {
        if (array.size() > 0 && array.get(0).isJsonArray()) {
            if (array.get(0).getAsJsonArray().size() > 0 &&
                    array.get(0).getAsJsonArray().get(0).isJsonPrimitive()) {
                return  array;
            }
            return  findDeepestArray(array.get(0).getAsJsonArray());
        }
        return array;
    }


    /**
     * Processes an entire folder worth of GeoJSON files
     *
     * @param directoryPath The place where the folder where your files are found
     * @return Returns a List of Lists, with each list holding {@link GeoPosition GeoPositions}
     */
    static Map<String, List<GeoPosition>> processGeoJsonFolder(String directoryPath) {
        Path startPath = Paths.get(directoryPath);
        Map<String, List<GeoPosition>> map = new HashMap<>();
        try (Stream<Path> paths = Files.walk(startPath)) {
            paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".geojson"))
                    .forEach(path -> {
                        try {
                            List<GeoPosition> geoPositions = parseGeoJsonFile(path.toFile());
                            String name = path.getFileName().toString().replace(".geojson", "");
                            map.put(name, geoPositions);
                        } catch (FileNotFoundException e) {
                            System.err.println("Oh... the file wasn't found...");
                        }
                    });
        } catch (IOException e) {
            System.err.println("Oh, an IO error happened...");
        }
        return map;
    }

    /**
     * A method used to process through a folder containing GeoJSON files
     *
     * @param directoryPath Unsurprisingly, the directory path, this path is actually used as a root folder
     * @return Returns a list of lists, with said lists holding location mappings.
     */
    static Map<String, List<GeoPosition>> processStateGeoJsonFolder(String statePath) {
        return processGeoJsonFolder(statePath);
    }



    /**
     * Makes a List of Painters
     *
     * @param locations Pretty much like, the state + the country you live
     * @return Returns the list of painters
     */
    static List<PolygonalPainter> createNewPainters(Map<String, List<GeoPosition>> locationsMap) {
        List<PolygonalPainter> painters = new ArrayList<>();
        for (Map.Entry<String, List<GeoPosition>> entry : locationsMap.entrySet()) {
            String cityName = entry.getKey();
            List<GeoPosition> coords = entry.getValue();
            PolygonalPainter painter = new PolygonalPainter(coords, Color.red, Color.red, cityName);
            painters.add(painter);
        }
        return painters;
    }

    /**
     * Creates a list of lists, with each list having painters
     *
     * @param locations
     * @return
     */
    static List<PolygonalPainter> createListOfPainters(Map<String, List<GeoPosition>> locationsMap) {
        return createNewPainters(locationsMap);
    }

    /**
     * Effectively a method to easily make 1 compound painter, and not have thousands of lines
     *
     * @param polygonPainters Pretty much your list of polygon painters
     * @param renderer        Pretty much just the button waypoint renderer
     * @return Returns a new {@link CompoundPainter}
     */
    static CompoundPainter<JXMapViewer> createCompoundPainter(List<List<PolygonalPainter>> polygonPainters) {
        List<AbstractPainter<JXMapViewer>> painters = new ArrayList<>();
        for (List<PolygonalPainter> painter : polygonPainters) {
            painters.addAll(painter);
        }
        return new CompoundPainter<JXMapViewer>(painters);
    }

    /**
     * Parses JSON files so that way I don't have to get an API key that complicates things.
     *
     * @param file
     * @return Returns a {@link ButtonWaypoint}
     * @throws FileNotFoundException In the event that the file this is looking for does not exist, it calls {@link System#exit(int)}
     */
    static ButtonWaypoint parseJsonFile(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        String jsonString = scanner.nextLine();
        ButtonWaypoint waypoint = null;
        Gson gson = new Gson();
        try {
            JSON json = gson.fromJson(jsonString, JSON.class);
            if (json != null) {
                int teamNumber = json.getTeamNumber();
                String name = json.getTeamName();
                List<Double> coordinates = json.getCoordinates();
                //System.out.println(coordinates);
                GeoPosition geoPosition = new GeoPosition(coordinates.get(0), coordinates.get(1));
                waypoint = new ButtonWaypoint(name, geoPosition, teamNumber);
            }
        } catch (Exception e) {
            System.err.println("Welp, some error happened");
            System.exit(-1);
        }
        return waypoint;
    }

    /**
     * Processes an entire folder worth of JSON files, basically the state
     *
     * @param directoryPath The path to the folder
     * @return Returns a list of {@link ButtonWaypoint Button Waypoints}
     */
    static List<ButtonWaypoint> processJsonFolder(String directoryPath) {
        Path startPath = Paths.get(directoryPath);
        List<ButtonWaypoint> list = new ArrayList<>();

        try (Stream<Path> paths = Files.walk(startPath)) {
            paths.filter(Files::isRegularFile).filter(path -> path.toString().endsWith(".json")).forEach(path -> {
                try {
                    ButtonWaypoint waypoint = parseJsonFile(path.toFile());
                    list.add(waypoint);
                } catch (FileNotFoundException e) {
                    System.err.println("Oh no 😢");
                }
            });
        } catch (IOException e) {
            System.err.println("Oh no 😢");
        }
        return list;
    }

    /**
     * Thing that iterates through states, or in short, basically it will automatically make any new state folder work.
     *
     * @param directoryPath Pretty much the directory path, usually just called your country's name, acts as a root folder
     * @return Returns a list of lists of type {@link ButtonWaypoint}
     */
    static List<List<ButtonWaypoint>> processStateJsonFolder(String directoryPath) {
        List<List<ButtonWaypoint>> list = new ArrayList<>();
        File rootFolder = new File(directoryPath);

        if (!rootFolder.exists() || !rootFolder.isDirectory()) {
            System.err.println("Oh, the directory path is invalid.");
            return list;
        }

        File[] folders = rootFolder.listFiles(File::isDirectory);


        if (folders != null) {
            for (File subfolder : folders) {
                String subfolderPath = subfolder.getAbsolutePath();

                List<ButtonWaypoint> pointsFromSubfolder = processJsonFolder(subfolderPath);
                if (pointsFromSubfolder != null) {
                    list.add(pointsFromSubfolder);
                }
            }
        }
        return list;
    }

    /**
     * Processes the entire frcData folder to get all teams
     * @return Returns a list os lists of type {@link ButtonWaypoint}, despite the fact that it initially starts out as a List of Lists of Lists of type {@link ButtonWaypoint}
     */
    static List<List<ButtonWaypoint>> processfrcDataFolder(){
        List<List<List<ButtonWaypoint>>> list = new ArrayList<>();
        File rootFolder = new  File("src/main/frcData");
        if (!rootFolder.exists() || !rootFolder.isDirectory()) {
            System.err.println("Oh, the directory path is invalid.");
            return null;
        }
        File[] folders = rootFolder.listFiles(File::isDirectory);

        if (folders != null) {
            for (File subfolder : folders) {
                List<List<ButtonWaypoint>> pointsFromSubfolder = processStateJsonFolder(subfolder.getAbsolutePath());
                if (pointsFromSubfolder != null) {
                    list.add(pointsFromSubfolder);
                }
            }
        }
        List<List<ButtonWaypoint>> flatterList = list.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
        return flatterList;
    }

    /**
     *Processes all data found within the folder called geojsons, well, all except the test geojson
     * @return Returns what is essentially a list of lists of lists of {@link GeoPosition GeoPositions}, so that way all cities work here
     */
    static Map<String, List<GeoPosition>> processGeojsonsFolder(String rootPath) {
        Map<String, List<GeoPosition>> masterMap = new HashMap<>();
        File rootFolder = new File(rootPath);
        for (File stateDir : rootFolder.listFiles(File::isDirectory)) {
            masterMap.putAll(processStateGeoJsonFolder(stateDir.getAbsolutePath()));
        }
        return masterMap;
    }

    /**
     * A method for parsing the custom file type of EJson, which is effectively Json with an E in the front
     * @param file used to look for a specific EJSON file
     * @return Returns the waypoint
     * @throws FileNotFoundException Throws an error in the event that a specific file isn't found
     */
    static ButtonWaypointAlt parseEJsonFile(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        String ejsonString = scanner.nextLine();
        ButtonWaypointAlt waypoint = null;
        Gson gson = new Gson();
        try {
            EJSON ejson = gson.fromJson(ejsonString, EJSON.class);
            if (ejson != null) {
                String reasonForImportance = ejson.getReasonForImportance();
                List<Double> coordinates = ejson.getCoordinates();
                //System.out.println(coordinates);
                GeoPosition geoPosition = new GeoPosition(coordinates.get(0), coordinates.get(1));
                waypoint = new ButtonWaypointAlt(reasonForImportance, geoPosition, new URI("https://www.firstinspires.org/programs/first-championship"), "src/main/specialPlaces/worlds.png");
            }
        } catch (Exception e) {
            System.err.println("Welp, some error happened");
            System.exit(-1);
        }
        return waypoint;
    }


}