# Open Source Map Project


Basically, this project is meant to be a map of all teams in FIRST Robotics Competition (Or FRC) based on cities, the data from most teams will be pulled from The Blue Alliance or the FIRST Robotics website. 

# Adding Cities to the Map
If you want to add cities to the map, just grab the ID, the thing under Relation from OpenStreetMap, and put the ID into https://polygons.openstreetmap.fr/get_geojson.py?id= . Then save the result as city.geojson, replacing city with the actual city name. Then move it over to the geojsons file, within your country and state (If Applicable)

If you want to add places with thousands of points, such as Houston Texas, you'll want to simplify it using https://mapshaper.org/. Then replace anywhere where there is 3 square brackets with 1 square bracket, then it should work with my code, if you don't simplify the points, the map gets very laggy.

# Adding Team Buttons to the Map
If you want to add teams to the map, make a JSON file like this: ```{"teamNumber": 1234, "teamName":  "Some Example", "coordinates": [0, 0]}``` and place it into whatever folder makes sense for where your team is located.
