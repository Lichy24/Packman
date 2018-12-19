# Packman
Packman eat fruits in the shortest path

## Development Status: in progress 
#### Software objectives:
- ~~**Graphical user interface (GUI) of MyFrame**~~ [COMPLETED]
	- ~~Load and Save CSV game file into Game class object.~~ [COMPLETED]
	- ~~Add new game objects with mouse click.~~ [COMPLETED]
-  ~~Game objects such as pacman and fruit~~ [COMPLETED]
- "**Map**" class:
	- ~~Store map range~~ [COMPLETED]
	- ~~Calculate distance between two pixelXY points in pixels.~~[COMPLETED]
	 ~~Calculate distance in meters between two GPS points or two pixelXY points by converting them first to GPS - like points.~~ [COMPLETED]
	- Convert from geodetic coordinates to pixel XY.
- "**Path**" class.
- "**Path2Kml**" class.
-	"**ShortestPathAlgo**" class.

#### General objectives:
- Write for each function and class a Javadoc description.
- Generate Javadoc folder.
- Edit readme.md file to have HOW-TO-USE toturial and software description.

## Contributors:
- Ofek Bader:
	-   -> https://github.com/neyney10
- Adi Lichy:
	-   -> https://github.com/Lichy24

# What is it?
This is a Multi-pacman game simulation over Google Earth! <br>
You can form this repisoritory and add new GameObjects by yourself!

# How to play the game?
## Game goals and notes
the goal in this game is to let the pacmans play in a group, and their group goal is to eat all fruits on game with the shortest time possible! isn't it exciting?
Of course the game is a simulation so they figure their routes by themselves but it's cool to see them chasing those little fruits!

## How to actually play?
- <u>Step 1:</u> As was written earlier, this is a simulation game! <br>
you choose an object from the GameObject menu, let it be pacman or fruit, and once you click an item you'll enter to a "drop mode" which allows you to click anywhere on the map to place your chosen item!  <br>

- <u>Step 2:</u> As your'e having fun building your own Pacman simulation, you click the "compute" button in the toolbar to let the pacmans figure out their paths! <br>

- <u>Step 3:</u> finally, you click the "Run" button to start the simulation and see those cute-furry-yellow-alien creatures running around! <br>

- <u>Step 4:</u> after you had a great time watching the pacmans playing the game of their lives, you can export your file into a KML and start watching those pacmans running over the Real world on Google Earth!
# How to save a game into a CSV file
# How to load a saved CSV game file
# How to export the game into a KML file
# How to load a KML file into Google Earth
# Game's Shortest Path Finding Algorithm
The algorithm is a forked Dijkstra algorithm, not famillar with Dijkstra? look up here:<br>
https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm <br>
Short summary of the algorithm: <br>
1. Calculate the Cost of each pacman for every fruit (Cost is the time that takes that pacman to eat that fruit) and store it in MinHeap.
2. While the MinHeap cost isn't empty.
- 2.1. Get the minimum Cost of all options in the heap and remove it (add it to the Path's of the pacman).
- 2.2. "Relax" all other Edges/Lines of all OTHER pacmans which isn't in the line we just got from the MinHeap by the time took the pacman to eat the fruit.


# Game's Code Library Structure


