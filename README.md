# MinesWeeper

This Was A project for JetBrains Academy Course 
mainly it was devided into four Stages As you can see ,But you can find all the code in the task folder 

This is a MinesWeeper game that is based on the following logic :
first laying down an Array called the primaryArray Which has all the Contents of the minesField 
In this stage 
1- The '.' represints the empty cell
2- The 'X' represints a Mine 
3- Numbers represints the number of surrounding Mines  
The primarryArray is a CharArray which is mainly laied by a funtction which is called printingDots() which just fills the array with dots to represint empty cells to prepare it to the following stage which is placing Mines which is done By the Placing Mines Array 
The placingMines Function places A Given Number of Mines By the user Randomly in the primaryArray
Then we have the LookArround Function in the class LookingArroung wich places the hints 
------------------------------------------------------------------------------------------------- 
Stage 2 
in this Stage we hide all the contents of the primaryArray and Shows Them with the user/player Interacts 
Inn this stage 
The '.' represints the undescvered cells 
The '/' represints the empthy desvoverd cells 
The '*' represints the cells marked as Containing Mines
The Numbers represints the surrounding number of Mines 

In This Stage Wherever the user clicks first time it has to be an empty and then this empty Cell reveals the besides empty cells 
For the empty cells revealing I used the flood fill Algorithm You can find it within the function floodFill(x, y) 
