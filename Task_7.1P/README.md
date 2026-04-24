# Lost and Found App
## Created for SIT305 Task 7.1P
### By Isaac McKissack - 218250292

[Video Demonstration Link](https://youtu.be/UvttKtYstrA)

Database content handled in AppDatabase, EventDao, and EventEntity. Displayed in EventDisplayFragment, using EventViewModel. RecyclerViewInterface used to pass functions to the EventViewHolder so that it could correctly detect the clicking of buttons within recyclerview.

Converters used to convert Date into a Long format for use in Room DB.

Array used by the spinner in the LostFoundListFragment and NewAdvertFragment stored under 
advert_category_array, and the conversion of results to String for use in the Room DB is 
currently hardcoded, so adding new categories will require manual correction in these places.

Currently navigation is only possible by backtracking, with no central navigation bar. This can 
cause problems after advert deletion, as the LostFoundListFragment will be on the backstack 
twice or more. Either restart the app or keep hitting back to return to the main screen.