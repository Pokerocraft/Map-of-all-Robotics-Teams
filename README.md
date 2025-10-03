# Open Source Map Project


Basically, this project is meant to be a map of all teams in FIRST Robotics Competition (Or FRC) based on cities, the data from most teams will be pulled from The Blue Alliance or the FIRST Robotics website. 

# Adding Cities to the Map
If you want to add cities to the map, just grab the ID, the thing under Relation from OpenStreetMap, and put the ID into https://polygons.openstreetmap.fr/get_geojson.py?id= . Then save the result as city.geojson, replacing city with the actual city name. Then move it over to the geojsons file, within your country and state (If Applicable)


Please don't add cities with thousands of points like Houston Texas, it lags the window a lot, even by itself
