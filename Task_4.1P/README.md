# Personal Event Planner App
## Created for SIT305 Task 4.1P
### By Isaac McKissack - 218250292

Database content handled in AppDatabase, EventDao, and EventEntity.
Displayed in EventDisplayFragment, using EventViewModel.
RecyclerViewInterface used to pass functions to the EventViewHolder so that it could correctly 
detect the clicking of buttons within recyclerview.

Converters used to convert Date into a Long format for use in Room DB.

I have to acknowledge this [tutorial](developer.android.com/codelabs/android-room-with-a-view) for providing a base that I actually understood and could 
alter to suit the needs of the project. There were innumerable others I referred to for 
individual components, but this one was the most influential once I actually got it to work!

This was a nightmare to make and I'm so sorry for how this is. I hope I haven't missed anything 
major!