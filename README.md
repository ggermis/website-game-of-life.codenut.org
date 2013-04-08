game-of-life
============

Background information: http://en.wikipedia.org/wiki/Conway's_Game_of_Life

game
----
- Played on a grid of cells (= world). A cell is either 'dead' or 'alive'
- An initial pattern (seed) is provided at the start of the game
- Birth and death occur simultaneously at each 'tick'

rules
-----
- Any live cell with fewer than two live neighbours dies, as if caused by under-population.
- Any live cell with two or three live neighbours lives on to the next generation.
- Any live cell with more than three live neighbours dies, as if by overcrowding.
- Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.