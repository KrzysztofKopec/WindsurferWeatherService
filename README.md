"# WindsurferWeatherService" 

The application for the given date in the format "yyyy-mm-dd", returns the best places to surf for 5 sample locations, example: 
GET "http://localhost:8080/date?date=2022-05-28"
You can also add new coordinates for new locations, Latitude and Longitude, example: 
POST "http://localhost:8080/coordinates?lat=10&lon=10", 
and view all coordinates, example: 
GET "http://localhost:8080/coordinates",
and delete coordniates by Id, example: 
DELETE "http://localhost:8080/coordinates?coordinatesId=1". 
If the weather conditions for a location are not suitable, this location will not be shown
