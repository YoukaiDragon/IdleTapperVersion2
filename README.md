#Idle Tapper Version 2

Idle Tapper Version 2 is an upgraded version of Idle Tapper from coding assignment 1, updated to meet the requirements of the second coding assignment.
It is a simple incremental gain where the user gains 'Taps' by repeatedly tapping a button, and using those taps to purchase upgrades that increase
the amount of taps gained from the button, as well as the amount gained passively each second
This version uses a second intent containing multiple buttons for upgrading manual and idle taps by differing amounts, implemented using a recyclerview 
to make it easy to add new buttons and change the parameters for each kind of upgrade

The app uses two activities: MainActivity, which has a large button for manually generating taps, and StoreActivity: containing a list of upgrade buttons.
Navigation between the two activities is done via a button on the bottom of the screen
Files used for the recyclerview include Datasource.kt, list_item.xml, and StoreButton.kt

There are no open issues with the app, though due to time issues I was unable to attempt my initial plan of using fragments on the store page to allow the user
to toggle between viewing manual tap upgrades and idle tap upgrades
