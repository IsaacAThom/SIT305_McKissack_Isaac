# Lost and Found App
## Created for SIT305 Task 9.1P
### By Isaac McKissack - 218250292

[Demonstration Video](https://youtu.be/h82AG-RY0vQ)

Database content handled in AppDatabase, EventDao, and EventEntity. Displayed in EventDisplayFragment, using EventViewModel. RecyclerViewInterface used to pass functions to the EventViewHolder so that it could correctly detect the clicking of buttons within recyclerview.

Converters used to convert Date into a Long format for use in Room DB.

Array used by the spinner in the LostFoundListFragment and NewAdvertFragment stored under 
advert_category_array, and the conversion of results to String for use in the Room DB is 
currently hardcoded, so adding new categories will require manual correction in these places.

Currently, navigation is only possible by backtracking, with no central navigation bar. This can 
cause problems after advert deletion, as the LostFoundListFragment will be on the backstack 
twice or more. Either restart the app or keep hitting back to return to the main screen.

API key stored in hidden file api_key_storage

Relevant reference sources for the Map Interface, Permissions, and CurrentLocation finder 
functionalities
[Google Documentation on Place IDs](https://developers.google.com/maps/documentation/places/android-sdk/place-id)

[Video on fetching Current Location, used to learn fusedLocationClient](www.youtube.com/watch?v=mwzKYIB9cQs)

[Google Tutorial on selecting Current Place](https://developers.google.com/maps/documentation/android-sdk/current-place-tutorial)

[Tutorial for calculating distances between points - used for Haversine Formula](https://mapsplatform.google.com/resources/blog/how-calculate-distances-map-maps-javascript-api/)

[Tutorial for finding nearby Places, used to help learn map setup](https://simpledevcode.wordpress.com/2023/07/18/getting-nearby-locations-using-google-maps-api-with-android/)

